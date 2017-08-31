package com.lcdt.web.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by ss on 2017/8/8.
 */
@Service
public class WmsWebLoginFailureHandler implements AuthenticationFailureHandler {

	private static final String failureTimekey = "failure-time";

	private static final Integer needValidCodeTime = 3;

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		HttpSession session = httpServletRequest.getSession();
		Integer attribute = (Integer) session.getAttribute(failureTimekey);
		if (attribute == null) {
			attribute = 0;
		}

		attribute ++ ;
		session.setAttribute("validCode", "1234");
	}
}
