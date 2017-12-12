package com.lcdt.notify.dao;

import com.lcdt.notify.model.Notify;

import java.util.List;

public interface NotifyMapper {
    int deleteByPrimaryKey(Long notifyId);

    int insert(Notify record);

    Notify selectByPrimaryKey(Long notifyId);

    List<Notify> selectAll();

    int updateByPrimaryKey(Notify record);
}