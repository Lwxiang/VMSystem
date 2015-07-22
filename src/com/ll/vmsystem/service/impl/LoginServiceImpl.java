package com.ll.vmsystem.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;

import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.GuardDAO;
import com.ll.vmsystem.dao.LoginDAO;
import com.ll.vmsystem.dao.ManagerDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.ProviderDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.LoginService;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.utilities.JSONUtils;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.GuardBean;
import com.ll.vmsystem.vo.ManagerBean;
import com.ll.vmsystem.vo.ProviderBean;

/**
 * Description:LoginServiceImpl ��¼������ʵ�֡� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class LoginServiceImpl implements LoginService {

	/**
	 * ����json�еı�ǵ�¼�Ƿ�ɹ��Ķ���ֵΪEnumUtils.LoginState�е�ֵ��
	 */
	private final static String FLAG = "flag";

	@Override
	public JSONObject login(int id, String password, String role) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);���ڸ��������������ε�Service��ʹ�����
			// ����role�жϵ�¼�Ľ�ɫ��
			LoginDAO lDAO = DAOFactory.getInstance().getLoginDAO();
			lDAO.setConn(conn);
			if (role.equals(EnumUtils.AppRole.DRIVER.toString())) {
				DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
				dDAO.setConn(conn);
				DriverBean db = dDAO.get(id);
				if (db != null && password.equals(db.getPassword())) {
					json.put(FLAG, EnumUtils.LoginState.SUCCESS.toString());
					// dDAO.login(db);���������¼������
					JSONUtils.putDriverBean(json, db);
					// lDAO.loignIn(id, role, cid);
				} else {
					json.put(FLAG, EnumUtils.LoginState.FAILED.toString());
				}
			} else if (role.equals(EnumUtils.AppRole.MANAGER.toString())) {
				ManagerDAO mDAO = DAOFactory.getInstance().getManagerDAO();
				mDAO.setConn(conn);
				ManagerBean mb = mDAO.get(id);
				if (mb != null && password.equals(mb.getPassword())) {
					json.put(FLAG, EnumUtils.LoginState.SUCCESS.toString());
					JSONUtils.putManagerBean(json, mb);
					mb.setLastlogin(new Timestamp(System.currentTimeMillis()));
					mDAO.login(mb);
				} else {
					json.put(FLAG, EnumUtils.LoginState.FAILED.toString());
				}
			} else if (role.equals(EnumUtils.AppRole.GUARD.toString())) {
				GuardDAO gDAO = DAOFactory.getInstance().getGuardDAO();
				gDAO.setConn(conn);
				GuardBean gb = gDAO.get(id);
				if (gb != null && password.equals(gb.getPassword())) {
					json.put(FLAG, EnumUtils.LoginState.SUCCESS.toString());
					JSONUtils.putGuardBean(json, gb);
					try {
						conn.setAutoCommit(false);
						GateDAO gateDAO = DAOFactory.getInstance().getGateDAO();
						gateDAO.setConn(conn);
						gDAO.login(gb);
						List<GateBean> gblist = gateDAO.getAll();
						for (GateBean gate : gblist) {
							// ����ȫ��gate������Ӧ��gate����Ϊ����״̬��
							if (gate.getGuardid() != null
									&& gate.getGuardid() == gb.getId()) {
								gateDAO.stateChange(true, gate.getId());
							}
						}
						conn.commit();
					} catch (Exception e) {
						// try{// �������֣������������ʹ�� finally���쳣�׵��������Ӱ�졣
						conn.rollback();
						// }catch(Exception ee){
						// //�������try catch����Ȼ�׳��쳣����Ҫ����������try catch���ˡ������� ���� rry
						// catch������
						// e.printStackTrace();
						// }
					} finally {
						// ����������Խ�������Ϊ������״̬��Ȼ���ͷš�
						conn.setAutoCommit(true);
					}
				} else {
					json.put(FLAG, EnumUtils.LoginState.FAILED.toString());
				}
			} else if (role.equals(EnumUtils.AppRole.PROVIDER.toString())) {
				ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
				pDAO.setConn(conn);
				ProviderBean pb = pDAO.get(id);
				if (pb != null && password.equals(pb.getPassword())) {
					json.put(FLAG, EnumUtils.LoginState.SUCCESS.toString());
					JSONUtils.putProivderBean(json, pb);
				} else {
					json.put(FLAG, EnumUtils.LoginState.FAILED.toString());
				}
			}

		} catch (Exception e) {
			try {
				json.put("ERROR", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}

		return json;
	}

	@Override
	public JSONObject addLoginInfo(String id, String role, String cid) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// ���������β�������ʹ��������д���
			conn.setAutoCommit(false);
			LoginDAO lDAO = DAOFactory.getInstance().getLoginDAO();
			lDAO.setConn(conn);
			int ID = Integer.parseInt(id);
			if (lDAO.isLogin(ID, role)) {
				lDAO.updateLogin(ID, role, cid);
			} else {
				lDAO.loignIn(ID, role, cid);
				MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
				mDAO.setConn(conn);
				// ����������Ϣ��������Ƕ�����ݿ�����ˡ�
				mDAO.pushLeavingMessage(ID, role);
			}
			// �޴������ύ���
			conn.commit();
		} catch (Exception e) {
			try {
				// �д�����ع���
				conn.rollback();
				json.put("ERROR", e.toString());
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
	public JSONObject delLoginInfo(String id, String role) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			LoginDAO lDAO = DAOFactory.getInstance().getLoginDAO();
			lDAO.setConn(conn);
			int ID = Integer.parseInt(id);
			if (lDAO.isLogin(ID, role)) {
				lDAO.loginOut(ID, role);
			} else {
				json.put("ERROR", "��ǰ�ʺ�δ��¼");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				json.put("ERROR", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}

		return json;
	}

}
