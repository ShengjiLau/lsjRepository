package com.lcdt.clms.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/23.
 */

public class TicketLogoutHandler implements LogoutHandler{
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		//删除cookie
		//设置cookie无效
		//删除session
	}
}
