package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Description: DriverService�ӿ� ����˾��һЩ��Ϣ״̬��service�� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface DriverService {

	/**
	 * ���driver���µ�����������ݡ�һ��Ϊ��Ӧ����Ϣ��������Ϣ���˵���Ϣ ��ж������Ϣ��������Ϣ<br/>
	 * ��ʱ���ص�˳�򣬱����μǣ� driverBean,providerbean,freightbean, carben,in
	 * gatebean,stopbean,outgatebean,���һλ��error<br/>
	 * ��˼���£�����ÿ���в�ͬ��˳�򣬲�������Ϊ�յ����Ϊ�գ�
	 * 
	 * @param id
	 * @param driverState
	 * @return
	 */
	JSONArray getDriverInfo(String id, String driverState);

	/**
	 * �ͻ���ȷ�ϵ��������Ŵ�������������Ŷ�Ӧ������id��������������Ϣ����ʾ���������Ҫ��׼���⡣
	 * @param guardid
	 * @return
	 */
	JSONObject sumbitArrivedOutGate(String carid,String gateid);
}
