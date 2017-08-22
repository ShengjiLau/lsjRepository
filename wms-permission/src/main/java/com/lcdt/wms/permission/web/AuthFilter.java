package com.lcdt.wms.permission.web;

import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ss on 2017/8/21.
 */
public class AuthFilter implements Filter {

	private static final String AUTH_SESSION_KEY = "wms_auth";

	private LoginService loginService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		//判断是否已经登陆
		HttpSession session = httpServletRequest.getSession(false);
		Object wmsAuth = session.getAttribute("wms_auth");
		if (wmsAuth != null) {
			if (wmsAuth instanceof FrontUserInfo) {

			}
		}else{
			Cookie[] cookies = httpServletRequest.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("")) {
					try {
						FrontUserInfo frontUserInfo = loginService.queryTicket(cookie.getValue());
						HttpSession session1 = httpServletRequest.getSession(true);
						session1.setAttribute("wms_auth", frontUserInfo);
					} catch (InvalidTicketException e) {
						e.printStackTrace();
					} catch (UserNotExistException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	public void goLoginPage(HttpServletResponse response) {
		try {
			if (!response.isCommitted()) {
				response.sendRedirect("www.baidu.com");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}
}
