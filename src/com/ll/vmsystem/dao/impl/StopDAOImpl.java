package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:StopDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class StopDAOImpl implements StopDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 加载rs中的stop数据
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private StopBean mapStopBean(ResultSet rs) throws SQLException {
		StopBean sb = new StopBean();
		sb.setId(rs.getInt("id"));
		sb.setName(rs.getString("name"));
		sb.setLongitude(rs.getDouble("longitude"));
		sb.setLatitude(rs.getDouble("latitude"));
		sb.setX(rs.getInt("x"));
		sb.setY(rs.getInt("y"));
		sb.setLineid(rs.getInt("lineid"));
		sb.setDistance(rs.getInt("distance"));
		sb.setState(rs.getString("state"));
		sb.setCarid(rs.getInt("carid"));
		sb.setDetail(rs.getString("detail"));
		return sb;
	}

	@Override
	public Integer save(StopBean sb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO stop(name , longitude , latitude, x ,y ,"
				+ "lineid , distance ,detail) VALUES (?,?,?,?,?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, sb.getName());
		ps.setDouble(2, sb.getLongitude());
		ps.setDouble(3, sb.getLatitude());
		ps.setInt(4, sb.getX());
		ps.setInt(5, sb.getY());
		ps.setInt(6, sb.getLineid());
		ps.setInt(7, sb.getDistance());
		ps.setString(8, sb.getDetail());
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
	public boolean delete(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM stop WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean update(StopBean sb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE stop SET name = ? , longitude =? , latitude= ? ,"
				+ " x = ?  ,y = ?  ,lineid = ? , distance = ? ,detail = ?"
				+ "   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, sb.getName());
		ps.setDouble(2, sb.getLongitude());
		ps.setDouble(3, sb.getLatitude());
		ps.setInt(4, sb.getX());
		ps.setInt(5, sb.getY());
		ps.setInt(6, sb.getLineid());
		ps.setInt(7, sb.getDistance());
		ps.setString(8, sb.getDetail());
		ps.setInt(9, sb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean discharge(StopBean sb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE stop SET state  =? ,  carid  = ?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, sb.getState());
		if (sb.getCarid() == null) {
			ps.setNull(2, java.sql.Types.NULL);
		} else {
			ps.setInt(2, sb.getCarid());
		}
		ps.setInt(3, sb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public StopBean get(int id) throws SQLException {
		StopBean sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM stop WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			sb = mapStopBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return sb;
	}

	/**
	 * 私有函数，获取当前全部页数
	 * 
	 * @param conn
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	private int getPageNum(Connection conn, String info) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "SELECT COUNT(id) FROM stop WHERE " + info;
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if (rs.next()) {
			num = rs.getInt(1);
		}
		rs.close();
		ps.close();
		num = num / (PageList.pageSize + 1) + 1;
		return num;
	}

	@Override
	public PageList searchByInfo(int pageNo, String info) throws SQLException {
		List<StopBean> li = new ArrayList<StopBean>();
		StopBean sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM stop  WHERE id <= (SELECT id FROM stop "
				+ " WHERE  " + info + " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		;
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			sb = mapStopBean(rs);
			li.add(sb);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

	@Override
	public List<StopBean> getAll() throws SQLException {
		List<StopBean> list =new ArrayList<StopBean>();
		StopBean sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM stop ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			sb = mapStopBean(rs);
			list.add(sb);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return list;
	}

}
