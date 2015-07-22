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
 * Description: DriverServiceʵ���� ����˾��һЩ��Ϣ״̬�� <br/>
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
	 * ���������Ϣ2����������׼����
	 */
	private final static int GUARD_MESSAGE2 = 0x00000100;

	/**
	 * ˾��δ��ʼ������
	 */
	private static final int OFF = -1;

	/**
	 * ˾�� ���ж�������������δȷ���˵��� �����Ƕ����Ѿ�������Աȷ�Ϻ󣬲���ʾ���˵�.
	 * ȷ�϶����ȴ���������Ӧ����success����ת���������沢����������
	 */
	private static final int FREIGHT_ESTABLISHED = 0;
	/**
	 * ˾��ȷ�϶���������λ��ǰ���ֿ�ĵ�·��
	 */
	private static final int OUT_STORE = 1;
	/**
	 * �����ڴ��Ŵ��ȴ���
	 */
	private static final int WATING_GATE = 2;

	/**
	 * ǰ��ж����
	 */
	private static final int BEFORE = 3;
	/**
	 * ж����
	 */
	private static final int DISCHARGING = 4;
	/**
	 * ж������������״̬��
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

			// ������������Ϣ
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(Id);
			json = new JSONObject();
			JSONUtils.putDriverBean(json, db);
			jarr.put(json);

			// ��Ӧ����Ϣ
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
			// ���������жϵ�ǰdriver��state״̬�ɡ�����
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
				// �˵���Ϣ
				fb = fDAO.get(db.getFreightid());

				if (state > FREIGHT_ESTABLISHED) {
					// ������car������ȡ��ϸ��Ϣ
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
						// ��ʱ�Ѿ������������ܵ����������״̬6.
						state = 6;
					}

					if (state < BEFORE) {
						// ������ �� gate ����Ϣ
						GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
						gDAO.setConn(conn);
						ingb = gDAO.get(ServiceFactory.getMapService()
								.getInGate());

					}
					if (state < AFTER) {
						// ������ж������Ϣ
						StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
						sDAO.setConn(conn);
						sb = sDAO.get(db.getStopid());

					}
					if (state > WATING_GATE) {
						// ������ �� gate ����Ϣ
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
			// // ������ڽ�json����jsonarray�е�˳�����⣬���ÿ��type������
			// // ÿ��state�������������ϴ���һ�㣬��¼��ͻ������м��һ�Σ������Լ���state
			// if (state == -1) {
			// // ��״̬Ϊ-1ʱ����˾����ǰδ�ж���������Ҫʱ�̼����״̬�Ƿ����ı䡣
			// if (db.getFreightid() != null) {
			// // ���û�һ��С���˳�����ʱ���ٻص�������ʱ����Ҫ�ӷ������л�ȡ��ǰ��״̬,���ǣ���ʱ��DriverBean�������������.
			// state ++;
			//
			// }
			// // else {
			// // page = fDAO.searchByInfo(1,
			// // " state != 'FINISHED' and driverid = " + id);
			// // if (page.listData.size() == 0) {
			// // // ����б�Ϊ�գ����ʾ��ǰ�޸�˾�����˵���
			// //
			// // } else {
			// // // ������������ϴ����˵�������Ա��˺󣬻���÷���ͬʱ�޸�drvierbean��ʹ��FreightIdָ��
			// // //��Ӧ���˵�������дstopbean��Ϣ��ͬʱ����˾����״̬Ϊ working,��ȱ��һ��carid
			// // //��֮��˾�����������󣬸��賵��id�����޸�Freight״̬��
			// //
			// //
			// // }
			// // }
			// }
			// // �����һһ�ֿ�����һ
			// if (state == 0) {
			// // ��״̬Ϊ 0 ���� ���ύ��������ָ���˾��������˾����δȷ�϶�����ָ������
			// //�˵���Ϣ
			// fb = fDAO.get(db.getFreightid());
			// json = new JSONObject();
			// JSONUtils.putFreightBean(json, fb);
			// jarr.put(json);
			//
			// //���gate��Ϣ������Ҫ����mapservice�����һ�����ţ���ʱ�����ޡ�
			// GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			// gDAO.setConn(conn);
			// GateBean gb =
			// gDAO.get(ServiceFactory.getMapService().getInGate());
			// json = new JSONObject();
			// JSONUtils.putGateBean(json, gb);
			// jarr.put(json);
			//
			// //��ȡstop��Ϣ����
			// StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			// sDAO.setConn(conn);
			// StopBean sb = sDAO.get(db.getStopid());
			// json = new JSONObject();
			// JSONUtils.putStopBean(json, sb);
			// jarr.put(json);
			//
			//
			//
			// //ָ��������state״̬������˭��ʵ�֣�����Ҫʵ�ֹ��ܾۼ�����Ӧ�ý������޸�state�ķ���ȫ������
			// //�������ˣ������������״̬���£�ֻ�е�˾��ȷ��ʱ���Ż���£����ǵ�˾��ȷ��ʱ����ȻҪ�����������
			// //���λ������ȡ���ݣ��Ҷ��ڴӶϵ������driverҪ�������ϻ�ø�������ݣ�������ȻҪ���ڷ�������
			// if(db.getCarid() != null){
			// //��������
			// state ++;
			// }
			// }
			// if(state == 1){
			// //���������
			//
			// }

			json = new JSONObject();
			json.put("driverState", state);
		} catch (Exception e) {
			try {
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
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
			// �����ж�β�������ʹ��������д���
			conn.setAutoCommit(false);

			// ��������car��״̬ΪOFF����Ϊ��֮ǰ�����ݿ�����������һ��״̬�����Դ�״̬��Ϊ�ڴ��Ŵ��ȴ������״̬����λ�ø���Ϊ
			// ���Ŵ����ꡣ
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
			// ����ȷ��������ڴ��Ŵ���
			cb.setState("OFF");
			cDAO.updateSite(cb);
			// ��ǰ�ȴ���������1,���ʱ��destination��ָ����ŵġ�
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
