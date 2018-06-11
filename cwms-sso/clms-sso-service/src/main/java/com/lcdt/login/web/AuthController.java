package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.annontion.ExcludeIntercept;
import com.lcdt.login.exception.LoginError;
import com.lcdt.login.exception.ValidCodeExistException;
import com.lcdt.login.service.AuthTicketService;
import com.lcdt.login.service.impl.ValidCodeService;
import com.lcdt.login.web.filter.CompanyInterceptorAbstract;
import com.lcdt.login.web.filter.LoginInterceptorAbstract;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.CreateCompanyService;
import com.lcdt.userinfo.service.LoginLogService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static String ADMIN_LOGIN_PAGE = "/admin_login";
    public final String CHOOSE_COMPANY_PAGE = "/account/company";

    @Autowired
    AuthTicketService ticketService;

    @Autowired
    RequestAuthRedirectStrategy strategy;

    @Reference()
    UserService userService;

    @Reference(check = false)
    CompanyService companyService;

    @Reference(check = false)
    CreateCompanyService createCompanyService;

    @Reference(async = true)
    NotifyService notifyService;

    /**
     * 登陆页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/",""})
    @ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        LoginSessionReposity.setCallBackUrl(request);
        if (!LoginSessionReposity.isLogin(request)) {
            ModelAndView view = new ModelAndView(LOGIN_PAGE);
            return view;
        }

        if (LoginSessionReposity.loginCompany(request)) {
            strategy.redirectToCallbackUrl(request, response);
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

    @RequestMapping(value = {"/admin"})
    @ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
    public ModelAndView loginAdminPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(ADMIN_LOGIN_PAGE);
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
    public String login(String username, String password, String captchacode, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        boolean captchaIsOk = LoginSessionReposity.captchaIsOk(request, captchacode);
        if (!captchaIsOk) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "验证码错误");
            return jsonObject.toString();
        }
        try {
            User user = userService.userLogin(username, password);
            if (user.getUserStatus() == 2) {
                jsonObject.put("data", null);
                jsonObject.put("code", -1);
                jsonObject.put("message", "您的账号审核中或已被禁用，请联系客服");
                return jsonObject.toString();
            }
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

    @ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
    @RequestMapping("/testlogin")
    @ResponseBody
    public String logintest(String username, String password, String captchacode, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        boolean captchaIsOk = LoginSessionReposity.captchaIsOk(request, captchacode);
        captchaIsOk= true;
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
        ModelAndView view = new ModelAndView("/createCom");
        return view;
    }

    @RequestMapping({"/company", "choosecompany"})
    @ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
    public ModelAndView chooseCompanyPage(HttpServletRequest request) {
        User userInfo = LoginSessionReposity.getUserInfoInSession(request);
        List<UserCompRel> companyMembers = companyService.companyList(userInfo.getUserId());
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
    public String initCompany(String fullname, String industry, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UserNotExistException {
        JSONObject jsonObject = new JSONObject();
        User userInfo = LoginSessionReposity.getUserInfoInSession(request);
        CompanyDto dtoVo = new CompanyDto();
        dtoVo.setIndustry(industry);
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
                ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), company.getCompId());
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
        jsonObject.put("message", "创建成功");
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
        boolean flag = companyService.removeCompanyRel(usercomprelid);
        try {
            response.sendRedirect(CHOOSE_COMPANY_PAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Reference(async = true)
    LoginLogService loginLogService;

    @RequestMapping("/logincompany")
    @ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class})
    public ModelAndView loginCompany(Long companyId, HttpServletRequest request, HttpServletResponse response) {
        User userInfo = LoginSessionReposity.getUserInfoInSession(request);
        UserCompRel companyMember = companyService.queryByUserIdCompanyId(userInfo.getUserId(), companyId);
        if (companyMember == null) {
            //当前用户不在所选公司之内
            throw new LoginError("用户不属于该公司");
        }

        if (companyMember.getIsEnable() != null && companyMember.getIsEnable() == false) {
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("msg", "用户已被禁用");
            return modelAndView;
        }

        if (companyMember.getCompany().getEnable() != null && companyMember.getCompany().getEnable().equals(false)) {
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("msg", "公司已被禁用");
            return modelAndView;
        }

        ticketService.generateTicketInResponse(request, response, userInfo.getUserId(), companyId);
        LoginSessionReposity.setCompanyMemberInSession(request, companyMember);
        User userInfoInSession = LoginSessionReposity.getUserInfoInSession(request);

        logger.info("user session is {}",userInfoInSession.getRealName());

        strategy.redirectToCallbackUrl(request, response);
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(userInfo.getUserId());
        loginLog.setLoginCompanyId(companyId);
        loginLog.setLoginAgent("PC");
        loginLog.setLoginIp(HttpUtils.getLocalIp(request));
        loginLogService.saveLog(loginLog);
        return null;
    }


    @RequestMapping("/adminlogin")
    @ExcludeIntercept(excludeIntercept = {LoginInterceptorAbstract.class, CompanyInterceptorAbstract.class})
    @ResponseBody
    public String adminUserLogin(String username, String password, String captchacode,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        if (!LoginSessionReposity.captchaIsOk(request, captchacode)) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "验证码错误");
            return jsonObject.toString();
        }
        try {
            User user = userService.userLogin(username, password);
            LoginSessionReposity.setUserInSession(request, user);
            ticketService.generateTicketInResponse(request, response, user.getUserId(), -1L);
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



    @ExcludeIntercept(excludeIntercept = {CompanyInterceptorAbstract.class,})
    public ModelAndView forgetPwd() {
        return new ModelAndView();
    }

    @Autowired
    ValidCodeService validCodeService;

    private static final String FORGET_PASS_TAG = "forgetpwd";

    //发送短信验证码
    //TODO 设置返回页面
    @RequestMapping("/sendsmscode")
    @ResponseBody
    public String sendValidSms(HttpServletRequest request, String phoneNum) throws UserNotExistException {
        User user = userService.queryByPhone(phoneNum);
        try {
            validCodeService.sendValidCode(request, FORGET_PASS_TAG, 60*15, phoneNum);
        } catch (ValidCodeExistException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ModelAndView setNewPwdPage(String validCode,String phone,HttpServletRequest request){
        boolean codeCorrect = validCodeService.isCodeCorrect(validCode, request, FORGET_PASS_TAG, phone);
        if (codeCorrect) {
            return new ModelAndView();
        }else{
            return null;
        }
    }

}
