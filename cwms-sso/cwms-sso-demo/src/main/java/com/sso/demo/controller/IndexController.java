package com.sso.demo.controller;

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
	public String index(){
		return "this is demo index";
	}

}
