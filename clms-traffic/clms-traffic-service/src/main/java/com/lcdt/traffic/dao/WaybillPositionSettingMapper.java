package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.WaybillPositionSetting;

import java.util.List;

public interface WaybillPositionSettingMapper {
    int deleteByPrimaryKey(Long wpsId);

    int insert(WaybillPositionSetting record);

    WaybillPositionSetting selectByPrimaryKey(Long wpsId);

    List<WaybillPositionSetting> selectAll();

    int updateByPrimaryKey(WaybillPositionSetting record);
}