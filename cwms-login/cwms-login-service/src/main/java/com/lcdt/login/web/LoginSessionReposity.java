package com.lcdt.login.web;

import com.lcdt.userinfo.model.FrontUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginSessionReposity {

	private static final String USERINFO_SESSION = "userinfo";

	public static FrontUserInfo getUserInfo(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USERINFO_SESSION);
		if (user == null) {
			return null;
		}else{
			return (FrontUserInfo)user;
		}
	}

	public static boolean isLogin(HttpServletRequest request) {
		return true;
	}


	public static void setUserInSession(HttpServletRequest request,FrontUserInfo userInfo){
		HttpSession session = request.getSession(true);
		session.setAttribute(USERINFO_SESSION,userInfo);
	}

}
