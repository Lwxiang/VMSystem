package com.ll.vmsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ll.vmsystem.dao.CargoDAO;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CargoBean;

public class CargoDAOImpl implements CargoDAO {

	/**
	 * 当前连接
	 */
	private Connection conn;

	@Override
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 将rs中的数据加载到 CargoBean对象中并输出。
	 * 
	 * @param rs
	 * @return cb
	 * @throws SQLException
	 */
	private CargoBean mapCargoBean(ResultSet rs) throws SQLException {
		CargoBean cb = new CargoBean();

		cb.setId(rs.getInt("id"));
		cb.setName(rs.getString("name"));
		cb.setCargoPriority(rs.getInt("cargopriority"));
		cb.setProviderId(rs.getInt("providerid"));
		cb.setStopId(rs.getInt("stopid"));
		cb.setDetail(rs.getString("detail"));

		return cb;
	}

	@Override
	public Integer save(CargoBean cb) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		String sql = " INSERT INTO cargo(name,cargopriority,providerid,stopid,detail) "
				+ "  VALUES (?,?,?,?,?)";
		ps = conn
				.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, cb.getName());
		ps.setInt(2, cb.getCargoPriority());
		ps.setInt(3, cb.getProviderId());
		ps.setInt(4, cb.getStopId());
		ps.setString(5, cb.getDetail());
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
	public boolean delete(int id) throws Exception {
		PreparedStatement ps = null;
		String sql = "DELETE FROM cargo WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
		ps = null;

		return true;
	}

	@Override
	public boolean update(CargoBean cb) throws SQLException {
		PreparedStatement ps = null;
		String sql = "UPDATE cargo SET  stopid =? , detail =?,"
				+ "name = ? , cargopriority =?    WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, cb.getStopId());
		ps.setString(2, cb.getDetail());
		ps.setString(3, cb.getName());
		ps.setInt(4, cb.getCargoPriority());
		ps.setInt(5, cb.getId());
		ps.executeUpdate();
		ps.close();
		ps = null;
		return true;
	}

	@Override
	public CargoBean get(int id) throws SQLException {
		CargoBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT *  FROM cargo WHERE id = ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCargoBean(rs);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
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
		String sql = "SELECT COUNT(id) FROM cargo WHERE " + info;
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
		List<CargoBean> li = new ArrayList<CargoBean>();
		CargoBean cb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageNum = 0;
		String sql = "SELECT * FROM cargo  WHERE id <= (SELECT id FROM cargo "
				+ " WHERE  " + info + " ORDER BY id DESC LIMIT ?,1 ) AND +  "
				+ info + " ORDER BY id DESC  LIMIT ?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, (pageNo - 1) * PageList.pageSize);
		ps.setInt(2, PageList.pageSize);
		rs = ps.executeQuery();
		while (rs.next()) {
			cb = mapCargoBean(rs);
			li.add(cb);
		}
		pageNum = getPageNum(conn, info);
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		return new PageList(pageNo, pageNum, li);
	}

}
