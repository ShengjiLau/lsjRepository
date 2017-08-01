package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.FrontUserInfoMapper;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ss on 2017/7/31.
 */
public class UserServiceImpl implements UserService {

	private FrontUserInfoMapper userInfoMapper;

	@Transactional
	@Override
	public FrontUserInfo registerUser(RegisterDto registerDto) throws PhoneHasRegisterException {

		boolean phoneBeenRegester = isPhoneBeenRegester(registerDto.getUserPhoneNum());
		if (phoneBeenRegester) {
			throw new PhoneHasRegisterException();
		}

		FrontUserInfo registerUser = new FrontUserInfo();
		registerUser.setUserName(registerDto.getUserPhoneNum());
		registerUser.setUserPass(registerDto.getPassword());
		int insert = userInfoMapper.insert(registerUser);
		return registerUser;
	}

	@Override
	public boolean isPhoneBeenRegester(String phone) {
		return false;
	}

	@Override
	public boolean checkUserLogin() {
		return false;
	}

	@Override
	public FrontUserInfo queryByUserId(Integer userId) {
		return null;
	}

	@Override
	public FrontUserInfo queryByPhone(String phone) {
		return null;
	}



}
