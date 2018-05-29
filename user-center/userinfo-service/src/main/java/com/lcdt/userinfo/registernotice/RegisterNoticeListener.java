package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.event.RegisterUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;
import java.util.List;

public class RegisterNoticeListener implements ApplicationListener<RegisterUserEvent>{

    @Autowired
    private MailSender mailSender;

    @Override
    public void onApplicationEvent(RegisterUserEvent event) {
        Object source = event.getSource();
        if (source != null && source instanceof User) {
            sendNoticeEmail((User)source);
        }
    }

    private void sendNoticeEmail(User source) {
        List<String> emails = getEmails();
        SimpleMailMessage message = NoticeEmailFactory.createMessage(source);

    }

    private List<String> getEmails(){
        return Arrays.asList("123");
    }


}
