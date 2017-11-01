package com.lcdt.util;

import javax.servlet.http.HttpServletRequest;

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

}
