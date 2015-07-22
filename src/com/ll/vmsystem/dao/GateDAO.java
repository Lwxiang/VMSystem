package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.GateBean;

/**
 * Description:GateDAO �ӿ� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface GateDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * ����Ա����gate���󣬴���ʱ��Ҫ����Ϊ name , type, longitude, latitude ,lineid ,detail
	 * 
	 * @param gb
	 * @return
	 * @throws SQLException
	 */
	Integer save(GateBean gb) throws SQLException;

	/**
	 * ����id���Ķ����ŵĻ������ݣ��Ķ��Ĳ����紴��ʱ���� name , type, longitude, latitude ,lineid
	 * ,detail
	 * 
	 * @param gb
	 * @return
	 * @throws SQLException
	 */
	boolean update(GateBean gb) throws SQLException;

	/**
	 * ����id���Ķ���ǰ�ȴ���������
	 * 
	 * @param add
	 *            �����ȴ����ǳ���
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean carNumChange(boolean add, int id) throws SQLException;

	/**
	 * ����id���Ķ����Ź���״̬
	 * 
	 * @param work
	 *            �����°໹�ǿ�ʼ����
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean stateChange(boolean work, int id) throws SQLException;

	/**
	 * ����id��ѯ
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	GateBean get(int id) throws SQLException;

	/**
	 * ��������������Ա���ʹ��ŵ����ݶ��������ģ�ֱ�ӿ��Բ�ѯȫ��
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<GateBean> getAll() throws SQLException;

	/**
	 * ����idɾ��ָ����
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * ����guardid�ҵ���Ӧ��gateid
	 * 
	 * @param guardid
	 * @return ����gateid��Ϊ0ʱ��ʾ����Ϊ�ҵ���Ӧguard��Ӧ��gateid��
	 * @throws Exception
	 */
	int getByGuard(int guardid) throws Exception;
}
