package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.annontion.ExcludeIntercept;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.web.filter.CompanyInterceptorAbstract;
import com.lcdt.login.web.filter.LoginInterceptorAbstract;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.CreateCompanyService;
import com.lcdt.userinfo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by ss on 2017/8/17.
 */
@Controller
@RequestMapping("/account")
public class AuthController {


	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	private static String LOGIN_PAGE = "/signin";
	public final String CHOOSE_COMPANY_PAGE = "/account/company";
	@Autowired
	AuthTicketService ticketService;
	@Autowired
	RequestAuthRedirectStrategy strategy;
	@Reference(check = false)
	UserService userService;
	@Reference(check = false)
	CompanyService companyService;

	@Reference(check = false)
	CreateCompanyService createCompanyService;

	/**
	 * 登陆页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/", ""})
	@ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
		boolean isLogin = LoginSessionReposity.isLogin(request);
		LoginSessionReposity.setCallBackUrl(request);
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
	@ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
	@RequestMapping("/login")
	@ResponseBody
	public String login(String username, String password,String captchacode, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		JSONObject jsonObject = new JSONObject();
		boolean captchaIsOk = LoginSessionReposity.captchaIsOk(request, captchacode);
		captchaIsOk = true;
		if (!captchaIsOk) {
			jsonObject.put("code", -1);
			jsonObject.put("message", "验证码错误");
			return jsonObject.toString();
		}

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
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ticketService.removeTicketInCookie(request, response);
		LoginSessionReposity.clearUserSession(request);
		String authCallback = RequestAuthRedirectStrategy.getAuthCallback(request);
		ModelAndView view = new ModelAndView(LOGIN_PAGE);
		view.addObject(RequestAuthRedirectStrategy.AUTH_CALLBACK, authCallback);
		return view;
	}

	@RequestMapping("/createcompany")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	public ModelAndView createCompanyPage(HttpServletRequest request) {
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		ModelAndView view = new ModelAndView("/createCom");
		return view;
	}


	@RequestMapping({"/company", "choosecompany"})
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	public ModelAndView chooseCompanyPage(HttpServletRequest request) {
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		List<UserCompRel> companyMembers = companyService.companyList(userInfo.getUserId());
		UserCompRel userCompRel = companyMembers.get(0);
		String fullName = userCompRel.getCompany().getFullName();

		logger.info("选择公司"+fullName);


		String authCallback = RequestAuthRedirectStrategy.getAuthCallback(request);
		ModelAndView view = new ModelAndView("/chooseCom");
		view.addObject("companyMembers", companyMembers);
		if (authCallback != null) {
			view.addObject("authcallback", authCallback);
		}
		return view;
	}


	private static final String CREATE_COMPANY_SESSION_COUNT = "create_company_count";

	/**
	 * 企业初始化
	 *
	 * @param fullname
	 * @param industry
	 * @param request
	 * @param response
	 * @return
	 */
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	@RequestMapping("/initcompany")
	@ResponseBody
	public String initCompany(String fullname, String industry, HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		CompanyDto dtoVo = new CompanyDto();
		dtoVo.setCompanyName(fullname);
		if (companyService.findCompany(dtoVo) != null) {
			jsonObject.put("message", "企业名称已存在");
			jsonObject.put("code", -1);
			return jsonObject.toString();
		} else {
			dtoVo.setUserId(userInfo.getUserId());
			dtoVo.setCreateName(userInfo.getRealName());
			try {
				Company company = createCompanyService.createCompany(dtoVo);
			} catch (CompanyExistException e) {
				e.printStackTrace();
				jsonObject.put("code", -1);
				jsonObject.put("message", "企业名称已存在");
				return jsonObject.toString();
			} catch (DeptmentExistException e) {
				e.printStackTrace();
				jsonObject.put("code", -1);
				jsonObject.put("message", "企业名称已存在");
				return jsonObject.toString();
			}
		}
		jsonObject.put("code", 0);
		String redirectUrl = RequestAuthRedirectStrategy.getAuthCallback(request);
		jsonObject.put("redirecturl", redirectUrl);
		return jsonObject.toString();
	}


	/**
	 * 解除绑定关系
	 *
	 * @param companyid
	 * @param request
	 * @param response
	 * @return
	 */
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	@RequestMapping("/removecompany")
	public void removeCompany(Long usercomprelid, Long companyid, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		int flag = companyService.removeCompanyRel(usercomprelid);
		try {
			response.sendRedirect(CHOOSE_COMPANY_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@RequestMapping("/logincompany")
	@ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
	public ModelAndView loginCompany(Long companyId, HttpServletRequest request, HttpServletResponse response) {
		User userInfo = LoginSessionReposity.getUserInfoInSession(request);
		UserCompRel companyMember = companyService.queryByUserIdCompanyId(userInfo.getUserId(), companyId);
		ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), companyId);
		LoginSessionReposity.setCompanyMemberInSession(request, companyMember);
		strategy.hasAuthRedirect(request, response);
		return null;
	}

}
