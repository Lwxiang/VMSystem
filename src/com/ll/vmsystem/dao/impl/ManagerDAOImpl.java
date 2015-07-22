package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.ManagerDAO;
import com.ll.vmsystem.vo.ManagerBean;

/**
 * Description:ManagerDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ManagerDAOImpl implements ManagerDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 从rs中加载数据
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ManagerBean mapManagerBean(ResultSet rs) throws SQLException {
		ManagerBean mb = new ManagerBean();
		mb.setId(rs.getInt("id"));
		mb.setName(rs.getString("name"));
		mb.setPhone(rs.getString("phone"));
		mb.setPassword(rs.getString("password"));
		mb.setOtherphone(rs.getString("otherphone"));
		mb.setLastlogin(rs.getTimestamp("lastlogin"));
		mb.setDetail(rs.getString("detail"));
		return mb;
	}

	@Override
	public Integer save(ManagerBean mb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO manager (name,phone,password,otherphone,"
				+ "detail, lastlogin ) VALUES (?,?,?,?,?,now())";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, mb.getName());
		ps.setString(2, mb.getPhone());
		ps.setString(3, mb.getPassword());
		ps.setString(4, mb.getOtherphone());
		ps.setString(5, mb.getDetail());
		ps.executeUpdate();
		rs = ps.getGeneratedKeys();
		if (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return id;
	}

	@Override
	public boolean update(ManagerBean mb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE manager SET  name = ? ,phone = ? ,password = ? ,"
				+ "otherphone = ?, detail = ?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, mb.getName());
		ps.setString(2, mb.getPhone());
		ps.setString(3, mb.getPassword());
		ps.setString(4, mb.getOtherphone());
		ps.setString(5, mb.getDetail());
		ps.setInt(6, mb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean login(ManagerBean mb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE manager SET  lastlogin = ?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, mb.getLastlogin());
		ps.setInt(2, mb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public ManagerBean get(int id) throws SQLException {
		ManagerBean mb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM manager WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			mb = mapManagerBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return mb;
	}

	@Override
	public List<ManagerBean> getAll() throws SQLException {
		List<ManagerBean> li = new ArrayList<ManagerBean>();
		ManagerBean mb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM manager ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			mb = mapManagerBean(rs);
			li.add(mb);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return li;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM manager WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public int getMainId() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id  FROM manager WHERE detail = 'MAIN' ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		int id = 1;
		if (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return id;
	}

}
