package com.lcdt.web.sso.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.web.auth.TicketAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by ss on 2017/8/22.
 */
public class TicketAuthManager implements AuthenticationManager {

	@Reference
	LoginService loginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication instanceof TicketAuthenticationToken) {
			Object credentials = authentication.getCredentials();
			String ticket = (String) credentials;
			try {
				FrontUserInfo frontUserInfo = loginService.queryTicket(ticket);
				((TicketAuthenticationToken) authentication).setDetails(frontUserInfo);
				authentication.setAuthenticated(true);
//				((TicketAuthenticationToken) authentication).eraseCredentials();
			} catch (InvalidTicketException e) {
				e.printStackTrace();
			} catch (UserNotExistException e) {
				e.printStackTrace();
			}

		}


		return null;
	}
}
