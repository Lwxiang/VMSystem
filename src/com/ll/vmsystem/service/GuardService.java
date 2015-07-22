package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Description: GuardService�ӿ� ������ѯ���ݺ���׼��������service�� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-04
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface GuardService {

	/**
	 * ������ȡ��ǰͣ���ڴ��Ŵ��ĳ�����Ϣ�б�
	 * 
	 * @param guardId
	 *            guard��ֱ��ָ��gate��������gateָ��guard����������ȥ��ȡ��Ҫ�õ���gate��
	 * @return
	 */
	JSONArray getWaitingCarList(String guardId);

	/**
	 * ������������⡣
	 * 
	 * @param carId
	 * @return
	 */
	JSONObject acceptCarIn(String carId);
	
	/**
	 * �������������⡣
	 * 
	 * @param carId
	 * @return
	 */
	JSONObject acceptCarOut(String carId);
}
