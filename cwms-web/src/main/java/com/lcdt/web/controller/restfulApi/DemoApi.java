package com.lcdt.web.controller.restfulApi;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.WebProduces;
import com.lcdt.web.config.Code;
import io.swagger.annotations.Api;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/10/26.
 */
@RestController("/api")
@RequestMapping("/api")
@Api(value = "demoapi")
public class DemoApi {

	@RequestMapping("/test")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
	public String test(){
		return "测试ajax";
	}

	@RequestMapping(value = "/user",produces = WebProduces.JSON_UTF_8)
	@ResponseBody
	@Code("1")
	public User restTest(){
		User user = SecurityInfoGetter.getUser();
		return user;
	}

}
