package com.lcdt.login.web;

import com.lcdt.userinfo.model.User;
import com.lcdt.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
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
	public static final String AUTH_CALLBACK = "auth_callback";
	@Value("${login.defaultcallback}")
	private static String default_callback = "";
	@Value("${login.safecallback}")
	private static List<String> safeCallbackUrls;

	public static String getAuthCallbackNodefault(HttpServletRequest request) {
		return request.getParameter(AUTH_CALLBACK);
	}


	public static String getAuthCallback(HttpServletRequest request){
		String callback = request.getParameter(AUTH_CALLBACK);
		if (StringUtils.isEmpty(callback)) {
			if (LoginSessionReposity.getCallback(request) != null) {
				callback = LoginSessionReposity.getCallback(request);
			}else {
				callback = default_callback;
			}
		}
		return callback;
	}


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
			if (LoginSessionReposity.getCallback(request) != null) {
				callback = LoginSessionReposity.getCallback(request);
			}else {
					callback = default_callback;
			}
		}

		//这里要判断是否是在同一个根域名下
		String callBackDomain = HttpUtils.getUrlDomain(callback);
		String requestDomain = HttpUtils.getUrlDomain(request.getRequestURL().toString());
		if (!callBackDomain.equals(requestDomain)) {
			try {
				response.setCharacterEncoding("UTF-8");
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"错误请求");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public static String getDefault_callback() {
		return default_callback;
	}
    @Value("${login.defaultcallback}")
	public  void setDefault_callback(String default_callback) {
		RequestAuthRedirectStrategy.default_callback = default_callback;
	}
}
