package com.ll.vmsystem.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.LineBean;

/**
 * Description:MapService ���е�ͼ��صĲ��� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface MapService {

	/**
	 * ������еĵ�ͼ��Ϣ����ͼ��Ϣ��gate ,line �� stop����Ԫ��
	 * 
	 * @return JSONArray�а�������JSONArray ..,��Ӧ����Ԫ�� ���˳�� gate, line ,stop ,���ĸ�Ϊ
	 *         errorflag���
	 */
	JSONArray getAllMapinfo();

	/**
	 * ��õ���ʱ��Ҫ��ȫ����·��Ϣ��ȫ������Ϣ��һ��Ŀ��ж������Ϣ
	 * 
	 * @param id
	 *            stop
	 * @return
	 */
	JSONArray getMapStopInfo(String id);

	/**
	 * ������µĳ�����Ϣ���ݡ�
	 * 
	 * @param id
	 * @return
	 */
	JSONObject getCarInfo(String id);

	/**
	 * ��ȡ��������ֿ���Ҫǰ���Ĵ��ţ���ʱֻ�ṩһ�����ţ�������
	 * 
	 * @return ����id
	 */
	int getInGate();

	/**
	 * ��ó����뿪�ֿ���Ҫǰ���Ĵ��ţ���ʱֻ�ṩһ�����ţ�������
	 * 
	 * @return ����ID
	 */
	int getOutGate();

	/**
	 * ���ݾ�γ�Ⱥ�ָ��line��id������ȡ��ǰ�������line�ϵ�λ��,�����ǻ�õ���line��ͶӰ���߶γ��ȣ�������
	 * 
	 * @param longitude
	 * @param latitude
	 * @param lb
	 *            ֱ�Ӵ���һ����õ�linebean
	 * @return int���͵ľ��룬���������ҿ��Դ���line�ĳ��ȡ�
	 * @throws Exception
	 */
	int getDistance(double longitude, double latitude, LineBean lb)
			throws Exception;

	/**
	 * ��ȡcar����ϸ��Ϣ���������ڵ���
	 * 
	 * @param conn
	 * @param carid
	 * @return
	 * @throws Exception
	 */
	CarBean getDetailCarInfo(Connection conn, int carid) throws Exception;

	/**
	 * ��⳵���Ƿ񵽴�԰����
	 * 
	 * @param cb
	 * @return
	 * @throws Exception
	 */
	boolean checkCarArrivded(CarBean cb) throws Exception;

	/**
	 * �������µ� ����λ����Ϣ��ֻ�е����������˵�·�󣬲Ż����������������³���λ�úͳ�����ǰ��ָ��ĵ�·id��
	 * 
	 * @param id
	 * @return
	 */
	JSONObject updateCarLineInfo(CarBean cb, String newlineid);

	/**
	 * ���³�������ж�����״̬��
	 * 
	 * @param cb
	 * @return
	 */
	JSONObject carArrivedStop(CarBean cb);

	/**
	 * ����˾��id�����µ�ǰ����״̬����ж���б�ɳ��⵼���У�ͬʱ�����˵���ɣ�����˾���ͳ���״̬���ڡ�
	 * 
	 * @param driverid
	 * @return
	 */
	JSONObject driverEndDischarge(String driverid);

}
