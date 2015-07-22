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
 * Description:ManagerService ����Ա���й��ܵķ��� <br/>
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
	 * ����Ա��Ȩ���޸��Լ������˹���Ա�����ݡ�
	 * 
	 * @return json �л��� error��ǣ�����¼�������顣 acton��ʹ�� json.has("error")���ж��ֶ��Ƿ���ڡ�
	 */
	JSONObject updateManager(ManagerBean mb);

	/**
	 * ����Ա��ѯȫ�����ʺ���Ϣ������role������ɫ��
	 * 
	 * @param info
	 *            ������������
	 * @param role
	 *            ��ɫ
	 * @param pageNo
	 *            ѡ��ҳ��
	 * @return
	 */
	JSONArray searchAccount(String info, String role, String pageNo);

	/**
	 * ����Աɾ���ʺŵķ���
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	JSONObject deleteAccount(String id, String role);

	/**
	 * ����Ա��Ӹ����ʺţ�����ÿ���ʺų�Ա��ͬ�� ���޷�����һ����Ϊһ��service�����ֻ����servlet�зֿ�����������s
	 * 
	 * @param mb
	 * @return
	 */
	JSONObject addManager(ManagerBean mb);

	/**
	 * ����Ա��ҳ ������������ѯStopBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchStop(String info, String pageNo);

	/**
	 * ����Ա���stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject addStop(StopBean sb);

	/**
	 * ����Աɾ��stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject deleteStop(StopBean sb);

	/**
	 * ����Ա����stop
	 * 
	 * @param sb
	 * @return
	 */
	JSONObject updateStop(StopBean sb);

	/**
	 * ����Ա��ҳ ������������ѯCargoBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchCargo(String info, String pageNo);

	/**
	 * ����Ա���Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject addCargo(CargoBean cb);

	/**
	 * ����Աɾ��Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject deleteCargo(CargoBean cb);

	/**
	 * ����Ա����Cargo
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject updateCargo(CargoBean cb);

	/**
	 * ����Ա��ҳ ������������ѯLineBean
	 * 
	 * @param info
	 * @param pageNo
	 * @return
	 */
	JSONArray searchLine(String info, String pageNo);

	/**
	 * ����Ա���Line
	 * 
	 * @param lb
	 * @return
	 */
	JSONObject addLine(LineBean lb);

	/**
	 * ����Աɾ��Line
	 * 
	 * @param lb
	 * @return
	 */
	JSONObject deleteLine(LineBean lb);

	/**
	 * ����Ա����Line
	 * line���������ݲ�Ӧ���ṩ���²�������Ȼ���ø����������ݡ�
	 * @param lb
	 * @return
	 */
	JSONObject updateLine(LineBean lb);

	/**
	 * ����Ա��ѯȫ��GateBean
	 * 
	 * @param id
	 * @return
	 */
	JSONArray searchGate(String id);

	/**
	 * ����Ա���Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject addGate(GateBean gb);

	/**
	 * ����Աɾ��Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject deleteGate(GateBean gb);

	/**
	 * ����Ա����Gate
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject updateGate(GateBean gb);

	/**
	 * ��� Guard�ʺ�
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject addGuard(GuardBean gb);

	/**
	 * ���� Guard�ʺ�
	 * 
	 * @param gb
	 * @return
	 */
	JSONObject updateGuard(GuardBean gb);

	/**
	 * ��� Provider�ʺ�
	 * 
	 * @param pb
	 * @return
	 */
	JSONObject addProvider(ProviderBean pb);

	/**
	 * ���� Provider�ʺ�
	 * 
	 * @param pb
	 * @return
	 */
	JSONObject updateProvider(ProviderBean pb);

	/**
	 * ��� Driver�ʺ�
	 * 
	 * @param db
	 * @return
	 */
	JSONObject addDriver(DriverBean db);

	/**
	 * ���� Driver�ʺ�
	 * 
	 * @param db
	 * @return
	 */
	JSONObject updateDriver(DriverBean db);
}
