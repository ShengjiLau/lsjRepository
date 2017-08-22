package com.lcdt.web.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ss on 2017/8/21.
 */
public class TicketAuthFilter extends GenericFilterBean {

	@Reference
	LoginService loginService;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Cookie[] cookies = request.getCookies();
		boolean hasTicket = false;

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cwms_ticket")) {
					try {
						FrontUserInfo frontUserInfo = loginService.queryTicket(cookie.getValue());
						TicketAuthenticationToken token = new TicketAuthenticationToken(null);
						token.setDetails(frontUserInfo);
						SecurityContextHolder.getContext().setAuthentication(token);
						hasTicket = true;
					} catch (InvalidTicketException e) {
						e.printStackTrace();
					} catch (UserNotExistException e) {
						e.printStackTrace();
					}
				}
			}
			String callback = "http://test.datuodui.com:8088";
			String url = "http://login.datuodui.com:8080/login";
			String encode = URLEncoder.encode(callback, "UTF-8");
			url = url+"?auth_callback="+encode;
			response.sendRedirect(url);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
