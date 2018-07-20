package com.lcdt.login.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.exception.ValidCodeExistException;
import com.lcdt.login.service.impl.ValidCodeService;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth/register")
public class RegisterController {

    public final String registerPagePath = "/register/signup";

    @Reference
    private UserService userService;

//    @Reference(check = false,version = "customer")
//    private SmsService smsService;

    @Reference(async = true)
    NotifyService notifyService;


    private String signature = "【大驼队】";


    /***
     * 注册首页
     * @return
     */
    @RequestMapping(path = "")
    public ModelAndView index() {
        return new ModelAndView(registerPagePath);
    }

    @Autowired
    ValidCodeService validCodeService;

    private static final String VALID_CODE_TAG = "register";

    /***
     * 注册提交
     * @param registerDto
     * @return
     */
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map toSave(HttpSession httpSession, @Valid RegisterDto registerDto, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        String msg = "";
        boolean flag = false;
        boolean codeCorrect = validCodeService.isCodeCorrect(registerDto.getEcode(), request, VALID_CODE_TAG, registerDto.getUserPhoneNum());
        if (!codeCorrect) {
            msg = "验证码错误！";
        }

        if (!registerDto.getPassword().equals(registerDto.getPassword1())) {
            msg = "两次输入密码不一样！";
        }
        if (userService.isPhoneBeenRegister(registerDto.getUserPhoneNum())) { //根据用户首页号检查用户是否有注册
            msg = "手机号" + registerDto.getUserPhoneNum() + "已注册！";
        }
        if (msg == "") {
            try {
                registerDto.setRegisterFrom("web主站");
                User fUser = userService.registerUser(registerDto);
                LoginSessionReposity.setUserInSession(request, fUser);
                if (fUser != null) {
                    flag = true;
                } else {
                    msg = "注册失败！";
                }
            } catch (PhoneHasRegisterException e) {
                msg = e.getMessage();
                e.printStackTrace();
            }
        }
        map.put("msg", msg);
        map.put("flag", flag);
        return map;
    }

    /***
     * 发信短信码
     * @return
     */
    @RequestMapping(path = "/vSmsCode")
    @ResponseBody
    public Map vCode(HttpServletRequest request,HttpSession httpSession, RegisterDto registerDto) {
        Map<String, Object> map = new HashMap();
        String msg = "";
        boolean flag = false;
        if (userService.isPhoneBeenRegister(registerDto.getUserPhoneNum())) { //根据用户首页号检查用户是否有注册
            msg = "手机号" + registerDto.getUserPhoneNum() + "已注册，请选用别的号注册！";
        } else {
            try {
                flag = validCodeService.sendValidCode(request, VALID_CODE_TAG, 60 * 15, registerDto.getUserPhoneNum());
                if (!flag) {
                    msg = "获取验证码已经超限，请明天再试!";
                }
            } catch (ValidCodeExistException e) {
                msg = "验证码已发送";
            }
        }
        map.put("msg", msg);
        map.put("flag", flag);
        return map;
    }

    /***
     * 注册成功页
     * @return
     */
    @RequestMapping(path = "/regSuccess")
    public ModelAndView regSuccess() {
        return new ModelAndView("/register/signup_success");
    }

}
