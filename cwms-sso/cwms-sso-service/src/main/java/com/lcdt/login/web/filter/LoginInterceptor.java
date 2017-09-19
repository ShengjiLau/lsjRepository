package com.lcdt.login.web.filter;

import com.lcdt.login.annontion.ExcludeIntercept;
import com.lcdt.login.web.LoginSessionReposity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;

/**
 * Created by ss on 2017/8/25.
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

		if (matchExcludeUrls(httpServletRequest,o)) {
			return true;
		}

		if (LoginSessionReposity.isLogin(httpServletRequest)) {
			httpServletResponse.sendRedirect("/login");
			return false;
		}

		if (!LoginSessionReposity.loginCompany(httpServletRequest)) {
			httpServletResponse.sendRedirect("/chooseCompany");
			return true;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}


	public boolean matchExcludeUrls(HttpServletRequest request,Object handler) {
		ExcludeIntercept excludeIntercept = ((HandlerMethod) handler).getMethodAnnotation(ExcludeIntercept.class);
		return excludeIntercept != null;
	}

}
