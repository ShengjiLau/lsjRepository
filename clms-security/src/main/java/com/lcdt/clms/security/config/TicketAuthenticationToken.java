package com.lcdt.clms.security.config;

import com.lcdt.clms.permission.model.Permission;
import com.lcdt.login.bean.TicketAuthentication;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ss on 2017/8/21.
 */
public class TicketAuthenticationToken extends AbstractAuthenticationToken {

	public TicketAuthenticationToken(String ticketString) {
		super(null);
		this.ticket = ticketString;
		super.setAuthenticated(false);
	}

	public TicketAuthenticationToken(String ticket,TicketAuthentication authentication,Collection<GrantedAuthority> authorities){
		super(authorities);
		this.authentication = authentication;
		this.ticket = ticket;
		super.setAuthenticated(true);
	}

	private String ticket;

	private TicketAuthentication authentication;

	public String getTicket() {
		return ticket;
	}

	@Override
	public Object getCredentials() {
		return ticket;
	}

	@Override
	public Object getDetails() {
		return authentication;
	}

	@Override
	public Object getPrincipal() {
		if (authentication != null) {
			return  authentication.getUser();
		}
		return null;
	}

	@Override
	public String getName() {
		return authentication.getUser().getRealName();
	}

}
