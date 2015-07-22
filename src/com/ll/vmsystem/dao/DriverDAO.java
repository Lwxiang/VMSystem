package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.DriverBean;

/**
 * Description:DriverDAO �ӿ� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public interface DriverDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * 
	 * @param conn
	 */
	void setConn(Connection conn);

	/**
	 * ��������id��state��carid,freightid,stopid
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	Integer save(DriverBean db) throws SQLException;

	/**
	 * ���£� ֻ�ܸ���name ,password , phone, otherphone ,detail
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean update(DriverBean db) throws SQLException;

	/**
	 * ˾����ʼ�˵�ʱ��ҵ��������ҵ��㽫��Ҫ���µ����ݶ������javabean�д��ݹ��� ��Ҫ���и��µ������У�
	 * carid,stopid,state,
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean startTrans(DriverBean db) throws SQLException;

	/**
	 * ����Աȷ���˵�ʱ������˾�����˵���������˾����freightid��
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean linkFreight(DriverBean db) throws SQLException;

	/**
	 * �������ʱ��Ҳ����һ�θ��£���ν�state�������£�ͬʱ�� carid,freightid,stopid ����Ϊnull
	 * 
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	boolean endTrans(DriverBean db) throws SQLException;

	/**
	 * ����idɾ��ָ����˾�����ݣ�һ�㲻��ʹ�õ�
	 * 
	 * @param id
	 * @return ɾ�����
	 * @throws SQLException
	 */
	boolean delete(int id) throws SQLException;

	/**
	 * �飬����id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	DriverBean get(int id) throws SQLException;

	/**
	 * ������ϸԼ������ȡ����Ҫ��ķ�ҳ����
	 * 
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	PageList searchByInfo(int pageNo, String info) throws SQLException;
}
