package com.lcdt.web.controller.restfulApi;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/10/26.
 */
@RestController("/api")
@RequestMapping("/api")
public class DemoApi {

	@RequestMapping("/test")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
	public String test(){
		return "测试ajax";
	}

}
