package com.lcdt.web.sso.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by ss on 2017/8/22.
 */
public class TicketAuthProvider implements AuthenticationProvider {

	@Reference
	private LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		TicketAuthenticationToken token = (TicketAuthenticationToken) authentication;
		Object credentials = authentication.getCredentials();
		String ticket = (String) credentials;
		try {
			TicketAuthentication ticketAuthentication = loginService.queryTicket(ticket);
			token.setDetails(ticketAuthentication);
			token.setAuthenticated(true);
			return token;
		} catch (InvalidTicketException e) {
			throw new TicketAuthException("请先登陆");
		} catch (UserNotExistException e) {
			throw new TicketAuthException("请先登陆");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TicketAuthenticationToken.class.equals(authentication);
	}
}
