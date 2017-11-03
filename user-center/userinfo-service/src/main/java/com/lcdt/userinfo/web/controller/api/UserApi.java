package com.lcdt.userinfo.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.web.dto.ModifyUserDto;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/10/27.
 * TODO 参数校验 swagger api
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "用户api",description = "用户相关操作")
public class UserApi {

	@Autowired
	UserService userService;

	@ApiOperation("获取用户信息")
	@RequestMapping(value = "/get", produces = WebProduces.JSON_UTF_8,method = RequestMethod.GET)
	public User getUserInfo() {
		User user = SecurityInfoGetter.getUser();
		//将密码设置为空字符串
		user.setPwd("");
		return user;
	}

	@GetMapping
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> listUsers() {
		User user = SecurityInfoGetter.getUser();
		//将密码设置为空字符串
		user.setPwd("");
		ArrayList<User> users = new ArrayListResponseWrapper<>(10);
		users.add(user);
		for (int i = 0; i < 10; i++) {
			User user1 = new User();
			user1.setUserId((long) i);
			users.add(user1);
//			users.add(user);
		}
		return users;
	}

	@PostMapping
	@RequestMapping(value = "/modify")
	public User editUserInfo(ModifyUserDto modifyUserDto) {
		User user = SecurityInfoGetter.getUser();
		userService.updateUser(user);
		return user;
	}

	@RequestMapping(value = "/modifypwd")
	public User modifyPwd( String oldPwd, String newPwd) {
		return null;
	}


}
