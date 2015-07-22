package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Description: GuardService接口 门卫查询数据和批准车辆入库的service。 <br/>
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
	 * 门卫获取当前停靠在大门处的车辆信息列表
	 * 
	 * @param guardId
	 *            guard不直接指向gate，而是由gate指向guard，则在这里去获取需要用到的gate。
	 * @return
	 */
	JSONArray getWaitingCarList(String guardId);

	/**
	 * 门卫允许车辆入库。
	 * 
	 * @param carId
	 * @return
	 */
	JSONObject acceptCarIn(String carId);
	
	/**
	 * 门卫允许车辆出库。
	 * 
	 * @param carId
	 * @return
	 */
	JSONObject acceptCarOut(String carId);
}
