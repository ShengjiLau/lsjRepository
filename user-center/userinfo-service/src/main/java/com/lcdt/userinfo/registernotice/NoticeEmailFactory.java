package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.mail.SimpleMailMessage;

public class NoticeEmailFactory {



    static SimpleMailMessage createMessage(User user) {
        SimpleMailMessage baseMessage = createBaseMessage();
        baseMessage.setText(messageText(user));
        return baseMessage;
    }


    static String messageText(User user) {
        return String.format("%s 账户：%s 姓名： %s ,注册来源： %s", prefix(user), user.getPhone(), user.getRealName(), user.getRegisterFrom());
    }


    static SimpleMailMessage createBaseMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("service@datuodui.com");
        simpleMailMessage.setSubject("注册提醒");
        return simpleMailMessage;
    }


    static private String prefix(User user){
        if (user.getRegisterFrom().equals("司机宝小程序")) {
            return "【司机注册】";
        }else{
            return "【用户注册】";
        }
    }

    static private String prefix(UserCompRel userCompRel) {
        return "【企业注册】";
    }

}
