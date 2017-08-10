package com.lcdt.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ss on 2017/8/10.
 */
public class HttpUtils {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String xmlhttpHeader = request.getHeader("X-Requested-With");
		if (xmlhttpHeader != null && xmlhttpHeader.equals("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}

}
