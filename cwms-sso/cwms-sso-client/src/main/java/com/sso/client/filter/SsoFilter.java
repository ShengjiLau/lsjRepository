package com.sso.client.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ss on 2017/9/18.
 */
public class SsoFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);
		String ticketInSession = getTicketInSession(session);
		String ticket = findTicketInCookie(httpRequest);

		if (ticket == null) {
			redirectLogin(httpResponse);
		}else{

			if (ticketInSession == null) {
				//设置用户信息
				HttpSession session1 = httpRequest.getSession(true);
				session1.setAttribute("ticket", ticket);
				chain.doFilter(request,response);
			}

			if (!ticketInSession.equals(ticket)) {
				//重新加载回到首页
				redirectHome(httpResponse);
				return;
			}else{
				chain.doFilter(request,response);
			}
		}
	}

	public void redirectHome(HttpServletResponse response){

	}

	public void redirectLogin(HttpServletResponse response){

	}


	public String getTicketInSession(HttpSession session){
		return null;
	}

	public String findTicketInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("")) {
				return cookie.getValue();
			}
		}
		return null;
	}


	@Override
	public void destroy() {

	}
}
