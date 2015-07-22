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
 * FreightServiceImpl����Freight�йصĲ�����ʵ�֣�������ʵ�ֵ�Service���ǲ���ȷ�ģ�δ���״̬�Ƿ�ɲ���<br/>
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
	 * ��˾��������Ϣ1����Ϣ1Ϊ˾����ǰ���µ��˵���ȷ��ִ�С�
	 */
	private final static int DRIVER_MESSAGE1 = 0x00000001;

	/**
	 * ��˾��������Ϣ2����Ϣ2Ϊ˾����ǰ������Ŵ����ȴ�������׼��⡣
	 */
	private final static int DRIVER_MESSAGE2 = 0x00000100;
	/**
	 * ��˾��������Ϣ1����Ϣ1Ϊ˾����ǰ���µ��˵���ȷ��ִ�С�
	 */
	private final static int DRIVER_MESSAGE3 = 0x00010000;

	/**
	 * ��Ӧ�̵���Ϣ1��Ϊ���µ��˵������ɡ�
	 */
	private final static int PROVIDER_MESSAGE1 = 0x00000001;
	
	
	private final static int PROVIDER_MESSAGE2 = 0x00000100;

	/**
	 * ����Ա����Ϣ1��Ϊ�µ��˵��ύ����ˡ�
	 */
	private final static int MANAGER_MESSAGE1 = 0x01;

	/**
	 * ��������Ϣ1���µĳ�������ȴ���׼��⡣
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
			// ��������д���
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			if (db.getFreightid() != 0) {
				// �����ڴ����ݿ��л�ȡ����ʱ��Ϊnull��intΪ0.
				throw new Exception("˾��" + db.getName() + "�Ѿ����˵����񣬲���������µ�����");
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
			// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
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
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			// fb = fDAO.get(fb.getId());
			fb.setState(EnumUtils.freightState.INWAY.toString());
			fDAO.confirmByDrv(fb);
			fb = fDAO.get(fb.getId());

			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			// ������š�
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
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json.put("error", e.toString());
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
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			// Ĭ�����ȼ�
			fb.setManagerPriority(3);
			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);
			CargoBean cb = cDAO.get(fb.getCargoId());
			if (null == cb) {
				throw new Exception("�޴�id�Ļ���");
			}
			if (cb.getProviderId() != fb.getProviderId()) {
				throw new Exception("��ѡ���Լ���˾���µĻ������");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			if (null == db) {
				throw new Exception("�޴�id��˾��");
			}
			if (db.getState().equals(EnumUtils.DriverState.WORKING.toString())) {
				// ����Ҫע�⣬��˾��ȷ��ʱ���Ż����״̬working����������Ա������ʱ��˾���ͽ�freightid���ö�Ӧ
				// freight�ˣ�������֮�����ʱ������Ҫ����һ�μ�⡣
				throw new Exception("˾��" + db.getName() + "�Ѿ���ִ���˵��У��޷�ִ��˫���˵���");
			}
			fb.setCargoPriority(cb.getCargoPriority());
			fb.setPriority((fb.getCargoPriority() + fb.getDriverPriority() + fb
					.getManagerPriority()) / 3);
			json.put("id", fDAO.save(fb));
			// json.put("cantempty", "agag");json����Ϊ��
			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			// ��õ�ǰ����Ա��ID������
			ManagerDAO MDAO = DAOFactory.getInstance().getManagerDAO();
			MDAO.setConn(conn);
			mDAO.addMessage(MDAO.getMainId(),
					EnumUtils.AppRole.MANAGER.toString(), MANAGER_MESSAGE1);
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json.put("error", e.toString());
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
	public JSONObject delFreight(String id) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			// ɾ��������˵������Ĳ���������ͬ������ֻ���� ��ɴ�������һ���������˵�����ɾ��������������˵���
			// ���ܶԸ��ύ���˵������쳣����
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(Integer.parseInt(id));
			if (null == fb
					|| !fb.getState().equals(
							EnumUtils.freightState.INWAY.toString())) {
				throw new Exception("��ǰid ���˵������ڣ����˵���״̬Ϊ���ύ������ɣ����ܽ��д���");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			CarBean cb = cDAO.get(db.getCarid());

			if (EnumUtils.CarState.WAITING.toString().equals(cb.getState())) {
				// ����car��״̬�����car�ڴ��Ŵ��ȴ�����ʹ��Ӧ���ŵȴ�����һ��
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				// ��ǰ�ȴ���������1,���ʱ��destination��ָ�����ŵġ�
				gDAO.carNumChange(false, cb.getDestination());
			} else if (null != cb.getLineid()) {
				// null��������dao���л�ȡʱ�Ѿ������ӹ����趨Ϊnull�ˣ�����Ͳ�Ҫ������ļ���ˡ�
				// ���car�ڿ��ڣ���һ��Ҫ��ָ��line�ϳ�������һ��
				LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
				lDAO.setConn(conn);
				lDAO.carNumChange(false, cb.getLineid());
				if (EnumUtils.CarState.BEFORE.toString().equals(cb.getState())
						|| EnumUtils.CarState.DISCHARGING.toString().equals(
								cb.getState())) {
					// ���car��ж���� ��before����ʹָ��stop��״̬Ϊfree��caridΪnull
					StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
					sDAO.setConn(conn);
					StopBean sb = sDAO.get(db.getStopid());
					sb.setCarid(null);
					sb.setState(EnumUtils.StopState.FREE.toString());
					sDAO.discharge(sb);
				} else if ("OFF".equals(cb.getState())) {
					// ���car��OFF״̬��������ȴ�����ʹ�ȴ����Ŵ���������һ��
					GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
					gDAO.setConn(conn);
					// ��ǰ�ȴ���������1,���ʱ��destination��ָ����ŵġ�
					gDAO.carNumChange(false, cb.getDestination());
				}
			}
			// ����driver��carid ,freightid,stopid =null������stateΪOFF��
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// ɾ����ǰcaridָ���car��
			cDAO.delete(cb.getId());
			// ɾ��ָ��freight��
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
	public JSONObject finFreight(String id) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			// ɾ��������˵������Ĳ���������ͬ������ֻ���� ��ɴ�������һ���������˵�����ɾ��������������˵���
			// ���ܶԸ��ύ���˵������쳣����
			FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
			fDAO.setConn(conn);
			FreightBean fb = fDAO.get(Integer.parseInt(id));
			if (null == fb
					|| !fb.getState().equals(
							EnumUtils.freightState.INWAY.toString())) {
				throw new Exception("��ǰid ���˵������ڣ����˵���״̬Ϊ���ύ������ɣ����ܽ��д���");
			}
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(fb.getDriverId());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);
			CarBean cb = cDAO.get(db.getCarid());

			if (EnumUtils.CarState.WAITING.toString().equals(cb.getState())) {
				// ����car��״̬�����car�ڴ��Ŵ��ȴ�����ʹ��Ӧ���ŵȴ�����һ��
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				// ��ǰ�ȴ���������1,���ʱ��destination��ָ�����ŵġ�
				gDAO.carNumChange(false, cb.getDestination());
			} else if (null != cb.getLineid()) {
				// ���car�ڿ��ڣ���һ��Ҫ��ָ��line�ϳ�������һ��
				LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
				lDAO.setConn(conn);
				lDAO.carNumChange(false, cb.getLineid());
				if (EnumUtils.CarState.BEFORE.toString().equals(cb.getState())
						|| EnumUtils.CarState.DISCHARGING.toString().equals(
								cb.getState())) {
					// ���car��ж���� ��before����ʹָ��stop��״̬Ϊfree��caridΪnull
					StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
					sDAO.setConn(conn);
					StopBean sb = sDAO.get(db.getStopid());
					sb.setCarid(null);
					sb.setState(EnumUtils.StopState.FREE.toString());
					sDAO.discharge(sb);
				} else if ("OFF".equals(cb.getState())) {
					// ���car��OFF״̬��������ȴ�����ʹ�ȴ����Ŵ���������һ��
					GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
					gDAO.setConn(conn);
					// ��ǰ�ȴ���������1,���ʱ��destination��ָ����ŵġ�
					gDAO.carNumChange(false, cb.getDestination());
				}
			}
			// ����driver��carid ,freightid,stopid =null������stateΪOFF��
			db.setState(EnumUtils.DriverState.OFF.toString());
			dDAO.endTrans(db);
			// ɾ����ǰcaridָ���car��
			cDAO.delete(cb.getId());
			// �����˵�����ʱ�����ݡ�
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
	public JSONObject updateSite(CarBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);
			MapService maps = ServiceFactory.getMapService();
			// ֱ�Ӵ������ȡ�������϶�������š�
			// ֻ����׼���󣬸ı�״̬��Ŀ�ĵزŸ���Ϊж���㣬��Ȼж����һֱ������freight�С�
			cb.setDestination(maps.getInGate());
			CarDAO cDAO = DAOFactory.getInstance().getCarDAO();
			cDAO.setConn(conn);

			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			DriverBean db = dDAO.get(cb.getDriverid());
			// ��cb�����е�DriverID���DriverBean���ڴ�db�л��Carid������
			cb.setId(db.getCarid());

			// ��⳵���Ƿ񵽴�԰�����Ŵ���
			if (maps.checkCarArrivded(cb)) {
				// ����������˾������һ����Ϣ����֪˾���Լ�����԰�����ȴ������������
				MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
				mDAO.setConn(conn);
				mDAO.addMessage(db.getId(),
						EnumUtils.AppRole.DRIVER.toString(), DRIVER_MESSAGE2);
				GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
				gDAO.setConn(conn);
				GateBean gb = gDAO.get(cb.getDestination());
				// ��ǰ�ȴ���������1.
				gDAO.carNumChange(true, gb.getId());
				// ���³���״̬Ϊ���ڵȴ���
				cb.setState(EnumUtils.CarState.WAITING.toString());
				// ��������������Lineid������׼�����Ÿ�������·����lineid�����ꡣ
				// cb.setLineid(gb.getLineid());
				// �����˵��еĳ�������ʱ�䡣
				FreightDAO fDAO = DAOFactory.getInstance().getFreightDAO();
				fDAO.setConn(conn);
				fDAO.confirmArrive(fDAO.get(db.getFreightid()));
				// ������Ϣ����������֪�µĳ�������ȴ���׼���
				mDAO.addMessage(gb.getGuardid(),
						EnumUtils.AppRole.GUARD.toString(), GUARD_MESSAGE1);
			}
			cDAO.updateSite(cb);
			// json.put("cantempty", "agag");json����Ϊ��
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				conn.rollback();
				// Ϊ��ʹ�������ͣ��Ҿ�������flag��ǡ�
				json.put("error", e.toString());
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
