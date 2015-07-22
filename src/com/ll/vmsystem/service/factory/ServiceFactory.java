package com.ll.vmsystem.service.factory;

import com.ll.vmsystem.service.*;
import com.ll.vmsystem.service.impl.*;

/**
 * Description:ServiceFactory ʹ�þ�̬�����������е�Service <br/>
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
	 * ��ȡ LoginService ����
	 * 
	 * @return
	 */
	public static LoginService getLoginService() {
		return new LoginServiceImpl();
	}

	/**
	 * manager����
	 * 
	 * @return
	 */
	public static ManagerService getManagerService() {
		return new ManagerServiceImpl();
	}

	/**
	 * map�������
	 * 
	 * @return
	 */
	public static MapService getMapService() {
		return new MapServiceImpl();
	}

	/**
	 * @return Driver�������
	 */
	public static DriverService getDriverService() {
		return new DriverServiceImpl();
	}

	/**
	 * @return Freight�������
	 */
	public static FreightService getFreightService() {
		return new FreightServiceImpl();
	}

	/**
	 * @return GuardServiceImplʵ��
	 */
	public static GuardService getGuardService() {
		return new GuardServiceImpl();
	}

}
