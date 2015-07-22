package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.DriverBean;

/**
 * Description:DriverDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class DriverDAOImpl implements DriverDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 存放数据
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private DriverBean mapDriverBean(ResultSet rs) throws SQLException {
		DriverBean db = new DriverBean();

		db.setId(rs.getInt("id"));
		db.setName(rs.getString("name"));
		db.setPassword(rs.getString("password"));
		db.setPhone(rs.getString("phone"));
		db.setOtherphone(rs.getString("otherphone"));
		db.setProviderid(rs.getInt("providerid"));
		db.setCarid(rs.getInt("carid"));
		db.setFreightid(rs.getInt("freightid"));
		db.setStopid(rs.getInt("stopid"));
		db.setState(rs.getString("state"));
		db.setDetail(rs.getString("detail"));
		return db;
	}

	@Override
	public Integer save(DriverBean db) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO driver( name,password,phone,otherphone,providerid,"
				+ "detail ) VALUES (?,?,?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, db.getName());
		ps.setString(2, db.getPassword());
		ps.setString(3, db.getPhone());
		ps.setString(4, db.getOtherphone());
		ps.setInt(5, db.getProviderid());
		ps.setString(6, db.getDetail());
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
	public boolean update(DriverBean db) throws SQLException {

		PreparedStatement ps = null;
		String sql = "UPDATE driver SET  name =? ,password =? , phone =? , "
				+ "otherphone = ?  ,detail = ?    WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, db.getName());
		ps.setString(2, db.getPassword());
		ps.setString(3, db.getPhone());
		ps.setString(4, db.getOtherphone());
		ps.setString(5, db.getDetail());
		ps.setInt(6, db.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean startTrans(DriverBean db) throws SQLException {

		PreparedStatement ps = null;
		String sql = "UPDATE driver SET  carid =? ,stopid =? ,"
				+ "state =?     WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, db.getCarid());
		// ps.setInt(2, db.getFreightid());
		// 我这里算明白为什么一般要用 i++，就是因为如果修改的时候，只要删掉一句而不修改其他的地方。。。
		ps.setInt(2, db.getStopid());
		ps.setString(3, db.getState());
		ps.setInt(4, db.getId());
		ps.executeUpdate();

		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean endTrans(DriverBean db) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE driver SET  carid =null ,freightid =null,stopid =null ,"
				+ "state =?     WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, db.getState());
		ps.setInt(2, db.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException {

		PreparedStatement ps = null;
		String sql = "DELETE FROM driver WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public DriverBean get(int id) throws SQLException {
		DriverBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM driver WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			db = mapDriverBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return db;
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
		String sql = "SELECT COUNT(id) FROM driver WHERE " + info;

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
		List<DriverBean> li = new ArrayList<DriverBean>();
		DriverBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM driver  WHERE id <= (SELECT id FROM driver "
				+ " WHERE  "
				+ info
				+ " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		;
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			db = mapDriverBean(rs);
			li.add(db);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

	@Override
	public boolean linkFreight(DriverBean db) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE driver SET  freightid =?    WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, db.getFreightid());
		ps.setInt(2, db.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

}
