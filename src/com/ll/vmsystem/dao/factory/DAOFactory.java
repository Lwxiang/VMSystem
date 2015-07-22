package com.ll.vmsystem.dao.factory;

import com.ll.vmsystem.dao.*;
import com.ll.vmsystem.dao.impl.*;

/**
 * Description:DAOFactory �������dao�����Ĺ����� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class DAOFactory {
	// ˽�еĹ��캯����ʵ�ֵ���ģʽ
	private DAOFactory() {

	}

	private static DAOFactory instance;

	private static synchronized void createNewInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
	}

	/**
	 * ��ù�����ʵ����������������ܻᴴ���µĶ����и��жϲ�������������ΪӦ������
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
	 * ���ｫ���е�DAO������DAOFactory��ȡ��������ProviderDAO�Ļ�ȡ��
	 * 
	 * @return
	 */
	public ProviderDAO getProviderDAO() {
		return new ProviderDAOImpl();
	}

	/**
	 * ����FreightDAO��ʵ��
	 * 
	 * @return
	 */
	public FreightDAO getFreightDAO() {
		return new FreightDAOImpl();
	}

	/**
	 * ����CargotDAO��ʵ��
	 * 
	 * @return
	 */
	public CargoDAO getCargoDAO() {
		return new CargoDAOImpl();
	}

	/**
	 * ���� DriverDAOʵ��
	 * 
	 * @return
	 */
	public DriverDAO getDriverDAO() {
		return new DriverDAOImpl();
	}

	/**
	 * ����CartDAO��ʵ��
	 * 
	 * @return
	 */
	public CarDAO getCarDAO() {
		return new CarDAOImpl();
	}

	/**
	 * ����StopDAO��ʵ��
	 * 
	 * @return
	 */
	public StopDAO getStopDAO() {
		return new StopDAOImpl();
	}

	/**
	 * ���� LineDAO ʵ��
	 * 
	 * @return
	 */
	public LineDAO getLineDAO() {
		return new LineDAOImpl();
	}

	/**
	 * ���� GateDAO ʵ��
	 * 
	 * @return
	 */
	public GateDAO getGateDAO() {
		return new GateDAOImpl();
	}

	/**
	 * ����ManagerDAO ʵ��
	 * 
	 * @return
	 */
	public ManagerDAO getManagerDAO() {
		return new ManagerDAOImpl();
	}

	/**
	 * ����GuardDAO ʵ��
	 * 
	 * @return
	 */
	public GuardDAO getGuardDAO() {
		return new GuardDAOImpl();
	}

	/**
	 * ����LoginDAOʵ��
	 * 
	 * @return
	 */
	public LoginDAO getLoginDAO() {
		return new LoginDAOImpl();
	}

	/**
	 * ����MessageDAOʵ��
	 * 
	 * @return
	 */
	public MessageDAO getMessageDAO() {
		return new MessageDAOImpl();
	}
}
