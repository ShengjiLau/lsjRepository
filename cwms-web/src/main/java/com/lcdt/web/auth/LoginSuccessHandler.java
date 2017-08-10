package com.lcdt.web.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ss on 2017/8/9.
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		//当认证成功时
		String xmlhttpRequest = httpServletRequest.getHeader("X-Requested-With");
		if (xmlhttpRequest != null && xmlhttpRequest.equals("XMLHttpRequest")){
			PrintWriter writer = httpServletResponse.getWriter();
			writer.write("loginsuccess");
			writer.flush();
			return;
		}

		httpServletResponse.sendRedirect("/auth/companys");
	}
}
