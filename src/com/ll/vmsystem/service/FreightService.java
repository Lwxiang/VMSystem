package com.ll.vmsystem.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description: FreightService����Freight�йصĲ����Ľӿ�<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface FreightService {

	/**
	 * ������������Freight�б�
	 * 
	 * @param info
	 * @param pageno
	 * @return
	 */
	JSONArray searchFreight(String info, String pageno);

	/**
	 * ����µ�FreightBean,����Ҫ���cargo��Ӧ��priority�����м����һ�����ȼ�
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject addFreight(FreightBean fb);

	// /**
	// * ����FreightBean�Ļ�����Ϣ�������������������ģ����д��ֱ���ɹ�Ӧ��ɾ�����ɣ����ǹ���Ա����ɾ���������Ͳ���Ҫ���²����ˡ�
	// * @param fb
	// * @return
	// */
	// JSONObject updateFreight(FreightBean fb);

	// Ϊ��֤���ݵ���ȷ�ԣ�Ҫ���˵���ɾ�ܸģ�Ϊ��֤���ݵİ�ȫ�ԣ���Ҫ�˵�����ɾ���ܸģ����գ������Ľ���ǣ�ֻ�й���Ա���Խ���ɾ����
	// �Ҷ������ɾ�����Ǽ���Σ�յģ����ܵ���������ջ���ϵͳ�군��delete����ɾ�������������ϵʱ�����޷�ɾ����
	/**
	 * ɾ��ָ��id��Freight
	 * 
	 * @param id
	 * @return
	 */
	JSONObject delFreight(String id);

	/**
	 * �趨ָ��id�˵�ֱ����ɡ�
	 * 
	 * @param id
	 * @return
	 */
	JSONObject finFreight(String id);

	/**
	 * ����idˢ��һ���˵����ݡ�
	 * 
	 * @param id
	 *            ʹ��string���������Ǵ������trycatch�У��÷��ش���ԭ����ͻ��ˡ�
	 * @return
	 */
	JSONObject getFreight(String id);

	/**
	 * ����Աȷ���˵�����ȷ�����ȼ� ���ı�Freight״̬�������ı�ע�ȡ� ���ȼ��Ѿ���ǰ�˼���ò����أ�fbz�������� priority
	 * managerpriority detail �ں�̨Ҫ���еĴ�����
	 * ����state�����confirmtime�����ö�Ӧdriver��freightid����������Ϣ��
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmByMgr(FreightBean fb);

	// �����⼸�������㵥����Freight�����ˣ���Ӧ�÷���driver��map�
	/**
	 * ˾��ȷ���˵������״̬��������Ԥ�Ƶ���ʱ�䡣 ���˾������һ��Ϊ�˵����½���Car�Ĳ�����������һ���µ�car����
	 * fb����Ϊ����carid���Լ�����state��starttime����dao����ʵ��starttime����expectedtime��
	 * cb���е����ݾ�γ�ȣ�carno cartype lastltime driverid detail ,��Ҫ�ں�̨����������У�
	 * destination������map�л���ŵķ��������id����state�̶��� ͬʱ���db����db���е��޸���carid�� stopid
	 * ��state ��
	 * 
	 * @param fb
	 * @param cb
	 *            ˾���½�һ��car���� ������ȷ��ʱ��ö�λ��Ϣ�����е�һ�ζ�λ��
	 * @return
	 */
	JSONObject confirmByDrv(FreightBean fb, CarBean cb);

	/**
	 * ������ţ�ͬʱ�޸Ĵ��ŵĵ�ǰͣ����������car��destinationδ�䣬��Ϊ����id�������Ϳ����ɴ���Ѱ�ҵ�ָ����car����ȡdrvier
	 * Freight����Ϣ��
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmArrive(FreightBean fb);

	/**
	 * ���µ�ǰλ����Ϣ,���ڿ����λ����Ϣ����Ϊ�ڿ���ʱ����λ�õĸ��º��жϡ�
	 * cb��Ҫ���������Ϊstate���̶�ֵ��x,y�̶�Ϊ-1��destination�����ݿ��õ�ǰֵ��
	 * 
	 * @param cb
	 *            ���е�����Ϊ����γ�ȣ�driverid����������ʱ�䣬�;��롣
	 * @return
	 */
	JSONObject updateSite(CarBean cb);

	/**
	 * ����ж����ɺ󣬸��£�state��finishʱ�䣬�͵�ǰstop״̬���ȡ�
	 * 
	 * @param fb
	 * @return
	 */
	JSONObject confirmFinish(FreightBean fb);

}
