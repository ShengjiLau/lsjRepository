package com.lcdt.web.controller;

import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.login.bean.TicketAuthentication;
import com.sso.client.utils.PropertyUtils;
import com.sso.client.utils.RedirectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	public final String IndexPage = "index";

	@Autowired
	CompanyService companyService;


	@RequestMapping("/")
	public ModelAndView indexController() {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();
		ModelAndView view = new ModelAndView(IndexPage);
		view.addObject("userinfo", authentication.toString()+authentication.getAuthorities().size());
		String logouturl = RedirectHelper.assembleUrl(PropertyUtils.readProperties("sso.server.logout"), "test.datuodui.com:8088/asd");
		view.addObject("company", PropertyUtils.readProperties("sso.server.company"));
		view.addObject("logout", logouturl);
		return view;
	}

	@RequestMapping("/test")
	@ResponseBody
	@PreAuthorize("hasPermission('test_a')")
	public String testController(){
		return "这是一个测试Controller";
	}


}
