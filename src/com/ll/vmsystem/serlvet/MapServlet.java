package com.ll.vmsystem.serlvet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;
import com.ll.vmsystem.vo.CarBean;

public class MapServlet extends HttpServlet {

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
		req.setCharacterEncoding("UTF-8");// 表单信息为乱码，字符集问题
		if (service.equals(EnumUtils.MapService.GET_MAPINFO.toString())) {
			// 获得当前地图信息
			resp.getWriter().println(
					ServiceFactory.getMapService().getAllMapinfo());
			return;
		} else if (service.equals(EnumUtils.MapService.GET_MAPSTOP.toString())) {
			// 获得导航所需的全部路，卸货点和大门信息
			resp.getWriter().println(
					ServiceFactory.getMapService().getMapStopInfo(
							req.getParameter("stopid")));
			return;
		} else if (service.equals(EnumUtils.MapService.REFRESH_CAR.toString())) {
			// 获得指定的Car信息，即在客户端刷新该car的详细信息
			resp.getWriter().println(
					ServiceFactory.getMapService().getCarInfo(
							req.getParameter("id")));
			return;
		} else if (service.equals(EnumUtils.MapService.UPDATE_LINE.toString())) {
			// 获得指定的Car信息，即在客户端刷新该car的详细信息
			resp.getWriter()
					.println(
							ServiceFactory
									.getMapService()
									.updateCarLineInfo(
											new CarBean(
													Integer.parseInt(req
															.getParameter("id")),
													null,
													null,
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y")),
													Timestamp.valueOf(req
															.getParameter("lastltime")),
													null,
													req.getParameter("state"),
													Integer.parseInt(req
															.getParameter("destination")),
													Integer.parseInt(req
															.getParameter("lineid")),
													Integer.parseInt(req
															.getParameter("distance")),
													req.getParameter("detail")),
											req.getParameter("newlineid")));
			return;
		} else if (service.equals(EnumUtils.MapService.ARRIVED_STOP.toString())) {
			// 车辆到底卸货点进行更新
			resp.getWriter()
					.println(
							ServiceFactory
									.getMapService()
									.carArrivedStop(
											new CarBean(
													Integer.parseInt(req
															.getParameter("id")),
													null,
													null,
													Double.parseDouble(req
															.getParameter("longitude")),
													Double.parseDouble(req
															.getParameter("latitude")),
													Integer.parseInt(req
															.getParameter("x")),
													Integer.parseInt(req
															.getParameter("y")),
													new Timestamp(
															System.currentTimeMillis()),
													null,
													req.getParameter("state"),
													Integer.parseInt(req
															.getParameter("destination")),
													Integer.parseInt(req
															.getParameter("lineid")),
													Integer.parseInt(req
															.getParameter("distance")),
													req.getParameter("detail"))));
			return;
		} else if (service.equals(EnumUtils.MapService.FINISH_FREIGHT
				.toString())) {
			// 司机结束卸货，设置运单完成。
			resp.getWriter().println(
					ServiceFactory.getMapService().driverEndDischarge(
							req.getParameter("driverid")));
			return;
		}
	}
}
