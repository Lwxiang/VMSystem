package com.ll.vmsystem.utilities;

/**
 * Description:EnumUtils enum�����࣬��Ҫ�������state״̬��ö�� <br/>
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
	 * �˵�״̬��submitted�ύ���,confirmedȷ�����,inway��������У� finished�˵���ɣ�����״̬��ʹ�ô�д
	 */
	public static enum freightState {
		SUBMITTED, CONFIRMED, INWAY, FINISHED
	};

	/**
	 * ˾��״̬��off�رգ�working ������
	 */
	public static enum DriverState {
		OFF, WORKING
	};

	/**
	 * ����״̬������OFF�����񣩣�STARTED�ѳ�����WAITING��԰���ſڵȴ����룬
	 * BEFORE����ж��ǰ��dischargingж���У�Afterж���� ������״̬��Ϊ�ڿ��ڵ�״̬��(�������OFF )
	 */
	public static enum CarState {
		STARTED, WAITING, BEFORE, DISCHARGING, AFTER
	};

	/**
	 * ж����״̬��free���У� dischargingж���С�
	 */
	public static enum StopState {
		FREE, DISCHARGING
	};

	/**
	 * ����״̬��OFF �رգ� WORKING �����С�
	 */
	public static enum GateState {
		OFF, WORKING
	};

	/**
	 * �������� �� IN ֻ���� OUT��ֻ��
	 */
	public static enum GateType {
		IN, OUT
	};

	/**
	 * ��¼״̬���ɹ���ʧ��
	 */
	public static enum LoginState {
		SUCCESS, FAILED
	};

	/**
	 * ���ֽ�ɫ��
	 */
	public static enum AppRole {
		MANAGER, PROVIDER, GUARD, DRIVER
	};

	/**
	 * ���е�manager���õ�service
	 */
	public static enum ManagerService {
		UPDATE_MANAGER, SEARCH_ACCOUNT, DELETEACCOUNT, ADD_ACCOUNT, SEARCH_STOP, ADD_STOP, DELETE_STOP, UPDATE_STOP, SEARCH_CARGO, ADD_CARGO, DELETE_CARGO, UPDATE_CARGO, SEARCH_LINE, ADD_LINE, DELETE_LINE, UPDATE_LINE, SEARCH_GATE, ADD_GATE, DELETE_GATE, UPDATE_GATE, ADD_GUARD, UPDATE_GUARD, ADD_PROVIDER, UPDATE_PROVIDER, ADD_DRIVER, UPDATE_DRIVER

	};

	/**
	 * ���е�map���õ�service
	 */
	public static enum MapService {
		GET_MAPINFO, GET_MAPSTOP, REFRESH_CAR ,UPDATE_LINE ,ARRIVED_STOP,FINISH_FREIGHT 
	};

	/**
	 * ��¼��ز���
	 * 
	 */
	public static enum LoginService {
		CHECK_IDPW, LOGIN_IN, LOGIN_OUT
	};

	/**
	 * ����driver���õĹ���ϵͳ��service
	 * 
	 */
	public static enum DriverService {
		UPDATE_INFO, ARRIVED_OUTGATE
	};

	/**
	 * ����Freight��صĲ�������ö��
	 */
	public static enum FreightService {
		SEARCH, ADD_FREIGHT, DEL_FREIGHT,FIN_FREIGHT ,CONFIRM_MGR, CONFIRM_DRV, UPDATE_SITE
	};

	/**
	 * �������ݳ�Ա
	 */
	public static enum DataType {
		STOP, LINE, CARGO, GATE
	}

	/**
	 * ˾���Ĳ�����
	 */
	public static enum GuardService {
		GET_CARLIST, ACCEPT_CARIN,ACCEPT_CAROUT
	}
}
