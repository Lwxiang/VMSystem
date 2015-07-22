package com.ll.vmsystem.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ll.vmsystem.dao.CargoDAO;
import com.ll.vmsystem.dao.DriverDAO;
import com.ll.vmsystem.dao.GateDAO;
import com.ll.vmsystem.dao.GuardDAO;
import com.ll.vmsystem.dao.LineDAO;
import com.ll.vmsystem.dao.ManagerDAO;
import com.ll.vmsystem.dao.ProviderDAO;
import com.ll.vmsystem.dao.StopDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.ManagerService;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.utilities.JSONUtils;
import com.ll.vmsystem.utilities.PageList;
import com.ll.vmsystem.vo.CargoBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.GuardBean;
import com.ll.vmsystem.vo.LineBean;
import com.ll.vmsystem.vo.ManagerBean;
import com.ll.vmsystem.vo.ProviderBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description:LoginServiceImpl 实现类。 <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ManagerServiceImpl implements ManagerService {

	@Override
	public JSONObject updateManager(ManagerBean mb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			ManagerDAO mDAO = DAOFactory.getInstance().getManagerDAO();
			mDAO.setConn(conn);
			mDAO.update(mb);
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONArray searchAccount(String info, String role, String pageNo) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。
			if (role.equals(EnumUtils.AppRole.MANAGER.toString())) {
				ManagerDAO mDAO = DAOFactory.getInstance().getManagerDAO();
				mDAO.setConn(conn);

				List<ManagerBean> pl = null;
				if (info.equals("")) {
					pl = mDAO.getAll();

				} else {
					ManagerBean mb = mDAO.get(Integer.parseInt(info));
					pl = new ArrayList<ManagerBean>();
					if (mb != null) {
						pl.add(mb);
					}
				}
				for (ManagerBean mb : pl) {
					json = new JSONObject();
					JSONUtils.putManagerBean(json, mb);
					jarr.put(json);
				}
				json = new JSONObject();
				json.put("pagenum", 1);
			} else if (role.equals(EnumUtils.AppRole.GUARD.toString())) {
				GuardDAO gDAO = DAOFactory.getInstance().getGuardDAO();
				gDAO.setConn(conn);

				List<GuardBean> pl = null;
				if (info.equals("")) {
					pl = gDAO.getAll();
					for (GuardBean gb : pl) {
						json = new JSONObject();
						JSONUtils.putGuardBean(json, gb);
						jarr.put(json);
					}
				} else {
					// info不为空，放的就是id。
					GuardBean gb = gDAO.get(Integer.parseInt(info));
					json = new JSONObject();
					JSONUtils.putGuardBean(json, gb);
					jarr.put(json);
				}
				json = new JSONObject();
				json.put("pagenum", 1);
			} else if (role.equals(EnumUtils.AppRole.PROVIDER.toString())) {
				// 这里要注意， Provider的getall是分页数据，要分页 查询，虽然没有详情设置。
				ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
				pDAO.setConn(conn);
				PageList pl = null;
				if (info.equals("")) {
					// info中放的是具体的id，这里查询id返回时，要添加一个pageno数据返回
					pl = pDAO.getAll(Integer.parseInt(pageNo));
					List<ProviderBean> list = pl.listData;
					for (ProviderBean pb : list) {
						json = new JSONObject();
						JSONUtils.putProivderBean(json, pb);
						jarr.put(json);
					}
				} else {
					ProviderBean pb = pDAO.get(Integer.parseInt(info));
					json = new JSONObject();
					JSONUtils.putProivderBean(json, pb);
					jarr.put(json);
				}
				json = new JSONObject();
				if (pl != null) {
					// 返回总页数
					json.put("pagenum", pl.PageNun);
				} else {
					json.put("pagenum", 1);
				}

			} else if (role.equals(EnumUtils.AppRole.DRIVER.toString())) {
				DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
				dDAO.setConn(conn);

				PageList pl = dDAO.searchByInfo(Integer.parseInt(pageNo), info);
				List<DriverBean> list = pl.listData;
				for (DriverBean db : list) {
					json = new JSONObject();
					JSONUtils.putDriverBean(json, db);
					jarr.put(json);
				}
				json = new JSONObject();
				json.put("pagenum", pl.PageNun);
			}

		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject deleteAccount(String id, String role) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。
			if (role.equals(EnumUtils.AppRole.MANAGER.toString())) {
				ManagerDAO mDAO = DAOFactory.getInstance().getManagerDAO();
				mDAO.setConn(conn);
				mDAO.delete(Integer.parseInt(id));
			} else if (role.equals(EnumUtils.AppRole.GUARD.toString())) {
				GuardDAO gDAO = DAOFactory.getInstance().getGuardDAO();
				gDAO.setConn(conn);
				gDAO.delete(Integer.parseInt(id));
			} else if (role.equals(EnumUtils.AppRole.DRIVER.toString())) {
				try {
					conn.setAutoCommit(false);
					DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
					dDAO.setConn(conn);
					dDAO.delete(Integer.parseInt(id));
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
				} finally {
					// 事物结束，仍将其设置为非事物状态，然后释放。
					conn.setAutoCommit(true);
				}
			} else if (role.equals(EnumUtils.AppRole.PROVIDER.toString())) {
				ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
				pDAO.setConn(conn);
				pDAO.delete(Integer.parseInt(id));
			}
			json.put("erroafsafr", "asg");
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;

	}

	@Override
	public JSONObject addManager(ManagerBean mb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			ManagerDAO mDAO = DAOFactory.getInstance().getManagerDAO();
			mDAO.setConn(conn);
			json.put("id", mDAO.save(mb));
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONArray searchStop(String info, String pageNo) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。

			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);

			List<StopBean> list = null;
			PageList pl = sDAO.searchByInfo(Integer.parseInt(pageNo), info);
			list = pl.listData;
			for (StopBean sb : list) {
				json = new JSONObject();
				JSONUtils.putStopBean(json, sb);
				jarr.put(json);
			}

			json = new JSONObject();
			json.put("pagenum", pl.PageNun);
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject addStop(StopBean sb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			LineBean lb = lDAO.get(sb.getLineid());
			sb.setDistance(ServiceFactory.getMapService().getDistance(
					sb.getLongitude(), sb.getLatitude(), lb));
			if (sb.getDistance() < 0 || sb.getDistance() > lb.getLength()) {
				throw new Exception("Stop点无法投影到相应的Line上");
			}
			json.put("id", sDAO.save(sb));
			json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject deleteStop(StopBean sb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			sDAO.delete(sb.getId());
			json.put("erroafsafr", "asg");
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;
	}

	@Override
	public JSONObject updateStop(StopBean sb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			StopDAO sDAO = DAOFactory.getInstance().getStopDAO();
			sDAO.setConn(conn);
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			LineBean lb = lDAO.get(sb.getLineid());
			sb.setDistance(ServiceFactory.getMapService().getDistance(
					sb.getLongitude(), sb.getLatitude(), lb));
			if (sb.getDistance() == 0 || sb.getDistance() > lb.getLength()) {
				throw new Exception("Stop点无法投影到相应的Line上");
			}
			sDAO.update(sb);
			json.put("distance", sb.getDistance());
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONArray searchCargo(String info, String pageNo) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。

			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);

			List<CargoBean> list = null;
			PageList pl = cDAO.searchByInfo(Integer.parseInt(pageNo), info);
			list = pl.listData;
			for (CargoBean cb : list) {
				json = new JSONObject();
				JSONUtils.putCargoBean(json, cb);
				jarr.put(json);
			}

			json = new JSONObject();
			json.put("pagenum", pl.PageNun);
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject addCargo(CargoBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);
			json.put("id", cDAO.save(cb));
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject deleteCargo(CargoBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);
			cDAO.delete(cb.getId());
			json.put("erroafsafr", "asg");
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;
	}

	@Override
	public JSONObject updateCargo(CargoBean cb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			CargoDAO cDAO = DAOFactory.getInstance().getCargoDAO();
			cDAO.setConn(conn);
			json.put("id", cDAO.update(cb));
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONArray searchLine(String info, String pageNo) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。

			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);

			List<LineBean> list = null;
			PageList pl = lDAO.searchByInfo(Integer.parseInt(pageNo), info);
			list = pl.listData;
			for (LineBean lb : list) {
				json = new JSONObject();
				JSONUtils.putLineBean(json, lb);
				jarr.put(json);
			}

			json = new JSONObject();
			json.put("pagenum", pl.PageNun);
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject addLine(LineBean lb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			json.put("id", lDAO.save(lb));
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject deleteLine(LineBean lb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			lDAO.delete(lb.getId());
			json.put("erroafsafr", "asg");
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;
	}

	@Override
	public JSONObject updateLine(LineBean lb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			LineDAO lDAO = DAOFactory.getInstance().getLineDAO();
			lDAO.setConn(conn);
			json.put("id", lDAO.update(lb));
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONArray searchGate(String id) {
		JSONArray jarr = new JSONArray();
		JSONObject json = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// conn.setAutoCommit(false);对于更新数据少于两次的Service不使用事物。
			// 根据role判断登录的角色。

			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);

			List<GateBean> list = null;
			if (id.equals("0")) {
				list = gDAO.getAll();
				for (GateBean gb : list) {
					json = new JSONObject();
					JSONUtils.putGateBean(json, gb);
					jarr.put(json);
				}
			} else {
				GateBean gb = gDAO.get(Integer.parseInt(id));
				json = new JSONObject();
				JSONUtils.putGateBean(json, gb);
				jarr.put(json);
			}
			json = new JSONObject();
			json.put("pagenum", 1);
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json = new JSONObject();
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		jarr.put(json);
		return jarr;
	}

	@Override
	public JSONObject addGate(GateBean gb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			if (gb.getGuardid() == 0) {
				gb.setGuardid(null);
			}
			json.put("id", gDAO.save(gb));
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject deleteGate(GateBean gb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			gDAO.delete(gb.getId());
			json.put("erroafsafr", "asg");
		} catch (Exception e) {
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);

		}
		return json;
	}

	@Override
	public JSONObject updateGate(GateBean gb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			GateDAO gDAO = DAOFactory.getInstance().getGateDAO();
			gDAO.setConn(conn);
			if (gb.getGuardid() == 0) {
				gb.setGuardid(null);
			}
			gDAO.update(gb);
			// json.put("distance", sb.getDistance());
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject addGuard(GuardBean gb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			GuardDAO gDAO = DAOFactory.getInstance().getGuardDAO();
			gDAO.setConn(conn);
			json.put("id", gDAO.save(gb));
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject updateGuard(GuardBean gb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			GuardDAO gDAO = DAOFactory.getInstance().getGuardDAO();
			gDAO.setConn(conn);
			gDAO.update(gb);
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject addProvider(ProviderBean pb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
			pDAO.setConn(conn);
			json.put("id", pDAO.save(pb));
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject updateProvider(ProviderBean pb) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
			pDAO.setConn(conn);
			pDAO.update(pb);
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject addDriver(DriverBean db) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			// 也要使用到事物。
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			json.put("id", dDAO.save(db));
			ProviderDAO pDAO = DAOFactory.getInstance().getProviderDAO();
			pDAO.setConn(conn);
			// 要添加pb的driver数量。
			ProviderBean pb = pDAO.get(db.getProviderid());
			pDAO.updateCarNum(pb.getId(), true);
			// json.put("cantempty", "agag");json可以为空
			conn.commit();
		} catch (Exception e) {
			// e.printStackTrace();

			try {
				// 出异常回滚
				conn.rollback();
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// 事物结束，仍将其设置为非事物状态，然后释放。
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCUtils.free(conn);
		}
		return json;
	}

	@Override
	public JSONObject updateDriver(DriverBean db) {
		JSONObject json = new JSONObject();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			DriverDAO dDAO = DAOFactory.getInstance().getDriverDAO();
			dDAO.setConn(conn);
			dDAO.update(db);
			// json.put("cantempty", "agag");json可以为空
		} catch (Exception e) {
			// e.printStackTrace();
			try {
				// 为了使流量更低，我决定放弃flag标记。
				json.put("error", e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			JDBCUtils.free(conn);
		}
		return json;
	}

}
