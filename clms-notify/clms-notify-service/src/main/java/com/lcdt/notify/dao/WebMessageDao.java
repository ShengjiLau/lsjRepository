package com.lcdt.notify.dao;

import com.lcdt.notify.model.WebMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebMessageDao {

    List<WebMessage> selectUnReadMessage(Long companyId,Long userId);

    void setReadMessage(@Param("messageId") Long messageId,@Param("companyId") Long companyId
    ,@Param("userId") Long userId);

    Integer unreadMessageCount(Long companyId, Long userId);

    void removeMessage(Long messageId, Long companyId, Long userId);

}
