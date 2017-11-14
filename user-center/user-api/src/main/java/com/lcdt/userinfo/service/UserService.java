package com.lcdt.userinfo.service;

import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;

/**
 * Created by ss on 2017/7/31.
 */
public interface UserService {

	User updateUser(User user);

	User registerUser(RegisterDto registerDto) throws PhoneHasRegisterException;

	boolean isPhoneBeenRegister(String phone);

	boolean checkUserLogin();

	User queryByUserId(Long userId) throws UserNotExistException;

	User queryByPhone(String phone) throws UserNotExistException;

	User userLogin(String username, String pwd) throws UserNotExistException, PassErrorException;

	User selectUserByPhone(String phone);
}
