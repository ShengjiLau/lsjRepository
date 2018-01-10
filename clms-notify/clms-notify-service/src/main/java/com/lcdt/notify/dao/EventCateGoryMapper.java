package com.lcdt.notify.dao;

import com.lcdt.notify.model.EventCateGory;

import java.util.List;

public interface EventCateGoryMapper {
    int insert(EventCateGory record);

    List<EventCateGory> selectAll();
}