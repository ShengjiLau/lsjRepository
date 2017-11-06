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
        try {

            return calcUnitMapper.insert(calcUnit);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return 0;
        }
    }

    @Override
    public int deleteCalcUnit(Long unitId) {
        try {
            return calcUnitMapper.deleteByPrimaryKey(unitId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return 0;
        }
    }

    @Override
    public int modifyCalcUnitByPrimaryKey(CalcUnit record) {
        try {
            calcUnitMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return 0;
        }
    }

    @Override
    public CalcUnit queryCalcUnitByPrimaryKey(Long unitId) {
        try {
            calcUnitMapper.selectByPrimaryKey(unitId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    @Override
    public List<CalcUnit> queryCalcUnitByCompanyId(Long companyId, PageInfo pageInfo) {
        try{
            PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
//            return calcUnitMapper.selectCalcUnitByCompanyId(companyId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
