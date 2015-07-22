package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.ManagerBean;

/**
 * Description:ManagerDAO �ӿ� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface ManagerDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * ����Ա����Manager���󣬴���ʱ��Ҫ����Ϊ name,phone,password,otherphone,detail
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	Integer save(ManagerBean mb) throws SQLException;

	/**
	 * ����Ա��Ȩ����ɾ�����˺��Լ������� ����id���Ķ�����Ա�Ļ������ݣ��Ķ��Ĳ����紴��ʱ����
	 * name,phone,password,otherphone,detail
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	boolean update(ManagerBean mb) throws SQLException;

	/**
	 * ����Ա��¼�������ϴε�¼ʱ��
	 * 
	 * @param mb
	 * @return
	 * @throws SQLException
	 */
	boolean login(ManagerBean mb) throws SQLException;

	/**
	 * ����id��ѯ
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	ManagerBean get(int id) throws SQLException;

	/**
	 * ��������������Ա���ʹ��ŵ����ݶ��������ģ�ֱ�ӿ��Բ�ѯȫ��
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<ManagerBean> getAll() throws SQLException;

	/**
	 * ����idɾ��ָ����
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * �����Ҫ�Ĺ���Ա��id ����������������ԱΪ��עΪ MAIN�Ĺ���Ա��
	 * 
	 * @return
	 * @throws SQLException
	 */
	int getMainId() throws SQLException;
}
