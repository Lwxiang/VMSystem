package com.ll.vmsystem.push;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;

/**
 * Description: PushWrapper 个推SDK使用的IGtPush的外覆类，数据库连接池没有做好，这个Push连接池要做的完美 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class PushWrapper {
	// 个推SDK定义的三个内容
	private final static String appId = "CfodTk7TIb5tXYkvvVSfF1";
	private final static String appkey = "RlN1lbXkZB83JGUfmBmDt4";
	private final static String master = "vLYgrryXWH9bjeZ1dUUGj9";
	private final static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	/**
	 * 外覆类包装的IGtPush
	 */
	private IGtPush push;
	/**
	 * 一个push指定使用一个message
	 */
	private SingleMessage message;

	/**
	 * target是SDK中标记接受用户的标记，这里还要设置CID，在使用是才调用
	 */
	private Target target;

	/**
	 * 无参的构造函数，获得一个可以使用的push，并进行连接。
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
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		target = new Target();
		target.setAppId(appId);
		// message.setData(ntemplate);
	}

	/**
	 * 该IGtPush是否正在被他人使用
	 */
	private boolean using;

	// /**
	// * 获取当前这个push是否可以使用。
	// *
	// * @return using
	// */
	// public boolean isUsing() {
	// return using;
	// }
	//
	// /**
	// * 锁上当前的push
	// */
	// public void lockPush() {
	// using = true;
	// }

	/**
	 * 在多线程中的处理，对于当前未使用的Push返回，使用中的返回null，来进行判断
	 * 
	 * @return
	 */
	public PushWrapper getPushWrapper() {
		if (!using) {
			synchronized (this) {
				// 双重检测来完全锁定，多线程的完美解决方法。
				if (!using) {
					// 这里获得一个Push的时候，设定其为using，则必须要使用，只有在使用中，才能回复unusing的状态。
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
	 * 调用这个来发送推送消息。这里考虑可以将发送失败的消息置于离线消息中，但这样就要求使用方法一来处理离线消息了。<br/>
	 * 暂不考虑。即假定每次发送推送都是必定成功的。
	 * 
	 * @param CID
	 *            个推SDK中用户的CID
	 * @param template
	 *            个推SDK定义的消息模版
	 */
	public void pushMessage(String CID, ITemplate template) {
		target.setClientId(CID);
		message.setData(template);
		// 之后可以考虑修改这里。
		// push.pushMessageToSingle(message, target);
		IPushResult ret = push.pushMessageToSingle(message, target);
		System.out.println(ret.getResponse().toString());
		using = false;
	}

}
