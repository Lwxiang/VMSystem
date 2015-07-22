package com.ll.vmsystem.utilities;

/**
 * Description:EnumUtils enum工具类，主要存放所有state状态的枚举 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class EnumUtils {
	/**
	 * 运单状态，submitted提交完成,confirmed确认完成,inway运输过程中， finished运单完成，所有状态都使用大写
	 */
	public static enum freightState {
		SUBMITTED, CONFIRMED, INWAY, FINISHED
	};

	/**
	 * 司机状态，off关闭，working 运输中
	 */
	public static enum DriverState {
		OFF, WORKING
	};

	/**
	 * 车辆状态，（无OFF无任务），STARTED已出发，WAITING在园区门口等待进入，
	 * BEFORE库内卸货前，discharging卸货中，After卸货后， 这三个状态都为在库内的状态，(出库后又OFF )
	 */
	public static enum CarState {
		STARTED, WAITING, BEFORE, DISCHARGING, AFTER
	};

	/**
	 * 卸货点状态，free空闲， discharging卸货中。
	 */
	public static enum StopState {
		FREE, DISCHARGING
	};

	/**
	 * 大门状态，OFF 关闭， WORKING 工作中。
	 */
	public static enum GateState {
		OFF, WORKING
	};

	/**
	 * 大门类型 ， IN 只进， OUT，只出
	 */
	public static enum GateType {
		IN, OUT
	};

	/**
	 * 登录状态，成功与失败
	 */
	public static enum LoginState {
		SUCCESS, FAILED
	};

	/**
	 * 四种角色。
	 */
	public static enum AppRole {
		MANAGER, PROVIDER, GUARD, DRIVER
	};

	/**
	 * 所有的manager可用的service
	 */
	public static enum ManagerService {
		UPDATE_MANAGER, SEARCH_ACCOUNT, DELETEACCOUNT, ADD_ACCOUNT, SEARCH_STOP, ADD_STOP, DELETE_STOP, UPDATE_STOP, SEARCH_CARGO, ADD_CARGO, DELETE_CARGO, UPDATE_CARGO, SEARCH_LINE, ADD_LINE, DELETE_LINE, UPDATE_LINE, SEARCH_GATE, ADD_GATE, DELETE_GATE, UPDATE_GATE, ADD_GUARD, UPDATE_GUARD, ADD_PROVIDER, UPDATE_PROVIDER, ADD_DRIVER, UPDATE_DRIVER

	};

	/**
	 * 所有的map可用的service
	 */
	public static enum MapService {
		GET_MAPINFO, GET_MAPSTOP, REFRESH_CAR ,UPDATE_LINE ,ARRIVED_STOP,FINISH_FREIGHT 
	};

	/**
	 * 登录相关操作
	 * 
	 */
	public static enum LoginService {
		CHECK_IDPW, LOGIN_IN, LOGIN_OUT
	};

	/**
	 * 所有driver可用的管理系统的service
	 * 
	 */
	public static enum DriverService {
		UPDATE_INFO, ARRIVED_OUTGATE
	};

	/**
	 * 所有Freight相关的操作常量枚举
	 */
	public static enum FreightService {
		SEARCH, ADD_FREIGHT, DEL_FREIGHT,FIN_FREIGHT ,CONFIRM_MGR, CONFIRM_DRV, UPDATE_SITE
	};

	/**
	 * 基础数据成员
	 */
	public static enum DataType {
		STOP, LINE, CARGO, GATE
	}

	/**
	 * 司机的操作。
	 */
	public static enum GuardService {
		GET_CARLIST, ACCEPT_CARIN,ACCEPT_CAROUT
	}
}
