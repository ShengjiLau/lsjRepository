package com.lcdt.web.sso.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by ss on 2017/8/21.
 */
public class TicketAuthenticationToken extends AbstractAuthenticationToken {


	private Object principal;
	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Creates a token with the supplied array of authorities.
	 *
	 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
	 *                    represented by this authentication object.
	 */
	public TicketAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return ticket;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
