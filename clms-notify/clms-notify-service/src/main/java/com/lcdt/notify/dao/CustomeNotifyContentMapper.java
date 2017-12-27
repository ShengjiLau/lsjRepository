package com.lcdt.notify.dao;

import com.lcdt.notify.model.CustomeNotifyContent;

import java.util.List;

public interface CustomeNotifyContentMapper {
    int deleteByPrimaryKey(Long customeId);

    int insert(CustomeNotifyContent record);

    CustomeNotifyContent selectByPrimaryKey(Long customeId);

    List<CustomeNotifyContent> selectAll();

    int updateByPrimaryKey(CustomeNotifyContent record);
}