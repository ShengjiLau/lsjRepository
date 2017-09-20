package com.lcdt.login.service;

import com.lcdt.login.ticket.TicketBean;
import com.lcdt.login.web.LoginSessionReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
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

	public TicketBean isTicketValid(String ticket) {
		return ticketManager.get(ticket);
	}

	public void removeTicketInCookie(HttpServletRequest request, HttpServletResponse response) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(ticketCookieKey)) {
				String ticket = cookie.getValue();
				ticketManager.removeTicketCache(ticket);
				Cookie removedCookie = new Cookie(ticketCookieKey, null);
				removedCookie.setMaxAge(0);
				removedCookie.setDomain("datuodui.com");
				removedCookie.setPath("/");
				response.addCookie(removedCookie);
			}
		}
	}


	public boolean generateTicketInResponse(HttpServletRequest request, HttpServletResponse response, Long userId, Integer companyId) {

		TicketBean ticketBean = new TicketBean();
		ticketBean.setCompanyId(Long.valueOf(companyId));
		ticketBean.setUserId(Long.valueOf(userId));
		ticketBean.setCreateTime(System.currentTimeMillis() / 1000);
		ticketBean.setValidateTime(60 * 60 * 12L);
		ticketBean.setClientIp(request.getRemoteAddr());

		String ticket = createTicket(); //要在ticket中设置相关信息
		Cookie cookie = new Cookie(ticketCookieKey, ticket);
		cookie.setDomain(getHost(request.getRequestURI()));
		cookie.setPath("/");
		response.addCookie(cookie);
		ticketManager.saveTicket(ticket, ticketBean);

		return true;

	}

	private String createTicket() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
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


}
