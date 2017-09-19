package com.sso.client.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ss on 2017/9/19.
 */
public class TicketHelper {

	public static String findTicketInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("cwms_ticket")) {
				return cookie.getValue();
			}
		}
		return null;
	}

}
