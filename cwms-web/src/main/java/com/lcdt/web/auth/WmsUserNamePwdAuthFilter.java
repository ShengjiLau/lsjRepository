package com.lcdt.web.auth;

import com.lcdt.web.exception.CaptchaTimeExpireException;
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
		String captchaCode = null;
		try {
			captchaCode = CaptchaUtil.getCaptchaString(request);
		} catch (CaptchaTimeExpireException e) {
			//验证码过期 提示刷新验证码
			throw new CaptchaCodeErrorException("验证码过期");
		}
		if (captchaCode != null) {
			String captchaCode1 = request.getParameter("captchaCode");
			if (captchaCode1 == null || !captchaCode.toUpperCase().equals(captchaCode1.toUpperCase())) {
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
