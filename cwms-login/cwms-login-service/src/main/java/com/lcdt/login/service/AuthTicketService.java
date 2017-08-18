package com.lcdt.login.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by ss on 2017/8/17.
 */
@Service
public class AuthTicketService {


	private DesEncypt encypt = new DesEncypt("91BE73DFEDFD0908");

	public Ticket isTicketValid(String ticket) {

		try {
			String decode = encypt.decode(ticket);
			Ticket ticket1 = JSON.parseObject(decode, Ticket.class);
			return ticket1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean generateTicketInResponse(HttpServletRequest request, HttpServletResponse response,Long userId) {
		Ticket ticket = new Ticket();
		ticket.setIp(request.getRemoteAddr());
		ticket.setUserId(userId);
		try {
			String ticketStr = encypt.encode(JSON.toJSONString(ticket));
			Cookie cookie = new Cookie("cwms_ticket", ticketStr);
			cookie.setDomain("");
			response.addCookie(cookie);
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


	public static class Ticket {
		private Long userId;
		private String ip;

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
