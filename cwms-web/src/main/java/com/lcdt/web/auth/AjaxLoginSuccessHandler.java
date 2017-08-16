package com.lcdt.web.auth;

import com.lcdt.web.http.JsonResponseStraegy;
import com.lcdt.web.utils.HttpUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/10.
 */
public class AjaxLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private JsonResponseStraegy jsonResponseStraegy = new JsonResponseStraegy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (HttpUtils.isAjaxRequest(request)) {
			jsonResponseStraegy.responseOk(response);
			return;
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
