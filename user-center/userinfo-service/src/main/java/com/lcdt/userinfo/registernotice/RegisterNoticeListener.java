package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.event.RegisterUserEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RegisterNoticeListener extends CommonNoticeListener<RegisterUserEvent> {

    @Override
    SimpleMailMessage createSimpleMailMessage(RegisterUserEvent event) {
        if (event.getSource() != null && event.getSource() instanceof User) {
            return NoticeEmailFactory.createMessage((User) event.getSource());
        }
        return null;
    }
}
