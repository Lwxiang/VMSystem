package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.LineBean;

/**
 * Description:LineDAO 接口
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface LineDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * 插入line，创建时拥有的参数为：
	 * name, a_longitude ,a_latitude ,b_longitude, b_latitude,
	 *  a_x, a_y ,b_x ,b_y, length, A, B ,C ,detail
	 * @param lb
	 * @return
	 * @throws SQLException 
	 */
	Integer save(LineBean lb) throws SQLException;
	
	/**
	 * 管理员更新基础数据，则可以更新的数据为全部创建数据
	 * name, a_longitude ,a_latitude ,b_longitude, b_latitude,
	 *  a_x, a_y ,b_x ,b_y, length, A, B ,C ,detail
	 * @param lb
	 * @return
	 * @throws SQLException 
	 */
	boolean update(LineBean lb) throws SQLException;
	
	/**
	 * 一般一辆车辆的进入或离开道路时进行的车辆数量的更新】
	 * 这里进入道路为根据导航路线 进行分析的到的道路。
	 * @param add
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean carNumChange(boolean add,int id) throws SQLException;

	/**
	 * 删除指定
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	LineBean get(int id) throws SQLException;
	
	/**
	 * 在路况查询中用到的所有道路信息的获取。
	 * @return
	 * @throws SQLException
	 */
	List<LineBean> getall() throws SQLException;
	
	/**
	 * 根据设定查询分页数据
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
	
	
	
}
