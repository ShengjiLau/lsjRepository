package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.WaybillPositionSetting;
import com.lcdt.traffic.web.dto.WaybillPositionSettingDto;

import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/1/15
 */

public interface WaybillPositionSettingService {

    /**
     * 新增运单定位设置
     * @param dto
     * @return
     */
    int addWaybillPositionSetting(WaybillPositionSettingDto dto);

    /**
     * 修改定位设置
     * @param dto
     * @return
     */
    int modifyWaybillPositionSetting(WaybillPositionSettingDto dto);

    /**
     * 司机定位设置
     * @param dto
     * @return
     */
    int modifyDriverPositionSetting(WaybillPositionSettingDto dto);
    /**
     * 查询定位设置
     * @param map
     * @return
     */
    WaybillPositionSetting queryWaybillPositionSetting(Map map);

    /**
     * 查询定位设置列表
     * @param map
     * @return
     */
    PageInfo<List<WaybillPositionSetting>> queryWaybillPositionSettingList(Map map);

    /**
     * 获取司机定位
     * @param driverPhone
     * @param companyId
     * @return
     */
    PageInfo<List<WaybillPositionSetting>> queryDriverPositionSettingList(String driverPhone,Long companyId);
}
