package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.GuardDAO;
import com.ll.vmsystem.vo.GuardBean;

public class GuardDAOImpl implements GuardDAO {

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
	private GuardBean mapGuardBean(ResultSet rs) throws SQLException {
		GuardBean mb = new GuardBean();
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
	public Integer save(GuardBean gb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO guard (name,phone,password,otherphone,"
				+ "detail,lastlogin  ) VALUES (?,?,?,?,?,now())";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, gb.getName());
		ps.setString(2, gb.getPhone());
		ps.setString(3, gb.getPassword());
		ps.setString(4, gb.getOtherphone());
		ps.setString(5, gb.getDetail());
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
	public boolean update(GuardBean gb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE guard SET  name = ? ,phone = ? ,password = ? ,"
				+ "otherphone = ?, detail = ?    WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, gb.getName());
		ps.setString(2, gb.getPhone());
		ps.setString(3, gb.getPassword());
		ps.setString(4, gb.getOtherphone());
		ps.setString(5, gb.getDetail());
		ps.setInt(6, gb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean login(GuardBean gb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE guard SET  lastlogin = ?  WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, gb.getLastlogin());
		ps.setInt(2, gb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public GuardBean get(int id) throws SQLException {
		GuardBean gb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM guard WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			gb = mapGuardBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return gb;
	}

	@Override
	public List<GuardBean> getAll() throws SQLException {
		List<GuardBean> li = new ArrayList<GuardBean>();
		GuardBean gb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM guard ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			gb = mapGuardBean(rs);
			li.add(gb);
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
		String sql = "DELETE FROM guard WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

}
