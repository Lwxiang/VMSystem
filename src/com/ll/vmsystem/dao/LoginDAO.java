package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: LoginDAO 接口 ，对login表中当前登录在线的帐号数据进行处理 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface LoginDAO {

	/**
	 * 设置Conncetion，整个DAO层对象使用一个连接，并在Service层实现事物处理。
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * 登录操作，将当前登录的帐号信息保存在login表中，同时，若帐号已存在，则更新cid和登录时间等信息
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return
	 */
	boolean loignIn(int id, String role, String cid) throws SQLException;;

	/**
	 * 登出操作，这里将操作都进行了整合，因为我已经完全的知道业务了。。。
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	boolean loginOut(int id, String role) throws SQLException;

	/**
	 * 检测该帐号是否登录之 是否在表中已经记录。
	 * 
	 * @param id
	 * @param role
	 * @return 返回是否在表中登录
	 * @throws SQLException
	 */
	boolean isLogin(int id, String role) throws SQLException;

	/**
	 * 获取已登录帐号当前的CID，之后用于消息推送。
	 * 
	 * @param id
	 * @param role
	 * @return
	 * @throws SQLException
	 */
	String getCID(int id, String role) throws SQLException;

	/**
	 * 当当前帐号id和角色决定的一个帐号已经在本系统中登录时，再次登录应该更新一些数据，如CID
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return
	 */
	boolean updateLogin(int id, String role, String cid) throws SQLException;

	/**
	 * 获得当前登录的帐号的总数
	 * 
	 * @return
	 * @throws SQLException 
	 */
	int countNum() throws SQLException;

}
