package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.vo.CargoBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.GuardBean;
import com.ll.vmsystem.vo.LineBean;
import com.ll.vmsystem.vo.ManagerBean;
import com.ll.vmsystem.vo.ProviderBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:ManagerService 管理员所有功能的服务。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface ManagerService {

	/**
	 * 管理员有权限修改自己和他人管理员的数据。
	 * 
	 * @return json 中会有 error标记，来记录出错详情。 acton中使用 json.has("error")来判断字段是否存在。
	 */
	JSONObject updateManager(ManagerBean mb);

	/**
	 * 管理员查询全部的帐号信息，根据role决定角色，
	 * 
	 * @param info
	 *            附加限制条件
	 * @param role
	 *            角色
	 * @param pageNo
	 *            选择页数
	 * @return
	 */
	JSONArray searchAccount(String info, String role, String pageNo);

	/**
	 * 管理员删除帐号的方法
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	JSONObject deleteAccount(String id, String role);

	/**
	 * 管理员添加各种帐号，由于每种帐号成员不同， 则无法放在一起作为一个service，结果只能在servlet中分开，这样不好s
	 * 
	 * @param mb
	 * @return
	 */
	JSONObject addManager(ManagerBean mb);

	/**
	 * 管理员分页 并根据条件查询StopBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchStop(String info, String pageNo);

	/**
	 * 管理员添加stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject addStop(StopBean sb);

	/**
	 * 管理员删除stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject deleteStop(StopBean sb);

	/**
	 * 管理员更新stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject updateStop(StopBean sb);

	/**
	 * 管理员分页 并根据条件查询CargoBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchCargo(String info, String pageNo);

	/**
	 * 管理员添加Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject addCargo(CargoBean cb);

	/**
	 * 管理员删除Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject deleteCargo(CargoBean cb);

	/**
	 * 管理员更新Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject updateCargo(CargoBean cb);

	/**
	 * 管理员分页 并根据条件查询LineBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchLine(String info, String pageNo);

	/**
	 * 管理员添加Line
	 * 
	 * @param lb
	 * @return
	 */
	JSONObject addLine(LineBean lb);

	/**
	 * 管理员删除Line
	 * 
	 * @param lb
	 * @return
	 */
	JSONObject deleteLine(LineBean lb);

	/**
	 * 管理员更新Line
	 * line这样的数据不应该提供更新操作，不然不好更新其他数据。
	 * @param lb
	 * @return
	 */
	JSONObject updateLine(LineBean lb);

	/**
	 * 管理员查询全部GateBean
	 * 
	 * @param id
	 * @return
	 */
	JSONArray searchGate(String id);

	/**
	 * 管理员添加Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject addGate(GateBean gb);

	/**
	 * 管理员删除Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject deleteGate(GateBean gb);

	/**
	 * 管理员更新Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject updateGate(GateBean gb);

	/**
	 * 添加 Guard帐号
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject addGuard(GuardBean gb);

	/**
	 * 更新 Guard帐号
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject updateGuard(GuardBean gb);

	/**
	 * 添加 Provider帐号
	 * 
	 * @param pb
	 * @return
	 */
	JSONObject addProvider(ProviderBean pb);

	/**
	 * 更新 Provider帐号
	 * 
	 * @param pb
	 * @return
	 */
	JSONObject updateProvider(ProviderBean pb);

	/**
	 * 添加 Driver帐号
	 * 
	 * @param db
	 * @return
	 */
	JSONObject addDriver(DriverBean db);

	/**
	 * 更新 Driver帐号
	 * 
	 * @param db
	 * @return
	 */
	JSONObject updateDriver(DriverBean db);
}
