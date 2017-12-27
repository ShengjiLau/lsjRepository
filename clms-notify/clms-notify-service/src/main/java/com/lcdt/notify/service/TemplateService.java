package com.lcdt.notify.service;

import com.lcdt.notify.dao.CustomeNotifyContentMapper;
import com.lcdt.notify.dao.MsgTemplateMapper;
import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.CustomeNotifyContent;
import com.lcdt.notify.model.MsgTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    MsgTemplateMapper templateMapper;

    @Autowired
    CustomeNotifyContentMapper contentMapper;

    @Autowired
    NotifyDao notifyDao;

    public String getNotifyTemplateContent(Long companyId,Long templateId) {
        CustomeNotifyContent customeNotifyContent = notifyDao.queryByTemplateId(companyId, templateId);
        if (customeNotifyContent == null) {
            MsgTemplate msgTemplate = templateMapper.selectByPrimaryKey(templateId);
            return msgTemplate.getTemplateContent();
        }
        return customeNotifyContent.getContent();
    }
}
