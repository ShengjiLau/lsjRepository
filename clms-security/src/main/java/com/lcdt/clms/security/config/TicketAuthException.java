package com.lcdt.clms.security.config;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by ss on 2017/9/19.
 */
public class TicketAuthException extends AuthenticationException {
	public TicketAuthException(String msg, Throwable t) {
		super(msg, t);
	}

	public TicketAuthException(String msg) {
		super(msg);
	}
}
