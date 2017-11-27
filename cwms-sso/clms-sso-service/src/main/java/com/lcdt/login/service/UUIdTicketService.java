package com.lcdt.login.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ss on 2017/11/27.
 */
@Service
public class UUIdTicketService implements CreateTicketService {
	@Override
	public String createTicket() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	@Override
	public String createTicket(Date date) {
		return createTicket();
	}
}
