package com.lcdt.notify.service;

import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.dao.NotifyMapper;
import com.lcdt.notify.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotifyService {

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    NotifyDao notifyDao;


    public List<Notify> queryNotifyByEventName(String eventName) {
        List<Notify> notifies = notifyDao.queryByEventName(eventName);
        return notifies;
    }

}
