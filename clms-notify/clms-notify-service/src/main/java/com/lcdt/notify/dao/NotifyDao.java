package com.lcdt.notify.dao;

import com.lcdt.notify.model.Notify;

import java.util.List;

public interface NotifyDao {

    List<Notify> queryByEventName(String eventName);

}
