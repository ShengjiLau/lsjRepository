package com.lcdt.web.sso.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.web.auth.TicketAuthenticationToken;
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
			FrontUserInfo frontUserInfo = loginService.queryTicket(ticket);
			token.setDetails(frontUserInfo);
			token.setAuthenticated(true);
//				((TicketAuthenticationToken) authentication).eraseCredentials();
			return token;
		} catch (InvalidTicketException e) {
			e.printStackTrace();
		} catch (UserNotExistException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TicketAuthenticationToken.class.equals(authentication);
	}
}
