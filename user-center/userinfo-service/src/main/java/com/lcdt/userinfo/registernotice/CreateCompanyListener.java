package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.event.CreateCompanyEvent;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyListener extends CommonNoticeListener<CreateCompanyEvent> {

    @Override
    SimpleMailMessage createSimpleMailMessage(CreateCompanyEvent event) {
        if (event.getSource() != null && event.getSource() instanceof UserCompRel) {
            return NoticeEmailFactory.createMessage((UserCompRel) event.getSource());
        }
        return null;
    }
}
