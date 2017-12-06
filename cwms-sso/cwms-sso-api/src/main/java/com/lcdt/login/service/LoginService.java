package com.lcdt.login.service;

import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.userinfo.exception.UserNotExistException;

/**
 * Created by ss on 2017/8/18.
 */
public interface LoginService {

	TicketAuthentication queryTicket(String ticket) throws InvalidTicketException, UserNotExistException;

	String loginUrl(String srcUrl);

	boolean needUpdateAuth(String ticket);

}
