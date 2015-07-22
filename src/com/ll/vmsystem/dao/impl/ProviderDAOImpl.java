package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.ProviderDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.ProviderBean;

/**
 * Description:providerDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ProviderDAOImpl implements ProviderDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 将rs中的第一项中的数据存放到一个providerBean中返回
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ProviderBean mapProviderBean(ResultSet rs) throws SQLException {
		ProviderBean pb = new ProviderBean();
		pb.setDetail(rs.getString("detail"));
		pb.setDrivernum(rs.getInt("drivernum"));
		pb.setId(rs.getInt("id"));
		pb.setName(rs.getString("name"));
		pb.setOtherphone(rs.getString("otherphone"));
		pb.setPassword(rs.getString("password"));
		pb.setPrincipalname(rs.getString("principalname"));
		pb.setPrincipalphone(rs.getString("principalphone"));
		return pb;
	}

	@Override
	public ProviderBean get(int id) throws SQLException {
		ProviderBean pb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT id,password,name,principalname,principalphone,otherphone,drivernum"
				+ ",detail  FROM provider WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			pb = mapProviderBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return pb;
	}

	@Override
	public boolean update(ProviderBean pb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE provider SET password=?, "
				+ "name=?, principalname=?, principalphone=?,"
				+ "otherphone=?, detail=? WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, pb.getPassword());
		ps.setString(2, pb.getName());
		ps.setString(3, pb.getPrincipalname());
		ps.setString(4, pb.getPrincipalphone());
		ps.setString(5, pb.getOtherphone());
		ps.setString(6, pb.getDetail());
		ps.setInt(7, pb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean updateCarNum(Integer id, boolean increase)
			throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE provider SET drivernum=drivernum + ?  "
				+ "WHERE id=?";
		int add = -1;
		if (increase) {
			add = 1;
		}
		ps = conn.prepareStatement(sql);
		ps.setInt(1, add);
		ps.setInt(2, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM provider WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	/**
	 * 私有函数，获取当前全部页数
	 * 
	 * @param conn
	 * @return 页数
	 * @throws SQLException
	 */
	private int getPageNum(Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		String sql = "SELECT COUNT(id) FROM provider";
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
	public PageList getAll(int pageNo) throws SQLException {
		List<ProviderBean> li = new ArrayList<ProviderBean>();
		ProviderBean pb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT id,password,name,principalname,principalphone,otherphone,"
				+ "drivernum,detail FROM provider  WHERE id <= (SELECT id FROM provider "
				+ "ORDER BY id DESC LIMIT ?,1 ) ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		;
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			pb = mapProviderBean(rs);
			li.add(pb);
		}
		pageNum = getPageNum(conn);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

	@Override
	public Integer save(ProviderBean pb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO provider(password,name,principalname,principalphone,"
				+ "otherphone,detail) VALUES (?,?,?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, pb.getPassword());
		ps.setString(2, pb.getName());
		ps.setString(3, pb.getPrincipalname());
		ps.setString(4, pb.getPrincipalphone());
		ps.setString(5, pb.getOtherphone());
		ps.setString(6, pb.getDetail());
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

}
