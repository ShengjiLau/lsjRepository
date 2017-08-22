package com.lcdt.web.sso.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ss on 2017/8/22.
 */
public class CasLoginEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

		String callback = request.getRequestURL().toString();
		String url = "http://login.datuodui.com:8080/login";
		String encode = URLEncoder.encode(callback, "UTF-8");
		url = url+"?auth_callback="+encode;
		if (!response.isCommitted()){
			response.sendRedirect(url);
		}
	}
}
