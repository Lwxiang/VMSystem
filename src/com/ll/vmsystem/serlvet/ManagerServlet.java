package com.ll.vmsystem.serlvet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.vo.CargoBean;
import com.ll.vmsystem.vo.DriverBean;
import com.ll.vmsystem.vo.GateBean;
import com.ll.vmsystem.vo.GuardBean;
import com.ll.vmsystem.vo.LineBean;
import com.ll.vmsystem.vo.ManagerBean;
import com.ll.vmsystem.vo.ProviderBean;
import com.ll.vmsystem.vo.StopBean;

/**
 * Description: ManagerServlet，处理大部分管理员的数据访问操作的Servlet<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ManagerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6200865089122434386L;

	/**
	 * 
	 */
	// private static final long serialVersionUID = 6200865089122434386L;

	/**
	 * Constructor of the object.
	 */
	public ManagerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 这里，我将所有的manager的操作全部放在一个Servlet中，都放在这个doPost中进行执行。
		// 则我在requset中放 service 字段来记录要操作的内容。
		String service = req.getParameter("service");
		resp.setContentType("text/html;charset=GBK");
		// System.out.println(req.getRemoteAddr());//获取请求对象的ip地址

		req.setCharacterEncoding("UTF-8");// 这句话没有用。
		// HttpEntity entity =
		// System.out.println(req.getParameter("name"));
		if (service.equals(EnumUtils.ManagerService.UPDATE_MANAGER.toString())) {
			// 更新管理员数据操作.
			// String name = new String(req.getParameter("name").getBytes(
			// "iso-8859-1"), "UTF-8");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String password = req.getParameter("password");
			String detail = req.getParameter("detail");
			String other = req.getParameter("other");
			int id = Integer.parseInt(req.getParameter("id"));
			ManagerBean mb = new ManagerBean(id, name, phone, password, other,
					null, detail);
			resp.getWriter().println(
					ServiceFactory.getManagerService().updateManager(mb)
							.toString());
			return;
		} else if (service.equals(EnumUtils.ManagerService.SEARCH_ACCOUNT
				.toString())) {
			// 查询管理员帐号信息操作。
			resp.getWriter().println(
					ServiceFactory.getManagerService().searchAccount(
							req.getParameter("info"), req.getParameter("role"),
							req.getParameter("pageNo")));

		} else if (service.equals(EnumUtils.ManagerService.DELETEACCOUNT
				.toString())) {
			// 删除帐号
			resp.getWriter().println(
					ServiceFactory.getManagerService().deleteAccount(
							req.getParameter("id"), req.getParameter("role")));
		} else if (service.equals(EnumUtils.ManagerService.ADD_ACCOUNT
				.toString())) {
			// 添加帐号
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String password = req.getParameter("password");
			String detail = req.getParameter("detail");
			String other = req.getParameter("other");
			ManagerBean mb = new ManagerBean(null, name, phone, password,
					other, null, detail);
			resp.getWriter().println(
					ServiceFactory.getManagerService().addManager(mb));
		} else if (service.equals(EnumUtils.ManagerService.SEARCH_STOP
				.toString())) {
			// 搜索卸货点
			resp.getWriter().println(
					ServiceFactory.getManagerService().searchStop(
							req.getParameter("info"),
							req.getParameter("pageno")));
		} else if (service.equals(EnumUtils.ManagerService.ADD_STOP.toString())) {
			// 添加卸货点

			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.addStop(
											new StopBean(
													null,
													req.getParameter("name"),
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y")),
													Integer.parseInt(req
															.getParameter("lineid")),
													null, null, null,
													req.getParameter("detail"))));
		} else if (service.equals(EnumUtils.ManagerService.DELETE_STOP
				.toString())) {
			// 删除卸货点
			resp.getWriter().println(
					ServiceFactory.getManagerService().deleteStop(
							new StopBean(Integer.parseInt(req
									.getParameter("id")), null, null, null,
									null, null, null, null, null, null, null)));
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_STOP
				.toString())) {
			// 更新卸货点
			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.updateStop(
											new StopBean(
													Integer.parseInt(req
															.getParameter("id")),
													req.getParameter("name"),
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y")),
													Integer.parseInt(req
															.getParameter("lineid")),
													null, null, null,
													req.getParameter("detail"))));
		} else if (service.equals(EnumUtils.ManagerService.SEARCH_CARGO
				.toString())) {
			// 搜索货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().searchCargo(
							req.getParameter("info"),
							req.getParameter("pageno")));
		} else if (service
				.equals(EnumUtils.ManagerService.ADD_CARGO.toString())) {
			// 添加货物

			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.addCargo(
											new CargoBean(
													null,
													req.getParameter("name"),
													Integer.parseInt(req
															.getParameter("cargoPriority")),
													Integer.parseInt(req
															.getParameter("stopId")),
													Integer.parseInt(req
															.getParameter("providerId")),
													req.getParameter("detail"))));
		} else if (service.equals(EnumUtils.ManagerService.DELETE_CARGO
				.toString())) {
			// 删除货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().deleteCargo(
							new CargoBean(Integer.parseInt(req
									.getParameter("id")), null, null, null,
									null, null)));
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_CARGO
				.toString())) {
			// 更新货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().updateCargo(
							new CargoBean(Integer.parseInt(req
									.getParameter("id")), req
									.getParameter("name"), Integer.parseInt(req
									.getParameter("cargoPriority")), Integer
									.parseInt(req.getParameter("stopId")),
									Integer.parseInt(req
											.getParameter("providerId")), req
											.getParameter("detail"))));
		} else if (service.equals(EnumUtils.ManagerService.SEARCH_LINE
				.toString())) {
			// 搜索货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().searchLine(
							req.getParameter("info"),
							req.getParameter("pageno")));
		} else if (service.equals(EnumUtils.ManagerService.ADD_LINE.toString())) {
			// 添加货物

			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.addLine(
											new LineBean(
													null,
													req.getParameter("name"),
													Double.parseDouble(req
															.getParameter("a_longitude")),
													Double.parseDouble(req
															.getParameter("a_latitude")),
													Double.parseDouble(req
															.getParameter("b_longitude")),
													Double.parseDouble(req
															.getParameter("b_latitude")),
													Integer.parseInt(req
															.getParameter("a_x")),
													Integer.parseInt(req
															.getParameter("a_y")),
													Integer.parseInt(req
															.getParameter("b_x")),
													Integer.parseInt(req
															.getParameter("b_y")),
													Integer.parseInt(req
															.getParameter("length")),
													Double.parseDouble(req
															.getParameter("A")),
													Double.parseDouble(req
															.getParameter("B")),
													Double.parseDouble(req
															.getParameter("C")),
													null,
													req.getParameter("detail")

											)));

		} else if (service.equals(EnumUtils.ManagerService.DELETE_LINE
				.toString())) {
			// 删除货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().deleteLine(
							new LineBean(Integer.parseInt(req
									.getParameter("id")), null, null, null,
									null, null, null, null, null, null, null,
									null, null, null, null, null)));
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_LINE
				.toString())) {
			// 更新货物
			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.updateLine(
											new LineBean(
													Integer.parseInt(req
															.getParameter("id")),
													req.getParameter("name"),
													Double.parseDouble(req
															.getParameter("a_longitude")),
													Double.parseDouble(req
															.getParameter("a_latitude")),
													Double.parseDouble(req
															.getParameter("b_longitude")),
													Double.parseDouble(req
															.getParameter("b_latitude")),
													Integer.parseInt(req
															.getParameter("a_x")),
													Integer.parseInt(req
															.getParameter("a_y")),
													Integer.parseInt(req
															.getParameter("b_x")),
													Integer.parseInt(req
															.getParameter("b_y")),
													Integer.parseInt(req
															.getParameter("length")),
													Double.parseDouble(req
															.getParameter("A")),
													Double.parseDouble(req
															.getParameter("B")),
													Double.parseDouble(req
															.getParameter("C")),
													null,
													req.getParameter("detail")

											)));
		} else if (service.equals(EnumUtils.ManagerService.SEARCH_GATE
				.toString())) {
			// 搜索货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().searchGate(
							req.getParameter("id")));
		} else if (service.equals(EnumUtils.ManagerService.ADD_GATE.toString())) {
			// 添加货物

			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.addGate(
											new GateBean(
													null,
													req.getParameter("name"),
													req.getParameter("state"),
													Integer.parseInt(req
															.getParameter("waitingcars")),
													req.getParameter("type"),
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("lineid")),
													Integer.parseInt(req
															.getParameter("guardid")),
													req.getParameter("detail"),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y"))

											)));

		} else if (service.equals(EnumUtils.ManagerService.DELETE_GATE
				.toString())) {
			// 删除货物
			resp.getWriter().println(
					ServiceFactory.getManagerService().deleteGate(
							new GateBean(Integer.parseInt(req
									.getParameter("id")), null, null, null,
									null, null, null, null, null, null, null,
									null)));
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_GATE
				.toString())) {
			// 更新货物
			resp.getWriter()
					.println(
							ServiceFactory
									.getManagerService()
									.updateGate(
											new GateBean(
													Integer.parseInt(req
															.getParameter("id")),
													req.getParameter("name"),
													req.getParameter("state"),
													Integer.parseInt(req
															.getParameter("waitingcars")),
													req.getParameter("type"),
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("lineid")),
													Integer.parseInt(req
															.getParameter("guardid")),
													req.getParameter("detail"),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y")))));
		} else if (service
				.equals(EnumUtils.ManagerService.ADD_GUARD.toString())) {
			// 添加门卫,这里的lastlogin为null，dao层会对其 初始化，而action自行添加值。
			resp.getWriter().println(
					ServiceFactory.getManagerService().addGuard(
							new GuardBean(null, req.getParameter("name"), req
									.getParameter("phone"), req
									.getParameter("password"), req
									.getParameter("otherphone"), null, req
									.getParameter("detail"))

					));
			return;
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_GUARD
				.toString())) {
			// 更新门卫信息
			resp.getWriter().println(
					ServiceFactory.getManagerService().updateGuard(
							new GuardBean(Integer.parseInt(req
									.getParameter("id")), req
									.getParameter("name"), req
									.getParameter("phone"), req
									.getParameter("password"), req
									.getParameter("otherphone"), null, req
									.getParameter("detail"))

					));
			return;
		} else if (service.equals(EnumUtils.ManagerService.ADD_PROVIDER
				.toString())) {
			// 添加供应商帐号,这里的lastlogin为null，dao层会对其 初始化，而action自行添加值。
			resp.getWriter().println(
					ServiceFactory.getManagerService().addProvider(
							new ProviderBean(null,
									req.getParameter("password"), req
											.getParameter("name"), req
											.getParameter("principalname"), req
											.getParameter("principalphone"),
									req.getParameter("otherphone"), null, req
											.getParameter("detail")

							)));
			return;
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_PROVIDER
				.toString())) {
			// 更新Provider
			resp.getWriter().println(
					ServiceFactory.getManagerService().updateProvider(
							new ProviderBean(Integer.parseInt(req
									.getParameter("id")), req
									.getParameter("password"), req
									.getParameter("name"), req
									.getParameter("principalname"), req
									.getParameter("principalphone"), req
									.getParameter("otherphone"), null, req
									.getParameter("detail")

							)));
			return;
		} else if (service.equals(EnumUtils.ManagerService.ADD_DRIVER
				.toString())) {
			// 添加司机
			resp.getWriter().println(
					ServiceFactory.getManagerService().addDriver(
							new DriverBean(null, req.getParameter("name"), req
									.getParameter("password"), req
									.getParameter("phone"), req
									.getParameter("otherphone"), Integer
									.parseInt(req.getParameter("providerid")),
									null, null, null, null, req
											.getParameter("detail"))));
			return;
		} else if (service.equals(EnumUtils.ManagerService.UPDATE_DRIVER
				.toString())) {
			// 更新司机
			resp.getWriter().println(
					ServiceFactory.getManagerService().updateDriver(
							new DriverBean(Integer.parseInt(req
									.getParameter("id")), req
									.getParameter("name"), req
									.getParameter("password"), req
									.getParameter("phone"), req
									.getParameter("otherphone"), Integer
									.parseInt(req.getParameter("providerid")),
									null, null, null, null, req
											.getParameter("detail"))));
			return;
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
