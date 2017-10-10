package com.lcdt.login.web;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.sso.common.utils.TicketHelper;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginSessionReposity {

	private static final String USERINFO_SESSION = "userinfo";

	private static final String COMPANY_SESSION = "company";

	public static User getUserInfoInSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		return getObjectInSession(User.class, session, USERINFO_SESSION);
	}

	public static UserCompRel getCompanyMember(HttpServletRequest request) {
		UserCompRel objectInSession = getObjectInSession(UserCompRel.class,request.getSession(false), COMPANY_SESSION);
		return objectInSession;
	}

	//判断当前是否已经选择公司
	public static boolean loginCompany(HttpServletRequest request){
		boolean companyIsLogin = isLogin(request) ? getCompanyMember(request) == null ? false : true : false;
		String ticketInCookie = TicketHelper.findTicketInCookie(request);
		if (!StringUtils.isEmpty(ticketInCookie) && companyIsLogin) {
			return true;
		}
		return false;
	}

	public static boolean isLogin(HttpServletRequest request) {
		return getUserInfoInSession(request) != null;
	}

	public static void setUserInSession(HttpServletRequest request,User userInfo){
		HttpSession session = request.getSession(true);
		session.setAttribute(USERINFO_SESSION,userInfo);
	}

	public static void setCompanyMemberInSession(HttpServletRequest request, UserCompRel member){
		HttpSession session = request.getSession(true);
		session.setAttribute(COMPANY_SESSION,member);
	}


	public static void clearUserSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		session.invalidate();
	}


	public static <T> T getObjectInSession(Class<T> clazz,HttpSession session, String key) {
		if (session == null) {
			return null;
		}

		Object attribute = session.getAttribute(key);
		if (attribute == null) {
			return null;
		}else{
			return (T) attribute;
		}
	}
}
