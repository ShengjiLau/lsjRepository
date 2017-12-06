package com.lcdt.login.service.impl;

import com.lcdt.login.service.TicketManager;
import com.lcdt.login.ticket.TicketBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ss on 2017/9/18.
 */
@Service
@Primary
public class JdkTicketManager implements TicketManager {

	private ConcurrentHashMap<String, TicketBean> ticketCache = new ConcurrentHashMap<>(20);

	@Override
	public TicketBean get(String ticket) {
		if (StringUtils.isEmpty(ticket)) {
			return null;
		}
		return ticketCache.get(ticket);
	}


	@Override
	public void saveTicket(String ticket,TicketBean ticketBean) {
		ticketCache.put(ticket, ticketBean);
	}

	@Override
	public boolean removeTicketCache(String ticket) {
		boolean contains = ticketCache.contains(ticket);
		return contains == true ? ticketCache.remove(ticket) == null : false;
	}
}
