package com.lcdt.clms.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.config.TicketAuthenticationToken;
import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.LoginService;
import com.lcdt.userinfo.exception.UserNotExistException;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ss on 2017/12/1.
 */
@Service
public class RpcGetAuthentication implements Authentication {

	private ThreadLocal<String> ticketThreadLocal = new NamedThreadLocal<String>("ticketThreadLocal");

	private ConcurrentHashMap<String, TicketAuthentication> authMap = new ConcurrentHashMap<>();



	@Reference(check = false)
	private LoginService loginService;

	public void setThreadTicket(String ticket) {
		ticketThreadLocal.set(ticket);
	}


	private TicketAuthentication queryAndSaveAuthInfo() throws InvalidTicketException, UserNotExistException {
		String s = ticketThreadLocal.get();
		TicketAuthentication ticketAuthentication = loginService.queryTicket(s);
		authMap.put(s,ticketAuthentication);
		return ticketAuthentication;
	}





	public String getTicket() {
		String ticket = ticketThreadLocal.get();
		if (StringUtils.isEmpty(ticket)) {
			throw new RuntimeException();
		} else {
			return ticket;
		}
	}


	public boolean needUpdate() {
		return false;
	}

	public TicketAuthentication getObject() {
		String ticket = getTicket();
		if (needUpdate()) {
			//TODO update
			try {
				return queryAndSaveAuthInfo();
			} catch (InvalidTicketException e) {
				e.printStackTrace();
			} catch (UserNotExistException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			TicketAuthentication ticketAuthentication = authMap.get(ticket);
			return ticketAuthentication;
		}
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return getObject() == null ? null : getObject(
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return getObject() != null;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (getObject() == null) {
			return;
		}
	}

	@Override
	public String getName() {

		return null;
	}
}
