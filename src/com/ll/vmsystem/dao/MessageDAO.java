package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: MessageDAO �ӿ�, �Ե�ǰ��������Ϣmessage�������ݽ��в����� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface MessageDAO {

	/**
	 * �������S
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	// �Ȼ�Щƪ���������Ķ���һ��message��messageӦ����һЩ�������ݲ�ͬ��role����ͬ������Ӧ��ͬ������
	// ���� manager ֻ�� message1����ӦΪ��ǰ���ύ�Ĵ���˵��˵�����
	// ����guard ֻ��message1����ӦΪ��ǰ���������Ŵ��ĳ�������
	// ����driver
	// ��message1����message2��һΪ�µĴ�ȷ�ϵ��˵������˵�ע��ֻ��һ���������ܳ���2Ϊ��������˴�����Ϣ��Ҳ��ֻ��1��
	// ����Provider ��ʱֻ��һ��message��������Ա��˵��˵���������Ա��˺ã���ͬʱ����Ӧ�̺�˾���������͡�
	// ֮���ǶԹ�Ӧ������µ���Ϣ�����˵���ɵ���Ϣ��
	// message��һ�����֣����ҿ�����һ���߶˵���ƣ��������ֵ����� ����messageΪһ��int
	// ��intΪ4���ֽڣ�����һ���ֽڱ�ʾһ��message
	// ��������Ȼ֮��д��ϵͳ��Ӧ�����������ķ�����㷨�������ʹ��message�Ļ�ȡ��Ӧ��Ϊ�����������ܹ�Ч�ʸߡ�

	/**
	 * ���һ����Ϣ����Ϊ��һ����Ϣ������messageֻҪ����1234���л��ּ���,message �������󣬵�һ���ֽ�Ϊ0���ڶ����ֽ�Ϊ1������
	 * 
	 * @param id
	 * @param role
	 * @param message
	 *            ��Ϣ����ֻ��һ�������ص�ǰ��Ϣ����λ����0��ʼ
	 * @throws SQLException
	 */
	void addMessage(int id, String role, int message) throws SQLException;

	/**
	 * ��⵱ǰ���ʺ��Ƿ�����Ϣ��
	 * 
	 * @param id
	 * @param role
	 * @return message��ֵ����Ϊ0��ʾ������������Ϣ������Ϊ������Ϣ��ֵ��
	 * @throws SQLException
	 */
	int checkMessage(int id, String role) throws SQLException;

	/**
	 * ��¼ʱ���еĴ��ȼ���Ƿ���������Ϣ������У�����������Ϣ��
	 * 
	 * @param id
	 * @param role
	 * @throws SQLException
	 */
	void pushLeavingMessage(int id, String role) throws SQLException;
}
