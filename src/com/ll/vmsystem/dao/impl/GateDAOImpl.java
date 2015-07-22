package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.vo.GateBean;

/**
 * Description:GateDAOImpl 实现 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class GateDAOImpl implements GateDAO {

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
	private GateBean mapGateBean(ResultSet rs) throws SQLException {
		GateBean gb = new GateBean();
		gb.setId(rs.getInt("id"));
		gb.setName(rs.getString("name"));
		gb.setState(rs.getString("state"));
		gb.setWaitingcars(rs.getInt("waitingcars"));
		gb.setType(rs.getString("type"));
		gb.setLongitude(rs.getDouble("longitude"));
		gb.setLatitude(rs.getDouble("latitude"));
		gb.setLineid(rs.getInt("lineid"));
		gb.setDetail(rs.getString("detail"));
		gb.setX(rs.getInt("x"));
		gb.setY(rs.getInt("y"));
		gb.setGuardid(rs.getInt("guardid"));
		return gb;
	}

	@Override
	public Integer save(GateBean gb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO gate (name , type, longitude, latitude ,"
				+ "lineid ,detail,waitingcars,x,y,guardid ) VALUES (?,?,?,?,?,?,0,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, gb.getName());
		ps.setString(2, gb.getType());
		ps.setDouble(3, gb.getLongitude());
		ps.setDouble(4, gb.getLatitude());
		ps.setInt(5, gb.getLineid());
		ps.setString(6, gb.getDetail());
		ps.setInt(7, gb.getX());
		ps.setInt(8, gb.getY());
		if (gb.getGuardid() == null) {
			ps.setNull(9, java.sql.Types.NULL);
		} else {
			ps.setInt(9, gb.getGuardid());
		}

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
	public boolean update(GateBean gb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE gate SET  name = ? , type = ?, longitude = ?, "
				+ "latitude = ? ,lineid = ? ,detail = ? ,x = ? , y = ? ,guardid = ?  WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, gb.getName());
		ps.setString(2, gb.getType());
		ps.setDouble(3, gb.getLongitude());
		ps.setDouble(4, gb.getLatitude());
		ps.setInt(5, gb.getLineid());
		ps.setString(6, gb.getDetail());
		ps.setInt(7, gb.getX());
		ps.setInt(8, gb.getY());
		if (gb.getGuardid() == null) {
			ps.setNull(9, java.sql.Types.NULL);
		} else {
			ps.setInt(9, gb.getGuardid());
		}
		ps.setInt(10, gb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean carNumChange(boolean add, int id) throws SQLException {
		PreparedStatement ps = null;
		int increase = add ? 1 : -1;
		String sql = "UPDATE gate SET  waitingcars = waitingcars+?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, increase);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean stateChange(boolean work, int id) throws SQLException {
		PreparedStatement ps = null;
		String state = null;
		if (work) {
			state = EnumUtils.GateState.WORKING.toString();
		} else {
			state = EnumUtils.GateState.OFF.toString();
		}
		String sql = "UPDATE gate SET  state =?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, state);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public GateBean get(int id) throws SQLException {
		GateBean gb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM gate WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			gb = mapGateBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return gb;
	}

	@Override
	public List<GateBean> getAll() throws SQLException {
		List<GateBean> li = new ArrayList<GateBean>();
		GateBean gb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM gate ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			gb = mapGateBean(rs);
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
		String sql = "DELETE FROM gate WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public int getByGuard(int guardid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM gate WHERE guardid = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i--, guardid);
		rs = ps.executeQuery();
		if (rs.next()) {
			i = rs.getInt(1);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return i;
	}

}
