package com.lcdt.notify.dao;

import com.lcdt.notify.model.MsgTemplate;

import java.util.List;

public interface MsgTemplateMapper {
    int deleteByPrimaryKey(Long templateId);

    int insert(MsgTemplate record);

    MsgTemplate selectByPrimaryKey(Long templateId);

    List<MsgTemplate> selectAll();

    int updateByPrimaryKey(MsgTemplate record);
}