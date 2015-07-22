package com.ll.vmsystem.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.CarDAO;
import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.dao.FreightDAO;
import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.ProviderDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.DriverService;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.utilities.JSONUtils;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.FreightBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.ProviderBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description: DriverService实现类 处理司机一些消息状态。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class DriverServiceImpl implements DriverService {

	/**
	 * 门卫获得消息2，即车辆批准出库
	 */
	private final static int GUARD_MESSAGE2 = 0x00000100;

	/**
	 * 司机未开始工作。
	 */
	private static final int OFF = -1;

	/**
	 * 司机 已有订单发布，但并未确认运单。 这里是订单已经被管理员确认后，才提示有运单.
	 * 确认订单等待服务器响应，在success后跳转到导航界面并开启导航。
	 */
	private static final int FREIGHT_ESTABLISHED = 0;
	/**
	 * 司机确认订单，车辆位于前往仓库的道路上
	 */
	private static final int OUT_STORE = 1;
	/**
	 * 车辆在大门处等待。
	 */
	private static final int WATING_GATE = 2;

	/**
	 * 前往卸货点
	 */
	private static final int BEFORE = 3;
	/**
	 * 卸货中
	 */
	private static final int DISCHARGING = 4;
	/**
	 * 卸货结束，出门状态中
	 */
	private static final int AFTER = 5;

	@Override
	public JSONArray getDriverInfo(String id, String driverState) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		int Id = Integer.parseInt(id);
		int state = Integer.parseInt(driverState);
		try {
			conn = JDBCUtils.getConnection();

			// 基础的自身信息
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(Id);
			json = new JSONObject();
			JSONUtils.putDriverBean(json, db);
			jarr.put(json);

			// 供应商信息
			ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
			pDAO.setConn(conn);
			ProviderBean pb = pDAO.get(db.getProviderid());
			json = new JSONObject();
			JSONUtils.putProivderBean(json, pb);
			jarr.put(json);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = null;
			CarBean cb = null;
			GateBean ingb = null;
			StopBean sb = null;
			GateBean outgb = null;
			// 还是先来判断当前driver的state状态吧。。。
			if (db.getFreightid() == 0) {

				state = OFF;
			} else {

				if (db.getCarid() == 0) {
					state = FREIGHT_ESTABLISHED;
				} else {
					state = OUT_STORE;

				}
				// throw new Exception("freightid ==  "
				// + db.getFreightid().toString());
			}

			if (state > OFF) {
				// 运单信息
				fb = fDAO.get(db.getFreightid());

				if (state > FREIGHT_ESTABLISHED) {
					// 这里有car。并获取详细信息
					cb = ServiceFactory.getMapService().getDetailCarInfo(conn,
							db.getCarid());
					if (cb.getState().equals(
							EnumUtils.CarState.STARTED.toString())) {
						state = OUT_STORE;
					} else if (cb.getState().equals(
							EnumUtils.CarState.WAITING.toString())) {
						state = WATING_GATE;
					} else if (cb.getState().equals(
							EnumUtils.CarState.BEFORE.toString())) {
						state = BEFORE;
					} else if (cb.getState().equals(
							EnumUtils.CarState.DISCHARGING.toString())) {
						state = DISCHARGING;
					} else if (cb.getState().equals(
							EnumUtils.CarState.AFTER.toString())) {
						state = AFTER;
					} else {
						// 这时已经结束，不可能到达这里，即无状态6.
						state = 6;
					}

					if (state < BEFORE) {
						// 这里有 入 gate 的信息
						GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
						gDAO.setConn(conn);
						ingb = gDAO.get(ServiceFactory.getMapService()
								.getInGate());

					}
					if (state < AFTER) {
						// 这里有卸货点信息
						StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
						sDAO.setConn(conn);
						sb = sDAO.get(db.getStopid());

					}
					if (state > WATING_GATE) {
						// 这里有 出 gate 的信息
						GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
						gDAO.setConn(conn);
						outgb = gDAO.get(ServiceFactory.getMapService()
								.getOutGate());

					}

				}

			}
			json = new JSONObject();
			if (fb != null) {
				JSONUtils.putFreightBean(json, fb);
			}
			jarr.put(json);
			json = new JSONObject();
			if (cb != null) {
				JSONUtils.putCarBean(json, cb);
			}
			jarr.put(json);
			json = new JSONObject();
			if (ingb != null) {
				JSONUtils.putGateBean(json, ingb);
			}
			jarr.put(json);
			json = new JSONObject();
			if (sb != null) {
				JSONUtils.putStopBean(json, sb);
			}
			jarr.put(json);
			json = new JSONObject();
			if (outgb != null) {
				JSONUtils.putGateBean(json, outgb);
			}
			jarr.put(json);
			//
			// // 这里关于将json放入jsonarray中的顺序问题，如果每个type都有其
			// // 每个state都可以自行向上传递一层，登录后客户端自行检测一次，更新自己的state
			// if (state == -1) {
			// // 当状态为-1时，即司机当前未有订单，则需要时刻检查其状态是否发生改变。
			// if (db.getFreightid() != null) {
			// // 但用户一不小心退出程序时，再回到程序中时，需要从服务器中获取当前的状态,但是，这时候DriverBean中有其具体数据.
			// state ++;
			//
			// }
			// // else {
			// // page = fDAO.searchByInfo(1,
			// // " state != 'FINISHED' and driverid = " + id);
			// // if (page.listData.size() == 0) {
			// // // 如果列表为空，则表示当前无该司机的运单。
			// //
			// // } else {
			// // // 这里，当供货商上传的运单被管理员审核后，会调用方法同时修改drvierbean，使其FreightId指向
			// // //响应的运单，和填写stopbean信息。同时设置司机的状态为 working,还缺少一个carid
			// // //当之后司机创建车辆后，赋予车辆id，并修改Freight状态。
			// //
			// //
			// // }
			// // }
			// }
			// // 这里给一一分开，开一
			// if (state == 0) {
			// // 当状态为 0 ，即 已提交订单，且指向该司机，但该司机并未确认订单并指定车辆
			// //运单信息
			// fb = fDAO.get(db.getFreightid());
			// json = new JSONObject();
			// JSONUtils.putFreightBean(json, fb);
			// jarr.put(json);
			//
			// //获得gate信息，这里要调用mapservice来获得一个大门，暂时功能无。
			// GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			// gDAO.setConn(conn);
			// GateBean gb =
			// gDAO.get(ServiceFactory.getMapService().getInGate());
			// json = new JSONObject();
			// JSONUtils.putGateBean(json, gb);
			// jarr.put(json);
			//
			// //获取stop信息返回
			// StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			// sDAO.setConn(conn);
			// StopBean sb = sDAO.get(db.getStopid());
			// json = new JSONObject();
			// JSONUtils.putStopBean(json, sb);
			// jarr.put(json);
			//
			//
			//
			// //指定车辆的state状态提升由谁来实现？若我要实现功能聚集，则应该将所有修改state的方法全部放在
			// //服务器端，但是这里这个状态更新，只有当司机确认时，才会更新，但是当司机确认时，依然要来到这个函数
			// //这个位置来获取数据，且对于从断点进来的driver要继续向上获得更多的数据，所以依然要放在服务器段
			// if(db.getCarid() != null){
			// //继续向上
			// state ++;
			// }
			// }
			// if(state == 1){
			// //这里添加了
			//
			// }

			json = new JSONObject();
			json.put("driverState", state);
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
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
	public JSONObject sumbitArrivedOutGate(String carid, String gateid) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有多次操作，则使用事物进行处理。
			conn.setAutoCommit(false);

			// 这里设置car的状态为OFF，因为我之前在数据库设置中有这一个状态，即以此状态作为在大门处等待的最后状态。且位置更新为
			// 大门处坐标。
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);

			CarBean cb = cDAO.get(Integer.parseInt(carid));
			GateBean gb = gDAO.get(Integer.parseInt(gateid));
			cb.setLongitude(gb.getLongitude());
			cb.setLatitude(gb.getLatitude());
			cb.setLastltime(new Timestamp(System.currentTimeMillis()));
			cb.setX(gb.getX());
			cb.setY(gb.getY());
			// 用来确定到达出口大门处。
			cb.setState("OFF");
			cDAO.updateSite(cb);
			// 当前等待车辆数加1,这个时候，destination是指向出门的。
			gDAO.carNumChange(true, cb.getDestination());

			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			mDAO.addMessage(gb.getGuardid(),
					EnumUtils.AppRole.GUARD.toString(), GUARD_MESSAGE2);

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
				// json = new JSONObject();
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
