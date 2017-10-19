package com.lcdt.login.web.filter;

import com.lcdt.login.web.LoginSessionReposity;
import com.lcdt.login.web.RequestAuthRedirectStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/9/19.
 */
public class CompanyInterceptorAbstract extends AbstractExcludeUrlAnnontionInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptorAbstract.class);

	@Override
	public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!LoginSessionReposity.loginCompany(request)) {
			RequestAuthRedirectStrategy.rediectToCompanyPage(request,response);
			logger.info(request.getRequestURI() + " 未选择公司 跳转至公司页面");
			return false;
		}
		return true;
	}
}
