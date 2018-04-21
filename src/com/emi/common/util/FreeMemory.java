package com.emi.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FreeMemory extends HttpServlet {
	private static final long serialVersionUID = 8938590196036665677L;
	public FreeMemory() {
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
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		float a=Float.parseFloat(request.getParameter("freeSize"));
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long freeSize = heapFreeSize / 1024L / 1024L;
		System.out.println("freesize==========="+freeSize);
		
		if (freeSize < a) {
			response.sendRedirect("2.jsp");
		}else {
			response.sendRedirect("Success.jsp");
		}
	}
	public void init() throws ServletException {
		// Put your code here
	}

}
