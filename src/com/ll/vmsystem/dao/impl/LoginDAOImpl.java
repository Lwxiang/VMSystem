package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ll.vmsystem.dao.LoginDAO;

/**
 * Description: LoginDAOImpl login接口的实现类<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class LoginDAOImpl implements LoginDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean loignIn(int id, String role, String cid) throws SQLException {
		PreparedStatement ps = null;
		String sql = "INSERT INTO login(id,role,cid,token,lasttime)"
				+ " VALUES (?,?,?,'token',now())";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, id);
		ps.setString(i++, role);
		ps.setString(i++, cid);
		ps.executeUpdate();
		ps.close();

		return true;
	}

	@Override
	public boolean loginOut(int id, String role) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM login WHERE id = ? AND role = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, id);
		ps.setString(i++, role);
		ps.executeUpdate();
		ps.close();
		return true;
	}

	@Override
	public boolean isLogin(int id, String role) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM login WHERE id = ? AND role = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, id);
		ps.setString(i++, role);
		rs = ps.executeQuery();
		if (rs.next()) {
			id -= rs.getInt(1);
		}
		rs.close();
		ps.close();
		// 高端写法。
		return 0 == id;
	}

	@Override
	public String getCID(int id, String role) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT cid FROM login WHERE id = ? AND role = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, id);
		ps.setString(i++, role);
		rs = ps.executeQuery();
		String str = null;
		if (rs.next()) {
			str = rs.getString(1);
		}
		rs.close();
		ps.close();
		return str;
	}

	@Override
	public boolean updateLogin(int id, String role, String cid)
			throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE login SET cid = ? , lasttime = now() WHERE id = ? AND role = ?";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setString(i++, cid);
		ps.setInt(i++, id);
		ps.setString(i++, role);
		ps.executeUpdate();
		ps.close();

		return true;
	}

	@Override
	public int countNum() throws SQLException {
		int num = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(id) FROM login ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			num = rs.getInt(1);
		}
		rs.close();
		ps.close();
		// 高端写法。
		return num;
	}

}
