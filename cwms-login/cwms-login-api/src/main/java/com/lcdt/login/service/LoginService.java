package com.lcdt.login.service;

import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;

/**
 * Created by ss on 2017/8/18.
 */
public interface LoginService {

	FrontUserInfo queryTicket(String ticket) throws InvalidTicketException, UserNotExistException;


}
