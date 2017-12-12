package com.lcdt.notify.service;

import com.lcdt.notify.dao.NotifyMapper;
import com.lcdt.notify.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotifyService {

    @Autowired
    NotifyMapper notifyMapper;


    public List<Notify> queryNotifyByEventName(){
        return null;
    }

}
