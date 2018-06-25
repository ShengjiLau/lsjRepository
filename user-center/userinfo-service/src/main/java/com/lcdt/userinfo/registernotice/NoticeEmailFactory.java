package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.StringUtils;

public class NoticeEmailFactory {
    static SimpleMailMessage createMessage(UserCompRel user) {
        SimpleMailMessage baseMessage = createBaseMessage();
        baseMessage.setText(messageText(user));
        return baseMessage;
    }


    static SimpleMailMessage createMessage(User user) throws NoticeException {
        SimpleMailMessage baseMessage = createBaseMessage();
        baseMessage.setText(messageText(user));
        return baseMessage;
    }


    static String messageText(User user) throws NoticeException {
        return String.format("%s 账户：%s ；姓名： %s ；注册来源： %s", prefix(user), user.getPhone(), user.getRealName(), user.getRegisterFrom());
    }

    static String messageText(UserCompRel userCompRel){
        return String.format("%s 账户：%s ；姓名： %s ；公司名称：%s；所属行业：%s；注册来源： %s", prefix(userCompRel), userCompRel.getUser().getPhone(),  userCompRel.getUser().getRealName(),userCompRel.getCompany().getFullName(),userCompRel.getCompany().getIndustry(), userCompRel.getUser().getRegisterFrom());
    }

    static SimpleMailMessage createBaseMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("service@datuodui.com");
        simpleMailMessage.setSubject("大驼队cLMS注册通知");
        return simpleMailMessage;
    }


    static private String prefix(User user) throws NoticeException {
        String from = user.getRegisterFrom();
        if (StringUtils.isEmpty(from)) {
            throw new NoticeException();
        }
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
