package com.lcdt.login.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.login.exception.ValidCodeExistException;
import com.lcdt.login.service.impl.ValidCodeService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public String forgetpwdSendValidCode(HttpServletRequest request,String phoneNum){
        JSONObject jsonObject = new JSONObject();
        try {
            boolean b = validCodeService.sendValidCode(request, validcodeTag, 60 * 3, phoneNum);
            jsonObject.put("result", b);
            if (b) {
                jsonObject.put("message", "发送成功");
            }else{
                jsonObject.put("message", "已验证已发送");
            }
        } catch (ValidCodeExistException e) {
            jsonObject.put("result", false);
            jsonObject.put("message", "验证码已发送");
        }
        return jsonObject.toString();
    }

    private static final String SESSIONKEY = "forgetpwd_phone";

    @RequestMapping("/checkvalidcode")
    public ModelAndView checkValidCode(HttpServletRequest request,String validcode,String phoneNum) throws UserNotExistException {

        userService.queryByPhone(phoneNum);

        boolean codeCorrect = validCodeService.isCodeCorrect(validcode, request, validcodeTag, phoneNum);
        ModelAndView modelAndView = new ModelAndView("/setPassWord");
        if (codeCorrect) {
            //如果验证码正确
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSIONKEY, phoneNum);
            modelAndView.addObject("phonenum", phoneNum);
            return modelAndView;
        }else{
            modelAndView.setViewName("/forgetPassword");
            return modelAndView;
        }
    }

    @RequestMapping("/setnewpwd")
    public ModelAndView setNewPwd(HttpServletRequest request,String pwd) {
        HttpSession session = request.getSession(false);
        Object attribute = session.getAttribute(SESSIONKEY);
        ModelAndView modelAndView = new ModelAndView();

        if (StringUtils.isEmpty(pwd)) {
            return null;
        }

        if (attribute == null) {
            return modelAndView;
        }
        String phone = (String) attribute;
        userService.resetPwd(phone, pwd);
        return new ModelAndView("/reset_success");
    }


}
