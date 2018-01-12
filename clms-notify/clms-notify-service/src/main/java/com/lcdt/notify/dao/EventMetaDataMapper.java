package com.lcdt.notify.dao;

import com.lcdt.notify.model.EventMetaData;

import java.util.List;

public interface EventMetaDataMapper {
    int deleteByPrimaryKey(Integer eventId);

    int insert(EventMetaData record);

    EventMetaData selectByPrimaryKey(Integer eventId);

    List<EventMetaData> selectAll();

    int updateByPrimaryKey(EventMetaData record);

    EventMetaData selectByEventName(String eventName);

}