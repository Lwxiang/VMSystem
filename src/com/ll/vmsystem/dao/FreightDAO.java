package com.ll.vmsystem.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description:FreightDAO �ӿ�
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public interface FreightDAO {

	/**
	 * ����Conncetion������DAO�����ʹ��һ�����ӣ�����Service��ʵ�����ﴦ��
	 * @param conn
	 */
	void setConn(Connection conn);
	
	/**
	 * ���в������ݣ���id��detailΪ�������������з�null����Ϊ����
	 * @param fb FreightBean
	 * @return ��������������ֵ
	 * @throws SQLException 
	 */
	Integer save(FreightBean fb) throws SQLException;
	
	/**
	 * ��Ϊ����Աȷ���˵�ʱ���е��޸Ĳ���������Ҫ�������ȼ�������Ҫ������ݣ�����
	 * ��ҵ����Ȼ��fbȻ�����õ��������ȼ������и��£�������������ݿ����������
	 * �������������������fb����Ӧ���ݽ��и��£�����fb�е�id�����µ������У�
	 * managerpriority , priority , confirmtime , detail,state . 5������
	 * @param fb ���������ΪʲôҪʹ��Integer��ԭ��ʹ��Object����ΪNULL��fb���ж������Ϊ��
	 * @return �޸Ľ��
	 * @throws SQLException 
	 */
	boolean confirmByMgr(FreightBean fb) throws SQLException;
	
	/**
	 * ��Ϊ˾��ȷ�϶���ʱ���е��޸Ĳ�����ֻ�޸�starttime, expectedtime,��state
	 * state ��ֱ����״̬�仯��֪������Ҫ�Ķ���ֻ������ʱ�䣬�����ݵ�����ֻ������
	 * ����ʱ���һ��id���ʣ��ޱ�Ҫʹ��FreightBean��Ϊ�����������޹�ʹ����
	 * @param fb FreightBean
	 * @return �޸Ľ��
	 * @throws SQLException 
	 */
	boolean confirmByDrv(FreightBean fb) throws SQLException;
	
	/**
	 * ��Ϊ˾������԰�����ź��ύ����Ϣ����������Ҫ���µ�����Ϊ���
	 * arrivedtime ��������ʱ�䡣
	 * ֮�����еĲ�����Ϊjava BEAN��ԭ��ܼ�
	 * ���ȣ��һ��ھ���ʵ���������Ը��ֶε�ʹ����������ң���������Ϊ���Ӻÿ�һ�㡣
	 * ���еĸ��µĺ����Ĳ�����ͬ�������������ۡ�
	 * @param fb FreightBean
	 * @return �޸Ľ��
	 * @throws SQLException 
	 */
	boolean confirmArrive(FreightBean fb) throws SQLException;
	
	/**
	 * ����ж�����ʱ���޸Ĳ�����
	 * ����stateΪ FINISHED
	 * ͬʱ����finishedtime
	 * @param fb FreightBean
	 * @return  �޸Ľ��
	 * @throws SQLException 
	 */
	boolean confirmFinish(FreightBean fb) throws SQLException;
	
	/**
	 * ����idɾ��ָ�����˵����ݣ�һ�㲻��ʹ�õ�
	 * @param id int����integer������ʹ��int��Ϊ��������ʵ���Ա�֤������ȫ������Ϊnull
	 * @return ɾ�����
	 * @throws SQLException 
	 */
	boolean delete(int id) throws SQLException;
	
	/**
	 * ����id��ȡ��Ӧ����
	 * @param id
	 * @return FreightBean
	 * @throws SQLException 
	 */
	FreightBean get(int id) throws SQLException;
	
	/**
	 * ����һЩ��ϸ��ϸ�ڻ�����Ѱ�ҷ���Ҫ��ķ�ҳ������ݡ�
	 * ������Ҫע��һ���ǣ�ϸ���趨Ӧ�Ǽ򵥵ģ��Ҳ���Ӱ�������������������
	 * ���еĲ�ѯ���������id����ģ����ǹ̶��Ĳ��ɱ��
	 * @param pageNo ��ѯ��ҳ��
	 * @param info ���ӵ�sql��䣬��Ϊ�����ݵ�һЩ�޶�����������ʹ��order by��� ,����������һ�䡰1=1����
	 * @return ����ҳ����list���PageList
	 * @throws SQLException 
	 */
	PageList searchByInfo(int pageNo,String info) throws SQLException;
}
