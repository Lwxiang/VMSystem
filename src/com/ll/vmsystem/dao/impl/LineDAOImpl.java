package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.LineBean;

/**
 * Description:LineDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class LineDAOImpl implements LineDAO {

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
	private LineBean mapLineBean(ResultSet rs) throws SQLException {
		LineBean lb = new LineBean();
		lb.setId(rs.getInt("id"));
		lb.setName(rs.getString("name"));
		lb.setA_longitude(rs.getDouble("a_longitude"));
		lb.setA_latitude(rs.getDouble("a_latitude"));
		lb.setB_longitude(rs.getDouble("b_longitude"));
		lb.setB_latitude(rs.getDouble("b_latitude"));
		lb.setA_x(rs.getInt("a_x"));
		lb.setA_y(rs.getInt("a_y"));
		lb.setB_x(rs.getInt("b_x"));
		lb.setB_y(rs.getInt("b_y"));
		lb.setLength(rs.getInt("length"));
		lb.setA(rs.getDouble("A"));
		lb.setB(rs.getDouble("B"));
		lb.setC(rs.getDouble("C"));
		lb.setCarnum(rs.getInt("carnum"));
		lb.setDetail(rs.getString("detail"));
		return lb;
	}

	@Override
	public Integer save(LineBean lb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO line( name, a_longitude ,a_latitude ,"
				+ "b_longitude, b_latitude, a_x, a_y ,b_x ,b_y, length,"
				+ " A, B ,C ,detail)  VALUES (?,?,?,?,?,  ?,?,?,?,?,   ?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, lb.getName());
		ps.setDouble(2, lb.getA_longitude());
		ps.setDouble(3, lb.getA_latitude());
		ps.setDouble(4, lb.getB_longitude());
		ps.setDouble(5, lb.getB_latitude());
		ps.setInt(6, lb.getA_x());
		ps.setInt(7, lb.getA_y());
		ps.setInt(8, lb.getB_x());
		ps.setInt(9, lb.getB_y());
		ps.setInt(10, lb.getLength());
		ps.setDouble(11, lb.getA());
		ps.setDouble(12, lb.getB());
		ps.setDouble(13, lb.getC());
		ps.setString(14, lb.getDetail());
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
	public boolean update(LineBean lb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE line SET  name = ?, a_longitude = ?  ,a_latitude = ? ,"
				+ "b_longitude = ?, b_latitude = ?, a_x = ?, a_y = ? ,b_x = ? ,"
				+ "b_y = ?, length = ?, A = ?, B = ? ,C = ? ,detail = ?"
				+ "   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, lb.getName());
		ps.setDouble(2, lb.getA_longitude());
		ps.setDouble(3, lb.getA_latitude());
		ps.setDouble(4, lb.getB_longitude());
		ps.setDouble(5, lb.getB_latitude());
		ps.setInt(6, lb.getA_x());
		ps.setInt(7, lb.getA_y());
		ps.setInt(8, lb.getB_x());
		ps.setInt(9, lb.getB_y());
		ps.setInt(10, lb.getLength());
		ps.setDouble(11, lb.getA());
		ps.setDouble(12, lb.getB());
		ps.setDouble(13, lb.getC());
		ps.setString(14, lb.getDetail());
		ps.setInt(15, lb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean carNumChange(boolean add, int id) throws SQLException {
		PreparedStatement ps = null;
		int increase = add ? 1 : -1;
		String sql = "UPDATE line SET  carnum = carnum +?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, increase);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM line WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public LineBean get(int id) throws SQLException {
		LineBean lb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM line WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			lb = mapLineBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return lb;
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
		String sql = "SELECT COUNT(id) FROM line WHERE " + info;
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
		List<LineBean> li = new ArrayList<LineBean>();
		LineBean lb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM line  WHERE id <= (SELECT id FROM line "
				+ " WHERE  " + info + " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		;
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			lb = mapLineBean(rs);
			li.add(lb);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

	@Override
	public List<LineBean> getall() throws SQLException {
		List<LineBean> list = new ArrayList<LineBean>();
		LineBean lb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM line ";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			lb = mapLineBean(rs);
			list.add(lb);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return list;
	}

}
