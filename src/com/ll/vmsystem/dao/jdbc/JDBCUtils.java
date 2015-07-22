package com.ll.vmsystem.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Description:jdbc�����࣬����mysql���ݿ�,ʵ�����ݿ����ӳء� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public final class JDBCUtils {
	private JDBCUtils() {
	}

	/**
	 * url,Ҫʹ��utf-8���뼯��������
	 */
	private static String url = "jdbc:mysql://localhost:3306/vmsystem?useUnicode=true&characterEncoding=UTF-8";
	/**
	 * ���ݿ��ʺ�
	 */
	private static String username = "root";
	// private static String username = "root";
	/**
	 * ����
	 */
	private static String password = "19951114";
	// private static String password = "q981932962";
	/**
	 * һ������10������
	 */
	private static int increment = 10;
	/**
	 * ���ٴ���10������
	 */
	private static int min = 10;
	/**
	 * ������500������
	 */
	private static int max = 500;
	/**
	 * ʹ��������ά�� δʹ�õ�����,TreeMap���ǻ��ں����ʵ�ֵ�Map��
	 */
	private static SortedMap<Long, Connection> nousedConnection = new TreeMap<Long, Connection>();
	/**
	 * ʹ��һ��hashset��ά����ǰʹ���е�����
	 */
	private static HashSet<Connection> usingConnection = new HashSet<Connection>();
	/**
	 * �ϴ��ͷ����ӵ�ʱ�䣬����ǰʱ�䳬������δʹ������������min�����ͷ�һ����������
	 */
	private static Long lassReleaseTime = System.currentTimeMillis();
	/**
	 * ��ǰ����Ϊ 2Сʱ���һ��,������������ӳصĴ�С
	 */
	private static long checkTime = 2 * 60 * 60 * 1000;// 2 * 60 * 60 *
														// 1000

	/**
	 * һ������Ӧ�ó�����ʱ��
	 */
	private static final long addTime = 7 * 60 * 60 * 1000;
	// private static final long addTime = 2 * 60 * 1000;
	/**
	 * ����Ϊ��� �����Ƿ�ʱ��ʱ�䡣30���Ӽ��һ��ʱ�䣬��ʱ�������ͷš�
	 */
	private static long checkConnStateTime = 30 * 60 * 1000;
	/**
	 * ��̬��ʼ��ʱ��������
	 */
	static {
		try {
			// System.out.println("��ʼ��...");
			Class.forName("com.mysql.jdbc.Driver");
			getNewConnection();
			// ��������״̬��⡣
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					long now = System.currentTimeMillis();
					// System.out.println("ˢ�¿�ʼ now" + now);
					int size = nousedConnection.size();
					for (int i = 0; i < size; i++) {
						if (nousedConnection.firstKey() < now) {
							Connection conn = nousedConnection
									.get(nousedConnection.firstKey());
							nousedConnection.remove(nousedConnection.firstKey());

							try {
								conn.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							// System.out.println("�ͷ�"+ (i+1)+ "������");
						} else {
							break;
						}

					}

					if (nousedConnection.size() < min) {
						// ���������������������������ӡ�
						getNewConnection();
						// System.out.println("��������" + nousedConnection.size());
					}
					// ÿ��һ��ʱ���ͷ� һ������ӡ�
					putbackConnection();

				}
			}, 0, checkConnStateTime);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("��ʼ���ɹ�");
	}

	/**
	 * �����ӳ��л�ȡһ�����ӣ�����ʹ�� synchronized�ؼ��֣������߳����ơ�
	 * 
	 * @return ���ݿ����ӳ���һ������
	 * @throws �����ӳ�����ʱ
	 *             ������ȥ���������µ����ӣ����Ƿ��ش��󣬻���֮����Կ��ǵȴ����������ͷš�
	 */
	public static synchronized Connection getConnection() throws Exception {
		Connection conn = null;
		if (nousedConnection.size() == 0) {
			if (usingConnection.size() > max) {
				throw new Exception("JDBC Connection Pool is FULL");
			} else {
				getNewConnection();
			}
		}
		// ÿ�λ�ȡ�Ķ����ͷ�ʱ����С��Ҳ���ǳ���ʱ����õ����ӣ����ǿ�����Ϊ��7Сʱ�ڣ��������Ӧ���ǰ�ȫ�ɿ���
		// System.out.println("��óɹ�  " + nousedConnection.firstKey());
		conn = (Connection) nousedConnection.get(nousedConnection.firstKey());
		nousedConnection.remove(nousedConnection.firstKey());
		usingConnection.add(conn);

		return conn;
	}

	/**
	 * �Ż����ӣ�����������ʹ�÷Żص�δʹ��������
	 */
	private static void putbackConnection() {

		if (nousedConnection.size() > 2 * min
				&& System.currentTimeMillis() - lassReleaseTime > checkTime) {
			synchronized (lassReleaseTime) {
				// �������ҲҪsynchronized ������������ͬ������Ϊ������getConnectionһ����Ҫ����
				// �̰߳�ȫ�Ŀ���
				lassReleaseTime = System.currentTimeMillis();
			}
			int releasenum = nousedConnection.size() / 2 / min * min;
			Connection con = null;
			for (int i = 0; i < releasenum; i++) {
				con = nousedConnection.get(nousedConnection.firstKey());
				try {
					con.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				nousedConnection.remove(nousedConnection.firstKey());
			}
			// System.out.println("�ɹ��ͷ�" + releasenum + "������");
		}
	}

	/**
	 * ���һ�����������ӵ����ӳ���
	 */
	private static void getNewConnection() {
		Connection conn = null;
		// ���ʱ��������Ҫ�ͷŵ�ʱ�䡣
		long now = System.currentTimeMillis() + addTime;
		;
		for (int i = 0; i < increment; i++) {
			try {
				now = System.currentTimeMillis() + addTime;
				conn = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			nousedConnection.put(now, conn);
		}
		return;
	}

	/**
	 * �޸Ľṹ��ֻ�е�����free connection������ ͨ�����ݿ����ӳػ�ȡ�����ӱ���ʹ���������������Ż����ӳأ�������ֱ�����١�
	 * 
	 * @param conn
	 *            Connection
	 */
	public static void free(Connection conn) {
		try {
			if (conn != null) {
				if (conn != null && usingConnection.contains(conn)) {
					// ��ʹ�ú�����ӷ���ʱ�������´��ͷ�ʱ�����õ���
					long now = System.currentTimeMillis() + addTime;
					nousedConnection.put(now, conn);
					usingConnection.remove(conn);
					// System.out.println("���سɹ�  " + now);
					// System.out.println("��ǰ��������  " + nousedConnection.size());
					// System.out.println("��С  " + nousedConnection.firstKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
