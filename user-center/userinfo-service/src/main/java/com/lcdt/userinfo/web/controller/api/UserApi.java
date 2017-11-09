package com.lcdt.userinfo.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.RegisterUtils;
import com.lcdt.userinfo.web.dto.ModifyUserDto;
import com.lcdt.userinfo.web.exception.PwdErrorException;
import com.lcdt.util.WebProduces;
import com.lcdt.util.validate.ValidateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Created by ss on 2017/10/27.
 * TODO 参数校验
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "用户api", description = "用户相关操作")
public class UserApi {

	@Autowired
	UserService userService;

	@ApiOperation("获取用户信息")
	@RequestMapping(value = "/get", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
	public User getUserInfo() {
		User user = SecurityInfoGetter.getUser();
		//将密码设置为空字符串
		user.setPwd("");
		return user;
	}

	@ApiOperation("获取用户权限")
	@RequestMapping(value = "/authorities",method = RequestMethod.GET)
	public List<? extends GrantedAuthority> getUserAuthorities() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		ArrayListResponseWrapper<? extends GrantedAuthority> grantedAuthorities = new ArrayListResponseWrapper<>(authorities);
		return grantedAuthorities;
	}


	@ApiOperation("编辑用户个人信息")
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public User editUserInfo(@Validated ModifyUserDto modifyUserDto, BindingResult result) {

		if (result.hasErrors()) {
			throw new ValidateException("参数错误");
		}

		User user = SecurityInfoGetter.getUser();
		user.setNickName(modifyUserDto.getNickName());
		user.setRealName(modifyUserDto.getName());
		if (!StringUtils.isEmpty(modifyUserDto.getAvatarUrl())) {
			user.setPictureUrl(modifyUserDto.getAvatarUrl());
		}
		if (modifyUserDto.getEmail() != null) {
			user.setEmail(modifyUserDto.getEmail());
		}

		if (modifyUserDto.getBirthDay() != null) {
			user.setBirthday(modifyUserDto.getBirthDay());
		}

		if (modifyUserDto.getIntro() != null) {
			user.setIntroMemo(modifyUserDto.getIntro());
		}

		userService.updateUser(user);
		return user;
	}

	@RequestMapping(value = "/modifypwd",method = RequestMethod.POST)
	@ApiOperation("修改用户密码")
	public User modifyPwd(@ApiParam(required = true) String oldPwd,@ApiParam(required = true) String newPwd) throws UserNotExistException {
		String encodeNewpwd = RegisterUtils.md5Encrypt(newPwd);
		String encodeOldpwd = RegisterUtils.md5Encrypt(oldPwd);
		User user = SecurityInfoGetter.getUser();
		User originUser = userService.queryByUserId(user.getUserId());
		if (originUser.getPwd().equals(encodeOldpwd)) {
			originUser.setPwd(encodeNewpwd);
			userService.updateUser(user);
		}else{
			throw new PwdErrorException("密码不正确");
		}
		return user;
	}


}
