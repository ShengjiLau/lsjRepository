package com.lcdt.notify.service;

import com.lcdt.notify.dao.MsgTemplateMapper;
import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.dao.NotifyMapper;
import com.lcdt.notify.model.MsgTemplate;
import com.lcdt.notify.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotifyService {

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    NotifyDao notifyDao;

    @Autowired
    MsgTemplateMapper templateMapper;

    public List<Notify> queryNotifyByEventName(String eventName) {
        List<Notify> notifies = notifyDao.queryByEventName(eventName);

        //解析模板 推送


        return notifies;
    }


    public String notifyTemplateString(Long notifyId){
        Notify notify = notifyMapper.selectByPrimaryKey(notifyId);
        if (notify == null) {
            //这里要报错
            throw new RuntimeException();
        }
        MsgTemplate msgTemplate = templateMapper.selectByPrimaryKey(notify.getTemplateId());
        String templateContent = msgTemplate.getTemplateContent();
        return templateContent;
    }


}
