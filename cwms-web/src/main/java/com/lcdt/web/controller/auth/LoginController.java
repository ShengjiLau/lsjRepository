package com.lcdt.web.controller.auth;

import com.lcdt.web.auth.WmsUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ss on 2017/8/8.
 */
@Controller
@RequestMapping("/auth")
public class LoginController {

	@RequestMapping("/loginpage")
	public ModelAndView loginPage() {
		return new ModelAndView("/auth/signin");
	}


	@RequestMapping("/companys")
	@ResponseBody
	public String loginSuccessChooseCompany() {
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WmsUserDetails user = (WmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUserInfo().getUserId().toString();
	}


}
