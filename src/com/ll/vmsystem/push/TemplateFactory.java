package com.ll.vmsystem.push;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * Description: TemplateFactory 个推SDK使用的Template消息模版的工厂类 <br/>
 * <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class TemplateFactory {
	private final static String appId = "CfodTk7TIb5tXYkvvVSfF1";
	private final static String appkey = "RlN1lbXkZB83JGUfmBmDt4";

	/**
	 * 获得透传Template，一般来说都是使用透传，在BroadcastReceiver中接受到消息后，然后处理时，<br/>
	 * 将推送推送到前台，且设置可删除，在用户点击相应的按钮后，会发送Broadcast消息，并在Receiver中将相应的Notification给清除
	 * 。
	 * 
	 * @param message
	 *            消息都放在一个int中，客户端解压后自己解读。
	 * @return
	 */
	public static TransmissionTemplate transmissionTemplateDemo(Integer message) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(message.toString());
		return template;
	}

	/**
	 * 返回一个正常推送
	 * 
	 * @return
	 */
	public static NotificationTemplate notificationTemplateDemo() {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle("正常推送");
		template.setText("推送测试中");
		// 配置通知栏图标
		template.setLogo("push.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");
		return template;
	}

	/**
	 * 返回连接推送
	 * 
	 * @return
	 */
	public static LinkTemplate linkTemplateDemo() {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle("连接推送");
		template.setText("点击推送的Notification会跳到浏览器，浏览消息的网址");
		// 配置通知栏图标
		template.setLogo("push.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		template.setUrl("http://luoxianming.cn");
		return template;
	}

}
