package com.lcdt.notify.dao;

import com.lcdt.notify.model.WebMessage;

import java.util.List;

public interface WebMessageDao {

    List<WebMessage> selectUnReadMessage(Long companyId,Long userId);

    void setReadMessage(Long messageId);

}
