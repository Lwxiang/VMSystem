import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.push.PushManager;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.*;
import com.ll.vmsystem.vo.*;

public class TEST {

	// static class TestThread {
	// public Boolean locked = false;
	//
	// public void run() {
	// long last = System.currentTimeMillis();
	// synchronized (locked) {
	// while (locked) {
	// // 释放这个锁吗？
	// try {
	// System.out.println(Thread.currentThread().toString()
	// + " now is waiting");
	// // 也就是最后测试的结果是，这个wait处如果没有写具体时间的话，其他的notify是不会叫醒这里的锁的。
	// locked.wait(1000);
	// System.out.println(Thread.currentThread().toString()
	// + " waiting is end");
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// }
	// // System.out.println(Thread.currentThread().toString()
	// // + " lock locked ");
	// locked = true;
	// // work。
	// // System.out.println(Thread.currentThread().toString()
	// // + " work start ");
	// // System.out.println(Thread.currentThread().toString()
	// // + " work start locked" + locked);
	// long now = System.currentTimeMillis();
	// while (now - last < 1000) {
	// locked = true;
	// now = System.currentTimeMillis();
	// }
	// locked = false;
	// // System.out.println(Thread.currentThread().toString()
	// // + " work end notify" );
	// synchronized (locked) {
	// locked.notifyAll();
	// System.out.println(Thread.currentThread().toString()
	// + " work end notify");
	// }
	// // System.out.println(Thread.currentThread().toString()
	// // + " work finished ");
	// }
	//
	// }
	// static class A {
	//
	// }
	//
	// static class B extends A {
	// public void fun() {
	// System.out.println("fun");
	// }
	// }

	private static final double startLongitude = 114.111108;
	private static final double startLatitude = 30.462222;
	private static final double longPerPx = 0.00000338;
	private static final double latiPerPx = 0.00000306;
	public static int changeLongitudeToX(double l) throws Exception {
		int x = (int) ((l - startLongitude) / longPerPx);
		if (x < 0 || x > 2737) {
			// 这里为地图最大值，之后改为真正的地图范围
			throw new Exception("坐标超出地图范围");
		}
		return x;
	}

	public static int changeLatitudeToY(double l) throws Exception {
		int y = (int) ((startLatitude - l) / latiPerPx);
		if (y < 0 || y > 2972) {
			// 这里为地图最大值，之后改为真正的地图范围
			throw new Exception("坐标超出地图范围");
		}
		return y;
	}

	static class Site {
		double lon;
		double lat;
		int truex;
		int truey;

		Site(double l1, double l2, int x, int y) {
			lon = l1;
			lat = l2;
			truex = x;
			truey = y;
		}

		Site() {

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Site> ss = new ArrayList<Site>();
		ss.add(new Site(114.113831, 30.460516, 826, 549));
		ss.add(new Site(114.114169, 30.459964, 915, 715));
		ss.add(new Site(114.113434, 30.459670, 710, 833));
		ss.add(new Site(114.112160, 30.459083, 326, 1044));
		ss.add(new Site(114.113565, 30.456884, 704, 1751));
		ss.add(new Site(114.113345, 30.456782, 658, 1776));
		ss.add(new Site(114.114880, 30.454655, 1112, 2492));
		ss.add(new Site(114.116226, 30.455233, 1494, 2285));
		ss.add(new Site(114.116958, 30.455508, 1718, 2173));
		ss.add(new Site(114.117135, 30.455259, 1787, 2298));
		ss.add(new Site(114.117500, 30.455862, 1878, 2089));
		ss.add(new Site(114.118790, 30.456459, 2271, 1873));
		ss.add(new Site(114.118455, 30.457002, 2182, 1704));
		ss.add(new Site(114.117154, 30.456424, 1791, 1914));
		ss.add(new Site(114.114818, 30.457481, 1094, 1540));
		ss.add(new Site(114.116103, 30.458119, 1473, 1343));
		ss.add(new Site(114.114737, 30.460287, 1085, 622));
		ss.add(new Site(114.116038, 30.460916, 1482, 410));
		ss.add(new Site(114.117409, 30.458697, 1872, 1130));
		ss.add(new Site(114.117607, 30.458778, 1923, 1105));
		LineBean lb = new LineBean();
		int newx, newy;
		String out = "";
		double avgx = 0;
		for (int i = 0; i < ss.size(); i++) {
			try {
				out = "";
				newx = changeLongitudeToX(ss.get(i).lon);
				newy = changeLatitudeToY(ss.get(i).lat);
				System.out.println((i + 1) + " :  ");
//				 System.out.println(newx + "  -  " + ss.get(i).truex + "  =  "
//				 + (newx - ss.get(i).truex));
				// avgx += lon[i - 1];
				System.out.println(newy + "  -  " + ss.get(i).truey + "  =  "
						+ (newy - ss.get(i).truey));
				if (newy > ss.get(i).truey) {
					avgx += newy - ss.get(i).truey;
				} else {
					avgx += ss.get(i).truey - newy;
				}
//				if (newx > ss.get(i).truex) {
//					avgx += newx - ss.get(i).truex;
//				} else {
//					avgx += ss.get(i).truex - newx;
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("avgx  :  " + avgx / 20);
		// A p = new B();
		// Class<?> c = p.getClass();
		// System.out.println(p.getClass().getName());
		// Method m1;
		// try {
		// m1 = c.getMethod("fun");
		// m1.invoke(p);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// final TestThread tt = new TestThread();
		// for (int i = 0; i < 5; i++) {
		// new Thread(new Runnable() {
		// public void run() {
		// tt.run();
		// }
		// }).start();
		// }
		// int ax = 963;
		// int ay = 634;
		//
		// int bx = 629;
		// int by = 664;
		// double a, b, c;
		// if (ax - bx < 1 && ax - bx > -1) {
		// // 当为平行于y轴的直线时，属特殊情况，单独拿出。
		// a = (double) 1;
		// b = (double) 0;
		// c = (double) (-ax);
		// } else {
		// a = (double) (((double) ay - (double) by) / ((double) ax - (double)
		// bx));
		// b = (double) -1;
		// c = ay - ax * a;
		// }
		// System.out.print("a= " + a + "  b= " + b + "   c= " + c);

		// PushManager.getInstance();
		// System.out.print(null==PushManager.getInstance());

		// MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
		// int message = 0x05060412;
		// mDAO.addMessage(0, null, message);

		// TODO Auto-generated method stub
		// float f = 1234;
		// System.out.print(String.format("%.6f",f ));
		// Integer i = null;
		// System.out.print(new FreightBean(null, null, null, null, null, null,
		// null, null, null, Timestamp.valueOf("2015-12-12 15:55:55"),
		// null, null, null, null, null, null, null).getExpectedTime()
		// .toGMTString());
		// LineBean lb = new LineBean();
		// lb.setA_x(128);
		// lb.setB_x(108);
		// lb.setA_y(463);
		// lb.setB_y(148);
		// try {
		// System.out.print(ServiceFactory.getDriverService().getDriverInfo("2",
		// "-1") );
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// GateBean gb = new GateBean();
		// JSONObject json = new JSONObject();
		// try {
		// json.put("gb.detail", gb.getDetail());
		// System.out.print(json);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

		/**
		 * 注注注注注注注注注注注注注注注注 对于这里的StopBean，其carid是可以为空的，而这里没有特殊处理的情况下，对于这个
		 * 空的int类型，从数据到最后的json就转换成为了0.而又已知id不可能有0，则以此来判断 是否为空的carid。
		 */
		// JSONObject json = new JSONObject();
		// try {
		// Connection conn = JDBCUtils.getConnection();
		// StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
		// sDAO.setConn(conn);
		//
		// JSONUtils.putStopBean(json, sDAO.get(1) );
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(json.toString());

		// System.out.println(
		// ServiceFactory.getMapService().getAllMapinfo().toString() );

		// JSONObject j = new JSONObject();
		// System.out.print(j);
		// Map<String,String> map = new HashMap<String,String>();
		// map.put("a", "aa");
		// map.put("a", "awfawf");
		// System.out.print(map.get("a"));

		// JSONArray jarr = new JSONArray();
		// JSONObject json1 = new JSONObject();
		// try {
		// json1.put("falg", "1111");
		// //json1.put("falg", "2222");
		// JSONObject json2 = new JSONObject();
		// jarr.put(json1);
		// jarr.put(json2);
		// System.out.println(jarr.getJSONObject(1).toString());
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

		// //format 输出
		// Integer i = 1;
		// String is = String.format("%09d", i);
		// System.out.println(is);
		//
		//

		// JSONObject json = new JSONObject();
		// try {
		// Integer i = 3;
		// json.put("id", i);
		// System.out.println(json.getInt("id"));
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// List<ProviderBean> pblist = new ArrayList<ProviderBean>();
		// PageWithList pl = new PageWithList(2,pblist);
		// System.out.println(pl.listData.size());
		// Timestamp ts = new Timestamp( System.currentTimeMillis());
		// System.out.println(ts.toString());

		/**
		 * DAO层 单元测试全部完成。
		 */
		/**
		 * 测试 GuardDAO
		 */
		// System.out.println(DAOFactory.getInstance().getGuardDAO().delete(1));
		// GuardBean mb = new GuardBean(2,"mm","1109","1224","11",
		// new Timestamp(System.currentTimeMillis()),1,"dddd");
		// List<GuardBean> li = DAOFactory.getInstance().getGuardDAO().getAll();
		// for(GuardBean m:li){
		// System.out.println(m.getId());
		// }
		// System.out.println(DAOFactory.getInstance().getGuardDAO().get(2).getLastlogin());
		// System.out.println(DAOFactory.getInstance().getGuardDAO().login(mb));
		// for(int i = 0 ; i <10;i++)
		// System.out.println(DAOFactory.getInstance().getGuardDAO().save(mb));
		// System.out.println(DAOFactory.getInstance().getGuardDAO().update(mb));
		//
		/**
		 * 测试 ManagerDAO
		 */
		// System.out.println(DAOFactory.getInstance().getManagerDAO().delete(1));
		// ManagerBean mb = new ManagerBean(2,"mm","1109","1224","11",
		// new Timestamp(System.currentTimeMillis()),"dd");
		// List<ManagerBean> li =
		// DAOFactory.getInstance().getManagerDAO().getAll();
		// for(ManagerBean m:li){
		// System.out.println(m.getId());
		// }
		// System.out.println(DAOFactory.getInstance().getManagerDAO().get(2).getLastlogin());
		// System.out.println(DAOFactory.getInstance().getManagerDAO().login(mb));
		// for(int i = 0 ; i <10;i++)
		// try {
		// System.out.println(DAOFactory.getInstance().getManagerDAO().save(mb));
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// System.out.println(DAOFactory.getInstance().getManagerDAO().update(mb));
		/**
		 * 测试GateDAO
		 */
		// Connection conn = null ;
		// try {
		// conn = JDBCUtils.getConnection();
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// Integer n = null;
		//
		// GateBean gb = new
		// GateBean(2,"gay!!",EnumUtils.GateState.WORKING.toString(),1,
		// EnumUtils.GateType.OUT.toString() , 1.2F,2.4F,1,null,"",2,3);
		//
		// try {
		// GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
		// gDAO.setConn(conn);
		// System.out.println(gDAO.save(gb));
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(DAOFactory.getInstance().getGateDAO().update(gb));
		// System.out.println(DAOFactory.getInstance().getGateDAO().carNumChange(false,
		// 2));
		// System.out.println(DAOFactory.getInstance().getGateDAO().stateChange(true,
		// 2));
		// System.out.println(DAOFactory.getInstance().getGateDAO().get(3).getType());
		// List<GateBean> li = DAOFactory.getInstance().getGateDAO().getAll();
		// for(GateBean gbb:li){
		// System.out.println(gbb.getType());
		// }
		/**
		 * 测试 LINEDAO
		 */

		// LineBean lb = new LineBean(2,"guodao",1.1F,1.2F,1.3F,1.4F,
		// 1,2,3,4,450,0F,1F,2F,1,"dd");
		// PageList pl = DAOFactory.getInstance().getLineDAO().searchByInfo(1,
		// " carnum = 1 ");
		// List<LineBean> li = pl.listData;
		// for(LineBean pc:li){
		// System.out.println(pc.getId());
		// }

		// System.out.println(DAOFactory.getInstance().getLineDAO().get(3).getA_latitude());
		// System.out.println(DAOFactory.getInstance().getLineDAO().delete(4));
		// System.out.println(DAOFactory.getInstance().getLineDAO().carNumChange(true,
		// 3));
		// System.out.println(DAOFactory.getInstance().getLineDAO().update(lb));
		// for(int i = 1;i<50;i++)
		// System.out.println(DAOFactory.getInstance().getLineDAO().save(lb));

		/**
		 * 测试 StopDAO
		 */

		// StopBean sb = new StopBean(3,"af",1.5F,2.4F,3,5,1,350,
		// EnumUtils.StopState.FREE.toString(),1,"detal");
		// PageList pl = DAOFactory.getInstance().getStopDAO().searchByInfo(1,
		// " lineid= 1 ");
		// List<StopBean> li = pl.listData;
		// for(StopBean pc:li){
		// System.out.println(pc.getId());
		// }

		// System.out.println(DAOFactory.getInstance().getStopDAO().get(3).getState());
		// System.out.println(DAOFactory.getInstance().getStopDAO().discharge(sb));
		// System.out.println(DAOFactory.getInstance().getStopDAO().update(sb));
		// for(int i = 100 ; i > 55;i--){
		// System.out.println(DAOFactory.getInstance().getStopDAO().save(sb));
		// }

		/**
		 * 测试 CarDAO
		 */

		// PageList pl = DAOFactory.getInstance().getCarDAO().searchByInfo(1,
		// " driverid= 1 ");
		// List<CarBean> li = pl.listData;
		// for(CarBean pc:li){
		// System.out.println(pc.getId());
		// }

		// CarBean cb = new CarBean(14, "A109", 1, 1.1F, 3.1F, 0, 0,
		// new Timestamp(System.currentTimeMillis()), "s",
		// EnumUtils.CarState.WAITING.toString(), 100, 1, 250, "");
		//

		// System.out.println(DAOFactory.getInstance().getCarDAO().get(14).getLastltime());
		// System.out.println(DAOFactory.getInstance().getCarDAO().updateSite(cb));
		// System.out.println(DAOFactory.getInstance().getCarDAO().delete(13));

		// for (int i = 0; i < 10; i++) {
		// System.out.println(DAOFactory.getInstance().getCarDAO().save(cb));
		// }

		/**
		 * 测试driverdao
		 */
		// PageList pl = DAOFactory.getInstance().getDriverDAO().searchByInfo(1,
		// " id>1 ");
		// List<DriverBean> li = pl.listData;
		// for(DriverBean pc:li){
		// System.out.println(pc.getId());
		// }
		// System.out.println("总页数为" + pl.PageNun);
		// DriverBean db = new
		// DriverBean(2,"123","456","110","120",17,1,1,1,"OFF","afawfawawf");

		// System.out.println(DAOFactory.getInstance().getDriverDAO().get(2).getName());
		// System.out.println(DAOFactory.getInstance().getDriverDAO().endTrans(db));
		// System.out.println(DAOFactory.getInstance().getDriverDAO().startTrans(db));
		// System.out.println(DAOFactory.getInstance().getDriverDAO().update(db));
		// System.out.println(DAOFactory.getInstance().getDriverDAO().save(db));

		/**
		 * 测试 CargoDAO
		 */
		//
		// PageList pl = DAOFactory.getInstance().getCargoDAO().searchByInfo(1,
		// " cargopriority = 5 ");
		// List<CargoBean> li = pl.listData;
		// for(CargoBean pc:li){
		// System.out.println(pc.getId());
		// }

		// System.out.println(DAOFactory.getInstance().getCargoDAO().get(1).getName());

		// CargoBean cb = new CargoBean(1,"huowu yihao ",5,1,17,"");
		// System.out.println(DAOFactory.getInstance().getCargoDAO().update(cb));
		// System.out.println(DAOFactory.getInstance().getCargoDAO().delete(4));
		// CargoBean cb = new CargoBean(null,"huowu yihao ",5,1,17,"");
		// System.out.println(DAOFactory.getInstance().getCargoDAO().save(cb));

		/**
		 * 测试FreightDAO
		 */
		// PageList pl =
		// DAOFactory.getInstance().getFreightDAO().searchByInfo(1,
		// " priority = 5 ");
		// List<FreightBean> li = pl.listData;
		// for(FreightBean pc:li){
		// System.out.println(pc.getId());
		// }
		// System.out.println("总页数为" + pl.PageNun);

		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().get(3).getId());

		// FreightBean fb = new FreightBean(3, 17, 1,null, null,null, null,
		// null,null, null,null, null, null,
		// EnumUtils.freightState.FINISHED.toString(), 1, "1t", "");
		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().confirmFinish(fb));
		//
		// FreightBean fb = new FreightBean(3, 17, 1,null, null,null, null,
		// null,null, null,null, null, null,null, null, null, null);
		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().confirmArrive(fb));
		// //

		// FreightBean fb = new FreightBean(3, 17, 1,
		// 2, 3,
		// 4, 5, null,
		// null, null,
		// null, null, new Timestamp(System.currentTimeMillis()),
		// EnumUtils.freightState.INWAY.toString(), 1, "1t", "");
		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().confirmByDrv(fb));
		//

		//
		// FreightBean fb = new FreightBean(3, 17, 1,
		// 2, 3,
		// 4, 5, null,
		// null, null,
		// null, null, null,
		// EnumUtils.freightState.CONFIRMED.toString(), 1, "1t", "");
		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().confirmByMgr(fb));
		//

		// FreightBean fb = new FreightBean(null, 17, 1,
		// 2, 3,
		// 4, 5, null,
		// null, null,
		// null, null, null,
		// null, 1, "1t", "");
		//
		//
		// System.out.println("id:"+DAOFactory.getInstance().getFreightDAO().save(fb)
		// );
		//

		/**
		 * 测试ProviderDAO。
		 */

		// System.out.println(EnumUtils.freightState.CONFIRMED);
		// PageList pl = DAOFactory.getInstance().getProviderDAO().getAll(1);
		// List<ProviderBean> li = pl.listData;
		// for(ProviderBean pc:li){
		// pc.outPut();
		// }
		// System.out.println("总页数为" + pl.PageNun);

		// ProviderBean pb = new ProviderBean(13,"1", "2", "3", "4", "5",
		// 4,"7");
		// System.out.println("id:"+DAOFactory.getInstance().getProviderDAO().save(pb)
		// );
		// ProviderBean pb = new ProviderBean(13,"1", "2", "3", "4", "5",
		// 4,"7");
		//
		// DAOFactory.getInstance().getProviderDAO().updateCarNum(13, true);
		// DAOFactory.getInstance().getProviderDAO().delete(13);
		// DAOFactory.getInstance().getProviderDAO().get(14).outPut();
		// List<ProviderBean> li =
		// DAOFactory.getInstance().getProviderDAO().getAll();
		// for(ProviderBean pc:li){
		// pc.outPut();
		// }
		/*
		 * 测试 数据库连接池 try { Connection con1 = JDBCUtils.getConnection();
		 * Connection con2 = JDBCUtils.getConnection(); JDBCUtils.free(con1);
		 * con1 = JDBCUtils.getConnection(); Connection con3 =
		 * JDBCUtils.getConnection(); Connection con4 =
		 * JDBCUtils.getConnection(); JDBCUtils.free(con4);
		 * JDBCUtils.free(con2); JDBCUtils.free(con1);
		 * 
		 * 
		 * String sql = "SELECT * FROM aa"; PreparedStatement ps = null;
		 * ResultSet rs = null;
		 * 
		 * ps = con3.prepareStatement(sql); rs = ps.executeQuery();
		 * 
		 * if(rs.next()) { System.out.println("成功连接数据库"); }
		 * 
		 * JDBCUtils.free(con3); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } System.out.println("success");
		 */
	}
}
