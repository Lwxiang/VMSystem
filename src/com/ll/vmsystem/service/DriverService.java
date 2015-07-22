package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Description: DriverService接口 处理司机一些消息状态的service。 <br/>
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
	 * 获得driver最新的所有相关数据。一般为供应商信息，车辆信息，运单信息 ，卸货点信息，大门信息<br/>
	 * 这时返回的顺序，必须牢记： driverBean,providerbean,freightbean, carben,in
	 * gatebean,stopbean,outgatebean,最后一位放error<br/>
	 * 再思考下，与其每次有不同的顺序，不如设置为空的项就为空？
	 * 
	 * @param id
	 * @param driverState
	 * @return
	 */
	JSONArray getDriverInfo(String id, String driverState);

	/**
	 * 客户端确认到达出库大门处，传来出库大门对应的门卫id，给门卫发送消息，提示车辆到达，需要批准出库。
	 * @param guardid
	 * @return
	 */
	JSONObject sumbitArrivedOutGate(String carid,String gateid);
}
