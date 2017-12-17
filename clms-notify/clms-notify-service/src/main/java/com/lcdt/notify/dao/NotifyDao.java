package com.lcdt.notify.dao;

import com.lcdt.notify.model.CustomeNotifyContent;
import com.lcdt.notify.model.Notify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotifyDao {

    List<Notify> queryByEventName(String eventName);

    CustomeNotifyContent queryByTemplateId(@Param("companyId") Long companyId,@Param("templateId") Long templateId);
}
