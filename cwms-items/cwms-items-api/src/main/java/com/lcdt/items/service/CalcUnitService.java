package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.CalcUnitDto;
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
     * @param calcUnitDto
     * @return
     */
    int addCalcUnit(CalcUnitDto calcUnitDto);

    /**
     * 根据unitId删除计量单位
     *
     * @param unitId
     * @return
     */
    int deleteCalcUnit(Long unitId);

    /**
     * 根据unitId更新计量单位CalcUnit
     *
     * @param calcUnitDto
     * @return
     */
    int modifyCalcUnitByPrimaryKey(CalcUnitDto calcUnitDto);

    /**
     * 根据unitId查询一条CalcUnit
     *
     * @param unitId
     * @return
     */
    CalcUnit queryCalcUnitByPrimaryKey(Long unitId);

    /**
     * 根据companyId查询此企业下的所有计量单位
     * @param companyId
     * @return
     */
    List<CalcUnit> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo);
}
