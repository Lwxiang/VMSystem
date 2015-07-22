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
 * Description:MapServiceImpl maoservice的实现 <br/>
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
	 * 设置到达园区大门的距离设定，在这个距离以内，则认为已经到达，这是大致的到达园区的判断。所以精度较低。
	 * 现在设定为200，在寝室直接可以模拟到达大门处。
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
			// 获得全部GateBean
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

			// 获得所有LineBean
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

			// 获得所有StopBean
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

			// 加载全部的库内车辆信息。
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			List<CarBean> cbList = cDAO.getAll();
			jArray = new JSONArray();
			int outcarnum = 0;
			for (CarBean cb : cbList) {
				if (EnumUtils.CarState.STARTED.toString().equals(cb.getState())
						|| EnumUtils.CarState.WAITING.toString().equals(
								cb.getState())) {
					// 这俩个状态为车辆在库外。
					outcarnum++;
				} else {
					// 只加载当前库中的车辆。
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
				// 为了使流量更低，我决定放弃flag标记。
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
		// 之后可能提供智能选择方法，但暂时只能东门进，西门出
		return 1;
	}

	@Override
	public int getOutGate() {
		// 之后可能提供智能选择方法，但暂时只能东门进，西门出
		return 3;
	}

	/**
	 * 起始点经度
	 */
	private static final double startLongitude = 114.111108;
	/**
	 * 起始点纬度
	 */
	private static final double startLatitude = 30.462222;
	/**
	 * 每像素点的经度差。
	 */
	private static final double longPerPx = 0.00000338;
	/**
	 * 每像素点的纬度差
	 */
	private static final double latiPerPx = 0.00000306;
	private final static int MAP_LENGTH = 2600;
	private final static int MAP_HEIGHT = 2760;

	/**
	 * 地图有转换函数，将经纬度转换为坐标点
	 * 
	 * @param longitude
	 * @return
	 * @throws Exception
	 */
	private int changeLongitudeToX(double longitude) throws Exception {
		int x = 0;
		x = (int) ((longitude - startLongitude) / longPerPx);
		if (x < 0 || x > MAP_LENGTH) {
			// 这里为地图最大值，之后改为真正的地图范围
			throw new Exception("坐标超出地图范围");
		}
		return x;
	}

	/**
	 * 地图有转换函数，将经纬度转换为坐标点
	 * 
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	private int changeLatitudeToy(double latitude) throws Exception {
		int y = 0;
		y = (int) (int) ((startLatitude - latitude) / latiPerPx);
		if (y < 0 || y > MAP_HEIGHT) {
			// 这里为地图最大值，之后改为真正的地图范围
			throw new Exception("坐标超出地图范围");
		}
		return y;
	}

	@Override
	public int getDistance(double longitude, double latitude, LineBean lb)
			throws Exception {
		// line中有两个点，就基本可以退出真正的坐标系，但没必要，这里计算的大致思路
		// 放在以经纬度为坐标的，显然，a和b是正确的，计算出c既可以进行使用。

		int distance = 0;
		// 计算...

		// 用向量啊，捉鸡。
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
		// cos夹角
		double cos0 = (vx1 * vx2 + vy1 * vy2) / dis1 / dis2;
		// 最终获得向量结果
		double vx3 = vx2 * cos0;
		double vy3 = vy2 * cos0;
		// 这里又有的说了，当 我使用一个car的坐标用这个函数计算时，我要的结果就是其向量的距离，即带正负的，则这里不能如此写。
		distance = (int) (Math.sqrt(vx3 * vx3 + vy3 * vy3) * METER_PRE_PX);
		if (cos0 < 0) {
			// 这里带正负，外部service层再自行报错。
			distance = -distance;
		}
		return distance;
	}

	/**
	 * 极其不精准的每像素点代表的距离。
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
			// 获得全部GateBean
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

			// 获得所有LineBean
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

			// 获得指定的stop
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
				// 为了使流量更低，我决定放弃flag标记。
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
				// 为了使流量更低，我决定放弃flag标记。
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
			// 解决方案
			// 在等待时，使用等待时间，其他时候都为运单开始到现在的总时间
			cb.setStarttime(fb.getArrivedTime());
		} else {
			// 根据不同阶段设置不同的开始时间。
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
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);

			int id = Integer.parseInt(newlineid);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			// 之后的line上车辆数加一。
			lDAO.carNumChange(true, id);
			// 之前的line上车辆数减一。
			lDAO.carNumChange(false, cb.getLineid());
			cb.setLineid(id);
			// 更新车辆定位信息。
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
			// 事物结束，仍将其设置为非事物状态，然后释放。
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
			// 这里有多次操作，则使用事物进行处理。
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
			// throw new Exception("当前此处已有车辆正在卸货。。。");
			// }
			sb.setState(EnumUtils.StopState.DISCHARGING.toString());
			sb.setCarid(cb.getId());
			// 更新卸货点状态为正在卸货中。
			sDAO.discharge(sb);

			MapService ms = ServiceFactory.getMapService();
			// 设置新的目标为出 的大门。
			cb.setDestination(ms.getOutGate());
			// 更新车辆定位信息。
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
			// 事物结束，仍将其设置为非事物状态，然后释放。
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
			// 这里有多次操作，则使用事物进行处理。
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
			// StopBean中设置carid为空，并更新状态
			sb.setCarid(null);
			sb.setState(EnumUtils.StopState.FREE.toString());
			sDAO.discharge(sb);
			CarBean cb = cDAO.get(db.getCarid());
			// carBean只更新一个状态。
			cb.setState(EnumUtils.CarState.AFTER.toString());
			cDAO.updateSite(cb);
			// 运单结束还是应该放在门卫允许车辆出库处。
			// FreightBean fb = fDAO.get(db.getFreightid());
			// // 设置订单完成。
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
			// 事物结束，仍将其设置为非事物状态，然后释放。
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
