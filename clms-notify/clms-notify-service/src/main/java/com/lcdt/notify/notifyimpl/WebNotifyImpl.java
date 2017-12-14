package com.lcdt.notify.notifyimpl;

import com.lcdt.notify.model.NotifyReceiver;
import com.lcdt.notify.webnotify.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebNotifyImpl {

    private Logger logger = LoggerFactory.getLogger(WebNotifyImpl.class);

    @Autowired
    MessageService messageService;

    /**
     * 通知
     */
    public boolean sendWebNotify(String content, NotifyReceiver receiver){
        logger.info("发送web通知 >>> {} >>> userId:{} companyId:{}", content, receiver.getCompanyId(), receiver.getUserId());
        messageService.createWebMessage(content,receiver.getCompanyId(),receiver.getUserId());
        return true;
    }

}
