package com.lcdt.werehouse.dao;

import com.lcdt.werehouse.entity.InHousePlan;
import com.lcdt.werehouse.mapper.InHousePlanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class DemoTest extends BaseIntegrationContext{

    @Autowired
    InHousePlanMapper mapper;

    @Test
    @Rollback
    public void testInsertPlan() {

        InHousePlan inHousePlan = new InHousePlan();
        inHousePlan.setCompanyId(1L);


        mapper.insert(inHousePlan);
    }

}
