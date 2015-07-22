package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: MessageDAO 接口, 对当前的离线消息message表中数据进行操作。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface MessageDAO {

	/**
	 * 获得连接S
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	// 先花些篇幅来完整的定义一下message，message应该是一些数，根据不同的role，不同的数对应不同的数据
	// 对于 manager 只有 message1，对应为当前新提交的待审核的运单数。
	// 对于guard 只有message1，对应为当前新来到大门处的车辆数。
	// 对于driver
	// 有message1，和message2，一为新的待确认的运单，但运单注定只有一个，不可能出错。2为门卫的审核处理消息，也是只有1个
	// 对于Provider 暂时只有一个message，即管理员审核的运单数，管理员审核好，会同时给供应商和司机发送推送。
	// 之后考虑对供应商添加新的消息，如运单完成的消息。
	// message是一个数字，则我可以想一个高端的设计，利用数字的特性 定义message为一个int
	// ，int为4个字节，则用一个字节表示一个message
	// 哈哈，虽然之后写的系统都应该是面向对象的方便的算法，但这里，使用message的获取，应该为内联函数才能够效率高。

	/**
	 * 添加一条消息，因为是一条消息，所以message只要根据1234进行划分即可,message 从右向左，第一个字节为0，第二个字节为1。。。
	 * 
	 * @param id
	 * @param role
	 * @param message
	 *            消息总是只有一个，返回当前消息的排位。从0开始
	 * @throws SQLException
	 */
	void addMessage(int id, String role, int message) throws SQLException;

	/**
	 * 检测当前该帐号是否有消息。
	 * 
	 * @param id
	 * @param role
	 * @return message的值，若为0表示不存在离线消息，否则即为离线消息的值。
	 * @throws SQLException
	 */
	int checkMessage(int id, String role) throws SQLException;

	/**
	 * 登录时进行的错，先检测是否有离线消息，如果有，则发送离线消息。
	 * 
	 * @param id
	 * @param role
	 * @throws SQLException
	 */
	void pushLeavingMessage(int id, String role) throws SQLException;
}
