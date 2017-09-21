package com.lcdt.web.controller;

import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.login.bean.TicketAuthentication;
import com.sso.client.utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ss on 2017/8/3.
 */
@Controller
public class RootController {

	@Autowired
	CompanyService companyService;


	@RequestMapping("/")
	public ModelAndView IndexController() {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();

		ModelAndView view = new ModelAndView("index");
		view.addObject("userinfo", "当前用户登录信息：" + details.getCompanyMember().toString() + " \n ticket:" + details.getTicket());
		view.addObject("logout", PropertyUtils.readProperties("sso.server.logout"));
		view.addObject("company", PropertyUtils.readProperties("sso.server.company"));

		return view;

//		return "index userId:" + details.getUserInfo().getUserId() + "company:"+details.getCompanyMember().getCompanyName();
	}


}
