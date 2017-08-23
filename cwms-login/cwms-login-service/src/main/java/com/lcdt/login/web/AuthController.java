package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/8/17.
 */
@Controller
public class AuthController {

	private static final String COOKIETICKETNAME = "TICKET";

	@Autowired
	AuthTicketService ticketService;

	@Autowired
	RequestAuthRedirectStrategy strategy;

	@Reference
	UserService userService;

	@RequestMapping("/")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(COOKIETICKETNAME) && ticketService.isTicketValid(cookie.getValue()) != null){
				strategy.hasAuthRedirect(request,response);
				return null;
			}
		}
		ModelAndView view = new ModelAndView("/auth/signin");
		return view;
	}

	@RequestMapping("/login")
	public ModelAndView login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		try {
			FrontUserInfo frontUserInfo = userService.userLogin(username, password);
			boolean inResponse = ticketService.generateTicketInResponse(request, response, frontUserInfo.getUserId());
			if (inResponse) {
				strategy.hasAuthRedirect(request,response);
				return null;
			}else{
				return new ModelAndView("/auth/signin");
			}
		} catch (UserNotExistException e) {
			e.printStackTrace();
		} catch (PassErrorException e) {
			e.printStackTrace();
		}
		return new ModelAndView("/auth/signin");
	}


	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
		ticketService.removeTicketInCookie(request,response);



		return new ModelAndView("/auth/signin");
	}


}
