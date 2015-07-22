package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.ManagerBean;

/**
 * Description:ManagerDAO 接口 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface ManagerDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * 管理员创建Manager对象，创建时需要参数为 name,phone,password,otherphone,detail
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	Integer save(ManagerBean mb) throws SQLException;

	/**
	 * 管理员有权限增删改他人和自己的数据 根据id，改动管理员的基础数据，改动的参数如创建时参数
	 * name,phone,password,otherphone,detail
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	boolean update(ManagerBean mb) throws SQLException;

	/**
	 * 管理员登录，更新上次登录时间
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	boolean login(ManagerBean mb) throws SQLException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	ManagerBean get(int id) throws SQLException;

	/**
	 * 由于门卫，管理员，和大门的数据都是少量的，直接可以查询全部
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<ManagerBean> getAll() throws SQLException;

	/**
	 * 根据id删除指定项
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * 获得主要的管理员的id ，这里设置主管理员为备注为 MAIN的管理员。
	 * 
	 * @return
	 * @throws SQLException
	 */
	int getMainId() throws SQLException;
}
