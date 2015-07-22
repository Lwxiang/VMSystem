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
 * Description: GuardServiceImplʵ���� ������ѯ���ݺ���׼��������service�� <br/>
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
	 * ˾����ȡ��Ϣ3������������׼��⡣
	 */
	private final static int DRIVER_MESSAGE3 = 0x00010000;
	
	private final static int PROVIDER_MESSAGE2 = 0x00000100;

	/**
	 * ˾����ȡ��Ϣ4������������׼���⡣
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
				throw new Exception("������δ�ڴ��Ŵ�����������Ӧ���ݡ�");
			}
			List<CarBean> list = cDAO.getCarAtGate(gateId);
			MapService map = ServiceFactory.getMapService();
			for (CarBean cb : list) {
				json = new JSONObject();
				// �����ϸ��Ϣ��
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
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);

			// ��׼������⣬ʹ������õ�һ��λ����Ϣ�������ɳ������л�õ�һ��λ�ã�
			// ���վ���������ֻ���³�����״̬�����޸ĳ�����λ����Ϣ��������������ʼ��λ�ú�line��Ϣ��
			// ��Ҫ�������ڿͻ��˽��У�ÿ���ͻ��˼���������µ�λ��ʱ���Ÿ���λ����Ϣ��
			// ����ʱ�򣬶��ǿͻ��˶��������е��������Ե�һ�δ�ͣ��������ȷ����������·����㷶Χ�ڣ���ȷ�����ŷ�����Ϣ��

			// ��������state ΪBEFORE��ж����ǰ���Լ�����Ŀ�ĵء�
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

			// ��ǰ�ȴ���������1,���ʱ��destination��ָ�����ŵġ�
			gDAO.carNumChange(false, cb.getDestination());
			DriverBean db = dDAO.get(cb.getDriverid());
			GateBean gb = gDAO.get(cb.getDestination());
			// ��ʼ�Ŵ�++
			lDAO.carNumChange(true, gb.getLineid());
			cb.setLineid(gb.getLineid());
			cb.setLongitude(gb.getLongitude());
			cb.setLatitude(gb.getLatitude());
			cb.setX(gb.getX());
			cb.setY(gb.getY());
			cb.setDestination(db.getStopid());
			cb.setState(EnumUtils.CarState.BEFORE.toString());
			cDAO.updateSite(cb);

			// ͬʱ����Ŀ��ж����״̬Ϊж���С�
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
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
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
			// ���������β�������ʹ��������д���
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
				throw new Exception("��ǰ���������ڻ򲻿���׼ͨ����");
			}
			// LineBean lb = lDAO.get(cb.getLineid());
			// ���������
			lDAO.carNumChange(false, cb.getLineid());

			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			// ��ǰ�ȴ���������1,���ʱ��destination��ָ����ŵġ�
			gDAO.carNumChange(false, cb.getDestination());
			// GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			// gDAO.setConn(conn);
			// // ��ǰ�ȴ���������1,���ʱ��destination��ָ�����ŵġ�
			// gDAO.carNumChange(false, cb.getDestination());
			DriverBean db = dDAO.get(cb.getDriverid());
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// ɾ��ָ��car��
			cDAO.delete(id);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(db.getFreightid());
			// ���ö�����ɡ�
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
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
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
