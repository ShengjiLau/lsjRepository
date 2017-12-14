package com.lcdt.notify.notifyimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotifyImpl {

    private Logger logger = LoggerFactory.getLogger(SmsNotifyImpl.class);

    public boolean sendSmsNotify(String content, String phoneNum) {
        logger.info("发送短信通知 >>> {} >>> {}",content,phoneNum);
        return true;
    }

}
