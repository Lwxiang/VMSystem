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
 * Description:jdbc连接类，连接mysql数据库,实现数据库连接池。 <br/>
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
	 * url,要使用utf-8编码集进行输入
	 */
	private static String url = "jdbc:mysql://localhost:3306/vmsystem?useUnicode=true&characterEncoding=UTF-8";
	/**
	 * 数据库帐号
	 */
	private static String username = "root";
	// private static String username = "root";
	/**
	 * 密码
	 */
	private static String password = "19951114";
	// private static String password = "q981932962";
	/**
	 * 一次增加10个连接
	 */
	private static int increment = 10;
	/**
	 * 最少存在10个连接
	 */
	private static int min = 10;
	/**
	 * 最多存在500个连接
	 */
	private static int max = 500;
	/**
	 * 使用链表来维护 未使用的连接,TreeMap才是基于红黑树实现的Map。
	 */
	private static SortedMap<Long, Connection> nousedConnection = new TreeMap<Long, Connection>();
	/**
	 * 使用一个hashset来维护当前使用中的连接
	 */
	private static HashSet<Connection> usingConnection = new HashSet<Connection>();
	/**
	 * 上次释放连接的时间，若当前时间超过，且未使用连接数大于min，则释放一定数量连接
	 */
	private static Long lassReleaseTime = System.currentTimeMillis();
	/**
	 * 当前设置为 2小时检测一次,这里检测的是连接池的大小
	 */
	private static long checkTime = 2 * 60 * 60 * 1000;// 2 * 60 * 60 *
														// 1000

	/**
	 * 一个连接应该持续的时间
	 */
	private static final long addTime = 7 * 60 * 60 * 1000;
	// private static final long addTime = 2 * 60 * 1000;
	/**
	 * 这里为检测 连接是否超时的时间。30分钟检测一次时间，超时的连接释放。
	 */
	private static long checkConnStateTime = 30 * 60 * 1000;
	/**
	 * 静态初始化时加载驱动
	 */
	static {
		try {
			// System.out.println("初始化...");
			Class.forName("com.mysql.jdbc.Driver");
			getNewConnection();
			// 设置连接状态检测。
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					long now = System.currentTimeMillis();
					// System.out.println("刷新开始 now" + now);
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
							// System.out.println("释放"+ (i+1)+ "个连接");
						} else {
							break;
						}

					}

					if (nousedConnection.size() < min) {
						// 如果连接数不够，则继续补充连接。
						getNewConnection();
						// System.out.println("补充连接" + nousedConnection.size());
					}
					// 每隔一段时间释放 一半的连接。
					putbackConnection();

				}
			}, 0, checkConnStateTime);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("初始化成功");
	}

	/**
	 * 从连接池中获取一个连接，这里使用 synchronized关键字，进行线程限制。
	 * 
	 * @return 数据库连接池中一个连接
	 * @throws 当连接池已满时
	 *             ，不会去尝试增加新的连接，而是返回错误，或者之后可以考虑等待其他连接释放。
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
		// 每次获取的都是释放时间最小，也就是持续时间最久的连接，我们可以认为在7小时内，这个连接应该是安全可靠的
		// System.out.println("获得成功  " + nousedConnection.firstKey());
		conn = (Connection) nousedConnection.get(nousedConnection.firstKey());
		nousedConnection.remove(nousedConnection.firstKey());
		usingConnection.add(conn);

		return conn;
	}

	/**
	 * 放回连接，将其有正在使用放回到未使用连接中
	 */
	private static void putbackConnection() {

		if (nousedConnection.size() > 2 * min
				&& System.currentTimeMillis() - lassReleaseTime > checkTime) {
			synchronized (lassReleaseTime) {
				// 这个操作也要synchronized 声明，来进行同步，因为这里与getConnection一样，要进行
				// 线程安全的控制
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
			// System.out.println("成功释放" + releasenum + "个连接");
		}
	}

	/**
	 * 添加一定数量的连接到连接池中
	 */
	private static void getNewConnection() {
		Connection conn = null;
		// 添加时，计算需要释放的时间。
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
	 * 修改结构后，只有单独的free connection操作。 通过数据库连接池获取的连接必须使用这个方法来将其放回连接池，而不是直接销毁。
	 * 
	 * @param conn
	 *            Connection
	 */
	public static void free(Connection conn) {
		try {
			if (conn != null) {
				if (conn != null && usingConnection.contains(conn)) {
					// 当使用后的连接返回时，则将其下次释放时间计算得到。
					long now = System.currentTimeMillis() + addTime;
					nousedConnection.put(now, conn);
					usingConnection.remove(conn);
					// System.out.println("返回成功  " + now);
					// System.out.println("当前连接数量  " + nousedConnection.size());
					// System.out.println("最小  " + nousedConnection.firstKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
