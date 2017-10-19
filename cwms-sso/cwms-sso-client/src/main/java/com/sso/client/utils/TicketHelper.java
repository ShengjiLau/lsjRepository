package com.sso.client.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ss on 2017/9/19.
 */
public class TicketHelper {

	public static String findTicketInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if ("cwms_ticket".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
