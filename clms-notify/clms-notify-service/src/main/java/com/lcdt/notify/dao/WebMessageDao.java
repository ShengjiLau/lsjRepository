package com.lcdt.notify.dao;

import com.lcdt.notify.model.WebMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebMessageDao {

    List<WebMessage> selectUnReadMessage(@Param("companyId") Long companyId,@Param("userId") Long userId,@Param("message_category")
                                         String messageCategory
                                         );

    void setReadMessage(@Param("messageId") Long messageId,@Param("companyId") Long companyId
    ,@Param("userId") Long userId);

    Integer unreadMessageCount(@Param("companyId") Long companyId, @Param("userId") Long userId,@Param("message_category")
            String messageCategory);

    void removeMessage(@Param("messageId") Long messageId,@Param("companyId") Long companyId, @Param("userId") Long userId);

}
