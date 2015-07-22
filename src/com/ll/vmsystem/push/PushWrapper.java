package com.ll.vmsystem.push;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;

/**
 * Description: PushWrapper ����SDKʹ�õ�IGtPush���⸲�࣬���ݿ����ӳ�û�����ã����Push���ӳ�Ҫ�������� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class PushWrapper {
	// ����SDK�������������
	private final static String appId = "CfodTk7TIb5tXYkvvVSfF1";
	private final static String appkey = "RlN1lbXkZB83JGUfmBmDt4";
	private final static String master = "vLYgrryXWH9bjeZ1dUUGj9";
	private final static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	/**
	 * �⸲���װ��IGtPush
	 */
	private IGtPush push;
	/**
	 * һ��pushָ��ʹ��һ��message
	 */
	private SingleMessage message;

	/**
	 * target��SDK�б�ǽ����û��ı�ǣ����ﻹҪ����CID����ʹ���ǲŵ���
	 */
	private Target target;

	/**
	 * �޲εĹ��캯�������һ������ʹ�õ�push�����������ӡ�
	 */
	public PushWrapper() {
		push = new IGtPush(host, appkey, master);
		try {
			push.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message = new SingleMessage();
		message.setOffline(true);
		// ������Чʱ�䣬��λΪ���룬��ѡ
		message.setOfflineExpireTime(24 * 3600 * 1000);
		target = new Target();
		target.setAppId(appId);
		// message.setData(ntemplate);
	}

	/**
	 * ��IGtPush�Ƿ����ڱ�����ʹ��
	 */
	private boolean using;

	// /**
	// * ��ȡ��ǰ���push�Ƿ����ʹ�á�
	// *
	// * @return using
	// */
	// public boolean isUsing() {
	// return using;
	// }
	//
	// /**
	// * ���ϵ�ǰ��push
	// */
	// public void lockPush() {
	// using = true;
	// }

	/**
	 * �ڶ��߳��еĴ������ڵ�ǰδʹ�õ�Push���أ�ʹ���еķ���null���������ж�
	 * 
	 * @return
	 */
	public PushWrapper getPushWrapper() {
		if (!using) {
			synchronized (this) {
				// ˫�ؼ������ȫ���������̵߳��������������
				if (!using) {
					// ������һ��Push��ʱ���趨��Ϊusing�������Ҫʹ�ã�ֻ����ʹ���У����ܻظ�unusing��״̬��
					using = true;
					return this;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * �������������������Ϣ�����￼�ǿ��Խ�����ʧ�ܵ���Ϣ����������Ϣ�У���������Ҫ��ʹ�÷���һ������������Ϣ�ˡ�<br/>
	 * �ݲ����ǡ����ٶ�ÿ�η������Ͷ��Ǳض��ɹ��ġ�
	 * 
	 * @param CID
	 *            ����SDK���û���CID
	 * @param template
	 *            ����SDK�������Ϣģ��
	 */
	public void pushMessage(String CID, ITemplate template) {
		target.setClientId(CID);
		message.setData(template);
		// ֮����Կ����޸����
		// push.pushMessageToSingle(message, target);
		IPushResult ret = push.pushMessageToSingle(message, target);
		System.out.println(ret.getResponse().toString());
		using = false;
	}

}
