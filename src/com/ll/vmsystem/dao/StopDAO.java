package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:StopDAO 接口
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface StopDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * 创建对象， 创建时需要的参数有
	 * name , longitude , latitude, x ,y ,lineid , distance ,detail
	 * @param sb
	 * @return 自增主键
	 * @throws SQLException 
	 */
	Integer save(StopBean sb) throws SQLException;
	
	/**
	 * 根据id删除指定stop数据
	 * @param id
	 * @return 删除结果
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * 管理员进行数据的更新操作，能够修改的数据有
	 * name , longitude , latitude, x ,y ,lineid , distance ,detail
	 * @param sb
	 * @return 更新结果
	 * @throws SQLException 
	 */
	boolean update(StopBean sb) throws SQLException;

	/**
	 * 车辆到达卸货点后以及卸货完成进行更新，根据id，更改卸货点state ，和当前停靠车辆 carid
	 * 这里可以做的一点优化是 根据sb中的state进行判断，决定是将carid设置为null与否。
	 * state的判断自行在业务层中决定，这些状态的枚举不应出现在DAO层，则DAO做的判断只是
	 * StopBean中的getCarid 是否为null，如为null，则置为空
	 * 即，所有的计算判断都放在业务层，DAO只是根据 计算的到的数据来 更新数据库。
	 * @param sb
	 * @return 更新结果
	 * @throws SQLException 
	 */
	boolean discharge(StopBean sb) throws SQLException;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	StopBean get(int id) throws SQLException;
	
	/**
	 * 获得全部卸货点信息
	 * @return
	 * @throws SQLException
	 */
	List<StopBean> getAll() throws SQLException;
	
	/**
	 * 根据info中的详细设定来获取分页后的数据。
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
