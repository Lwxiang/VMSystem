package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CargoBean;

/**
 * Description:CargoDAO 接口
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface CargoDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * 增
	 * @param cb CargoBean
	 * @return 自增主键
	 * @throws SQLException 
	 */
	Integer save(CargoBean cb) throws SQLException;
	
	/**
	 * 根据id删除
	 * @param id 
	 * @return 删除结果
	 * @throws Exception 
	 */
	boolean delete(int id) throws Exception;
	
	/**
	 * 改,如果有改这个功能，则改的只有stopid。detail和name 和cargopriority；
	 * @param cb
	 * @return 修改结果
	 * @throws SQLException 
	 */
	boolean update(CargoBean cb) throws SQLException;
	
	/**
	 * 查
	 * @param id
	 * @return 根据id查询结果
	 * @throws SQLException 
	 */
	CargoBean get(int id) throws SQLException;
	
	/**
	 * 分页查询 , 可能的条件如指定 卸货点的货物，或者指定供应商的全部货物，或者定优先级的货物等。
	 * @param pageNo
	 * @return 查询结果
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
