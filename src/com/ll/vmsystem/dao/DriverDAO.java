package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.DriverBean;

/**
 * Description:DriverDAO 接口 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface DriverDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * 创建，无id和state，carid,freightid,stopid
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	Integer save(DriverBean db) throws SQLException;

	/**
	 * 更新， 只能更新name ,password , phone, otherphone ,detail
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean update(DriverBean db) throws SQLException;

	/**
	 * 司机开始运单时的业务，这里在业务层将需要更新的数据都存放在javabean中传递过来 需要进行更新的数据有：
	 * carid,stopid,state,
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean startTrans(DriverBean db) throws SQLException;

	/**
	 * 管理员确认运单时，连接司机和运单，即设置司机的freightid。
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean linkFreight(DriverBean db) throws SQLException;

	/**
	 * 完成运输时，也进行一次更新，这次将state继续更新，同时将 carid,freightid,stopid 设置为null
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean endTrans(DriverBean db) throws SQLException;

	/**
	 * 根据id删除指定的司机数据，一般不会使用到
	 * 
	 * @param id
	 * @return 删除结果
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * 查，根据id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	DriverBean get(int id) throws SQLException;

	/**
	 * 根据详细约束，获取符合要求的分页数据
	 * 
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	PageList searchByInfo(int pageNo, String info) throws SQLException;
}
