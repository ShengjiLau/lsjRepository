package com.lcdt.login.service;

import com.lcdt.login.ticket.TicketBean;

/**
 * Created by ss on 2017/9/15.
 */
public interface TicketManager {

	public TicketBean get(String ticket);

	public void saveTicket(String ticket, TicketBean ticketBean);

	public boolean removeTicketCache(String ticket);

}
