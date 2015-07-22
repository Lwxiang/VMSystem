package com.ll.vmsystem.utilities;

import java.util.List;

/**
 * Description:PageList 分页输出辅助类，从数据库中获取获得
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class PageList {
	/**
	 * 这里为每页数量
	 */
	public final static int pageSize = 20 ;
	public final int pageNo;
	public final int PageNun;
	public final List listData;
	/**
	 * 构造函数
	 * @param pageNo 当前页数 ,从1开始
	 * @param pageNun 全部页数
	 * @param listData 当前页数据
	 */
	public PageList(int pageNo, int pageNun, List listData) {
		this.pageNo = pageNo;
		PageNun = pageNun;
		this.listData = listData;
	}
	
	
}
