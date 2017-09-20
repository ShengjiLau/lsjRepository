package com.lcdt.login.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ss on 2017/8/17.
 */
@Component
public class RequestAuthRedirectStrategy {

	public static final String LOGINPAGE = "/account/";
	public static final String COMPANYPAGE = "/account/company";
	private static final String AUTH_CALLBACK = "auth_callback";
	@Value("${login.defaultcallback}")
	private static String default_callback = "http://test.datuodui.com:8088";
	@Value("${login.safecallback}")
	private static List<String> safeCallbackUrls;

	public static void rediectToLoginPage(HttpServletRequest request, HttpServletResponse response) {
		safeRediect(response, LOGINPAGE);
	}

	public static void rediectToCompanyPage(HttpServletRequest request, HttpServletResponse response) {
		safeRediect(response, COMPANYPAGE);
	}

	public static void safeRediect(HttpServletResponse response, String url) {
		if (!response.isCommitted()) {
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
		if (safeCallbackUrls == null) {
			return true;
		}
		for (String url : safeCallbackUrls) {
			if (url.equals(callback)) {
				return true;
			}
		}
		return false;
	}


}
