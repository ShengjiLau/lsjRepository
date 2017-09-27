package com.lcdt.login.web.filter;

import com.lcdt.login.web.LoginSessionReposity;
import com.lcdt.login.web.RequestAuthRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginInterceptor extends ExcludeUrlAnnontionInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!LoginSessionReposity.isLogin(request)) {
			RequestAuthRedirectStrategy.rediectToLoginPage(request,response);
			logger.info(request.getRequestURI() + " 未登录 跳转登陆页");
			return false;
		}
		return true;
	}

}
