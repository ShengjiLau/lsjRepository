package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CalcUnitMapper;
import com.lcdt.items.dto.CalcUnitDto;
import com.lcdt.items.model.CalcUnit;
import com.lcdt.items.service.CalcUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/30
 */
@Transactional
@Service
public class CalcUnitServiceImpl implements CalcUnitService {

    @Autowired
    private CalcUnitMapper calcUnitMapper;

    @Override
    public int addCalcUnit(CalcUnitDto calcUnitDto) {
        int result = 0;
        try {
            CalcUnit calcUnit = new CalcUnit();
            calcUnit.setUnitName(calcUnitDto.getUnitName());
            calcUnit.setCompanyId(calcUnitDto.getCompanyId());
            result = calcUnitMapper.insert(calcUnit);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int deleteCalcUnit(Long unitId) {
        int result = 0;
        try {
            result = calcUnitMapper.deleteByPrimaryKey(unitId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int modifyCalcUnitByPrimaryKey(CalcUnitDto calcUnitDto) {
        int result = 0;
        try {
            CalcUnit calcUnit = new CalcUnit();
            calcUnit.setUnitId(calcUnitDto.getUnitId());
            calcUnit.setUnitName(calcUnitDto.getUnitName());
            calcUnit.setCompanyId(calcUnitDto.getCompanyId());
            result = calcUnitMapper.updateByPrimaryKey(calcUnit);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public CalcUnit queryCalcUnitByPrimaryKey(Long unitId) {
        CalcUnit calcUnit = null;
        try {
            calcUnit = calcUnitMapper.selectByPrimaryKey(unitId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return calcUnit;
        }
    }

    @Override
    public List<CalcUnit> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo) {
        List<CalcUnit> calcUnitList = null;
        try {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
            calcUnitList = calcUnitMapper.selectCalcUnitByCompanyId(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return calcUnitList;
        }
    }
}
