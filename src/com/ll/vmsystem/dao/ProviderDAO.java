package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.ProviderBean;

/**
 * Description:providerDAO�ӿڣ�Ҫʵ�ֵĺ�����CRUD
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface ProviderDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	/**
	 * ��provider���ݴ洢������Ҫע��idӦ���ǿգ�������id������������ֵ
	 * @param pb 	ProviderBean
	 * @return	�洢�Ķ������ݿ⸳���id
	 * @throws SQLException 
	 */
	Integer save(ProviderBean pb) throws SQLException;
	
	/**
	 * ����idɾ��ָ���֮�����������ɾ��
	 * @param id	����
	 * @return	ɾ�����
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	
	/**
	 * ����Provider��Ϣ��������»����idѡ�� Ҫ�޸ĳ�id�����е���
	 * @param pb ProviderBean
	 * @return ���½��
	 * @throws SQLException 
	 */
	boolean  update(ProviderBean pb) throws SQLException;
	
	/**
	 * ����Ӧ����ɾ˾��ʱ��������ӵ��˾������
	 * @param id ��Ӧ��id
	 * @param increase trueΪ���ӣ�false Ϊɾ��
	 * @return ���½��
	 * @throws SQLException 
	 */
	boolean updateCarNum(Integer id,boolean increase) throws SQLException;
	
	/**
	 * ������������provider����
	 * @param id ����
	 * @return ��Ӧ������provider ��������󲻴��ڣ�����NULL
	 * @throws SQLException 
	 */
	ProviderBean get(int id) throws SQLException;
	
	/**
	 * �������ȫ��provider��Ϣ,��ѯȫ��Ϊ��ҳ��ѯ���ҷ��ص�ǰҳ����ȫ������ҳ����
	 * @return ���ݿ���ȫ����provider,��ȫ��������ʱ������Ϊ�յ�arraylist
	 * @throws SQLException 
	 */
	PageList getAll(int pageNo) throws SQLException;

}
