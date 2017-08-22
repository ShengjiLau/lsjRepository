package com.lcdt.login.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ss on 2017/8/18.
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	AuthTicketService ticketService;

	@Reference
	UserService userService;

	@Value("${login.host}")
	private String host;

	@Override
	public FrontUserInfo queryTicket(String ticket) throws InvalidTicketException, UserNotExistException {
		AuthTicketService.Ticket ticketValid = ticketService.isTicketValid(ticket);
		if (ticketValid == null) {
			throw new InvalidTicketException("ticket 验证失败");
		}
		FrontUserInfo frontUserInfo = userService.queryByUserId(ticketValid.getUserId());
		return frontUserInfo;
	}


	public String loginUrl(String srcUrl){
		if (!host.startsWith("http://")) {
			host = "http://" + host;
		}
		StringBuilder sb = new StringBuilder(host);
		sb.append("?").append("auth_callback=").append(srcUrl);
		return sb.toString();
	}

}
