package com.sso.client.filter;

import com.sso.client.utils.PropertyUtils;
import com.sso.client.utils.RedirectHelper;
import com.sso.client.utils.TicketHelper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ss on 2017/9/18.
 */
public class SsoFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);
		String ticketInSession = getTicketInSession(session);
		String ticket = TicketHelper.findTicketInCookie(httpRequest);

		if (ticket == null) {
			redirectLogin(httpRequest,httpResponse);
			return;
		} else {
			if (ticketInSession == null) {
				//设置用户信息
				HttpSession session1 = httpRequest.getSession(true);
				session1.setAttribute("ticket", ticket);
				chain.doFilter(request, response);
			}

			if (!ticketInSession.equals(ticket)) {
				//重新加载回到首页
				//
				redirectHome(httpResponse);
				return;
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	public void redirectHome(HttpServletResponse response) {

	}

	public void redirectLogin(HttpServletRequest request,HttpServletResponse response) {
		RedirectHelper.redirectToLoginUrlWithAuthBack(request,response);
	}


	public String getTicketInSession(HttpSession session) {
		return null;
	}




	@Override
	public void destroy() {

	}
}
