package com.sso.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ss on 2017/9/18.
 */
@Controller
public class IndexController {

	@ResponseBody
	@RequestMapping(value = {"/",""})
	@PreAuthorize("hasRole('admin')")
	public String index(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "this is demo index auth : " + authentication.toString();
	}


	@ResponseBody
	@RequestMapping("/test")
	@PreAuthorize("hasPermission('index')")
	public String test(){
		return "this is test page";
	}

}
