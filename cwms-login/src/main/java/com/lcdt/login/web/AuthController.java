package com.lcdt.login.web;

import com.lcdt.login.service.AuthTicketService;
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

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(COOKIETICKETNAME) && ticketService.isTicketValid(cookie.getValue())){
				strategy.hasAuthRedirect(request,response);
				return null;
			}
		}
		ModelAndView view = new ModelAndView("redirect:/auth/signin");
		return view;
	}

	@RequestMapping("/login")
	public ModelAndView login(String username,String password){
		return null;
	}



}
