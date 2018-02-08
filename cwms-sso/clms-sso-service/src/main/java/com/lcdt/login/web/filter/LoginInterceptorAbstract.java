package com.lcdt.login.web.filter;

import com.lcdt.login.bean.TicketAuthentication;
import com.lcdt.login.exception.InvalidTicketException;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.service.LoginService;
import com.lcdt.login.service.impl.LoginServiceImpl;
import com.lcdt.login.ticket.TicketBean;
import com.lcdt.login.web.LoginSessionReposity;
import com.lcdt.login.web.RequestAuthRedirectStrategy;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.sso.common.utils.TicketHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/25.
 */
@Component
public class LoginInterceptorAbstract extends AbstractExcludeUrlAnnontionInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptorAbstract.class);

	@Autowired
	AuthTicketService ticketService;


	@Autowired
	LoginService loginService;

	@Override
	public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
		if (!LoginSessionReposity.isLogin(request)) {

			String ticketInCookie = TicketHelper.findTicketInCookie(request);

			try {
				if (checkTicket(ticketInCookie,request)) {
					return true;
				}
			}catch (InvalidTicketException e){

			} catch (UserNotExistException e) {
				e.printStackTrace();
			}

			RequestAuthRedirectStrategy.rediectToLoginPage(request,response);
			logger.info(request.getRequestURI() + " 未登录 跳转登陆页");
			return false;
		}
		return true;
	}


	private boolean checkTicket(String ticket,HttpServletRequest request) throws InvalidTicketException, UserNotExistException {
		if (StringUtils.isEmpty(ticket)) {
			return false;
		}
		TicketBean ticketValid = ticketService.isTicketValid(ticket);
		TicketAuthentication ticketAuthentication = loginService.queryTicket(ticket);

		LoginSessionReposity.setUserInSession(request,ticketAuthentication.getUser());

		return ticketValid == null ? false : true;
	}


}
