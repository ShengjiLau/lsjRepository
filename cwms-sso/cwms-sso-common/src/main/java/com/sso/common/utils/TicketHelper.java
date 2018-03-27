package com.sso.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ss on 2017/9/19.
 */
public class TicketHelper {

	public static String findTicketInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (Cookie cookie : cookies) {
			if (cookie != null && "cwms_ticket".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
