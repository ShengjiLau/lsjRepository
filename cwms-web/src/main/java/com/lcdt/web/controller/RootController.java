package com.lcdt.web.controller;

import com.lcdt.cwms.user.dto.CreateCompanyDto;
import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.userinfo.exception.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ss on 2017/8/3.
 */
@Controller
public class RootController {

	@Autowired
	CompanyService companyService;

	@RequestMapping("/index")
	public String IndexController(){
		CreateCompanyDto createCompanyDto = new CreateCompanyDto();
		createCompanyDto.setBusinessScope("ceshi");
		createCompanyDto.setUserId(14);
		createCompanyDto.setCompanyName("ceshi");
		try {
			companyService.createCompany(createCompanyDto);
		} catch (UserNotExistException e) {
			e.printStackTrace();
			System.out.println("用户不存在");
			return "index";
		}
		return "index";
	}


	@RequestMapping("/login")
	public String loginController(){
		return "login";
	}

}
