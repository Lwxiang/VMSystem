package com.ll.vmsystem.utilities;

import java.util.List;

/**
 * Description:PageList ��ҳ��������࣬�����ݿ��л�ȡ���
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class PageList {
	/**
	 * ����Ϊÿҳ����
	 */
	public final static int pageSize = 20 ;
	public final int pageNo;
	public final int PageNun;
	public final List listData;
	/**
	 * ���캯��
	 * @param pageNo ��ǰҳ�� ,��1��ʼ
	 * @param pageNun ȫ��ҳ��
	 * @param listData ��ǰҳ����
	 */
	public PageList(int pageNo, int pageNun, List listData) {
		this.pageNo = pageNo;
		PageNun = pageNun;
		this.listData = listData;
	}
	
	
}
