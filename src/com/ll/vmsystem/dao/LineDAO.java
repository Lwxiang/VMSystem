package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.LineBean;

/**
 * Description:LineDAO �ӿ�
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface LineDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * ����line������ʱӵ�еĲ���Ϊ��
	 * name, a_longitude ,a_latitude ,b_longitude, b_latitude,
	 *  a_x, a_y ,b_x ,b_y, length, A, B ,C ,detail
	 * @param lb
	 * @return
	 * @throws SQLException 
	 */
	Integer save(LineBean lb) throws SQLException;
	
	/**
	 * ����Ա���»������ݣ�����Ը��µ�����Ϊȫ����������
	 * name, a_longitude ,a_latitude ,b_longitude, b_latitude,
	 *  a_x, a_y ,b_x ,b_y, length, A, B ,C ,detail
	 * @param lb
	 * @return
	 * @throws SQLException 
	 */
	boolean update(LineBean lb) throws SQLException;
	
	/**
	 * һ��һ�������Ľ�����뿪��·ʱ���еĳ��������ĸ��¡�
	 * ��������·Ϊ���ݵ���·�� ���з����ĵ��ĵ�·��
	 * @param add
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean carNumChange(boolean add,int id) throws SQLException;

	/**
	 * ɾ��ָ��
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * ����id��ȡ
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	LineBean get(int id) throws SQLException;
	
	/**
	 * ��·����ѯ���õ������е�·��Ϣ�Ļ�ȡ��
	 * @return
	 * @throws SQLException
	 */
	List<LineBean> getall() throws SQLException;
	
	/**
	 * �����趨��ѯ��ҳ����
	 * @param pageNo
	 * @param info
	 * @return
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
	
	
	
}
