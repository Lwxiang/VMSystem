package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.FreightDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description:FreightDAOImpl 实现类 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class FreightDAOImpl implements FreightDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 将rs中的第一项中的数据存放到一个FreightBean中返回,由于Bean中全为对象
	 * 则无须担心存储空值的问题，且rs中亦可随意取出NULL，所以每次存放一个整的javabean。
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private FreightBean mapFreightBean(ResultSet rs) throws SQLException {
		FreightBean fb = new FreightBean();

		fb.setId(rs.getInt("id"));
		fb.setProviderId(rs.getInt("providerid"));
		fb.setDriverId(rs.getInt("driverid"));
		fb.setDriverPriority(rs.getInt("driverpriority"));
		fb.setManagerPriority(rs.getInt("managerpriority"));
		fb.setCargoPriority(rs.getInt("cargopriority"));
		fb.setPriority(rs.getInt("priority"));
		fb.setSubmitTime(rs.getTimestamp("submittime"));
		fb.setConfirmTime(rs.getTimestamp("confirmtime"));
		fb.setExpectedTime(rs.getTimestamp("expectedtime"));
		fb.setArrivedTime(rs.getTimestamp("arrivedtime"));
		fb.setStartTime(rs.getTimestamp("starttime"));
		fb.setFinishedTime(rs.getTimestamp("finishedtime"));
		fb.setState(rs.getString("state"));
		fb.setCargoId(rs.getInt("cargoid"));
		fb.setCargoAmount(rs.getString("cargoamount"));
		fb.setDetail(rs.getString("detail"));

		return fb;
	}

	@Override
	public Integer save(FreightBean fb) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = "INSERT INTO freight(providerid, driverid, "
				+ "driverpriority, managerpriority, cargopriority,"
				+ " priority, submittime, cargoid, cargoamount,detail) VALUES (?,?,?,3,?,?,now(),?,?,?)";

		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setInt(1, fb.getProviderId());
		ps.setInt(2, fb.getDriverId());
		ps.setInt(3, fb.getDriverPriority());
		ps.setInt(4, fb.getCargoPriority());
		ps.setInt(5, fb.getPriority());
		ps.setInt(6, fb.getCargoId());
		ps.setString(7, fb.getCargoAmount());
		ps.setString(8, fb.getDetail());
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
	public boolean confirmByMgr(FreightBean fb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE freight SET  managerpriority =? , priority =?,"
				+ "confirmtime = now() , detail =? ,state =?   WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, fb.getManagerPriority());
		ps.setInt(2, fb.getPriority());
		ps.setString(3, fb.getDetail());
		ps.setString(4, fb.getState());
		ps.setInt(5, fb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean confirmByDrv(FreightBean fb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE freight SET expectedtime  =? ,"
				+ "starttime = now() ,state =?   WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, fb.getExpectedTime());
		ps.setString(2, fb.getState());
		ps.setInt(3, fb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean confirmArrive(FreightBean fb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE freight SET arrivedtime  = now() "
				+ "  WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, fb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean confirmFinish(FreightBean fb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE freight SET  "
				+ "finishedtime = now() ,state =?   WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, fb.getState());
		ps.setInt(2, fb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "DELETE FROM freight WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public FreightBean get(int id) throws SQLException {
		FreightBean fb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM freight WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			fb = mapFreightBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return fb;
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
		String sql = "SELECT COUNT(id) FROM freight WHERE " + info;
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
		List<FreightBean> li = new ArrayList<FreightBean>();
		FreightBean fb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM freight  WHERE id <= (SELECT id FROM freight "
				+ " WHERE  "
				+ info
				+ " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			fb = mapFreightBean(rs);
			li.add(fb);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

}
