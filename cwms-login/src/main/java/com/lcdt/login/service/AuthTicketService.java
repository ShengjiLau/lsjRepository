package com.lcdt.login.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by ss on 2017/8/17.
 */
@Service
public class AuthTicketService {

	private DesEncypt encypt = new DesEncypt("91BE73DFEDFD0908");


	public boolean isTicketValid(String ticket) {

		try {
			String decode = encypt.decode(ticket);
			Ticket ticket1 = JSON.parseObject(decode, Ticket.class);
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

		return false;
	}

	public String generateTicket(HttpServletRequest request, Long userId) {
		Ticket ticket = new Ticket();
		ticket.setIp(request.getRemoteAddr());
		ticket.setUserId(userId);

		try {
			return encypt.encode(JSON.toJSONString(ticket));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

		return null;
	}


	static class Ticket {
		Long userId;
		String ip;


		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}
	}

}
