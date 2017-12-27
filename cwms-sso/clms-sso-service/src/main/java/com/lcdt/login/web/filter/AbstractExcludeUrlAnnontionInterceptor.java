package com.lcdt.login.web.filter;

import com.lcdt.login.annontion.ExcludeIntercept;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/9/19.
 */
public abstract class AbstractExcludeUrlAnnontionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (matchExcludeUrls(request,handler)) {
			return true;
		}else{
			return doPreHandle(request, response, handler);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	public abstract boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

	public boolean matchExcludeUrls(HttpServletRequest request,Object handler) {
		if (handler instanceof HandlerMethod) {
			ExcludeIntercept excludeIntercept = ((HandlerMethod) handler).getMethodAnnotation(ExcludeIntercept.class);
			if (excludeIntercept == null) {
				return false;
			}
			Class[] classes = excludeIntercept.excludeIntercept();
			for (Class c : classes) {
				if (c.equals(this.getClass())) {
					return true;
				}
			}
			return false;
		}
			return true;

	}

}
