package com.lcdt.notify.service;

import com.lcdt.notify.dao.*;
import com.lcdt.notify.model.CompanyNotifySetting;
import com.lcdt.notify.model.CustomeNotifyContent;
import com.lcdt.notify.model.MsgTemplate;
import com.lcdt.notify.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class NotifyService {

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    NotifyDao notifyDao;

    @Autowired
    MsgTemplateMapper templateMapper;

    @Autowired
    CompanyNotifySettingMapper settingMapper;

    @Autowired
    NotifySettingMapper notifySettingMapper;

    public List<Notify> queryNotifyByEventName(String eventName) {
        List<Notify> notifies = notifyDao.queryByEventName(eventName);
        //解析模板 推送
        return notifies;
    }

    @Transactional
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

    /**
     * 读取公司通知设置
     * @param companyId
     * @param notifyId
     * @return
     */
    @Transactional(readOnly = true)
    public CompanyNotifySetting queryCompanyNotifySetting(Long companyId,Long notifyId){
        List<CompanyNotifySetting> companyNotifySettings = notifySettingMapper.selectByCompanyAndNotify(companyId, notifyId);
        if (CollectionUtils.isEmpty(companyNotifySettings)) {
            //读取默认设置
            Notify notify = notifyMapper.selectByPrimaryKey(notifyId);
            return getDefaultNotifySetting(companyId, notify);
        }else{
            return companyNotifySettings.get(0);
        }
    }


    public String templateContent(Long templateId, Long companyId) {
        CustomeNotifyContent customeNotifyContent = notifyDao.queryByTemplateId(templateId, companyId);
        if (customeNotifyContent != null) {
            String content = customeNotifyContent.getContent();
            return content;
        }else{
            MsgTemplate msgTemplate = templateMapper.selectByPrimaryKey(templateId);
            return msgTemplate.getTemplateContent();
        }
    }

    private CompanyNotifySetting getDefaultNotifySetting(Long companyId,Notify notify) {
        CompanyNotifySetting companyNotifySetting = new CompanyNotifySetting();
        companyNotifySetting.setEnableSms(notify.getDefaultEnableSms());
        companyNotifySetting.setEnableWeb(notify.getDefaultEnableWeb());
        companyNotifySetting.setCompanyId(companyId);
        companyNotifySetting.setNotifyId(notify.getNotifyId());
        return companyNotifySetting;
    }

}
