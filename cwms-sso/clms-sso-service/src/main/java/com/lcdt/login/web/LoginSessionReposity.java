package com.lcdt.login.web;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.sso.common.utils.TicketHelper;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginSessionReposity {

	private static final String USERINFO_SESSION = "userinfo";

	private static final String COMPANY_SESSION = "company";

	private static final String CAPTCHA_SESSION = "captcha_code";
	private static final String AUTH_CALLBACK = "auth_callback";

	public static void setCallBackUrl(HttpServletRequest request) {
		String authCallback = RequestAuthRedirectStrategy.getAuthCallback(request);
		if (!StringUtils.isEmpty(authCallback)) {
			HttpSession session = request.getSession(true);
			session.setAttribute(AUTH_CALLBACK, authCallback);
		}
	}

	public static String getCallback(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		return getObjectInSession(String.class, session, AUTH_CALLBACK);
	}


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
		HttpSession session = request.getSession(false);
		session.setAttribute(COMPANY_SESSION,member);
	}


	/**
	 * 删除session
	 * @param request
	 */
	public static void clearUserSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		session.invalidate();
	}


	/***
	 * 验证码压入session
	 * @param request
	 * @param captchaCode
	 */
	public static void setCaptchaSession(HttpServletRequest request, String captchaCode) {
		HttpSession session = request.getSession(true);
		session.setAttribute(CAPTCHA_SESSION,captchaCode);
	}

	public static boolean captchaIsOk(HttpServletRequest request, String captchaCode) {
			HttpSession session = request.getSession(true);
			Object tCode = session.getAttribute(CAPTCHA_SESSION);
			if (tCode == null) {
				return false;
			}
			captchaCode = captchaCode.toUpperCase();
			if (!StringUtils.isEmpty(tCode) && tCode.equals(captchaCode)) {
				return true;
			}
			return false;
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
