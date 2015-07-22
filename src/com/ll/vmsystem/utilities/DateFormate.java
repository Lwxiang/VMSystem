package com.ll.vmsystem.utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Description:EnumUtils enum�����࣬��Ҫ�������state״̬��ö��
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public abstract class DateFormate {
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getFormat(Timestamp ts){
		return sf.format(ts);
	}
}
