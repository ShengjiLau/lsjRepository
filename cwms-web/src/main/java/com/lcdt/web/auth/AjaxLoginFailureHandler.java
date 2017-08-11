package com.lcdt.web.auth;

import com.lcdt.web.http.JsonResponseStraegy;
import com.lcdt.web.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by ss on 2017/8/10.
 */
public class AjaxLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private JsonResponseStraegy jsonResponseStraegy;

	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String message = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, Locale.CHINA);
		System.out.println(message);
		if(HttpUtils.isAjaxRequest(request)){
			jsonResponseStraegy.responseLoginError(response, exception);
			return;
		}
		super.onAuthenticationFailure(request, response, exception);
	}
}
