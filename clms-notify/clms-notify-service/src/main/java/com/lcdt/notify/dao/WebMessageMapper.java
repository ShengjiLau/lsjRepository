package com.lcdt.notify.dao;

import com.lcdt.notify.model.WebMessage;

import java.util.List;

public interface WebMessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(WebMessage record);

    WebMessage selectByPrimaryKey(Long messageId);

    List<WebMessage> selectAll();

    int updateByPrimaryKey(WebMessage record);
}