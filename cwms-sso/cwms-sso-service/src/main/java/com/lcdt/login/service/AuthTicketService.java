package com.lcdt.login.service;

import com.alibaba.fastjson.JSON;
import com.lcdt.login.ticket.TicketBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ss on 2017/8/17.
 */
@Service
public class AuthTicketService {

	@Value("${login.cookieHost}")
	public String cookieHost;
	public String ticketCookieKey = "cwms_ticket";
	@Autowired
	TicketManager ticketManager;
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

	public void removeTicketInCookie(HttpServletRequest request, HttpServletResponse response) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(ticketCookieKey)) {

				String ticket = cookie.getValue();
				ticketManager.removeTicketCache(ticket);

				Cookie cookie1 = new Cookie(ticketCookieKey, "");
				cookie1.setMaxAge(0);
				cookie1.setPath(cookie.getPath());
				cookie1.setDomain(cookie.getDomain());
				response.addCookie(cookie);
			}
		}

	}


	public boolean generateTicketInResponse(HttpServletRequest request, HttpServletResponse response, Long userId, Integer companyId) {
		Ticket ticket = new Ticket();
		ticket.setIp(request.getRemoteAddr());
		ticket.setUserId(userId);
		ticket.setCompanyId(companyId);

		TicketBean ticketBean = new TicketBean();
		ticketBean.setCompanyId(Long.valueOf(companyId));
		ticketBean.setUserId(Long.valueOf(userId));
		ticketBean.setCreateTime(System.currentTimeMillis()/1000);
		ticketBean.setValidateTime(60*60*12L);
		ticketBean.setClientIp(request.getRemoteAddr());


		try {
			String ticketStr = encypt.encode(JSON.toJSONString(ticket));
			Cookie cookie = new Cookie(ticketCookieKey, ticketStr);
			cookie.setDomain(getHost(request.getRequestURI()));
			response.addCookie(cookie);

			ticketManager.saveTicket(ticketStr,ticketBean);

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


	//获取父域名
	private String getHost(String url) {

		if (cookieHost != null && cookieHost.length() > 0) {
			return cookieHost;
		}

		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}


	public static class Ticket {
		private Long userId;
		private String ip;
		private Integer companyId;

		public Integer getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}

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
