package com.ll.vmsystem.serlvet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ll.vmsystem.dao.MessageDAO;
import com.ll.vmsystem.dao.factory.DAOFactory;
import com.ll.vmsystem.dao.jdbc.JDBCUtils;
import com.ll.vmsystem.service.factory.ServiceFactory;
import com.ll.vmsystem.utilities.EnumUtils;

/**
 * Description:LoginServlet �����¼��servlet�� <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {

	/**
	 * ��ƺ�����servlet��Ҫ����serialVersionUID�����ڽ��̼�ͨѶ�ı�ǡ�
	 */
	private static final long serialVersionUID = 8254488010607820862L;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	/**
	 * ֻ��post�������ͻ����ύid��password,role���������������������η��ص�jsonObject
	 * �г��˶�Ӧ��bean���󣬻���һ��flag��ǣ���������Ƿ��¼�ɹ������ʺ������Ƿ���ȷ��
	 * �����᳹�ֲ��˼�룬servlet�㣬ֻ��ȡ�����͵���Service���еķ�����ý���������
	 * servlet�㣬��Ӧ�����߼������ʵ�ʲ�����
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String service = req.getParameter("service");
		resp.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("UTF-8");// ����ϢΪ���룬�ַ�������
		if (EnumUtils.LoginService.CHECK_IDPW.toString().equals(service)) {
			resp.getWriter().println(
					ServiceFactory.getLoginService().login(
							Integer.parseInt(req.getParameter("id")),
							req.getParameter("password"),
							req.getParameter("role")));
		} else if (EnumUtils.LoginService.LOGIN_OUT.toString().equals(service)) {
			resp.getWriter().println(
					ServiceFactory.getLoginService().delLoginInfo(
							req.getParameter("id"), req.getParameter("role")));
		} else if (EnumUtils.LoginService.LOGIN_IN.toString().equals(service)) {
			resp.getWriter().println(
					ServiceFactory.getLoginService().addLoginInfo(
							req.getParameter("id"), req.getParameter("role"),
							req.getParameter("cid")));
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("UTF-8");// ����ϢΪ���룬�ַ�������
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			MessageDAO mDAO = DAOFactory.getInstance().getMessageDAO();
			mDAO.setConn(conn);
			mDAO.addMessage(3, EnumUtils.AppRole.DRIVER.toString(), 0x12345678);
			mDAO.addMessage(2, EnumUtils.AppRole.MANAGER.toString(), 0x12345678);
			mDAO.addMessage(17, EnumUtils.AppRole.PROVIDER.toString(),
					0x12345678);
			mDAO.addMessage(2, EnumUtils.AppRole.GUARD.toString(), 0x12345678);
			resp.getWriter().println("success");
		} catch (Exception e) {

			e.printStackTrace(resp.getWriter());
			resp.getWriter().println();
		} finally {
			JDBCUtils.free(conn);
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {

	}

}
