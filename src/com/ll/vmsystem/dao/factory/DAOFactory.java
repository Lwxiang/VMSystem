package com.ll.vmsystem.dao.factory;

import com.ll.vmsystem.dao.*;
import com.ll.vmsystem.dao.impl.*;

/**
 * Description:DAOFactory 获得所有dao层对象的工厂。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class DAOFactory {
	// 私有的构造函数，实现单例模式
	private DAOFactory() {

	}

	private static DAOFactory instance;

	private static synchronized void createNewInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
	}

	/**
	 * 获得工厂类实例对象，由于这里可能会创建新的对象，有个判断操作，所以我认为应该锁上
	 * 
	 * @return
	 */
	public static DAOFactory getInstance() {
		if (instance == null) {
			createNewInstance();
		}
		return instance;
	}

	/**
	 * 这里将所有的DAO对象都有DAOFactory获取。这里是ProviderDAO的获取。
	 * 
	 * @return
	 */
	public ProviderDAO getProviderDAO() {
		return new ProviderDAOImpl();
	}

	/**
	 * 返回FreightDAO的实例
	 * 
	 * @return
	 */
	public FreightDAO getFreightDAO() {
		return new FreightDAOImpl();
	}

	/**
	 * 返回CargotDAO的实例
	 * 
	 * @return
	 */
	public CargoDAO getCargoDAO() {
		return new CargoDAOImpl();
	}

	/**
	 * 返回 DriverDAO实例
	 * 
	 * @return
	 */
	public DriverDAO getDriverDAO() {
		return new DriverDAOImpl();
	}

	/**
	 * 返回CartDAO的实例
	 * 
	 * @return
	 */
	public CarDAO getCarDAO() {
		return new CarDAOImpl();
	}

	/**
	 * 返回StopDAO的实例
	 * 
	 * @return
	 */
	public StopDAO getStopDAO() {
		return new StopDAOImpl();
	}

	/**
	 * 返回 LineDAO 实例
	 * 
	 * @return
	 */
	public LineDAO getLineDAO() {
		return new LineDAOImpl();
	}

	/**
	 * 返回 GateDAO 实例
	 * 
	 * @return
	 */
	public GateDAO getGateDAO() {
		return new GateDAOImpl();
	}

	/**
	 * 返回ManagerDAO 实例
	 * 
	 * @return
	 */
	public ManagerDAO getManagerDAO() {
		return new ManagerDAOImpl();
	}

	/**
	 * 返回GuardDAO 实例
	 * 
	 * @return
	 */
	public GuardDAO getGuardDAO() {
		return new GuardDAOImpl();
	}

	/**
	 * 返回LoginDAO实例
	 * 
	 * @return
	 */
	public LoginDAO getLoginDAO() {
		return new LoginDAOImpl();
	}

	/**
	 * 返回MessageDAO实例
	 * 
	 * @return
	 */
	public MessageDAO getMessageDAO() {
		return new MessageDAOImpl();
	}
}
