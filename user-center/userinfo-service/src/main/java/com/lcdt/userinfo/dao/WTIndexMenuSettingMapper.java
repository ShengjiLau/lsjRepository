package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dto.WTIndexMenuSettingListParams;
import com.lcdt.userinfo.model.WTIndexMenuSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WTIndexMenuSettingMapper {
    int deleteByPrimaryKey(Long menuSettingId);

    int insert(WTIndexMenuSetting record);

    WTIndexMenuSetting selectByPrimaryKey(Long menuSettingId);

    List<WTIndexMenuSetting> selectAll();

    int updateByPrimaryKey(WTIndexMenuSetting record);

    WTIndexMenuSetting selectMenuSettingById(@Param("companyId") Long companyId,@Param("userId") Long userId,@Param("menuId") Long menuId);

    /**
     * 查询列表
     * @param params
     * @return
     */
    List<WTIndexMenuSettingDao> selectWTIndexMenuSetting(WTIndexMenuSettingListParams params);
}