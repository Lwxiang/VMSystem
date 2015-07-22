package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: LoginDAO �ӿ� ����login���е�ǰ��¼���ߵ��ʺ����ݽ��д��� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface LoginDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * ��¼����������ǰ��¼���ʺ���Ϣ������login���У�ͬʱ�����ʺ��Ѵ��ڣ������cid�͵�¼ʱ�����Ϣ
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return
	 */
	boolean loignIn(int id, String role, String cid) throws SQLException;;

	/**
	 * �ǳ����������ｫ���������������ϣ���Ϊ���Ѿ���ȫ��֪��ҵ���ˡ�����
	 * 
	 * @param id
	 * @param role
	 * @return
	 */
	boolean loginOut(int id, String role) throws SQLException;

	/**
	 * �����ʺ��Ƿ��¼֮ �Ƿ��ڱ����Ѿ���¼��
	 * 
	 * @param id
	 * @param role
	 * @return �����Ƿ��ڱ��е�¼
	 * @throws SQLException
	 */
	boolean isLogin(int id, String role) throws SQLException;

	/**
	 * ��ȡ�ѵ�¼�ʺŵ�ǰ��CID��֮��������Ϣ���͡�
	 * 
	 * @param id
	 * @param role
	 * @return
	 * @throws SQLException
	 */
	String getCID(int id, String role) throws SQLException;

	/**
	 * ����ǰ�ʺ�id�ͽ�ɫ������һ���ʺ��Ѿ��ڱ�ϵͳ�е�¼ʱ���ٴε�¼Ӧ�ø���һЩ���ݣ���CID
	 * 
	 * @param id
	 * @param role
	 * @param cid
	 * @return
	 */
	boolean updateLogin(int id, String role, String cid) throws SQLException;

	/**
	 * ��õ�ǰ��¼���ʺŵ�����
	 * 
	 * @return
	 * @throws SQLException 
	 */
	int countNum() throws SQLException;

}
