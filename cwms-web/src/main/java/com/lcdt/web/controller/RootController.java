package com.lcdt.web.controller;

import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.web.auth.WmsUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.HttpSession;

/**
 * Created by ss on 2017/8/3.
 */
@Controller
public class RootController {

	@Autowired
	CompanyService companyService;


	@RequestMapping("/")
	@ResponseBody
	public String IndexController() {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		TicketAuthentication details = (TicketAuthentication) authentication.getDetails();
		return "index userId:" + details.getUserInfo().getUserId() + "company:"+details.getCompanyMember().getCompanyName();
	}


}
