package com.lcdt.web.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/9.
 */
public class WmsUserNamePwdAuthFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String captchaCode = CaptchaUtil.getCaptchaString(request);
		if (captchaCode != null) {
			String captchaCode1 = request.getParameter("captchaCode");
			if (captchaCode1 == null || !captchaCode.equals(captchaCode1)) {
				throw new CaptchaCodeErrorException("验证码错误");
			}
		}
		return super.attemptAuthentication(request, response);
	}


	static class CaptchaCodeErrorException extends AuthenticationException {

		public CaptchaCodeErrorException(String msg, Throwable t) {
			super(msg, t);
		}

		public CaptchaCodeErrorException(String msg) {
			super(msg);
		}
	}

}
