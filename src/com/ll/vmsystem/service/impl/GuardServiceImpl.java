package com.ll.vmsystem.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.CarDAO;
import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.dao.FreightDAO;
import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.GuardService;
import com.ll.vmsystem.service.MapService;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.utilities.JSONUtils;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.FreightBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.LineBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description: GuardServiceImpl实现类 门卫查询数据和批准车辆入库的service。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-04
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class GuardServiceImpl implements GuardService {

	/**
	 * 司机获取消息3，即车辆被批准入库。
	 */
	private final static int DRIVER_MESSAGE3 = 0x00010000;
	
	private final static int PROVIDER_MESSAGE2 = 0x00000100;

	/**
	 * 司机获取消息4，即车辆被批准出库。
	 */
	private final static int DRIVER_MESSAGE4 = 0x01000000;

	@Override
	public JSONArray getWaitingCarList(String guardId) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			int guardid = Integer.parseInt(guardId);
			int gateId = gDAO.getByGuard(guardid);
			if (0 == gateId) {
				throw new Exception("该门卫未在大门处工作，无响应数据。");
			}
			List<CarBean> list = cDAO.getCarAtGate(gateId);
			MapService map = ServiceFactory.getMapService();
			for (CarBean cb : list) {
				json = new JSONObject();
				// 获得详细信息。
				cb = map.getDetailCarInfo(conn, cb.getId());
				JSONUtils.putCarBean(json, cb);
				jarr.put(json);
			}
			json = new JSONObject();
			json.put("fff", "ffff");

		} catch (Exception e) {
			try {
				String str = e.toString() + " AT ";
				int i = 0;
				for (StackTraceElement elem : e.getStackTrace()) {
					str += elem.toString() + "  // ";
					if (i++ > 2) {
						break;
					}
				}
				json = new JSONObject();
				json.put("error", str);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		jarr.put(json);
		return jarr;

	}

	@Override
	public JSONObject acceptCarIn(String carId) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);

			// 批准车辆入库，使车辆获得第一次位置信息？还是由车辆自行获得第一次位置？
			// 最终决定，这里只更新车辆的状态，不修改车辆的位置信息，即不赋予其起始的位置和line信息。
			// 主要导航都在客户端进行，每当客户端检测自身到达新的位置时，才更新位置信息。
			// 其他时候，都是客户端独自在自行导航，所以第一次从停留处出发确定进入入库道路的起点范围内，即确定入库才发送消息。

			// 这里设置state 为BEFORE即卸货点前，以及更新目的地。
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			int id = Integer.parseInt(carId);
			CarBean cb = cDAO.get(id);
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);

			// 当前等待车辆数减1,这个时候，destination是指向入门的。
			gDAO.carNumChange(false, cb.getDestination());
			DriverBean db = dDAO.get(cb.getDriverid());
			GateBean gb = gDAO.get(cb.getDestination());
			// 初始门处++
			lDAO.carNumChange(true, gb.getLineid());
			cb.setLineid(gb.getLineid());
			cb.setLongitude(gb.getLongitude());
			cb.setLatitude(gb.getLatitude());
			cb.setX(gb.getX());
			cb.setY(gb.getY());
			cb.setDestination(db.getStopid());
			cb.setState(EnumUtils.CarState.BEFORE.toString());
			cDAO.updateSite(cb);

			// 同时设置目标卸货点状态为卸货中。
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			StopBean sb = sDAO.get(cb.getDestination());
			sb.setState(EnumUtils.StopState.DISCHARGING.toString());
			sb.setCarid(cb.getId());
			sDAO.discharge(sb);

			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			mDAO.addMessage(db.getId(), EnumUtils.AppRole.DRIVER.toString(),
					DRIVER_MESSAGE3);

			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				String str = e.toString() + " AT ";
				int i = 0;
				for (StackTraceElement elem : e.getStackTrace()) {
					str += elem.toString() + "  // ";
					if (i++ > 2) {
						break;
					}
				}
				json = new JSONObject();
				json.put("error", str);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// 事物结束，仍将其设置为非事物状态，然后释放。
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject acceptCarOut(String carId) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);

			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			int id = Integer.parseInt(carId);
			CarBean cb = cDAO.get(id);
			if (null == cb || !cb.getState().equals("OFF")) {
				throw new Exception("当前车辆不存在或不可批准通过。");
			}
			// LineBean lb = lDAO.get(cb.getLineid());
			// 清除车辆。
			lDAO.carNumChange(false, cb.getLineid());

			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			// 当前等待车辆数减1,这个时候，destination是指向出门的。
			gDAO.carNumChange(false, cb.getDestination());
			// GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			// gDAO.setConn(conn);
			// // 当前等待车辆数减1,这个时候，destination是指向入门的。
			// gDAO.carNumChange(false, cb.getDestination());
			DriverBean db = dDAO.get(cb.getDriverid());
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// 删除指定car。
			cDAO.delete(id);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(db.getFreightid());
			// 设置订单完成。
			fb.setState(EnumUtils.freightState.FINISHED.toString());
			fDAO.confirmFinish(fb);

			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			mDAO.addMessage(db.getId(), EnumUtils.AppRole.DRIVER.toString(),
					DRIVER_MESSAGE4);
			mDAO.addMessage(db.getProviderid(),
					EnumUtils.AppRole.PROVIDER.toString(), PROVIDER_MESSAGE2);

			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				String str = e.toString() + " AT ";
				int i = 0;
				for (StackTraceElement elem : e.getStackTrace()) {
					str += elem.toString() + "  // ";
					if (i++ > 2) {
						break;
					}
				}
				json = new JSONObject();
				json.put("error", str);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// 事物结束，仍将其设置为非事物状态，然后释放。
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

}
