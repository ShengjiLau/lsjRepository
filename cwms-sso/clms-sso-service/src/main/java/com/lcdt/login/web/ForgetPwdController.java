package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.exception.ValidCodeExistException;
import com.lcdt.login.service.impl.ValidCodeService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ForgetPwdController {


    @Autowired
    ValidCodeService validCodeService;

    @Reference
    UserService userService;


    private String validcodeTag = "forgetpwd";


    @RequestMapping(value = "/forgetpage",method = RequestMethod.GET)
    public ModelAndView forgetPwdPage(){
        ModelAndView modelAndView = new ModelAndView("/forgetPassword");
        return modelAndView;
    }


    @RequestMapping("/sendforgetpwdcode")
    public String forgetpwdSendValidCode(HttpServletRequest request,String phoneNum){
        JSONObject jsonObject = new JSONObject();
        try {
            boolean b = validCodeService.sendValidCode(request, validcodeTag, 60 * 3, phoneNum);

            jsonObject.put("result", b);
            jsonObject.put("message", "发送成功");


        } catch (ValidCodeExistException e) {
            jsonObject.put("result", false);
            jsonObject.put("message", "验证码已发送");
        }
        return jsonObject.toString();
    }

    private static final String SESSIONKEY = "forgetpwd_phone";

    public ModelAndView checkValidCode(HttpServletRequest request,String validcode,String phoneNum){

        boolean codeCorrect = validCodeService.isCodeCorrect(validcode, request, validcodeTag, phoneNum);


        ModelAndView modelAndView = new ModelAndView("/setPassWord");
        if (codeCorrect) {
            //如果验证码正确
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSIONKEY, phoneNum);
            return modelAndView;
        }else{

        }


        return new ModelAndView("/setPassWord");
    }


    public ModelAndView setNewPwd(HttpServletRequest request,String pwd) {
        HttpSession session = request.getSession(false);
        Object attribute = session.getAttribute(SESSIONKEY);
        String phone = (String) attribute;

        userService.resetPwd(phone, pwd);
        return new ModelAndView("/register/signup_success");
    }



}
