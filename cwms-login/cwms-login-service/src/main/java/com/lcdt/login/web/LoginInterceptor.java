package com.lcdt.login.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		if (!hasLogin(httpServletRequest) && matchLoginUrl(httpServletRequest)) {
			return true;
		} else {
			httpServletResponse.sendRedirect("/login");
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}


	public boolean hasLogin(HttpServletRequest request) {
		boolean login = LoginSessionReposity.isLogin(request);
		return login;
	}

	public boolean matchLoginUrl(HttpServletRequest request) {
		return false;
	}

}
