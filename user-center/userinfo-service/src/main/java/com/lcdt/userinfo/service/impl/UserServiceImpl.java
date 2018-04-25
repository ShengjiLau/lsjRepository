package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.DriverMapper;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.RegisterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by ss on 2017/7/31.
 */
@com.alibaba.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DriverMapper driverMapper;

	@Override
	public User updateUser(User user) {
		userMapper.updateByPrimaryKeyWithoutPwd(user);
		return user;
	}

	@Override
	public User registerDriverUser(User user) {
		userMapper.insert(user);
		Driver driver = new Driver();
		driver.setUserId(user.getUserId());
		driver.setDriverPhone(user.getPhone());
		driver.setDriverName(user.getNickName());
		driverMapper.insert(driver);
		return user;
	}

	public User updateUserWithpwd(User user) {
		userMapper.updateByPrimaryKey(user);
		return user;
	}


	@Transactional
	@Override
	public User registerUser(RegisterDto registerDto) throws PhoneHasRegisterException {
		boolean phoneBeenRegester = isPhoneBeenRegister(registerDto.getUserPhoneNum());
		if (phoneBeenRegester) {
			throw new PhoneHasRegisterException("手机号已被注册");
		}
		User registerUser = new User();
		registerUser.setPhone(registerDto.getUserPhoneNum());
		String md5EncryptPwd = RegisterUtils.md5Encrypt(registerDto.getPassword());
		registerUser.setPwd(md5EncryptPwd);
		registerUser.setRealName(registerDto.getName());
		registerUser.setReferrerPhone(registerDto.getIntroducer());
		registerUser.setRegisterDate(new Date());
		registerUser.setUserStatus((short)1); //默认启用状态
		registerUser.setMobileCountry("86");
		registerUser.setEmail(registerDto.getEmail());
		registerUser.setLastLoginTime(new Date());
		registerUser.setNickName(registerDto.getName());
		int insert = userMapper.insert(registerUser);
		return registerUser;
	}


	@Transactional(readOnly = true)
	@Override
	public boolean isPhoneBeenRegister(String phone) {
		User user = userMapper.queryByUserPhone(phone);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public User userLogin(String username, String pwd) throws UserNotExistException, PassErrorException {
		User user = queryByPhone(username);

		if (user.getPwd() == null) {
			throw new PassErrorException("密码未设置");
		}
		if (user.getPwd().toUpperCase().equals(RegisterUtils.md5Encrypt(pwd).toUpperCase())){
			user.setLastLoginTime(new Date()); //更新登录时间
			userMapper.updateByPrimaryKey(user);
			return user;
		}else{
			throw new PassErrorException("手机号或者密码错误，请重新输入");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public User selectUserByPhone(String phone) {
		return userMapper.queryByUserPhone(phone);
	}

	@Override
	public User resetPwd(String username,String pwd) {
		String encrypt = RegisterUtils.md5Encrypt(pwd);
		User user = userMapper.queryByUserPhone(username);
		user.setPwd(encrypt);
		userMapper.updateByPrimaryKey(user);
		return user;
	}


	@Override
	public boolean checkUserLogin() {
		return false;
	}

	@Transactional(readOnly = true)
	@Override
	public User queryByUserId(Long userId) throws UserNotExistException {
		User user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new UserNotExistException();
		}
		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public User queryByPhone(String phone) throws UserNotExistException {
		User user = userMapper.queryByUserPhone(phone);
		if (user == null) {
			throw new UserNotExistException("手机号码未注册，请重新输入");
		}
		return user;
	}


}
