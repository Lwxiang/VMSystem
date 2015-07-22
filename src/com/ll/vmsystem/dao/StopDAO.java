package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:StopDAO �ӿ�
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface StopDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * �������� ����ʱ��Ҫ�Ĳ�����
	 * name , longitude , latitude, x ,y ,lineid , distance ,detail
	 * @param sb
	 * @return ��������
	 * @throws SQLException 
	 */
	Integer save(StopBean sb) throws SQLException;
	
	/**
	 * ����idɾ��ָ��stop����
	 * @param id
	 * @return ɾ�����
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * ����Ա�������ݵĸ��²������ܹ��޸ĵ�������
	 * name , longitude , latitude, x ,y ,lineid , distance ,detail
	 * @param sb
	 * @return ���½��
	 * @throws SQLException 
	 */
	boolean update(StopBean sb) throws SQLException;

	/**
	 * ��������ж������Լ�ж����ɽ��и��£�����id������ж����state ���͵�ǰͣ������ carid
	 * �����������һ���Ż��� ����sb�е�state�����жϣ������ǽ�carid����Ϊnull���
	 * state���ж�������ҵ����о�������Щ״̬��ö�ٲ�Ӧ������DAO�㣬��DAO�����ж�ֻ��
	 * StopBean�е�getCarid �Ƿ�Ϊnull����Ϊnull������Ϊ��
	 * �������еļ����ж϶�����ҵ��㣬DAOֻ�Ǹ��� ����ĵ��������� �������ݿ⡣
	 * @param sb
	 * @return ���½��
	 * @throws SQLException 
	 */
	boolean discharge(StopBean sb) throws SQLException;
	
	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	StopBean get(int id) throws SQLException;
	
	/**
	 * ���ȫ��ж������Ϣ
	 * @return
	 * @throws SQLException
	 */
	List<StopBean> getAll() throws SQLException;
	
	/**
	 * ����info�е���ϸ�趨����ȡ��ҳ������ݡ�
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
