package com.lcdt.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ss on 2017/8/9.
 */
@Controller
public class CaptchaController {

	//返回验证码
	@RequestMapping("/auth/getcaptcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) {
		try {
			String randomString = CaptchaUtil.outputCaptcha(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
