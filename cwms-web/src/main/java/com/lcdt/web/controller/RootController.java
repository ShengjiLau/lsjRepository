package com.lcdt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ss on 2017/8/3.
 */
@Controller
public class RootController {

	@RequestMapping("/index")
	public String IndexController(){
		return "index";
	}


	@RequestMapping("/login")
	public String loginController(){
		return "login";
	}

}
