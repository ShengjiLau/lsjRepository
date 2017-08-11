package com.lcdt.web.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.cwms.user.model.WmsCompanyUserRelation;
import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.web.auth.WmsUserDetails;
import com.lcdt.web.http.JsonResponseStraegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
@Controller
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	CompanyService companyService;

	@Autowired
	JsonResponseStraegy jsonResponseStraegy;

	@RequestMapping("/loginpage")
	public ModelAndView loginPage() {
		return new ModelAndView("/auth/signin");
	}

	@RequestMapping("/companys")
	@ResponseBody
	public String loginSuccessChooseCompany() {
		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WmsUserDetails user = (WmsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<WmsCompanyUserRelation> wmsCompanyUserRelations = companyService.userCompanys(user.getUserInfo().getUserId());
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("data", wmsCompanyUserRelations);
		return obj.toString();
	}
}
