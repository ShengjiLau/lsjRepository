package com.lcdt.login.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.RandomNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
    public ModelAndView index(){
        return new ModelAndView(registerPagePath);
    }

    /***
     * 注册提交
     * @param registerDto
     * @return
     */
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Map toSave(HttpSession httpSession, RegisterDto registerDto) {
        Map<String,Object> map = new HashMap();
        String msg = "";
        boolean flag = false;
        if (httpSession.getAttribute("CWMS_SMS_VCODE") == null) {
            msg = "验证码不正确！";
        } else {
            String vCodeSession = (String) httpSession.getAttribute("CWMS_SMS_VCODE");
            if (!registerDto.getEcode().equals(vCodeSession.split("_")[0])) {
                msg = "验证码无效！";
            }
            if (!registerDto.getUserPhoneNum().equals(vCodeSession.split("_")[1])) {
                msg = "注册手机号无效！";
            }
            if (!registerDto.getPassword().equals(registerDto.getPassword1())) {
                msg = "两次输入密码不一样！";
            }
            if (userService.isPhoneBeenRegister(registerDto.getUserPhoneNum())) { //根据用户首页号检查用户是否有注册
                msg = "手机号"+registerDto.getUserPhoneNum()+"已注册！";
            }
        }
        if (msg == "") {
            try {
                User fUser = userService.registerUser(registerDto);
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
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    /***
     * 发信短信码
     * @return
     */
    @RequestMapping(path = "/vSmsCode")
    @ResponseBody
    public Map vCode(HttpSession httpSession, RegisterDto registerDto) {
        Map<String,Object> map = new HashMap();
        String msg = "";
        boolean flag = false;
        if (userService.isPhoneBeenRegister(registerDto.getUserPhoneNum())) { //根据用户首页号检查用户是否有注册
            msg = "手机号"+registerDto.getUserPhoneNum()+"已注册，请选用别的号注册！";
        } else {
            Long  cTime = System.currentTimeMillis(); //其实时间
            if (httpSession.getAttribute("CWMS_SMS_SEND_TIME")==null) {
                httpSession.setAttribute("CWMS_SMS_SEND_TIME", cTime);
                String[] phones = new String[]{registerDto.getUserPhoneNum()};
                String vCode = RandomNoUtil.createRandom(true,4);
                notifyService.sendSms(phones,vCode);
                httpSession.setAttribute("CWMS_SMS_VCODE", vCode+"_"+registerDto.getUserPhoneNum());
                flag = true;
            } else {
                Long oTime = Long.valueOf(httpSession.getAttribute("CWMS_SMS_SEND_TIME").toString());
                if (cTime - oTime >= 60*1000) { //大于1分中重新获取
                    httpSession.setAttribute("CWMS_SMS_SEND_TIME", cTime);
                    String[] phones = new String[]{registerDto.getUserPhoneNum()};
                    String vCode = RandomNoUtil.createRandom(true,4);
//                    smsService.sendSms(phones, signature, vCode);
                    httpSession.setAttribute("CWMS_SMS_VCODE", vCode+"_"+registerDto.getUserPhoneNum());
                    flag = true;
                }
            }
        }
        map.put("msg",msg);
        map.put("flag",flag);
        return map;
    }

    /***
     * 注册成功页
     * @return
     */
    @RequestMapping(path = "/regSuccess")
    public ModelAndView regSuccess(){
        return new ModelAndView("/register/signup_success");
    }

}
