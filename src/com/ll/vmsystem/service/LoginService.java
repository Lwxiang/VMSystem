package com.ll.vmsystem.service;

import org.json.JSONObject;

/**
 * Description:LoginService 登录service类，负责所有登录有关的操作的类。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface LoginService {

	/**
	 * Service层与Servlet层分离，后者只有表现的功能，而逻辑以及所有操作都在Service层中实现 <br/>
	 * 这个Service很简单，只有一个登录方法
	 * 
	 * @param id
	 * @param password
	 * @param role
	 *            为Servlet中获取的客户端的角色类型
	 * @return 处理好的JSONObject
	 */
	JSONObject login(int id, String password, String role);

	/**
	 * 在用户登录后，再在主界面处，获得cid，发送过来，有后台绑定帐号和cid。
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return 是否带error
	 */
	JSONObject addLoginInfo(String id, String role, String cid);

	/**
	 * 用户退出登录操作
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	JSONObject delLoginInfo(String id, String role);
}
