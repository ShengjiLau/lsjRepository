package com.lcdt.notify.dao;

import com.lcdt.notify.model.CompanyNotifySetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotifySettingMapper {

    List<CompanyNotifySetting> selectByCompanyAndNotify(@Param("companyId") Long companyId, @Param("notifyId") Long notifyId);

}
