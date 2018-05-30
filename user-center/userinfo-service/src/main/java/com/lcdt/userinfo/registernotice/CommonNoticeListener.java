package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.dao.TNoticeEmailMapper;
import com.lcdt.userinfo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CommonNoticeListener<T extends ApplicationEvent> implements ApplicationListener<T> {

    private Logger logger = LoggerFactory.getLogger(CommonNoticeListener.class);
    @Autowired
    private TNoticeEmailMapper mapper;

    @Autowired
    private MailSender mailSender;
    @Override
    public void onApplicationEvent(T event) {
        logger.info("receive applicationEvent: {}",event);
        sendNoticeEmail(createSimpleMailMessage(event));
    }

    abstract SimpleMailMessage createSimpleMailMessage(T event);

    private void sendNoticeEmail(SimpleMailMessage simpleMailMessage) {
        if (simpleMailMessage == null) {
            return;
        }
        List<String> emails = getEmails();
        emails.forEach(email -> sendMessage(simpleMailMessage,email));
    }

    private void sendMessage(SimpleMailMessage message, String email) {
        message.setTo(email);
        mailSender.send(message);
        logger.info("已发送通知邮件 接收:{}",email);
    }

    private List<String> getEmails(){
        return mapper.selectAll().stream().map(tNoticeEmail -> tNoticeEmail.getEmail()).collect(Collectors.toList());
    }
}
