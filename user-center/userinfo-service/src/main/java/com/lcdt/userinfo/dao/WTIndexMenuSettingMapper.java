package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.WTIndexMenuSetting;

import java.util.List;

public interface WTIndexMenuSettingMapper {
    int deleteByPrimaryKey(Long menuSettingId);

    int insert(WTIndexMenuSetting record);

    WTIndexMenuSetting selectByPrimaryKey(Long menuSettingId);

    List<WTIndexMenuSetting> selectAll();

    int updateByPrimaryKey(WTIndexMenuSetting record);
}