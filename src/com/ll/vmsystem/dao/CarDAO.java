package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CarBean;

/**
 * Description:CarDAO 接口 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface CarDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * 车辆的创建为司机确认运单时，填写的车辆信息表单 上的数据获取。提交表单时，android端开启定位 并将第一次定位结果作为表单中数据一并提交
	 * 则，创建的参数为 去处id，lineid，distance三个字段后
	 * 这里其实不应该有第一次定位的信息，原因很简单，这里是提交时操作，提交时，并未打开定位，而在程序跳转到定位界面进行
	 * 初始化时，才能获得第一次定位数据，并返回，而且，要注意的一点就是在库外的定位数据，都是无法获得正确的xy返回的，这里
	 * 不用担心，直接存入-1，-1，而初始化亦是如此，存入-1，-1。 最后还是没办法，在一个新的Activity中获得当前的位置。
	 * 
	 * @param cb
	 * @return
	 */
	Integer save(CarBean cb) throws Exception;

	/**
	 * 根据id删除指定car信息
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(int id) throws Exception;

	/**
	 * 定时获取定位信息的位置更新方法，更新的数据有，在库外的位置信息不更新。
	 * longitude，latitude，x，y，lastltime，lineid，distance，state，destination
	 * 值都为在客户端计算后的结果，客户端进行各种计算，或者在业务逻辑层？
	 * 对于x,y的更新与客户端计算，而其他数据需要联系数据库，还是要在业务逻辑层完成计算
	 * 
	 * @param cb
	 * @return
	 */
	boolean updateSite(CarBean cb) throws Exception;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	CarBean get(int id) throws Exception;

	/**
	 * 根据限定条件分页查询
	 * 
	 * @param pageNo
	 * @param info
	 * @return
	 */
	PageList searchByInfo(int pageNo, String info) throws Exception;

	/**
	 * 获取gateid对应的大门处停留的全部车辆列表。
	 * 
	 * @param gateId
	 * @return
	 * @throws Exception
	 */
	List<CarBean> getCarAtGate(int gateId) throws Exception;

	/**
	 * 获得全部车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	List<CarBean> getAll() throws Exception;

}
