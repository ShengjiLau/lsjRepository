package com.sso.client.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

		String queryurl = request.getQueryString();
		if(null != queryurl){
			callback += "?" + queryurl;
		}
		callback = "http://" + request.getServerName() //服务器地址
				+ ":"
				+ request.getServerPort()           //端口号
				+ request.getContextPath()      //项目名称
				+ request.getServletPath();
		//请求页面或其他地址
		if (request.getQueryString() != null) {
			callback = callback + "?" + request.getQueryString();
		}

		String url = PropertyUtils.readProperties(PropertyUtils.LOGIN_URL);
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		if (!url.startsWith("http")) {
			url = "http://" + url;
		}

		String encode = "";
		try {
			encode = URLEncoder.encode(callback, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url = url+"?auth_callback="+encode;
		return url;
	}

	public static void redirectToLoginUrlWithAuthBack(HttpServletRequest request, HttpServletResponse response)  {

		if (!response.isCommitted()) {
			if (isAjaxRequest(request)) {
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", "302");
				jsonObject.put("message", "请先登录");
				jsonObject.put("redirecturl", assembleLoginUrlWithAuthBack(request));
				writer.write(jsonObject.toString());
				writer.flush();
				return;
			}

			try {
				String redirectUrl = assembleLoginUrlWithAuthBack(request);
				if (StringUtils.isEmpty(redirectUrl)) {
					response.setCharacterEncoding("UTF-8");
					response.sendError(404, "没有找到登陆页面");
					response.setHeader("Content-Type","text/html;charset=UTF-8");
					return;
				}

				response.sendRedirect(assembleLoginUrlWithAuthBack(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xmlhttpHeader = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(xmlhttpHeader)){
			return true;
		} else {
			return false;
		}
	}

}
