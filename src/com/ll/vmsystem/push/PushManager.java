package com.ll.vmsystem.push;

/**
 * Description: PushManager 服务器端的推送管理 ，使用单例模式，进行各种推送操作。 <br/>
 * 这里主要是接收各个阶段的传过来的推送要求，根据情况进行保存。<br/>
 * 对于离线消息的处理，有两种想法，一是对于登录的用户，检测其是否有离线消息，并发送消息给他<br/>
 * 二是设置每隔一段时间，该Manager进行检测当前的离线消息和登录用户。<br/>
 * 显然用方法一更好，关键是，这个检测方法要尽量的高效，但它是访问数据库，肯定高效不起来，而且服务器的内存也就1g<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class PushManager {

	/**
	 * 依然是单例模式
	 */
	private static PushManager instance;

	private PushManager() {

	}

	/**
	 * 单例的获取。
	 * 
	 * @return
	 */
	public static PushManager getInstance() {
		if (null == instance) {
			synchronized (PushManager.class) {
				// 双重检测来完全锁定，多线程的完美解决方法。
				if (null == instance) {
					instance = new PushManager();
				}
			}
		}
		return instance;
	}

	// static String CID = "3be5cd05fda2fbfe46be5026116f52b0";

	/**
	 * 由此接口完全带领发送推送的操作，包括对离线用户将其消息保存在离线信息表中。
	 * 
	 * @param message
	 * @param CID
	 */
	public void PushMessage(int message, String CID) {
		PushPool.getInstance().PushMessage(CID,
				TemplateFactory.transmissionTemplateDemo(message));
	}


	// /**
	// * 在用户帐号登录时，检测其是否有离线消息，若有，则将消息推送给用户，没有直接返回
	// *
	// * @param id
	// * @param role
	// * @param conn
	// */
	// public void CheckLeavingMessage(int id, String role, Connection conn) {
	// // 我他妈又把业务写到DAO层离去了。。。
	// }

}
