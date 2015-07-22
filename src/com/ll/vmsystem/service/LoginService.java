package com.ll.vmsystem.service;

import org.json.JSONObject;

/**
 * Description:LoginService ��¼service�࣬�������е�¼�йصĲ������ࡣ <br/>
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
	 * Service����Servlet����룬����ֻ�б��ֵĹ��ܣ����߼��Լ����в�������Service����ʵ�� <br/>
	 * ���Service�ܼ򵥣�ֻ��һ����¼����
	 * 
	 * @param id
	 * @param password
	 * @param role
	 *            ΪServlet�л�ȡ�Ŀͻ��˵Ľ�ɫ����
	 * @return ����õ�JSONObject
	 */
	JSONObject login(int id, String password, String role);

	/**
	 * ���û���¼�����������洦�����cid�����͹������к�̨���ʺź�cid��
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return �Ƿ��error
	 */
	JSONObject addLoginInfo(String id, String role, String cid);

	/**
	 * �û��˳���¼����
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	JSONObject delLoginInfo(String id, String role);
}
