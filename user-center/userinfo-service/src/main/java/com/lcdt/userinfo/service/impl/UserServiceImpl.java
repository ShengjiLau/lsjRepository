package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.RegisterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ss on 2017/7/31.
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Transactional
	@Override
	public User registerUser(RegisterDto registerDto) throws PhoneHasRegisterException {
		boolean phoneBeenRegester = isPhoneBeenRegister(registerDto.getUserPhoneNum());
		if (phoneBeenRegester) {
			throw new PhoneHasRegisterException();
		}
		User registerUser = new User();


/*		registerUser.setUserName(registerDto.getUserPhoneNum());
		String md5EncryptPwd = RegisterUtils.md5Encrypt(registerDto.getPassword());
		registerUser.setUserPass(md5EncryptPwd);
		registerUser.setName(registerDto.getName());
		registerUser.setIntroducer(registerDto.getIntroducer());
		registerUser.setRegDt(new Date());*/
		int insert = userMapper.insert(registerUser);
		return registerUser;
	}


	@Transactional(readOnly = true)
	@Override
	public boolean isPhoneBeenRegister(String phone) {


/*		User user = userMapper.queryByUserPhone(phone);
		if (user == null) {
			return false;
		}*/
		return true;
	}

	@Transactional
	public User userLogin(String username, String pwd) throws UserNotExistException, PassErrorException {
		User user = queryByPhone(username);
		if (user.getPwd().toUpperCase().equals(RegisterUtils.md5Encrypt(pwd).toUpperCase())){
			return user;
		}else{
			throw new PassErrorException();
		}
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
			throw new UserNotExistException();
		}
		return user;
	}


}
