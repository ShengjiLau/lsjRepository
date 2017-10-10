package com.lcdt.login.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.service.LoginService;
import com.lcdt.login.ticket.TicketBean;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
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

	@Reference
	CompanyService companyService;

	@Value("${login.host}")
	private String host;

	@Override
	public TicketAuthentication queryTicket(String ticket) throws InvalidTicketException, UserNotExistException {
		TicketBean ticketValid = ticketService.isTicketValid(ticket);
		if (ticketValid == null) {
			throw new InvalidTicketException("ticket 验证失败");
		}
		User user = userService.queryByUserId(ticketValid.getUserId());
		TicketAuthentication authentication = new TicketAuthentication();
		authentication.setTicket(ticket);
		authentication.setUser(user);
		if (ticketValid.getCompanyId() == null) {
			authentication.setChooseCompany(false);
		}else{
			UserCompRel companyMember = companyService.queryByUserIdCompanyId(ticketValid.getUserId(), ticketValid.getCompanyId().intValue());
			authentication.setUserCompRel(companyMember);
		}
		return authentication;
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
