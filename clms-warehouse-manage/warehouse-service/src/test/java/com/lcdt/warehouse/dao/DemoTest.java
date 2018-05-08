package com.lcdt.warehouse.dao;

import com.lcdt.warehouse.entity.InHousePlan;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class DemoTest extends BaseIntegrationContext{

    @Autowired
    InWarehousePlanMapper mapper;

    @Test
    @Rollback
    public void testInsertPlan() {
//        InHousePlan inHousePlan = new InHousePlan();
//        inHousePlan.setCompanyId(1L);

        InWarehousePlan inWarehousePlan = new InWarehousePlan();
        inWarehousePlan.setCompanyId(1L);
        inWarehousePlan.setPlanNo("iadoj");
        inWarehousePlan.setGroupId(1);
        inWarehousePlan.setGroupName("groupName");
        inWarehousePlan.setWarehouseName("asd");
        inWarehousePlan.setWareHouseId(1L);
        mapper.insert(inWarehousePlan);

        mapper.inWarehousePlanList();
    }

}
