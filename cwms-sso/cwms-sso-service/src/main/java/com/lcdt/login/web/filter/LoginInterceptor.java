package com.lcdt.login.web.filter;

import com.lcdt.login.web.LoginSessionReposity;
import com.lcdt.login.web.RequestAuthRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginInterceptor extends ExcludeUrlAnnontionInterceptor {


	@Override
	public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!LoginSessionReposity.isLogin(request)) {
			RequestAuthRedirectStrategy.rediectToLoginPage(request,response);
			return false;
		}

		return true;
	}

}
