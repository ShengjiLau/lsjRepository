package com.lcdt.web.sso.auth;

import com.sso.client.utils.PropertyUtils;
import com.sso.client.utils.RedirectHelper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/9/19.
 */
public class TicketAuthFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		RedirectHelper.redirectToLoginUrlWithAuthBack(request,response);
	}

}
