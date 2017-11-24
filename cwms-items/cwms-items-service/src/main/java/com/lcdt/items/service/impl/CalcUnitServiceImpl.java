package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CalcUnitMapper;
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
    public int addCalcUnit(CalcUnit calcUnit) {
        int result = 0;
        result = calcUnitMapper.insert(calcUnit);
        return result;
    }

    @Override
    public int deleteCalcUnit(Long unitId) {
        int result = 0;
        result = calcUnitMapper.deleteByPrimaryKey(unitId);
        return result;
    }

    @Override
    public int modifyCalcUnitByPrimaryKey(CalcUnit calcUnit) {
        int result = 0;
        result = calcUnitMapper.updateByPrimaryKey(calcUnit);
        return result;
    }

    @Override
    public CalcUnit queryCalcUnitByPrimaryKey(Long unitId) {
        return calcUnitMapper.selectByPrimaryKey(unitId);
    }

    @Override
    public List<CalcUnit> queryCalcUnitByCompanyId(Long companyId) {
        return calcUnitMapper.selectCalcUnitByCompanyId(companyId);
    }


    @Override
    public List<CalcUnit> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo) {
        List<CalcUnit> calcUnitList = null;
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        calcUnitList = calcUnitMapper.selectCalcUnitByCompanyId(companyId);
        return calcUnitList;
    }

    @Override
    public boolean isUnitNameExist(CalcUnit calcUnit) {
        List<CalcUnit> calcUnitList=calcUnitMapper.selectCalcUnitList(calcUnit);
        if(calcUnitList!=null&&calcUnitList.size()>0){
            return true;
        }else{
            return false;
        }
    }


}
