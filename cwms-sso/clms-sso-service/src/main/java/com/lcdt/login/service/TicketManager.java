package com.lcdt.login.service;

import com.lcdt.login.ticket.TicketBean;

/**
 * Created by ss on 2017/9/15.
 */
public interface TicketManager {

	TicketBean get(String ticket);

	void saveTicket(String ticket, TicketBean ticketBean);

	boolean removeTicketCache(String ticket);

}
