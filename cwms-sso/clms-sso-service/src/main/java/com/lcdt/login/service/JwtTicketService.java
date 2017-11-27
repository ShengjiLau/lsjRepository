package com.lcdt.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ss on 2017/11/27.
 */
public class JwtTicketService implements CreateTicketService {

	Algorithm algorithmHS = Algorithm.HMAC256("datuodui_clms");

	public JwtTicketService() throws UnsupportedEncodingException {
	}

	@Override
	public String createTicket() {
		return null;
	}

	@Override
	public String createTicket(Date date) {

		HashMap<String, Object> headers = new HashMap<>(1);

		headers.put("token", "");

		String auth0 = JWT.create()
				.withIssuer("auth0")
				.withHeader(headers)
				.sign(algorithmHS);

		return auth0;
	}
}
