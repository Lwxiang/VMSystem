package com.ll.vmsystem.serlvet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;

public class GuardServlet extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String service = req.getParameter("service");
		resp.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("UTF-8");// 表单信息为乱码，字符集问题
		if (EnumUtils.GuardService.GET_CARLIST.toString().equals(service)) {
			resp.getWriter().println(
					ServiceFactory.getGuardService().getWaitingCarList(
							req.getParameter("guardId")));
		} else if (EnumUtils.GuardService.ACCEPT_CARIN.toString().equals(
				service)) {
			resp.getWriter().println(
					ServiceFactory.getGuardService().acceptCarIn(
							req.getParameter("carId")));
		} else if (EnumUtils.GuardService.ACCEPT_CAROUT.toString().equals(
				service)) {
			resp.getWriter().println(
					ServiceFactory.getGuardService().acceptCarOut(
							req.getParameter("carId")));
		}
	}

}
