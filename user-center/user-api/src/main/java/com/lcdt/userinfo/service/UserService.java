package com.lcdt.userinfo.service;

import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;

/**
 * Created by ss on 2017/7/31.
 */
public interface UserService {

	FrontUserInfo registerUser(RegisterDto registerDto) throws PhoneHasRegisterException;

	boolean isPhoneBeenRegester(String phone);

	boolean checkUserLogin();

	FrontUserInfo queryByUserId(Integer userId) throws UserNotExistException;

	FrontUserInfo queryByPhone(String phone) throws UserNotExistException;
}
