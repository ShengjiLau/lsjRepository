package com.lcdt.warehouse.dao;

import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.dto.WarehouseDto;
import com.lcdt.warehouse.mapper.WarehousseMapper;
import com.lcdt.warehouse.service.WarehouseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class WareHouseTest extends BaseIntegrationContext {

    @Autowired
    WarehousseMapper mapper;


    @Autowired
    WarehouseService warehouseService;

    @Test
    public void testWarehouseSelect() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        List<WarehouseDto> warehouseDtos = mapper.selectByCondition(objectObjectHashMap);
        Assert.assertNotNull(warehouseDtos);
    }

    @Test
    public void testWarehouseService(){
        PageInfo pageInfo = warehouseService.warehouseList(new HashMap());
        Assert.assertNotNull(pageInfo);
    }

}
