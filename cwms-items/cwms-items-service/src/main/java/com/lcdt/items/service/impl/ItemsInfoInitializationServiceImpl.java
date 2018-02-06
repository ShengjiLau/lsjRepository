package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.model.CalcUnit;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.service.CalcUnitService;
import com.lcdt.items.service.ItemClassifyService;
import com.lcdt.items.service.ItemsInfoInitializationService;
import com.lcdt.userinfo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 2018/2/6
 */
@Transactional
@Service
public class ItemsInfoInitializationServiceImpl implements ItemsInfoInitializationService{

    @Autowired
    private ItemClassifyService itemClassifyService;
    @Autowired
    private CalcUnitService calcUnitService;

    @Override
    public int itemInfoInitialization(Long companyId,String userName,Long userId) {
        int result=0;
        ItemClassify itemClassify=new ItemClassify();
        itemClassify.setClassifyName("默认分类");
        itemClassify.setCreateId(userId);
        itemClassify.setCreateName(userName);
        itemClassify.setIsDefault(true);
        itemClassify.setCompanyId(companyId);
        itemClassify=itemClassifyService.addItemClassify(itemClassify);
        if(itemClassify.getClassifyId()!=null&&itemClassify.getClassifyId()>0){
            result+=1;
        }

        CalcUnit calcUnit=new CalcUnit();
        calcUnit.setCompanyId(companyId);
        calcUnit.setCreateId(userId);
        calcUnit.setCreateName(userName);
        calcUnit.setUnitName("吨");
        result+=calcUnitService.addCalcUnit(calcUnit);

        CalcUnit calcUnit1=new CalcUnit();
        calcUnit1.setCompanyId(companyId);
        calcUnit1.setCreateId(userId);
        calcUnit1.setCreateName(userName);
        calcUnit1.setUnitName("立方");
        result+=calcUnitService.addCalcUnit(calcUnit1);

        CalcUnit calcUnit2=new CalcUnit();
        calcUnit2.setCompanyId(companyId);
        calcUnit2.setCreateId(userId);
        calcUnit2.setCreateName(userName);
        calcUnit2.setUnitName("件");
        result+=calcUnitService.addCalcUnit(calcUnit2);
        return result;
    }
}
