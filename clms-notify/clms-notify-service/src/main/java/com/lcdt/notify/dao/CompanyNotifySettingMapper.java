package com.lcdt.notify.dao;

import com.lcdt.notify.model.CompanyNotifySetting;

import java.util.List;

public interface CompanyNotifySettingMapper {
    int deleteByPrimaryKey(Long settingId);

    int insert(CompanyNotifySetting record);

    CompanyNotifySetting selectByPrimaryKey(Long settingId);

    List<CompanyNotifySetting> selectAll();

    int updateByPrimaryKey(CompanyNotifySetting record);
}