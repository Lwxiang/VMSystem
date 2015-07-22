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
import com.ll.vmsystem.dao.ProviderDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
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
 * Description:MapServiceImpl maoservice��ʵ�� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class MapServiceImpl implements MapService {

	/**
	 * ���õ���԰�����ŵľ����趨��������������ڣ�����Ϊ�Ѿ�������Ǵ��µĵ���԰�����жϡ����Ծ��Ƚϵ͡�
	 * �����趨Ϊ200��������ֱ�ӿ���ģ�⵽����Ŵ���
	 */
	private static final int ARRIVED_DISTANCE = 30000;

	@Override
	public JSONArray getAllMapinfo() {
		JSONArray jarr = new JSONArray();
		JSONArray jArray = null;
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// ���ȫ��GateBean
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			List<GateBean> gblist = gDAO.getAll();
			jArray = new JSONArray();
			for (GateBean gb : gblist) {
				json = new JSONObject();
				JSONUtils.putGateBean(json, gb);
				jArray.put(json);
			}
			jarr.put(jArray);

			// �������LineBean
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			List<LineBean> lbList = lDAO.getall();
			jArray = new JSONArray();
			for (LineBean lb : lbList) {
				json = new JSONObject();
				JSONUtils.putLineBean(json, lb);
				jArray.put(json);
			}
			jarr.put(jArray);

			// �������StopBean
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			List<StopBean> sbList = sDAO.getAll();
			jArray = new JSONArray();
			for (StopBean sb : sbList) {
				json = new JSONObject();
				JSONUtils.putStopBean(json, sb);
				jArray.put(json);
			}
			jarr.put(jArray);

			// ����ȫ���Ŀ��ڳ�����Ϣ��
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			List<CarBean> cbList = cDAO.getAll();
			jArray = new JSONArray();
			int outcarnum = 0;
			for (CarBean cb : cbList) {
				if (EnumUtils.CarState.STARTED.toString().equals(cb.getState())
						|| EnumUtils.CarState.WAITING.toString().equals(
								cb.getState())) {
					// ������״̬Ϊ�����ڿ��⡣
					outcarnum++;
				} else {
					// ֻ���ص�ǰ���еĳ�����
					json = new JSONObject();
					cb = getDetailCarInfo(conn, cb.getId());
					JSONUtils.putCarBean(json, cb);
					jArray.put(json);
				}
			}
			jarr.put(jArray);

			json = new JSONObject();
			json.put("outcarnum", outcarnum);
		} catch (Exception e) {
			try {
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public int getInGate() {
		// ֮������ṩ����ѡ�񷽷�������ʱֻ�ܶ��Ž������ų�
		return 1;
	}

	@Override
	public int getOutGate() {
		// ֮������ṩ����ѡ�񷽷�������ʱֻ�ܶ��Ž������ų�
		return 3;
	}

	/**
	 * ��ʼ�㾭��
	 */
	private static final double startLongitude = 114.111108;
	/**
	 * ��ʼ��γ��
	 */
	private static final double startLatitude = 30.462222;
	/**
	 * ÿ���ص�ľ��Ȳ
	 */
	private static final double longPerPx = 0.00000338;
	/**
	 * ÿ���ص��γ�Ȳ�
	 */
	private static final double latiPerPx = 0.00000306;
	private final static int MAP_LENGTH = 2600;
	private final static int MAP_HEIGHT = 2760;

	/**
	 * ��ͼ��ת������������γ��ת��Ϊ�����
	 * 
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	private int changeLongitudeToX(double longitude) throws Exception {
		int x = 0;
		x = (int) ((longitude - startLongitude) / longPerPx);
		if (x < 0 || x > MAP_LENGTH) {
			// ����Ϊ��ͼ���ֵ��֮���Ϊ�����ĵ�ͼ��Χ
			throw new Exception("���곬����ͼ��Χ");
		}
		return x;
	}

	/**
	 * ��ͼ��ת������������γ��ת��Ϊ�����
	 * 
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	private int changeLatitudeToy(double latitude) throws Exception {
		int y = 0;
		y = (int) (int) ((startLatitude - latitude) / latiPerPx);
		if (y < 0 || y > MAP_HEIGHT) {
			// ����Ϊ��ͼ���ֵ��֮���Ϊ�����ĵ�ͼ��Χ
			throw new Exception("���곬����ͼ��Χ");
		}
		return y;
	}

	@Override
	public int getDistance(double longitude, double latitude, LineBean lb)
			throws Exception {
		// line���������㣬�ͻ��������˳�����������ϵ����û��Ҫ���������Ĵ���˼·
		// �����Ծ�γ��Ϊ����ģ���Ȼ��a��b����ȷ�ģ������c�ȿ��Խ���ʹ�á�

		int distance = 0;
		// ����...

		// ����������׽����
		double vx1 = lb.getB_x() - lb.getA_x();
		double vy1 = lb.getB_y() - lb.getA_y();
		double lx = changeLongitudeToX(longitude);
		double ly = changeLatitudeToy(latitude);
		double vx2 = lx - lb.getA_x();
		double vy2 = ly - lb.getA_y();
		double dis1 = Math.sqrt((lb.getB_x() - lb.getA_x())
				* (lb.getB_x() - lb.getA_x()) + (lb.getB_y() - lb.getA_y())
				* (lb.getB_y() - lb.getA_y()));
		double dis2 = Math.sqrt((lx - lb.getA_x()) * (lx - lb.getA_x())
				+ (ly - lb.getA_y()) * (ly - lb.getA_y()));
		// cos�н�
		double cos0 = (vx1 * vx2 + vy1 * vy2) / dis1 / dis2;
		// ���ջ���������
		double vx3 = vx2 * cos0;
		double vy3 = vy2 * cos0;
		// �������е�˵�ˣ��� ��ʹ��һ��car�������������������ʱ����Ҫ�Ľ�������������ľ��룬���������ģ������ﲻ�����д��
		distance = (int) (Math.sqrt(vx3 * vx3 + vy3 * vy3) * METER_PRE_PX);
		if (cos0 < 0) {
			// ������������ⲿservice�������б���
			distance = -distance;
		}
		return distance;
	}

	/**
	 * ���䲻��׼��ÿ���ص����ľ��롣
	 */
	private final static double METER_PRE_PX = 0.3;

	@Override
	public JSONArray getMapStopInfo(String id) {
		JSONArray jarr = new JSONArray();
		JSONArray jArray = null;
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// ���ȫ��GateBean
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			List<GateBean> gblist = gDAO.getAll();
			jArray = new JSONArray();
			for (GateBean gb : gblist) {
				json = new JSONObject();
				JSONUtils.putGateBean(json, gb);
				jArray.put(json);
			}
			jarr.put(jArray);

			// �������LineBean
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			List<LineBean> lbList = lDAO.getall();
			jArray = new JSONArray();
			for (LineBean lb : lbList) {
				json = new JSONObject();
				JSONUtils.putLineBean(json, lb);
				jArray.put(json);
			}
			jarr.put(jArray);

			// ���ָ����stop
			int stopId = Integer.parseInt(id);
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			List<StopBean> sbList = new ArrayList<StopBean>();
			sbList.add(sDAO.get(stopId));
			jArray = new JSONArray();
			for (StopBean sb : sbList) {
				json = new JSONObject();
				JSONUtils.putStopBean(json, sb);
				jArray.put(json);
			}
			jarr.put(jArray);

			json = new JSONObject();

		} catch (Exception e) {
			try {
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject getCarInfo(String id) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();

			CarBean cb = getDetailCarInfo(conn, Integer.parseInt(id));

			JSONUtils.putCarBean(json, cb);
		} catch (Exception e) {
			try {
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json.put("error", e.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;
	}

	@Override
	public CarBean getDetailCarInfo(Connection conn, int carid)
			throws Exception {
		FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
		fDAO.setConn(conn);
		DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
		dDAO.setConn(conn);
		CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
		cDAO.setConn(conn);
		StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
		sDAO.setConn(conn);
		ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
		pDAO.setConn(conn);

		CarBean cb = cDAO.get(carid);
		DriverBean db = dDAO.get(cb.getDriverid());
		cb.setDriverName(db.getName());
		cb.setProviderName(pDAO.get(db.getProviderid()).getName());
		FreightBean fb = fDAO.get(db.getFreightid());
		StopBean sb = sDAO.get(db.getStopid());
		cb.setStopname(sb.getName());
		cb.setStopstate(sb.getState());
		if (EnumUtils.CarState.WAITING.toString().equals(cb.getState())) {
			// �������
			// �ڵȴ�ʱ��ʹ�õȴ�ʱ�䣬����ʱ��Ϊ�˵���ʼ�����ڵ���ʱ��
			cb.setStarttime(fb.getArrivedTime());
		} else {
			// ���ݲ�ͬ�׶����ò�ͬ�Ŀ�ʼʱ�䡣
			cb.setStarttime(fb.getStartTime());
		}
		cb.setProirity(fb.getPriority());
		return cb;
	}

	@Override
	public boolean checkCarArrivded(CarBean cb) throws Exception {
		return cb.getDistance() < ARRIVED_DISTANCE;
	}

	@Override
	public JSONObject updateCarLineInfo(CarBean cb, String newlineid) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);

			int id = Integer.parseInt(newlineid);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			// ֮���line�ϳ�������һ��
			lDAO.carNumChange(true, id);
			// ֮ǰ��line�ϳ�������һ��
			lDAO.carNumChange(false, cb.getLineid());
			cb.setLineid(id);
			// ���³�����λ��Ϣ��
			cDAO.updateSite(cb);

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
				e1.printStackTrace();
			}
		} finally {
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject carArrivedStop(CarBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// �����ж�β�������ʹ��������д���
			conn.setAutoCommit(false);

			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();

			sDAO.setConn(conn);
			StopBean sb = sDAO.get(cb.getDestination());
			// if (EnumUtils.StopState.DISCHARGING.toString()
			// .equals(sb.getState())) {
			// throw new Exception("��ǰ�˴����г�������ж��������");
			// }
			sb.setState(EnumUtils.StopState.DISCHARGING.toString());
			sb.setCarid(cb.getId());
			// ����ж����״̬Ϊ����ж���С�
			sDAO.discharge(sb);

			MapService ms = ServiceFactory.getMapService();
			// �����µ�Ŀ��Ϊ�� �Ĵ��š�
			cb.setDestination(ms.getOutGate());
			// ���³�����λ��Ϣ��
			cDAO.updateSite(cb);

			conn.commit();
			json.put("destination", cb.getDestination());
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
				e1.printStackTrace();
			}
		} finally {
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject driverEndDischarge(String driverid) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// �����ж�β�������ʹ��������д���
			conn.setAutoCommit(false);

			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			DriverBean db = dDAO.get(Integer.parseInt(driverid));
			StopBean sb = sDAO.get(db.getStopid());
			// StopBean������caridΪ�գ�������״̬
			sb.setCarid(null);
			sb.setState(EnumUtils.StopState.FREE.toString());
			sDAO.discharge(sb);
			CarBean cb = cDAO.get(db.getCarid());
			// carBeanֻ����һ��״̬��
			cb.setState(EnumUtils.CarState.AFTER.toString());
			cDAO.updateSite(cb);
			// �˵���������Ӧ�÷����������������⴦��
			// FreightBean fb = fDAO.get(db.getFreightid());
			// // ���ö�����ɡ�
			// fb.setState(EnumUtils.freightState.FINISHED.toString());
			// fDAO.confirmFinish(fb);

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
				e1.printStackTrace();
			}
		} finally {
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}
}
