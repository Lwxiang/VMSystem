package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CarBean;

/**
 * Description:CarDAO �ӿ� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface CarDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * �����Ĵ���Ϊ˾��ȷ���˵�ʱ����д�ĳ�����Ϣ�� �ϵ����ݻ�ȡ���ύ��ʱ��android�˿�����λ ������һ�ζ�λ�����Ϊ��������һ���ύ
	 * �򣬴����Ĳ���Ϊ ȥ��id��lineid��distance�����ֶκ�
	 * ������ʵ��Ӧ���е�һ�ζ�λ����Ϣ��ԭ��ܼ򵥣��������ύʱ�������ύʱ����δ�򿪶�λ�����ڳ�����ת����λ�������
	 * ��ʼ��ʱ�����ܻ�õ�һ�ζ�λ���ݣ������أ����ң�Ҫע���һ������ڿ���Ķ�λ���ݣ������޷������ȷ��xy���صģ�����
	 * ���õ��ģ�ֱ�Ӵ���-1��-1������ʼ��������ˣ�����-1��-1�� �����û�취����һ���µ�Activity�л�õ�ǰ��λ�á�
	 * 
	 * @param cb
	 * @return
	 */
	Integer save(CarBean cb) throws Exception;

	/**
	 * ����idɾ��ָ��car��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(int id) throws Exception;

	/**
	 * ��ʱ��ȡ��λ��Ϣ��λ�ø��·��������µ������У��ڿ����λ����Ϣ�����¡�
	 * longitude��latitude��x��y��lastltime��lineid��distance��state��destination
	 * ֵ��Ϊ�ڿͻ��˼����Ľ�����ͻ��˽��и��ּ��㣬������ҵ���߼��㣿
	 * ����x,y�ĸ�����ͻ��˼��㣬������������Ҫ��ϵ���ݿ⣬����Ҫ��ҵ���߼�����ɼ���
	 * 
	 * @param cb
	 * @return
	 */
	boolean updateSite(CarBean cb) throws Exception;

	/**
	 * ����id��ѯ
	 * 
	 * @param id
	 * @return
	 */
	CarBean get(int id) throws Exception;

	/**
	 * �����޶�������ҳ��ѯ
	 * 
	 * @param pageNo
	 * @param info
	 * @return
	 */
	PageList searchByInfo(int pageNo, String info) throws Exception;

	/**
	 * ��ȡgateid��Ӧ�Ĵ��Ŵ�ͣ����ȫ�������б�
	 * 
	 * @param gateId
	 * @return
	 * @throws Exception
	 */
	List<CarBean> getCarAtGate(int gateId) throws Exception;

	/**
	 * ���ȫ��������Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	List<CarBean> getAll() throws Exception;

}
