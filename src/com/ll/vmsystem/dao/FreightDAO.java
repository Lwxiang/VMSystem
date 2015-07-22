package com.ll.vmsystem.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description:FreightDAO 接口
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface FreightDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * 表中插入数据，无id，detail为“”，其他所有非null列作为数据
	 * @param fb FreightBean
	 * @return 返回自增主键的值
	 * @throws SQLException 
	 */
	Integer save(FreightBean fb) throws SQLException;
	
	/**
	 * 此为管理员确认运单时进行的修改操作，由于要计算优先级，则需要获得数据，必须
	 * 在业务层先获得fb然后计算得到最终优先级来进行更新，则必须两次数据库操作，所以
	 * 这里这个函数就用来将fb中相应数据进行更新，根据fb中的id，更新的数据有：
	 * managerpriority , priority , confirmtime , detail,state . 5个数据
	 * @param fb 这里就是我为什么要使用Integer的原因，使用Object可以为NULL，fb中有多个数据为空
	 * @return 修改结果
	 * @throws SQLException 
	 */
	boolean confirmByMgr(FreightBean fb) throws SQLException;
	
	/**
	 * 此为司机确认订单时进行的修改操作，只修改starttime, expectedtime,和state
	 * state 可直接由状态变化可知，则需要改动的只有两个时间，而传递的数据只有三个
	 * 两个时间加一个id，故，无必要使用FreightBean作为参数。但这无关痛痒，
	 * @param fb FreightBean
	 * @return 修改结果
	 * @throws SQLException 
	 */
	boolean confirmByDrv(FreightBean fb) throws SQLException;
	
	/**
	 * 此为司机到达园区大门后提交的信息更新请求，需要更新的数据为添加
	 * arrivedtime 车辆到达时间。
	 * 之后所有的参数都为java BEAN，原因很简单
	 * 首先，我会在具体实现中设计其对各字段的使用情况，而且，这样函数为更加好看一点。
	 * 所有的更新的函数的参数相同，这样整洁美观。
	 * @param fb FreightBean
	 * @return 修改结果
	 * @throws SQLException 
	 */
	boolean confirmArrive(FreightBean fb) throws SQLException;
	
	/**
	 * 车辆卸货完成时的修改操作，
	 * 更新state为 FINISHED
	 * 同时更新finishedtime
	 * @param fb FreightBean
	 * @return  修改结果
	 * @throws SQLException 
	 */
	boolean confirmFinish(FreightBean fb) throws SQLException;
	
	/**
	 * 根据id删除指定的运单数据，一般不会使用到
	 * @param id int不是integer，这里使用int作为参数，其实可以保证参数安全，不会为null
	 * @return 删除结果
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * 根据id获取对应数据
	 * @param id
	 * @return FreightBean
	 * @throws SQLException 
	 */
	FreightBean get(int id) throws SQLException;
	
	/**
	 * 根据一些详细的细节划分来寻找符合要求的分页后的数据。
	 * 这里需要注意一点是，细节设定应是简单的，且不会影响数据排序的条件，即
	 * 所有的查询结果都是以id降序的，这是固定的不可变的
	 * @param pageNo 查询的页数
	 * @param info 附加的sql语句，中为对数据的一些限定条件，不能使用order by语句 ,里面至少有一句“1=1”。
	 * @return 整合页数和list后的PageList
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
