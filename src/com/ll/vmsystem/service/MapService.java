package com.ll.vmsystem.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.LineBean;

/**
 * Description:MapService 所有地图相关的操作 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface MapService {

	/**
	 * 获得所有的地图信息，地图信息有gate ,line 和 stop三种元素
	 * 
	 * @return JSONArray中包含三个JSONArray ..,对应三种元素 存放顺序， gate, line ,stop ,第四个为
	 *         errorflag检测
	 */
	JSONArray getAllMapinfo();

	/**
	 * 获得导航时需要的全部道路信息，全部门信息和一个目标卸货点信息
	 * 
	 * @param id
	 *            stop
	 * @return
	 */
	JSONArray getMapStopInfo(String id);

	/**
	 * 获得最新的车辆信息数据。
	 * 
	 * @param id
	 * @return
	 */
	JSONObject getCarInfo(String id);

	/**
	 * 获取车辆进入仓库需要前往的大门，暂时只提供一个大门，即东门
	 * 
	 * @return 大门id
	 */
	int getInGate();

	/**
	 * 获得车辆离开仓库需要前往的大门，暂时只提供一个大门，即西门
	 * 
	 * @return 大门ID
	 */
	int getOutGate();

	/**
	 * 根据经纬度和指向line的id，来获取当前坐标点在line上的位置,这里是获得点在line上投影的线段长度，带正负
	 * 
	 * @param longitude
	 * @param latitude
	 * @param lb
	 *            直接传入一个获得的linebean
	 * @return int类型的距离，有正负，且可以大于line的长度。
	 * @throws Exception
	 */
	int getDistance(double longitude, double latitude, LineBean lb)
			throws Exception;

	/**
	 * 获取car的详细信息，这里用于调用
	 * 
	 * @param conn
	 * @param carid
	 * @return
	 * @throws Exception
	 */
	CarBean getDetailCarInfo(Connection conn, int carid) throws Exception;

	/**
	 * 检测车辆是否到达园区。
	 * 
	 * @param cb
	 * @return
	 * @throws Exception
	 */
	boolean checkCarArrivded(CarBean cb) throws Exception;

	/**
	 * 更新最新的 车辆位置信息，只有当车辆更改了道路后，才会调用这个方法，更新车辆位置和车辆当前所指向的道路id。
	 * 
	 * @param id
	 * @return
	 */
	JSONObject updateCarLineInfo(CarBean cb, String newlineid);

	/**
	 * 更新车辆到达卸货点的状态。
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject carArrivedStop(CarBean cb);

	/**
	 * 根据司机id，更新当前车辆状态，由卸货中变成出库导航中，同时设置运单完成，但是司机和车辆状态还在。
	 * 
	 * @param driverid
	 * @return
	 */
	JSONObject driverEndDischarge(String driverid);

}
