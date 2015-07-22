package com.ll.vmsystem.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import com.ll.vmsystem.vo.*;

/**
 * Description:JSONUtils json工具类，负责将需要的数据全部放入json对象中 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class JSONUtils {

	/**
	 * 将ManagerBean对象的数据存如JSONObject
	 * 
	 * @param json
	 * @param mb
	 * @throws JSONException
	 */
	public static void putManagerBean(JSONObject json, ManagerBean mb)
			throws JSONException {
		json.put("id", mb.getId());
		json.put("name", mb.getName());
		json.put("phone", mb.getPhone());
		json.put("password", mb.getPassword());
		json.put("otherphone", mb.getOtherphone());
		json.put("lastlogin", mb.getLastlogin().toString());
		json.put("detail", mb.getDetail());
	}

	/**
	 * 将 DriverBean 对象数据保存进json对象中。
	 * 
	 * @param json
	 * @param db
	 * @throws JSONException
	 */
	public static void putDriverBean(JSONObject json, DriverBean db)
			throws JSONException {
		json.put("id", db.getId());
		json.put("name", db.getName());
		json.put("phone", db.getPhone());
		json.put("password", db.getPassword());
		json.put("otherphone", db.getOtherphone());
		json.put("detail", db.getDetail());
		json.put("carid", db.getCarid());
		json.put("freightid", db.getFreightid());
		json.put("stopid", db.getStopid());
		json.put("state", db.getState());
		json.put("providerid", db.getProviderid());
	}

	/**
	 * 将GuardBean 数据存放如json对象中
	 * 
	 * @param json
	 * @param gb
	 * @throws JSONException
	 */
	public static void putGuardBean(JSONObject json, GuardBean gb)
			throws JSONException {
		json.put("id", gb.getId());
		json.put("name", gb.getName());
		json.put("phone", gb.getPhone());
		json.put("password", gb.getPassword());
		json.put("otherphone", gb.getOtherphone());
		json.put("detail", gb.getDetail());
		json.put("lastlogin", gb.getLastlogin().toString());
	}

	/**
	 * 将 ProviderBean 对象中的数据存放如 json对象中。
	 * 
	 * @param json
	 * @param pb
	 * @throws JSONException
	 */
	public static void putProivderBean(JSONObject json, ProviderBean pb)
			throws JSONException {
		json.put("id", pb.getId());
		json.put("name", pb.getName());
		json.put("phone", pb.getPrincipalphone());
		json.put("principalname", pb.getPrincipalname());
		json.put("password", pb.getPassword());
		json.put("otherphone", pb.getOtherphone());
		json.put("detail", pb.getDetail());
		json.put("drivernum", pb.getDrivernum());
	}

	/**
	 * 将GateBean对象中数据保存入json对象中。
	 * 
	 * @param json
	 * @param gb
	 * @throws JSONException
	 */
	public static void putGateBean(JSONObject json, GateBean gb)
			throws JSONException {
		json.put("id", gb.getId());
		json.put("name", gb.getName());
		json.put("state", gb.getState());
		json.put("waitingcars", gb.getWaitingcars());
		json.put("type", gb.getType());
		json.put("longitude", gb.getLongitude());
		json.put("latitude", gb.getLatitude());
		json.put("x", gb.getX());
		json.put("y", gb.getY());
		json.put("lineid", gb.getLineid());
		json.put("detail", gb.getDetail());
		json.put("guardid", gb.getGuardid());
	}

	/**
	 * 将LineBean对象放入json中
	 * 
	 * @param json
	 * @param lb
	 * @throws JSONException
	 */
	public static void putLineBean(JSONObject json, LineBean lb)
			throws JSONException {
		json.put("id", lb.getId());
		json.put("name", lb.getName());
		json.put("a_longitude", lb.getA_longitude());
		json.put("a_latitude", lb.getA_latitude());
		json.put("b_longitude", lb.getB_longitude());
		json.put("b_latitude", lb.getB_latitude());
		json.put("a_x", lb.getA_x());
		json.put("a_y", lb.getA_y());
		json.put("b_x", lb.getB_x());
		json.put("b_y", lb.getB_y());
		json.put("length", lb.getLength());
		json.put("A", lb.getA());
		json.put("B", lb.getB());
		json.put("C", lb.getC());
		json.put("carnum", lb.getCarnum());
		json.put("detail", lb.getDetail());
	}

	/**
	 * 将StopBean对象的数据存入 Json中。
	 * 
	 * @param json
	 * @param sb
	 * @throws JSONException
	 */
	public static void putStopBean(JSONObject json, StopBean sb)
			throws JSONException {
		json.put("id", sb.getId());
		json.put("name", sb.getName());
		json.put("longitude", sb.getLongitude());
		json.put("latitude", sb.getLatitude());
		json.put("x", sb.getX());
		json.put("y", sb.getY());
		json.put("lineid", sb.getLineid());
		json.put("distance", sb.getDistance());
		json.put("state", sb.getState());
		json.put("carid", sb.getCarid());
		json.put("detail", sb.getDetail());
	}

	/**
	 * 想json中放入FreightBean
	 * 
	 * @param json
	 * @param fb
	 * @throws JSONException
	 */
	public static void putFreightBean(JSONObject json, FreightBean fb)
			throws JSONException {
		json.put("id", fb.getId());
		json.put("providerid", fb.getProviderId());
		json.put("driverid", fb.getDriverId());
		json.put("driverpriority", fb.getDriverPriority());
		json.put("managerpriority", fb.getManagerPriority());
		json.put("cargopriority", fb.getCargoPriority());
		json.put("priority", fb.getPriority());
		json.put("submittime", fb.getSubmitTime().toString());
		if (fb.getConfirmTime() != null) {
			json.put("confirmtime", fb.getConfirmTime().toString());
		}
		if (fb.getExpectedTime() != null) {
			json.put("expectedtime", fb.getExpectedTime().toString());
		}
		if (fb.getArrivedTime() != null) {
			json.put("arrivedtime", fb.getArrivedTime().toString());
		}
		if (fb.getStartTime() != null) {
			json.put("starttime", fb.getStartTime().toString());
		}
		if (fb.getFinishedTime() != null) {
			json.put("finishedtime", fb.getFinishedTime().toString());
		}

		json.put("state", fb.getState());
		json.put("cargoid", fb.getCargoId());
		json.put("cargoamount", fb.getCargoAmount());
		json.put("detail", fb.getDetail());
	}

	/**
	 * 向json中放入CargoBean
	 * 
	 * @param json
	 * @param cb
	 * @throws JSONException
	 */
	public static void putCargoBean(JSONObject json, CargoBean cb)
			throws JSONException {
		json.put("id", cb.getId());
		json.put("name", cb.getName());
		json.put("cargopriority", cb.getCargoPriority());
		json.put("providerid", cb.getProviderId());
		json.put("stopid", cb.getStopId());
		json.put("detail", cb.getDetail());
	}

	/**
	 * 向json中放入CarBean
	 * 
	 * @param json
	 * @param cb
	 * @throws JSONException
	 */
	public static void putCarBean(JSONObject json, CarBean cb)
			throws JSONException {
		json.put("id", cb.getId());
		json.put("carno", cb.getCarno());
		json.put("driverid", cb.getDriverid());
		json.put("longitude", cb.getLongitude());
		json.put("latitude", cb.getLatitude());
		json.put("x", cb.getX());
		json.put("y", cb.getY());
		json.put("lastltime", cb.getLastltime());
		json.put("cartype", cb.getCartype());
		json.put("state", cb.getState());
		json.put("destination", cb.getDestination());
		if (null != cb.getLineid() ) {
			json.put("lineid", cb.getLineid());
		}
		// 这个distance还表示司机距离园区的大致距离
		json.put("distance", cb.getDistance());
		json.put("detail", cb.getDetail());

		if (null != cb.getProviderName()) {
			// 从司机处获得位置信息不是这个car信息。
			json.put("providerName", cb.getProviderName());
			json.put("driverName", cb.getDriverName());
			json.put("starttime", cb.getStarttime().toString());
			json.put("proirity", cb.getProirity().toString());
			json.put("stopname", cb.getStopname());
			json.put("stopstate", cb.getStopstate());
		}

	}

}