package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.GuardBean;

/**
 * Description:GuardDAO 接口
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-02
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface GuardDAO {
	
	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	/**
	 * 管理员创建Gunard对象，创建时需要参数为
	 *  name,phone,password,otherphone,detail,gateid
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	Integer save(GuardBean gb) throws SQLException;
	
	/**
	 * 根据id，改动门卫的基础数据，改动的参数如创建时参数
	 * name,phone,password,otherphone,detail,gateid
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	boolean update(GuardBean gb) throws SQLException;
	
	/**
	 * 门卫登录，更新上次登录时间
	 * lastlogin
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	boolean login(GuardBean gb) throws SQLException;
	

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	GuardBean get(int id) throws SQLException;
	
	/**
	 * 由于门卫，管理员，和大门的数据都是少量的，直接可以查询全部
	 * @return
	 * @throws SQLException 
	 */
	List<GuardBean> getAll() throws SQLException;
	
	/**
	 * 根据id删除指定项
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
}
