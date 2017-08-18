package com.lcdt.login.web;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/17.
 */
public class RequestAuthRedirectStrategy {

	private static final String AUTH_CALLBACK = "auth_callback";

	private static String default_callback;

	private static String[] safeCallbackUrls;

	public void hasAuthRedirect(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter(AUTH_CALLBACK);
		if (StringUtils.isEmpty(callback) || !isCallBackSafe(callback)) {
			callback = default_callback;
		}
		try {
			response.sendRedirect(callback);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean isCallBackSafe(String callback) {
		for (String url : safeCallbackUrls) {
			if (url.equals(callback)) {
				return true;
			}
		}
		return false;
	}


}
