package com.ll.vmsystem.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.CarDAO;
import com.ll.vmsystem.dao.CargoDAO;
import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.dao.FreightDAO;
import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.dao.ManagerDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.FreightService;
import com.ll.vmsystem.service.MapService;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.utilities.JSONUtils;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.CargoBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.FreightBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:
 * FreightServiceImpl，与Freight有关的操作的实现，所有我实现的Service都是不正确的，未检测状态是否可操作<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class FreightServiceImpl implements FreightService {

	/**
	 * 给司机发生消息1，消息1为司机当前有新的运单待确认执行。
	 */
	private final static int DRIVER_MESSAGE1 = 0x00000001;

	/**
	 * 给司机发生消息2，消息2为司机当前到达大门处，等待门卫批准入库。
	 */
	private final static int DRIVER_MESSAGE2 = 0x00000100;
	/**
	 * 给司机发生消息1，消息1为司机当前有新的运单待确认执行。
	 */
	private final static int DRIVER_MESSAGE3 = 0x00010000;

	/**
	 * 供应商的消息1，为有新的运单审核完成。
	 */
	private final static int PROVIDER_MESSAGE1 = 0x00000001;
	
	
	private final static int PROVIDER_MESSAGE2 = 0x00000100;

	/**
	 * 管理员的消息1，为新的运单提交待审核。
	 */
	private final static int MANAGER_MESSAGE1 = 0x01;

	/**
	 * 门卫的消息1，新的车辆到达，等待批准入库。
	 */
	private final static int GUARD_MESSAGE1 = 0x01;

	@Override
	public JSONArray searchFreight(String info, String pageno) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);

			PageList pl = fDAO.searchByInfo(Integer.parseInt(pageno), info);
			List<FreightBean> list = pl.listData;
			for (FreightBean fb : list) {
				json = new JSONObject();
				JSONUtils.putFreightBean(json, fb);
				jarr.put(json);
			}
			json = new JSONObject();
			json.put("pagenum", pl.PageNun);

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
	public JSONObject getFreight(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject confirmByMgr(FreightBean fb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 用事物进行处理。
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			if (db.getFreightid() != 0) {
				// 这里在从数据库中获取数据时，为null的int为0.
				throw new Exception("司机" + db.getName() + "已经有运单任务，不能在添加新的任务。");
			}
			db.setFreightid(fb.getId());
			dDAO.linkFreight(db);
			fb.setState(EnumUtils.freightState.CONFIRMED.toString());
			fDAO.confirmByMgr(fb);
			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			mDAO.addMessage(db.getId(), EnumUtils.AppRole.DRIVER.toString(),
					DRIVER_MESSAGE1);
			mDAO.addMessage(db.getProviderid(),
					EnumUtils.AppRole.PROVIDER.toString(), PROVIDER_MESSAGE1);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				json.put("error", sw.toString());
			} catch (Exception e1) {
			}
		} finally {
			// 事物结束，仍将其设置为非事物状态，然后释放。
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject confirmByDrv(FreightBean fb, CarBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			// fb = fDAO.get(fb.getId());
			fb.setState(EnumUtils.freightState.INWAY.toString());
			fDAO.confirmByDrv(fb);
			fb = fDAO.get(fb.getId());

			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			// 获得入门。
			cb.setDestination(ServiceFactory.getMapService().getInGate());
			cb.setState(EnumUtils.CarState.STARTED.toString());
			cb.setId(cDAO.save(cb));

			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(cb.getDriverid());
			db.setCarid(cb.getId());
			db.setState(EnumUtils.DriverState.WORKING.toString());
			CargoDAO cargoDAO = DAOFactory.getInstance().getCargoDAO();
			cargoDAO.setConn(conn);
			CargoBean cargob = cargoDAO.get(fb.getCargoId());
			db.setStopid(cargob.getStopId());
			dDAO.startTrans(db);
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
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
	public JSONObject confirmArrive(FreightBean fb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject confirmFinish(FreightBean fb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject addFreight(FreightBean fb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			// 默认优先级
			fb.setManagerPriority(3);
			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);
			CargoBean cb = cDAO.get(fb.getCargoId());
			if (null == cb) {
				throw new Exception("无此id的货物");
			}
			if (cb.getProviderId() != fb.getProviderId()) {
				throw new Exception("请选择自己公司旗下的货物！！！");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			if (null == db) {
				throw new Exception("无此id的司机");
			}
			if (db.getState().equals(EnumUtils.DriverState.WORKING.toString())) {
				// 这里要注意，当司机确认时，才会进入状态working，而当管理员审核完成时，司机就将freightid设置对应
				// freight了，所以在之后审核时，还是要进行一次检测。
				throw new Exception("司机" + db.getName() + "已经在执行运单中，无法执行双份运单。");
			}
			fb.setCargoPriority(cb.getCargoPriority());
			fb.setPriority((fb.getCargoPriority() + fb.getDriverPriority() + fb
					.getManagerPriority()) / 3);
			json.put("id", fDAO.save(fb));
			// json.put("cantempty", "agag");json可以为空
			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			// 获得当前管理员的ID。。。
			ManagerDAO MDAO = DAOFactory.getInstance().getManagerDAO();
			MDAO.setConn(conn);
			mDAO.addMessage(MDAO.getMainId(),
					EnumUtils.AppRole.MANAGER.toString(), MANAGER_MESSAGE1);
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
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
	public JSONObject delFreight(String id) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			// 删除与完成运单所做的操作大致相同，区别只在于 完成处保存了一个完整的运单，而删除处不存在这个运单。
			// 不能对刚提交的运单进行异常处理。
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(Integer.parseInt(id));
			if (null == fb
					|| !fb.getState().equals(
							EnumUtils.freightState.INWAY.toString())) {
				throw new Exception("当前id 的运单不存在，或运单的状态为刚提交或已完成，不能进行处理");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			CarBean cb = cDAO.get(db.getCarid());

			if (EnumUtils.CarState.WAITING.toString().equals(cb.getState())) {
				// 根据car的状态，如果car在大门处等待，则使相应大门等待数减一。
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				// 当前等待车辆数减1,这个时候，destination是指向入门的。
				gDAO.carNumChange(false, cb.getDestination());
			} else if (null != cb.getLineid()) {
				// null的数据在dao层中获取时已经经过加工，设定为null了，这里就不要做多余的检测了。
				// 如果car在库内，则一定要让指向line上车辆数减一。
				LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
				lDAO.setConn(conn);
				lDAO.carNumChange(false, cb.getLineid());
				if (EnumUtils.CarState.BEFORE.toString().equals(cb.getState())
						|| EnumUtils.CarState.DISCHARGING.toString().equals(
								cb.getState())) {
					// 如果car在卸货中 或before，则使指向stop的状态为free和carid为null
					StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
					sDAO.setConn(conn);
					StopBean sb = sDAO.get(db.getStopid());
					sb.setCarid(null);
					sb.setState(EnumUtils.StopState.FREE.toString());
					sDAO.discharge(sb);
				} else if ("OFF".equals(cb.getState())) {
					// 如果car在OFF状态，即出库等待，则使等待大门处车辆数减一。
					GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
					gDAO.setConn(conn);
					// 当前等待车辆数减1,这个时候，destination是指向出门的。
					gDAO.carNumChange(false, cb.getDestination());
				}
			}
			// 设置driver的carid ,freightid,stopid =null，设置state为OFF。
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// 删除当前carid指向的car。
			cDAO.delete(cb.getId());
			// 删除指向freight。
			fDAO.delete(fb.getId());

			json.put("erroafsafr", "asg");
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
	public JSONObject finFreight(String id) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			// 删除与完成运单所做的操作大致相同，区别只在于 完成处保存了一个完整的运单，而删除处不存在这个运单。
			// 不能对刚提交的运单进行异常处理。
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(Integer.parseInt(id));
			if (null == fb
					|| !fb.getState().equals(
							EnumUtils.freightState.INWAY.toString())) {
				throw new Exception("当前id 的运单不存在，或运单的状态为刚提交或已完成，不能进行处理");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			CarBean cb = cDAO.get(db.getCarid());

			if (EnumUtils.CarState.WAITING.toString().equals(cb.getState())) {
				// 根据car的状态，如果car在大门处等待，则使相应大门等待数减一。
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				// 当前等待车辆数减1,这个时候，destination是指向入门的。
				gDAO.carNumChange(false, cb.getDestination());
			} else if (null != cb.getLineid()) {
				// 如果car在库内，则一定要让指向line上车辆数减一。
				LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
				lDAO.setConn(conn);
				lDAO.carNumChange(false, cb.getLineid());
				if (EnumUtils.CarState.BEFORE.toString().equals(cb.getState())
						|| EnumUtils.CarState.DISCHARGING.toString().equals(
								cb.getState())) {
					// 如果car在卸货中 或before，则使指向stop的状态为free和carid为null
					StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
					sDAO.setConn(conn);
					StopBean sb = sDAO.get(db.getStopid());
					sb.setCarid(null);
					sb.setState(EnumUtils.StopState.FREE.toString());
					sDAO.discharge(sb);
				} else if ("OFF".equals(cb.getState())) {
					// 如果car在OFF状态，即出库等待，则使等待大门处车辆数减一。
					GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
					gDAO.setConn(conn);
					// 当前等待车辆数减1,这个时候，destination是指向出门的。
					gDAO.carNumChange(false, cb.getDestination());
				}
			}
			// 设置driver的carid ,freightid,stopid =null，设置state为OFF。
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// 删除当前carid指向的car。
			cDAO.delete(cb.getId());
			// 赋予运单两个时间数据。
			if (null == fb.getArrivedTime()) {
				fDAO.confirmArrive(fb);
			}
			fb.setState(EnumUtils.freightState.FINISHED.toString());
			fDAO.confirmFinish(fb);

			json.put("erroafsafr", "asg");
			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
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

	@Override
	public JSONObject updateSite(CarBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 这里有两次操作，则使用事物进行处理。
			conn.setAutoCommit(false);
			MapService maps = ServiceFactory.getMapService();
			// 直接从这里获取，反正肯定是这个门。
			// 只有批准入库后，改变状态后目的地才更新为卸货点，不然卸货点一直保存在freight中。
			cb.setDestination(maps.getInGate());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);

			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(cb.getDriverid());
			// 从cb中已有的DriverID获得DriverBean，在从db中获得Carid。。。
			cb.setId(db.getCarid());

			// 检测车辆是否到达园区大门处。
			if (maps.checkCarArrivded(cb)) {
				// 如果到达，则想司机发送一个消息，告知司机自己到达园区，等待门卫允许入库
				MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
				mDAO.setConn(conn);
				mDAO.addMessage(db.getId(),
						EnumUtils.AppRole.DRIVER.toString(), DRIVER_MESSAGE2);
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				GateBean gb = gDAO.get(cb.getDestination());
				// 当前等待车辆数加1.
				gDAO.carNumChange(true, gb.getId());
				// 更新车辆状态为正在等待。
				cb.setState(EnumUtils.CarState.WAITING.toString());
				// 到达是无须设置Lineid，在批准进入后才给车辆道路起点的lineid和坐标。
				// cb.setLineid(gb.getLineid());
				// 更新运单中的车辆到达时间。
				FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
				fDAO.setConn(conn);
				fDAO.confirmArrive(fDAO.get(db.getFreightid()));
				// 发送消息给门卫，告知新的车辆到达，等待批准入库
				mDAO.addMessage(gb.getGuardid(),
						EnumUtils.AppRole.GUARD.toString(), GUARD_MESSAGE1);
			}
			cDAO.updateSite(cb);
			// json.put("cantempty", "agag");json可以为空
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
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
