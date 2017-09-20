package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.annontion.ExcludeIntercept;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.web.filter.CompanyInterceptor;
import com.lcdt.login.web.filter.LoginInterceptor;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.CompanyMember;
import com.lcdt.userinfo.model.FrontUserInfo;
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
	@RequestMapping("/")
	@ExcludeIntercept(excludeIntercept = {LoginInterceptor.class, CompanyInterceptor.class})
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
		boolean isLogin = LoginSessionReposity.isLogin(request);
		if (!isLogin) {
			ModelAndView view = new ModelAndView("/auth/signin");
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
		ModelAndView view = new ModelAndView("/auth/signin");
		return view;
	}

	/**
	 * 登陆入口
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
			FrontUserInfo frontUserInfo = userService.userLogin(username, password);
			LoginSessionReposity.setUserInSession(request, frontUserInfo);
			List<CompanyMember> companyMembers = companyService.companyList(frontUserInfo.getUserId());
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
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ticketService.removeTicketInCookie(request, response);
		LoginSessionReposity.clearUserSession(request);
		return new ModelAndView("/auth/signin");
	}


	@RequestMapping("/companys")
	@ExcludeIntercept(excludeIntercept = {LoginInterceptor.class, CompanyInterceptor.class})
	public String showCompanys(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		FrontUserInfo userInfo = LoginSessionReposity.getUserInfoInSession(request);
		if (userInfo == null) {
			jsonObject.put("code", -1);
			jsonObject.put("message", "请先登陆");
			return jsonObject.toString();
		}

		List<CompanyMember> companyMembers = companyService.companyList(userInfo.getUserId());
		jsonObject.put("data", companyMembers);
		jsonObject.put("code", 0);
		jsonObject.put("message", "success");
		return jsonObject.toString();
	}


	@RequestMapping("/chooseCompany")
	@ResponseBody
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public String chooseCompany(HttpServletRequest request, HttpServletResponse response, Integer companyId) {
		//生成ticket
		JSONObject jsonObject = new JSONObject();
		FrontUserInfo userInfo = LoginSessionReposity.getUserInfoInSession(request);
		if (userInfo == null) {
			jsonObject.put("code", -1);
			jsonObject.put("message", "请先登陆");
			return jsonObject.toString();
		}
		jsonObject.put("code", 0);
		jsonObject.put("message", "success");
		CompanyMember companyMember = companyService.queryByUserIdCompanyId(userInfo.getUserId(), companyId);
		LoginSessionReposity.setCompanyMemberInSession(request, companyMember);

		ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), companyId);
		return jsonObject.toString();
	}

	@RequestMapping("/company")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public ModelAndView chooseCompanyPage(HttpServletRequest request) {
		FrontUserInfo userInfo = LoginSessionReposity.getUserInfoInSession(request);
		List<CompanyMember> companyMembers = companyService.companyList(userInfo.getUserId());
		ModelAndView view = new ModelAndView("/auth/company");
		view.addObject("companyMembers", companyMembers);
		return view;
	}

	@RequestMapping("/logincompany")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptor.class})
	public ModelAndView loginCompany(Integer companyId, HttpServletRequest request, HttpServletResponse response) {
		FrontUserInfo userInfo = LoginSessionReposity.getUserInfoInSession(request);
		CompanyMember companyMember = companyService.queryByUserIdCompanyId(userInfo.getUserId(), companyId);
		ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), companyId);
		LoginSessionReposity.setCompanyMemberInSession(request, companyMember);
		strategy.hasAuthRedirect(request, response);
		return null;
	}

}
