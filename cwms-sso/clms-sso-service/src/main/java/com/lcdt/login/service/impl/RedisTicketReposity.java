package com.lcdt.login.service.impl;

import com.lcdt.login.service.TicketManager;
import com.lcdt.login.ticket.TicketBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by ss on 2017/12/1.
 */
@Service()
public class RedisTicketReposity implements TicketManager {

	@Autowired
	RedisTemplate redisTemplate;

	ValueOperations valueOperations;

	@PostConstruct
	public void init(){
		valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public TicketBean get(String ticket) {
		Object o = valueOperations.get(ticket);
		return (TicketBean) o;
	}

	@Override
	public void saveTicket(String ticket, TicketBean ticketBean) {
		valueOperations.set(ticket,ticketBean);
	}

	@Override
	public boolean removeTicketCache(String ticket) {
		valueOperations.set(ticket,null);
		return true;
	}
}
