package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.model.CalcUnit;

import java.util.List;

/**
 * 计量单位CalcUnitService类
 * Created by lyqishan on 2017/10/30
 */
public interface CalcUnitService {
    /**
     * 新增单位 CalcUnit
     *
     * @param calcUnit
     * @return
     */
    int addCalcUnit(CalcUnit calcUnit);

    /**
     * 根据unitId删除计量单位
     *
     * @param unitId
     * @return
     */
    int deleteCalcUnit(Long unitId,Long companyId);

    /**
     * 根据unitId 和 companyId 更新计量单位CalcUnit
     *
     * @param calcUnit
     * @return
     */
    int modifyByUnitIdAndCompanyId(CalcUnit calcUnit);

    /**
     * 根据unitId查询一条CalcUnit
     *
     * @param unitId
     * @return
     */
    CalcUnit queryCalcUnit(Long unitId,Long companyId);

    /**
     * 根据companyId查询此企业下的所有计量单位,无分页
     * @param companyId
     * @return
     */
    List<CalcUnit> queryCalcUnitByCompanyId(Long companyId);

    /**
     * 根据companyId查询此企业下的所有计量单位，有分页
     * @param companyId
     * @param pageInfo
     * @return
     */
    PageInfo<List<CalcUnit>> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo);

    /**
     * 判断本企业内单位名称是否存在
     * @param calcUnit
     * @return
     */
    boolean isUnitNameExist(CalcUnit calcUnit);
}
