package com.lcdt.web.sso.auth;

import com.sso.client.utils.PropertyUtils;
import com.sso.client.utils.RedirectHelper;
import org.springframework.beans.factory.annotation.Value;
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
		RedirectHelper.redirectToLoginUrlWithAuthBack(request,response);
	}
}
