package com.lcdt.login.web.filter;

import com.lcdt.login.web.LoginSessionReposity;
import com.lcdt.login.web.RequestAuthRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/9/19.
 */
public class CompanyInterceptor extends ExcludeUrlAnnontionInterceptor  {

	@Override
	public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!LoginSessionReposity.loginCompany(request)) {
			RequestAuthRedirectStrategy.rediectToCompanyPage(request,response);
			return false;
		}

		return true;
	}
}
