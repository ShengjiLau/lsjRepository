package com.lcdt.warehouse.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InPlanGoodsInfoResultDto;
import com.lcdt.warehouse.entity.InHousePlan;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import com.lcdt.warehouse.mapper.InplanGoodsInfoMapper;
import com.lcdt.warehouse.service.InWarehousePlanService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

public class DemoTest extends BaseIntegrationContext{

    @Autowired
    InWarehousePlanMapper mapper;

    @Autowired
    InWarehousePlanService inWarehousePlanService;

    @Autowired
    InplanGoodsInfoMapper mapper1;

    @Test
    @Rollback
    public void testInsertPlan() {
//        InHousePlan inHousePlan = new InHousePlan();
//        inHousePlan.setCompanyId(1L);
/*
        InWarehousePlan inWarehousePlan = new InWarehousePlan();
        inWarehousePlan.setCompanyId(1L);
        inWarehousePlan.setPlanNo("iadoj");
        inWarehousePlan.setGroupId(1);
        inWarehousePlan.setGroupName("groupName");
        inWarehousePlan.setWarehouseName("asd");
        inWarehousePlan.setWareHouseId(1L);
        mapper.insert(inWarehousePlan);

        mapper.inWarehousePlanList();*/
        //Page<InWarehousePlan> list = inWarehousePlanService.inWarehousePlanList(null);

        List<InPlanGoodsInfoResultDto> list =  mapper1.inWhPlanGoodsInfoList(new Page<InPlanGoodsInfoResultDto>(),2l);
        for(InPlanGoodsInfoResultDto obj :list) {
            System.out.println(obj.getGoodsBarcode());
        }

    }

}
