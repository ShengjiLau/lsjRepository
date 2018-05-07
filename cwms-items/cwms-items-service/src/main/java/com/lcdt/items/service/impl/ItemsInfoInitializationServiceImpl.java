package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.model.*;
import com.lcdt.items.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private ConversionRelService conversionRelService;
    @Autowired
    private ItemsInfoService itemsInfoService;


    @Override
    public int itemInfoInitialization(Long companyId,String userName,Long userId) {
        int result=0;
        ItemClassify itemClassify=new ItemClassify();
        itemClassify.setClassifyName("默认分类");
        itemClassify.setCreateId(userId);
        itemClassify.setCreateName(userName);
        itemClassify.setIsDefault(true);
        itemClassify.setCompanyId(companyId);
        itemClassify.setPid(0L);
        List<ItemClassify> list=itemClassifyService.queryIsExistClassify(itemClassify);
        if(list!=null&&list.size()>0){
           throw new RuntimeException("默认分类已存在");
        }
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
        calcUnit1.setUnitName("件");
        result+=calcUnitService.addCalcUnit(calcUnit1);

        CalcUnit calcUnit2=new CalcUnit();
        calcUnit2.setCompanyId(companyId);
        calcUnit2.setCreateId(userId);
        calcUnit2.setCreateName(userName);
        calcUnit2.setUnitName("方");
        result+=calcUnitService.addCalcUnit(calcUnit2);

        //多单位初始化
        ConversionRel conversionRel=new ConversionRel();
        conversionRel.setCompanyId(companyId);
        conversionRel.setCreateId(userId);
        conversionRel.setCreateName(userName);
        conversionRel.setUnitId(calcUnit.getUnitId());
        conversionRel.setUnitName(calcUnit.getUnitName());
        conversionRel.setUnitId1(calcUnit1.getUnitId());
        conversionRel.setUnitName1(calcUnit1.getUnitName());
        conversionRel.setData1(1);
        conversionRel.setUnitId2(calcUnit2.getUnitId());
        conversionRel.setUnitName2(calcUnit2.getUnitName());
        conversionRel.setData2(1);
        conversionRelService.addConversionRel(conversionRel);

        ItemsInfoDao itemsInfoDao=new ItemsInfoDao();
        itemsInfoDao.setCompanyId(companyId);
        itemsInfoDao.setCreateId(userId);
        itemsInfoDao.setCreateName(userName);
        itemsInfoDao.setSubject("普货");
        itemsInfoDao.setClassifyId(itemClassify.getClassifyId());
        itemsInfoDao.setClassifyName(itemClassify.getClassifyName());
        itemsInfoDao.setUnitId(calcUnit.getUnitId());
        itemsInfoDao.setUnitName(calcUnit.getUnitName());
        itemsInfoDao.setConverId(conversionRel.getConverId());
        itemsInfoDao.setTradeType((short)3);

        SubItemsInfoDao subItemsInfoDao=new SubItemsInfoDao();
        subItemsInfoDao.setCode("P"+System.currentTimeMillis()+(10+(int)(Math.random()*90)));
        List<SubItemsInfoDao> subItemsInfoDaoList=new ArrayList<>();
        subItemsInfoDaoList.add(subItemsInfoDao);
        itemsInfoDao.setSubItemsInfoDaoList(subItemsInfoDaoList);

        itemsInfoService.addItemsInfo(itemsInfoDao);

        return result;
    }
}
