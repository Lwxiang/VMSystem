package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.GateBean;

/**
 * Description:GateDAO 接口 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface GateDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * 管理员创建gate对象，创建时需要参数为 name , type, longitude, latitude ,lineid ,detail
	 * 
	 * @param gb
	 * @return
	 * @throws SQLException
	 */
	Integer save(GateBean gb) throws SQLException;

	/**
	 * 根据id，改动大门的基础数据，改动的参数如创建时参数 name , type, longitude, latitude ,lineid
	 * ,detail
	 * 
	 * @param gb
	 * @return
	 * @throws SQLException
	 */
	boolean update(GateBean gb) throws SQLException;

	/**
	 * 根据id，改动当前等待车辆数量
	 * 
	 * @param add
	 *            车辆等待还是出发
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean carNumChange(boolean add, int id) throws SQLException;

	/**
	 * 根据id，改动大门工作状态
	 * 
	 * @param work
	 *            门卫下班还是开始工作
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean stateChange(boolean work, int id) throws SQLException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	GateBean get(int id) throws SQLException;

	/**
	 * 由于门卫，管理员，和大门的数据都是少量的，直接可以查询全部
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<GateBean> getAll() throws SQLException;

	/**
	 * 根据id删除指定项
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * 根据guardid找到对应的gateid
	 * 
	 * @param guardid
	 * @return 返回gateid，为0时表示错误，为找到对应guard对应的gateid。
	 * @throws Exception
	 */
	int getByGuard(int guardid) throws Exception;
}
