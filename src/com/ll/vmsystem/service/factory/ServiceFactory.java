package com.ll.vmsystem.service.factory;

import com.ll.vmsystem.service.*;
import com.ll.vmsystem.service.impl.*;

/**
 * Description:ServiceFactory 使用静态方法返回所有的Service <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ServiceFactory {

	/**
	 * 获取 LoginService 对象。
	 * 
	 * @return
	 */
	public static LoginService getLoginService() {
		return new LoginServiceImpl();
	}

	/**
	 * manager服务
	 * 
	 * @return
	 */
	public static ManagerService getManagerService() {
		return new ManagerServiceImpl();
	}

	/**
	 * map服务对象
	 * 
	 * @return
	 */
	public static MapService getMapService() {
		return new MapServiceImpl();
	}

	/**
	 * @return Driver服务对象
	 */
	public static DriverService getDriverService() {
		return new DriverServiceImpl();
	}

	/**
	 * @return Freight服务对象
	 */
	public static FreightService getFreightService() {
		return new FreightServiceImpl();
	}

	/**
	 * @return GuardServiceImpl实例
	 */
	public static GuardService getGuardService() {
		return new GuardServiceImpl();
	}

}
