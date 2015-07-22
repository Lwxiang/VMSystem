package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CargoBean;

/**
 * Description:CargoDAO �ӿ�
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface CargoDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * ��
	 * @param cb CargoBean
	 * @return ��������
	 * @throws SQLException 
	 */
	Integer save(CargoBean cb) throws SQLException;
	
	/**
	 * ����idɾ��
	 * @param id 
	 * @return ɾ�����
	 * @throws Exception 
	 */
	boolean delete(int id) throws Exception;
	
	/**
	 * ��,����и�������ܣ���ĵ�ֻ��stopid��detail��name ��cargopriority��
	 * @param cb
	 * @return �޸Ľ��
	 * @throws SQLException 
	 */
	boolean update(CargoBean cb) throws SQLException;
	
	/**
	 * ��
	 * @param id
	 * @return ����id��ѯ���
	 * @throws SQLException 
	 */
	CargoBean get(int id) throws SQLException;
	
	/**
	 * ��ҳ��ѯ , ���ܵ�������ָ�� ж����Ļ������ָ����Ӧ�̵�ȫ��������߶����ȼ��Ļ���ȡ�
	 * @param pageNo
	 * @return ��ѯ���
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
