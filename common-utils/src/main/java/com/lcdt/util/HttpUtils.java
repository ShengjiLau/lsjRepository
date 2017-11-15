package com.lcdt.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ss on 2017/8/10.
 */
public final class HttpUtils {

	public static String xmlHttpRequestHeader = "XMLHttpRequest";

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xmlhttpHeader = request.getHeader("X-Requested-With");
		if (xmlHttpRequestHeader.equals(xmlhttpHeader)){
			return true;
		} else {
			return false;
		}
	}
	private static Pattern HOST_PATTERN = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);

	//获取父域名
	public static String getUrlDomain(String url) {

		if (StringUtils.isEmpty(url)) {
			return "";
		}

		if (url.contains("localhost")) {
			return "localhost";
		}
		Matcher matcher = HOST_PATTERN.matcher(url);
		String result = matcher.find() ? matcher.group() : "";
		return result;
	}
}
