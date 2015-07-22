package com.ll.vmsystem.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ll.vmsystem.vo.GuardBean;

/**
 * Description:GuardDAO �ӿ�
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-02
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface GuardDAO {
	
	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	/**
	 * ����Ա����Gunard���󣬴���ʱ��Ҫ����Ϊ
	 *  name,phone,password,otherphone,detail,gateid
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	Integer save(GuardBean gb) throws SQLException;
	
	/**
	 * ����id���Ķ������Ļ������ݣ��Ķ��Ĳ����紴��ʱ����
	 * name,phone,password,otherphone,detail,gateid
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	boolean update(GuardBean gb) throws SQLException;
	
	/**
	 * ������¼�������ϴε�¼ʱ��
	 * lastlogin
	 * @param gb
	 * @return
	 * @throws SQLException 
	 */
	boolean login(GuardBean gb) throws SQLException;
	

	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	GuardBean get(int id) throws SQLException;
	
	/**
	 * ��������������Ա���ʹ��ŵ����ݶ��������ģ�ֱ�ӿ��Բ�ѯȫ��
	 * @return
	 * @throws SQLException 
	 */
	List<GuardBean> getAll() throws SQLException;
	
	/**
	 * ����idɾ��ָ����
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
}
