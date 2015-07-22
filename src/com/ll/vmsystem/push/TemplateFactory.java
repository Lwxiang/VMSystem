package com.ll.vmsystem.push;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * Description: TemplateFactory ����SDKʹ�õ�Template��Ϣģ��Ĺ����� <br/>
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
	 * ���͸��Template��һ����˵����ʹ��͸������BroadcastReceiver�н��ܵ���Ϣ��Ȼ����ʱ��<br/>
	 * ���������͵�ǰ̨�������ÿ�ɾ�������û������Ӧ�İ�ť�󣬻ᷢ��Broadcast��Ϣ������Receiver�н���Ӧ��Notification�����
	 * ��
	 * 
	 * @param message
	 *            ��Ϣ������һ��int�У��ͻ��˽�ѹ���Լ������
	 * @return
	 */
	public static TransmissionTemplate transmissionTemplateDemo(Integer message) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		// ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
		template.setTransmissionType(2);
		template.setTransmissionContent(message.toString());
		return template;
	}

	/**
	 * ����һ����������
	 * 
	 * @return
	 */
	public static NotificationTemplate notificationTemplateDemo() {
		NotificationTemplate template = new NotificationTemplate();
		// ����APPID��APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// ����֪ͨ������������
		template.setTitle("��������");
		template.setText("���Ͳ�����");
		// ����֪ͨ��ͼ��
		template.setLogo("push.png");
		// ����֪ͨ������ͼ��
		template.setLogoUrl("");
		// ����֪ͨ�Ƿ����壬�𶯣����߿����
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
		template.setTransmissionType(1);
		template.setTransmissionContent("��������Ҫ͸��������");
		return template;
	}

	/**
	 * ������������
	 * 
	 * @return
	 */
	public static LinkTemplate linkTemplateDemo() {
		LinkTemplate template = new LinkTemplate();
		// ����APPID��APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// ����֪ͨ������������
		template.setTitle("��������");
		template.setText("������͵�Notification������������������Ϣ����ַ");
		// ����֪ͨ��ͼ��
		template.setLogo("push.png");
		// ����֪ͨ������ͼ��
		template.setLogoUrl("");
		// ����֪ͨ�Ƿ����壬�𶯣����߿����
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// ���ô򿪵���ַ��ַ
		template.setUrl("http://luoxianming.cn");
		return template;
	}

}
