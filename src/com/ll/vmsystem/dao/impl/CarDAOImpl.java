package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.CarDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CarBean;

/**
 * Description:CarDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class CarDAOImpl implements CarDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 匹配数据，获得 Carbean。
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private CarBean mapCarBean(ResultSet rs) throws SQLException {
		CarBean cb = new CarBean();
		cb.setId(rs.getInt("id"));
		cb.setCarno(rs.getString("carno"));
		cb.setDriverid(rs.getInt("driverid"));
		cb.setLongitude(rs.getDouble("longitude"));
		cb.setLatitude(rs.getDouble("latitude"));
		cb.setX(rs.getInt("x"));
		cb.setY(rs.getInt("y"));
		cb.setLastltime(rs.getTimestamp("lastltime"));
		cb.setCartype(rs.getString("cartype"));
		cb.setState(rs.getString("state"));
		cb.setDestination(rs.getInt("destination"));
		cb.setLineid(rs.getInt("lineid"));
		cb.setDistance(rs.getInt("distance"));
		if (0 == cb.getLineid()) {
			cb.setLineid(null);
		}
		cb.setDetail(rs.getString("detail"));
		return cb;
	}

	@Override
	public Integer save(CarBean cb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO car(carno,driverid,longitude,latitude,x,y,lastltime,"
				+ "cartype,destination,detail,distance)"
				+ " VALUES (?,?,?,?,-1,-1,?,?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, cb.getCarno());
		ps.setInt(2, cb.getDriverid());
		ps.setDouble(3, cb.getLongitude());
		ps.setDouble(4, cb.getLatitude());
		ps.setTimestamp(5, cb.getLastltime());
		ps.setString(6, cb.getCartype());
		ps.setInt(7, cb.getDestination());
		ps.setString(8, cb.getDetail());
		ps.setInt(9, cb.getDistance());
		ps.executeUpdate();
		rs = ps.getGeneratedKeys();
		if (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		ps.close();

		return id;
	}

	@Override
	public boolean delete(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "DELETE FROM car WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		return true;
	}

	@Override
	public boolean updateSite(CarBean cb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE car SET  longitude = ?,latitude = ?,x = ?,y = ? ,"
				+ " lastltime=?,lineid = ?,distance = ?,state = ?,"
				+ " destination = ?,detail = ?   WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setDouble(1, cb.getLongitude());
		ps.setDouble(2, cb.getLatitude());
		ps.setInt(3, cb.getX());
		ps.setInt(4, cb.getY());
		ps.setTimestamp(5, cb.getLastltime());
		if (null == cb.getLineid()) {
			ps.setNull(6, java.sql.Types.NULL);
		} else {
			ps.setInt(6, cb.getLineid());
		}

		ps.setInt(7, cb.getDistance());
		ps.setString(8, cb.getState());
		ps.setInt(9, cb.getDestination());
		ps.setString(10, cb.getDetail());
		ps.setInt(11, cb.getId());
		ps.executeUpdate();
		ps.close();
		return true;
	}

	@Override
	public CarBean get(int id) throws Exception {
		CarBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM car WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCarBean(rs);
		}
		rs.close();
		ps.close();
		return cb;
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
		String sql = "SELECT COUNT(id) FROM car WHERE " + info;

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
		List<CarBean> li = new ArrayList<CarBean>();
		CarBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM car  WHERE id <= (SELECT id FROM car "
				+ " WHERE  " + info + " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";

		ps = conn.prepareStatement(sql);
		;
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCarBean(rs);
			li.add(cb);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		ps.close();

		return new PageList(pageNo, pageNum, li);
	}

	@Override
	public List<CarBean> getCarAtGate(int gateId) throws Exception {
		List<CarBean> li = new ArrayList<CarBean>();
		CarBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM car  WHERE (state = 'WAITING' OR state = 'OFF')   AND destination = ? ";
		ps = conn.prepareStatement(sql);
		int i = 1;
		ps.setInt(i, gateId);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCarBean(rs);
			li.add(cb);
		}
		rs.close();
		ps.close();

		return li;
	}

	@Override
	public List<CarBean> getAll() throws Exception {
		List<CarBean> li = new ArrayList<CarBean>();
		CarBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM car ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCarBean(rs);
			li.add(cb);
		}
		rs.close();
		ps.close();

		return li;
	}

}
