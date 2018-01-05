package com.lcdt.notify.dao;

import com.lcdt.notify.model.CustomeNotifyContent;
import com.lcdt.notify.model.Notify;
import com.lcdt.notify.web.dto.NotifySetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotifyDao {

    List<Notify> queryByEventName(String eventName);

    CustomeNotifyContent queryByTemplateId(@Param("companyId") Long companyId,@Param("templateId") Long templateId);


    List<NotifySetDto> selectNotifySetByCateGory(@Param("companyId") Long companyId, @Param("category") String category);
}
