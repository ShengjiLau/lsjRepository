package com.lcdt.userinfo.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.web.dto.ModifyUserDto;
import com.lcdt.util.WebProduces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/10/27.
 * TODO 参数校验 swagger api
 */
@RestController
@RequestMapping("/api/user")
public class UserApi {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/get",produces = WebProduces.JSON_UTF_8)
	@PreAuthorize("hasAnyAuthority('userget')")
	public User getUserInfo(){
		User user = SecurityInfoGetter.getUser();
		//将密码设置为空字符串
		user.setPwd("");
		return user;
	}

	@RequestMapping(value = "/modify")
	public User editUserInfo(ModifyUserDto modifyUserDto){
		User user = SecurityInfoGetter.getUser();
		userService.updateUser(user);
		return user;
	}

	@RequestMapping(value = "/modifypwd")
	public User modifyPwd(String oldPwd,String newPwd){
		return null;
	}


}
