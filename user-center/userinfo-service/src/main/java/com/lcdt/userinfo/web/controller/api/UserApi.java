package com.lcdt.userinfo.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.RegisterUtils;
import com.lcdt.userinfo.web.dto.ModifyUserDto;
import com.lcdt.userinfo.web.dto.UserInfoDto;
import com.lcdt.userinfo.web.exception.PwdErrorException;
import com.lcdt.util.RandomNoUtil;
import com.lcdt.util.WebProduces;
import com.lcdt.util.validate.ValidateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by ss on 2017/10/27.
 *
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "用户api", description = "用户相关操作")
public class UserApi {

	@Autowired
	UserService userService;

	@Autowired
	GroupManageService groupManageService;

	@Reference
	NotifyService notifyService;

	@ApiOperation("手机号是否已注册")
	@RequestMapping(value = "/isPhoneRegister",method = RequestMethod.POST)
	public String isPhoneRegister(String phone) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 0);
		try {
			User user = userService.queryByPhone(phone);
			user.setPwd("");
			jsonObject.put("data", user);
		} catch (UserNotExistException e) {
			e.printStackTrace();
			jsonObject.put("data", false);
		}
		return jsonObject.toString();
	}


	@ApiOperation("获取用户信息")
	@RequestMapping(value = "/get", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('getUserInfo')")
	public UserInfoDto getUserInfo() {
		User user = SecurityInfoGetter.getUser();
		List<Group> groups = SecurityInfoGetter.groups();
		user = userService.selectUserByPhone(user.getPhone());
		//将密码设置为空字符串
		user.setPwd("");
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setGroups(groups);
		userInfoDto.setUser(user);
		return userInfoDto;
	}


	@ApiOperation("获取企业信息")
	@RequestMapping("/getallinfo")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('getUserInfo')")
	public UserCompRel userCompInfo(){
		UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
		userCompRel.getUser().setPwd("");
		return userCompRel;
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
		user = userService.selectUserByPhone(user.getPhone());
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
		user.setPwd("");
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
			userService.updateUser(originUser);
		}else{
			throw new PwdErrorException("密码不正确");
		}
		return user;
	}


	@ApiOperation("更换手机号")
	@RequestMapping(value = "/changephone",method = RequestMethod.POST)
	public User changePhone(HttpSession httpSession,
							@ApiParam(value = "登录密码",required = true) @RequestParam String pwd,
							@ApiParam(value = "新手机号",required = true) @RequestParam String newphone,
							@ApiParam(value = "验证码",required = true) @RequestParam String captcha) {
		if (userService.isPhoneBeenRegister(newphone)) {
			throw new ValidateException("手机号"+newphone+"已注册！");
		}
		User user = SecurityInfoGetter.getUser(); //获取当前登录账号
		try {
			user = userService.queryByUserId(user.getUserId());
		} catch (UserNotExistException e) {
			throw new ValidateException("获取用户信息出错:"+e.getMessage());
		}
		String encodeNewpwd = RegisterUtils.md5Encrypt(pwd);
		if (!user.getPwd().equals(encodeNewpwd)) {
			throw new PwdErrorException("密码不正确！");
		}
		String vCodeSession = (String) httpSession.getAttribute("CWMS_SMS_VCODE");
		if (vCodeSession==null) {
			throw new ValidateException("验证码无效！");
		} else {
			if (!captcha.equals(vCodeSession.split("_")[0])) {
				throw new ValidateException("验证码无效！");
			}
		}
		user.setPhone(newphone);
		userService.updateUser(user);
		return user;
	}


	@ApiOperation("发送验证码")
	@RequestMapping(value = "/getCaptcha",method = RequestMethod.POST)
	@ResponseBody
	public String getCaptcha(HttpSession httpSession,@ApiParam(value = "新手机号",required = true) @RequestParam String newphone) {
		if (userService.isPhoneBeenRegister(newphone)) {
			throw new ValidateException("手机号"+newphone+"已注册！");
		}

		Long  cTime = System.currentTimeMillis(); //其实时间
		if (httpSession.getAttribute("CWMS_SMS_SEND_TIME")==null) {
			httpSession.setAttribute("CWMS_SMS_SEND_TIME", cTime);
			String[] phones = new String[]{newphone};
			String vCode = RandomNoUtil.createRandom(true,4);
			notifyService.sendSms(phones,vCode);
			httpSession.setAttribute("CWMS_SMS_VCODE", vCode+"_"+newphone);
		} else {
			Long oTime = Long.valueOf(httpSession.getAttribute("CWMS_SMS_SEND_TIME").toString());
			if (cTime - oTime >= 60*1000) { //大于1分中重新获取
				httpSession.setAttribute("CWMS_SMS_SEND_TIME", cTime);
				String[] phones = new String[]{newphone};
				String vCode = RandomNoUtil.createRandom(true,4);
//                    smsService.sendSms(phones, signature, vCode);
				httpSession.setAttribute("CWMS_SMS_VCODE", vCode+"_"+newphone);
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message","发送成功");
		jsonObject.put("code",0);
		return jsonObject.toString();
	}

}
