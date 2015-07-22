package com.ll.vmsystem.push;

/**
 * Description: PushManager �������˵����͹��� ��ʹ�õ���ģʽ�����и������Ͳ����� <br/>
 * ������Ҫ�ǽ��ո����׶εĴ�����������Ҫ�󣬸���������б��档<br/>
 * ����������Ϣ�Ĵ����������뷨��һ�Ƕ��ڵ�¼���û���������Ƿ���������Ϣ����������Ϣ����<br/>
 * ��������ÿ��һ��ʱ�䣬��Manager���м�⵱ǰ��������Ϣ�͵�¼�û���<br/>
 * ��Ȼ�÷���һ���ã��ؼ��ǣ������ⷽ��Ҫ�����ĸ�Ч�������Ƿ������ݿ⣬�϶���Ч�����������ҷ��������ڴ�Ҳ��1g<br/>
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
	 * ��Ȼ�ǵ���ģʽ
	 */
	private static PushManager instance;

	private PushManager() {

	}

	/**
	 * �����Ļ�ȡ��
	 * 
	 * @return
	 */
	public static PushManager getInstance() {
		if (null == instance) {
			synchronized (PushManager.class) {
				// ˫�ؼ������ȫ���������̵߳��������������
				if (null == instance) {
					instance = new PushManager();
				}
			}
		}
		return instance;
	}

	// static String CID = "3be5cd05fda2fbfe46be5026116f52b0";

	/**
	 * �ɴ˽ӿ���ȫ���췢�����͵Ĳ����������������û�������Ϣ������������Ϣ���С�
	 * 
	 * @param message
	 * @param CID
	 */
	public void PushMessage(int message, String CID) {
		PushPool.getInstance().PushMessage(CID,
				TemplateFactory.transmissionTemplateDemo(message));
	}


	// /**
	// * ���û��ʺŵ�¼ʱ��������Ƿ���������Ϣ�����У�����Ϣ���͸��û���û��ֱ�ӷ���
	// *
	// * @param id
	// * @param role
	// * @param conn
	// */
	// public void CheckLeavingMessage(int id, String role, Connection conn) {
	// // �������ְ�ҵ��д��DAO����ȥ�ˡ�����
	// }

}
