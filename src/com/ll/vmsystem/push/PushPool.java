package com.ll.vmsystem.push;

import com.gexin.rp.sdk.base.ITemplate;

/**
 * Description: PushPool ����SDKʹ�õ�IGtPush�����ӳ� <br/>
 * PushWrapper���ṩ�˺ܺõĵ��ýӿڣ����ǣ����ӳز��Ὣ��Щ��ڱ�¶����磬���������ӳ����д�����Ϊ�н�<br/>
 * ��ʵ��������һ�ַ�ʽ��������Щ��Ϣ������������������͵���Ϣ��Դ���û�������Ϣ������ʱ���еģ������<br/>
 * ������������˷�̫��ʱ�䣬�Ƕ��û���һ�ֲ��õ����飬���ң��������ﲻ����������ͬʱҪ����Ϣ���ͣ���Ϊ�ҵķ�������<br/>
 * �ǵ��˵ģ�����Ҳ�����˹�������Ϣ��������������Ϣͬʱ����������ͳ��˷�ʱ�䡣һ�ֽ�������ǣ���Ϣ����������͵�<br/>
 * ��Ϣֱ�ӷ������߱��У���֮������ڿ��У��ٽ�����Ϣ���ͣ�<br/>
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
	 * �ݶ����ӳ��е���������Ϊ5����Ϊ�����ǵ��˵ķ���������
	 */
	private final int PoolSize = 5;

	/**
	 * ʹ��һ���������������ӳ�
	 */
	private PushWrapper[] PushWrapPool;

	/**
	 * ʹ�õ���ģʽ���������ӳ�
	 */
	private static PushPool instance;

	/**
	 * ˽�й��캯��
	 */
	private PushPool() {
		PushWrapPool = new PushWrapper[PoolSize];
		for (int i = 0; i < PoolSize;) {
			PushWrapPool[i++] = new PushWrapper();
		}
	}

	/**
	 * ���ʵ��
	 * 
	 * @return
	 */
	public static PushPool getInstance() {
		if (null == instance) {
			synchronized (PushPool.class) {
				// ˫�ؼ������ȫ���������̵߳��������������
				if (null == instance) {
					instance = new PushPool();
				}
			}
		}
		return instance;
	}

	/**
	 * �����ӳض���Ľ��ֻ����һ�������������ͣ�֮��Ĵ��������ڲ���
	 * 
	 * @param CID
	 * @param template
	 */
	public void PushMessage(String CID, ITemplate template) {
		PushWrapper push = null;
		for (int i = 0; i < PoolSize; i++) {
			push = PushWrapPool[i].getPushWrapper();
			if (null != push) {
				// ���һ��δ��ʹ�õ�push������
				break;
			}
		}
		// ��pushΪ�գ������ӳ����޿������ӣ�����������������ܷ���
		if (null == push) {
			// �����ܷ������¾Ͳ�������
		}
		push.pushMessage(CID, template);
	}

}
