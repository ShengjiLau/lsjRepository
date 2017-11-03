package com.sso.client.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ss on 2017/9/20.
 */
public final class RedirectHelper {

	public static final String HTTP_PREFIX = "http://";

	public static final String HTTPS_PREFIX = "https://";



	public static String assembleUrl(String requestUrl, String callbackUrl) {
		try {
			if (!callbackUrl.toLowerCase().startsWith(HTTP_PREFIX) && !callbackUrl.startsWith(HTTPS_PREFIX)) {
				callbackUrl = HTTP_PREFIX + callbackUrl;
			}
			callbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
			requestUrl = requestUrl + "?auth_callback=" + callbackUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requestUrl;
	}


	public static String assembleLoginUrlWithAuthBack(HttpServletRequest request){
		String callback = request.getRequestURL().toString();
		String url = PropertyUtils.readProperties(PropertyUtils.LOGIN_URL);
		String encode = "";
		try {
			encode = URLEncoder.encode(callback, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url = url+"?auth_callback="+encode;
		return url;
	}

	public static void redirectToLoginUrlWithAuthBack(HttpServletRequest request, HttpServletResponse response) {

		if (!response.isCommitted()) {
			try {
				response.sendRedirect(assembleLoginUrlWithAuthBack(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
