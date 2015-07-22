package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ll.vmsystem.dao.LoginDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.push.PushManager;

/**
 * Description: MessageDAOImpl login接口的实现类<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class MessageDAOImpl implements MessageDAO {

	/**
	 * 连接
	 */
	private Connection conn;

	

	@Override
	public void addMessage(int id, String role, int message)
			throws SQLException {
		// 消息总是只有一个，

		LoginDAO lDAO = DAOFactory.getInstance().getLoginDAO();
		lDAO.setConn(conn);
		if (lDAO.isLogin(id, role)) {
			// 如果对应帐号已经登录，则直接发送消息。
			String cid = lDAO.getCID(id, role);
			if (null == cid) {
				throw new SQLException("cid为空？");
			} else {
				PushManager.getInstance().PushMessage(message, cid);
			}
		} else {
			int add = message;
			if (0 != checkMessage(id, role)) {
				PreparedStatement ps = null;
				String sql = "UPDATE message SET message1 = message1 + ? ,lasttime = NOW()  WHERE id = ? AND role = ?";
				ps = conn.prepareStatement(sql);
				int i = 1;
				ps.setInt(i++, add);
				ps.setInt(i++, id);
				ps.setString(i++, role);
				ps.executeUpdate();
				ps.close();
			} else {
				PreparedStatement ps = null;
				String sql = "INSERT INTO  message(id,role,message1,lasttime) VALUES (?,?,?,now()) ";
				ps = conn.prepareStatement(sql);
				int i = 1;
				ps.setInt(i++, id);
				ps.setString(i++, role);
				ps.setInt(i++, message);
				ps.executeUpdate();
				ps.close();
			}
		}
	}

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int checkMessage(int id, String role) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT message1 FROM message WHERE id = ? AND role = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, id);
		ps.setString(i++, role);
		rs = ps.executeQuery();
		int messsage = 0;
		if (rs.next()) {
			messsage = rs.getInt(1);
		}
		rs.close();
		ps.close();
		return messsage;
	}

	@Override
	public void pushLeavingMessage(int id, String role) throws SQLException {
		int message = checkMessage(id, role);
		if (0 != message) {
			// 如果存在离线消息，则发送
			LoginDAO lDAO = DAOFactory.getInstance().getLoginDAO();
			lDAO.setConn(conn);
			PreparedStatement ps = null;
			String sql = "DELETE FROM message WHERE id = ? AND role = ? ";
			int i = 1;
			ps = conn.prepareStatement(sql);
			ps.setInt(i++, id);
			ps.setString(i++, role);
			ps.executeUpdate();
			PushManager.getInstance().PushMessage(message,
					lDAO.getCID(id, role));
		}

	}

}
