package com.ll.vmsystem.serlvet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;

/**
 * Description: DriverServlet ����˾��һЩ��Ϣ״̬��servlet�� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-03
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class DriverServlet extends HttpServlet {

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
		// System.out.println(req.getRemoteAddr());//��ȡ��������ip��ַ
		req.setCharacterEncoding("UTF-8");// ����ϢΪ���룬�ַ�������
		// HttpEntity entity =
		// System.out.println(req.getParameter("name"));
		if (service.equals(EnumUtils.DriverService.UPDATE_INFO.toString())) {
			// ���¹���Ա���ݲ���.
			String id = req.getParameter("id");
			String driverState = req.getParameter("driverState");
			resp.getWriter().println(
					ServiceFactory.getDriverService()
							.getDriverInfo(id, driverState).toString());
		} else if (service.equals(EnumUtils.DriverService.ARRIVED_OUTGATE
				.toString())) {
			resp.getWriter().println(
					ServiceFactory.getDriverService().sumbitArrivedOutGate(
							req.getParameter("carid"),
							req.getParameter("gateid")));
			return;
		}
	}

}
