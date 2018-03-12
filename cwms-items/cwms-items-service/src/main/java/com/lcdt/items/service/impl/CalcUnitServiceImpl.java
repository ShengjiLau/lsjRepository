package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CalcUnitMapper;
import com.lcdt.items.model.CalcUnit;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.service.CalcUnitService;
import com.lcdt.items.service.ItemsInfoService;
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

    @Autowired
    private ItemsInfoService itemsInfoService;

    @Override
    public int addCalcUnit(CalcUnit calcUnit) {
        int result = 0;
        if (isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        result = calcUnitMapper.insert(calcUnit);
        return result;
    }

    @Override
    public int deleteCalcUnit(Long unitId,Long companyId) {
        int result = 0;
        List<ItemsInfo> list=itemsInfoService.queryItemsByCalUnitId(unitId,companyId);
        if(list!=null&&list.size()>0){
            throw new RuntimeException("删除失败，单位已被使用！");
        }else {
            CalcUnit calcUnit=new CalcUnit();
            calcUnit.setUnitId(unitId);
            calcUnit.setCompanyId(companyId);
            result = calcUnitMapper.deleteByUnitIdAndCompanyId(calcUnit);
        }
        return result;
    }

    @Override
    public int modifyByUnitIdAndCompanyId(CalcUnit calcUnit) {
        int result = 0;
        if (isUnitNameExist(calcUnit)) {
            throw new RuntimeException("单位名称已存在");
        }
        result = calcUnitMapper.updateByUnitIdAndCompanyId(calcUnit);
        return result;
    }

    @Override
    public CalcUnit queryCalcUnit(Long unitId,Long companyId) {
        CalcUnit calcUnit=new CalcUnit();
        calcUnit.setUnitId(unitId);
        calcUnit.setCompanyId(companyId);
        return calcUnitMapper.selectByUnitIdAndCompanyId(calcUnit);
    }

    @Override
    public List<CalcUnit> queryCalcUnitByCompanyId(Long companyId) {
        return calcUnitMapper.selectCalcUnitByCompanyId(companyId);
    }


    @Override
    public PageInfo<List<CalcUnit>> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo) {
        List<CalcUnit> calcUnitList = null;
        PageInfo page=null;
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        calcUnitList = calcUnitMapper.selectCalcUnitByCompanyId(companyId);
        page=new PageInfo(calcUnitList);
        return page;
    }

    @Override
    public boolean isUnitNameExist(CalcUnit calcUnit) {
        List<CalcUnit> calcUnitList=calcUnitMapper.selectCalcUnitList(calcUnit);
        if(calcUnitList!=null&&calcUnitList.size()>0){
            if(calcUnit.getUnitName().equals(calcUnitList.get(0).getUnitName())&&calcUnit.getUnitId().equals(calcUnitList.get(0).getUnitId())){
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
}
