package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.FrontUserInfoMapper;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.utils.RegisterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ss on 2017/7/31.
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
public class UserServiceImpl implements UserService {

	@Autowired
	private FrontUserInfoMapper userInfoMapper;

	@Transactional
	@Override
	public FrontUserInfo registerUser(RegisterDto registerDto) throws PhoneHasRegisterException {
		boolean phoneBeenRegester = isPhoneBeenRegister(registerDto.getUserPhoneNum());
		if (phoneBeenRegester) {
			throw new PhoneHasRegisterException();
		}
		FrontUserInfo registerUser = new FrontUserInfo();
		registerUser.setUserName(registerDto.getUserPhoneNum());
		String md5EncryptPwd = RegisterUtils.md5Encrypt(registerDto.getPassword());
		registerUser.setUserPass(md5EncryptPwd);
		registerUser.setName(registerDto.getName());
		registerUser.setIntroducer(registerDto.getIntroducer());
		registerUser.setRegDt(new Date());
		int insert = userInfoMapper.insert(registerUser);
		return registerUser;
	}


	@Transactional(readOnly = true)
	@Override
	public boolean isPhoneBeenRegister(String phone) {
		List<FrontUserInfo> frontUserInfos = userInfoMapper.queryByUserPhone(phone);
		if (frontUserInfos == null || frontUserInfos.isEmpty()) {
			return false;
		}
		return true;
	}



	@Override
	public boolean checkUserLogin() {
		return false;
	}

	@Transactional(readOnly = true)
	@Override
	public FrontUserInfo queryByUserId(Integer userId) throws UserNotExistException {
		FrontUserInfo frontUserInfo = userInfoMapper.selectByPrimaryKey(userId);
		if (frontUserInfo == null) {
			throw new UserNotExistException();
		}
		return frontUserInfo;
	}

	@Transactional(readOnly = true)
	@Override
	public FrontUserInfo queryByPhone(String phone) throws UserNotExistException {
		List<FrontUserInfo> frontUserInfos = userInfoMapper.queryByUserPhone(phone);
		if (frontUserInfos == null || frontUserInfos.isEmpty()) {
			throw new UserNotExistException();
		}
		return frontUserInfos.get(0);
	}


}
