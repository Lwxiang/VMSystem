package com.ll.vmsystem.push;

import com.gexin.rp.sdk.base.ITemplate;

/**
 * Description: PushPool 个推SDK使用的IGtPush的连接池 <br/>
 * PushWrapper里提供了很好的调用接口，但是，连接池不会将这些借口暴露给外界，而是有连接池自行处理，作为中介<br/>
 * 其实还有另外一种方式，对于这些消息推送请求，首先这个推送的消息来源是用户发送消息请求处理时进行的，而如果<br/>
 * 在这个过程中浪费太多时间，是对用户的一种不好的体验，而且，首先这里不会遇到多人同时要求消息推送，因为我的服务器就<br/>
 * 是单核的，本来也处理不了过量的消息，但，如果多个消息同时到达，会在推送出浪费时间。一种解决方法是，消息处理对与推送的<br/>
 * 消息直接放在离线表中，若之后程序处于空闲，再进行消息推送？<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class PushPool {

	/**
	 * 暂定连接池中的连接数量为5，因为我这是单核的服务器啊。
	 */
	private final int PoolSize = 5;

	/**
	 * 使用一个数组来保存连接池
	 */
	private PushWrapper[] PushWrapPool;

	/**
	 * 使用单例模式来保存连接池
	 */
	private static PushPool instance;

	/**
	 * 私有构造函数
	 */
	private PushPool() {
		PushWrapPool = new PushWrapper[PoolSize];
		for (int i = 0; i < PoolSize;) {
			PushWrapPool[i++] = new PushWrapper();
		}
	}

	/**
	 * 获得实例
	 * 
	 * @return
	 */
	public static PushPool getInstance() {
		if (null == instance) {
			synchronized (PushPool.class) {
				// 双重检测来完全锁定，多线程的完美解决方法。
				if (null == instance) {
					instance = new PushPool();
				}
			}
		}
		return instance;
	}

	/**
	 * 该连接池对外的借口只有这一个，即发送推送，之后的处理，都是内部的
	 * 
	 * @param CID
	 * @param template
	 */
	public void PushMessage(String CID, ITemplate template) {
		PushWrapper push = null;
		for (int i = 0; i < PoolSize; i++) {
			push = PushWrapPool[i].getPushWrapper();
			if (null != push) {
				// 获得一个未被使用的push后，跳出
				break;
			}
		}
		// 当push为空，即连接池中无可用连接，不过这种情况不可能发生
		if (null == push) {
			// 不可能发生的事就不做处理
		}
		push.pushMessage(CID, template);
	}

}
