package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description: FreightService，与Freight有关的操作的接口<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface FreightService {

	/**
	 * 根据条件返回Freight列表
	 * 
	 * @param info
	 * @param pageno
	 * @return
	 */
	JSONArray searchFreight(String info, String pageno);

	/**
	 * 添加新的FreightBean,这里要获得cargo对应的priority并自行计算第一次优先级
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject addFreight(FreightBean fb);

	// /**
	// * 更新FreightBean的基本信息，大多数东西不给与更改，如果写错，直接由供应商删除即可，但是管理员不能删除。这样就不需要更新操作了。
	// * @param fb
	// * @return
	// */
	// JSONObject updateFreight(FreightBean fb);

	// 为保证数据的正确性，要是运单能删能改，为保证数据的安全性，又要运单不能删不能改，最终，决定的结果是，只有管理员可以进行删除。
	// 且对于这次删除，是极其危险的，可能导致我这个闭环的系统完蛋。delete是软删除，若有外键联系时报错，无法删除。
	/**
	 * 删除指定id的Freight
	 * 
	 * @param id
	 * @return
	 */
	JSONObject delFreight(String id);

	/**
	 * 设定指定id运单直接完成。
	 * 
	 * @param id
	 * @return
	 */
	JSONObject finFreight(String id);

	/**
	 * 根据id刷新一个运单数据。
	 * 
	 * @param id
	 *            使用string做参数，是错误出在trycatch中，好返回错误原因给客户端。
	 * @return
	 */
	JSONObject getFreight(String id);

	/**
	 * 管理员确认运单，并确定优先级 ，改变Freight状态，并更改备注等。 优先级已经在前端计算好并返回，fbz中数据有 priority
	 * managerpriority detail 在后台要进行的处理有
	 * 更新state，添加confirmtime。设置对应driver的freightid。（推送消息）
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmByMgr(FreightBean fb);

	// 下面这几个都不算单纯的Freight操作了，都应该放在driver或map里。
	/**
	 * 司机确认运单，变更状态，并设置预计到达时间。 这里，司机还有一个为运单绑定新建的Car的操作。所以有一个新的car对象
	 * fb操作为设置carid，以及更新state和starttime（在dao中已实现starttime）和expectedtime。
	 * cb中有的数据经纬度，carno cartype lastltime driverid detail ,需要于后台补充的数据有：
	 * destination（调用map中获得门的方法获得门id），state固定。 同时获得db，对db进行的修改有carid， stopid
	 * ，state 。
	 * 
	 * @param fb
	 * @param cb
	 *            司机新建一个car对象 ，并在确认时获得定位信息，进行第一次定位。
	 * @return
	 */
	JSONObject confirmByDrv(FreightBean fb, CarBean cb);

	/**
	 * 到达大门，同时修改大门的当前停留车辆数。car的destination未变，仍为大门id，这样就可以由大门寻找到指定的car并获取drvier
	 * Freight等信息了
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmArrive(FreightBean fb);

	/**
	 * 更新当前位置信息,当在库外的位置信息，仅为在库外时进行位置的更新和判断。
	 * cb中要补充的数据为state，固定值，x,y固定为-1，destination从数据库获得当前值。
	 * 
	 * @param cb
	 *            中有的数据为，经纬度，driverid，更新数据时间，和距离。
	 * @return
	 */
	JSONObject updateSite(CarBean cb);

	/**
	 * 最终卸货完成后，更新，state，finish时间，和当前stop状态，等。
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmFinish(FreightBean fb);

}
