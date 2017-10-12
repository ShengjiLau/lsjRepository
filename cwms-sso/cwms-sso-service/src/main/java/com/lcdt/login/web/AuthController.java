package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.annontion.ExcludeIntercept;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.web.filter.CompanyInterceptor;
import com.lcdt.login.web.filter.LoginInterceptor;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ss on 2017/8/17.
 */
@Controller
@RequestMapping("/account")
public class AuthController {

	private static String LOGIN_PAGE = "/signin";
	@Autowired
	AuthTicketService ticketService;
	@Autowired
	RequestAuthRedirectStrategy strategy;
	@Reference(check = false)
	UserService userService;
	@Reference(check = false)
	CompanyService companyService;

	/**
	 * 登陆页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/", ""})
	@ExcludeIntercept(excludeIntercept = {LoginInterceptor.class, CompanyInterceptor.class})
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
		boolean isLogin = LoginSessionReposity.isLogin(request);
		if (!isLogin) {
			ModelAndView view = new ModelAndView(LOGIN_PAGE);
			return view;
		}

		if (LoginSessionReposity.loginCompany(request)) {
			strategy.hasAuthRedirect(request, response);
		} else {
			try {
				response.sendRedirect("/account/company");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}
		ModelAndView view = new ModelAndView(LOGIN_PAGE);
		return view;
	}

	/**
	 * 登陆入口 返回json数据
	 *
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@ExcludeIntercept(excludeIntercept = {LoginInterceptor.class, CompanyInterceptor.class})
	@RequestMapping("/login")
	@ResponseBody
	public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			User user = userService.userLogin(username, password);
			LoginSessionReposity.setUserInSession(request, user);
			List<UserCompRel> companyMembers = companyService.companyList(user.getUserId());
			jsonObject.put("data", companyMembers);
			jsonObject.put("code", 0);
			jsonObject.put("message", "success");
			return jsonObject.toString();
		} catch (UserNotExistException e) {
			e.printStackTrace();
			jsonObject.put("message", "账号不存在");
			jsonObject.put("code", -1);
			return jsonObject.toString();
		} catch (PassErrorException e) {
			jsonObject.put("message", "账号密码错误");
			jsonObject.put("code", -1);
			e.printStackTrace();
			return jsonObject.toString();
		}
	}


	@RequestMapping("/logout")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ticketService.removeTicketInCookie(request, response);
		LoginSessionReposity.clearUserSession(request);
		String authCallback = RequestAuthRedirectStrategy.getAuthCallback(request);
		ModelAndView view = new ModelAndView(LOGIN_PAGE);
		view.addObject(RequestAuthRedirectStrategy.AUTH_CALLBACK, authCallback);
		return view;
	}


	@RequestMapping("/company")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public ModelAndView chooseCompanyPage(HttpServletRequest request) {
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		List<UserCompRel> companyMembers = companyService.companyList(userInfo.getUserId());
		ModelAndView view = new ModelAndView("/chooseCom");
		view.addObject("companyMembers", companyMembers);
		return view;
	}

	@RequestMapping("/logincompany")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public ModelAndView loginCompany(Integer companyId, HttpServletRequest request, HttpServletResponse response) {
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		UserCompRel companyMember = companyService.queryByUserIdCompanyId(userInfo.getUserId(), companyId);
		ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), companyId);
		LoginSessionReposity.setCompanyMemberInSession(request, companyMember);
		strategy.hasAuthRedirect(request, response);
		return null;
	}

}
