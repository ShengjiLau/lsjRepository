package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.WaybillPositionSetting;
import com.lcdt.traffic.web.dto.WaybillPositionSettingDto;

import java.util.List;
import java.util.Map;

public interface WaybillPositionSettingMapper {
    /**
     * 删除运单定位设置
     * @param wpsId
     * @return
     */
    int deleteByPrimaryKey(Long wpsId);

    /**
     * 插入新增运单定位设置
     * @param record
     * @return
     */
    int insert(WaybillPositionSetting record);

    /**
     * 根据运单定位设置id查询
     * @param wpsId
     * @return
     */
    WaybillPositionSetting selectByPrimaryKey(Long wpsId);

    /**
     * 查询所有运单定位设置记录
     * @return
     */
    List<WaybillPositionSetting> selectAll();

    /**
     * 根据id更新运单定位设置
     * @param record
     * @return
     */
    int updateByPrimaryKey(WaybillPositionSetting record);

    /**
     * 根据id和X公司Id更新
     * @param record
     * @return
     */
    int updateByWpsIdAndXCompanyId(WaybillPositionSetting record);

    /**
     * 根据id和XCompanyId查询（WpsId,(companyId or carrierCompanyId)）
     * @param map
     * @return
     */
    WaybillPositionSetting selectByWpsIdAndXCompanyId(Map map);

    /**
     * 根据运单id和XCompanyId查询（WpsId,(companyId or carrierCompanyId)）
     * @param map
     * @return
     */
    List<WaybillPositionSetting> selectByWaybillIdAndXCompanyId(Map map);
}