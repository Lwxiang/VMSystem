package com.ll.vmsystem.serlvet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.vo.CarBean;
import com.ll.vmsystem.vo.FreightBean;

/**
 * Description: FreightServlet，处理与运单相关的数据访问操作的Servlet<br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class FreightServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -657723784400190620L;

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

		String service = req.getParameter("service");
		resp.setContentType("text/html;charset=GBK");
		// System.out.println(req.getRemoteAddr());//获取请求对象的ip地址

		req.setCharacterEncoding("UTF-8");// 这句话没有用。
		if (service.equals(EnumUtils.FreightService.SEARCH.toString())) {
			// 更新司机
			resp.getWriter().println(
					ServiceFactory.getFreightService().searchFreight(
							req.getParameter("info"),
							req.getParameter("pageNo"))

			);
			return;
		} else if (service.equals(EnumUtils.FreightService.ADD_FREIGHT
				.toString())) {
			// 更新司机
			resp.getWriter().println(
					ServiceFactory.getFreightService().addFreight(
							new FreightBean(null, Integer.parseInt(req
									.getParameter("providerId")), Integer
									.parseInt(req.getParameter("driverId")),
									Integer.parseInt(req
											.getParameter("driverPriority")),
									null, null, null, null, null, null, null,
									null, null, null, Integer.parseInt(req
											.getParameter("cargoId")), req
											.getParameter("cargoAmount"), req
											.getParameter("detail"))

					)

			);
			return;
		} else if (service.equals(EnumUtils.FreightService.DEL_FREIGHT
				.toString())) {
			resp.getWriter().println(
					ServiceFactory.getFreightService().delFreight(
							req.getParameter("id")));

			return;

		} else if (service.equals(EnumUtils.FreightService.FIN_FREIGHT
				.toString())) {
			resp.getWriter().println(
					ServiceFactory.getFreightService().finFreight(
							req.getParameter("id")));

			return;

		} else if (service.equals(EnumUtils.FreightService.CONFIRM_MGR
				.toString())) {
			resp.getWriter().println(
					ServiceFactory.getFreightService().confirmByMgr(
							new FreightBean(Integer.parseInt(req
									.getParameter("id")), null, Integer
									.parseInt(req.getParameter("driverId")),
									null, Integer.parseInt(req
											.getParameter("managerpriority")),
									null, Integer.parseInt(req
											.getParameter("priority")), null,
									null, null, null, null, null, null, null,
									null, req.getParameter("detail"))));

			return;

		} else if (EnumUtils.FreightService.CONFIRM_DRV.toString().equals(
				service)) {
			resp.getWriter().println(
					ServiceFactory.getFreightService().confirmByDrv(
							new FreightBean(Integer.parseInt(req
									.getParameter("id")), null, null, null,
									null, null, null, null, null,
									Timestamp.valueOf(req
											.getParameter("expectedtime")),
									null, null, null, null, null, null, null),
							new CarBean(null, req.getParameter("carno"),
									Integer.parseInt(req
											.getParameter("driverid")),
									Double.parseDouble(req
											.getParameter("longitude")), Double
											.parseDouble(req
													.getParameter("latitude")),
									null, null, Timestamp.valueOf(req
											.getParameter("lastltime")), req
											.getParameter("cartype"), null,
									null, null, Integer.parseInt(req
											.getParameter("distance")), req
											.getParameter("detail"))));
			return;
		} else if (EnumUtils.FreightService.UPDATE_SITE.toString().equals(
				service)) {
			resp.getWriter().println(
					ServiceFactory.getFreightService().updateSite(
							new CarBean(null, null, Integer.parseInt(req
									.getParameter("driverid")),
									Double.parseDouble(req
											.getParameter("longitude")), Double
											.parseDouble(req
													.getParameter("latitude")),
									-1, -1, Timestamp.valueOf(req
											.getParameter("lastltime")), null,
									EnumUtils.CarState.STARTED.toString(),
									null, null, Integer.parseInt(req
											.getParameter("distance")), req
											.getParameter("detail"))));
			return;
		}
	}

}
