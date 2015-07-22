package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.ProviderBean;

/**
 * Description:providerDAO接口，要实现的函数有CRUD
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface ProviderDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	/**
	 * 将provider数据存储，这里要注意id应该是空，而返回id级自增主键的值
	 * @param pb 	ProviderBean
	 * @return	存储的对象被数据库赋予的id
	 * @throws SQLException 
	 */
	Integer save(ProviderBean pb) throws SQLException;
	
	/**
	 * 根据id删除指定项，之后可能有批量删除
	 * @param id	主键
	 * @return	删除结果
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	
	/**
	 * 更新Provider信息。这里更新会根据id选择 要修改除id外所有的项
	 * @param pb ProviderBean
	 * @return 更新结果
	 * @throws SQLException 
	 */
	boolean  update(ProviderBean pb) throws SQLException;
	
	/**
	 * 当供应商增删司机时，更新其拥有司机数量
	 * @param id 供应商id
	 * @param increase true为增加，false 为删除
	 * @return 更新结果
	 * @throws SQLException 
	 */
	boolean updateCarNum(Integer id,boolean increase) throws SQLException;
	
	/**
	 * 根据主键加载provider数据
	 * @param id 主键
	 * @return 对应主键的provider ，如果对象不存在，返回NULL
	 * @throws SQLException 
	 */
	ProviderBean get(int id) throws SQLException;
	
	/**
	 * 检索获得全部provider信息,查询全部为分页查询，且返回当前页数与全部数据页数。
	 * @return 数据库内全部的provider,当全部不存在时，返回为空的arraylist
	 * @throws SQLException 
	 */
	PageList getAll(int pageNo) throws SQLException;

}
