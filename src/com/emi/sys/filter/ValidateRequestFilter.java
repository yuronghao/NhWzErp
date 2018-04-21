package com.emi.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateRequestFilter implements Filter{

	protected String encoding = null;

    protected FilterConfig filterConfig = null;

    protected boolean ignore = false;

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	 HttpServletRequest httpServletRequest = (HttpServletRequest) request;
         HttpServletResponse httpServletResponse = (HttpServletResponse) response;
         String requesturi = httpServletRequest.getRequestURI();
        String q= httpServletRequest.getQueryString();
         
        // 通过检查session中的变量，过虑请求
        HttpSession session = httpServletRequest.getSession();
        
        
        Object currentUser = session.getAttribute("UserId");
        if(requesturi.indexOf(".action") == -1 && requesturi.indexOf(".emi") == -1 && requesturi.indexOf(".jsp") == -1){
        	chain.doFilter(request, response);
        	return;
        }
        // 当前会话用户为空而且不是请求登录、退出登录、安卓访问和根目录则退回到应用的根目录
        if (currentUser == null
        		 && !requesturi.endsWith("/Success.jsp")
                 && !requesturi.endsWith("/lostSession.jsp")
                 && !requesturi.endsWith("/style!uploadPage.action")
                 && !requesturi.endsWith("/user!login.action")
                 && !requesturi.endsWith("/login_checkLogin.emi")
                 && !requesturi.endsWith("/login_getcookie.emi")
                 && !requesturi.endsWith("/user!vCode.action")
                 && !requesturi.endsWith("/user!logout.action")
                 && !requesturi.endsWith("/login.jsp")
                 && !requesturi.endsWith("/index.jsp")
                 && !(requesturi.indexOf("android/")>0)
                 && !(requesturi.indexOf("mobile/")>0)
                 && !(requesturi.indexOf("mobileweigh/")>0)
                 && !(requesturi.indexOf("test/")>0)
                && !requesturi.endsWith(httpServletRequest.getContextPath()
                        + "/")) {
        	System.out.println(requesturi);
        	System.out.println("非法登录或验证已过期!!!!!");
            httpServletResponse.sendRedirect(httpServletRequest
                    .getContextPath()
                    + "/lostSession.jsp");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setAttribute("menuCode", request.getParameter("menuCode"));
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
            this.ignore = true;
        else if (value.equalsIgnoreCase("true"))
            this.ignore = true;
        else if (value.equalsIgnoreCase("yes"))
            this.ignore = true;
        else
            this.ignore = false;
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
    
}
