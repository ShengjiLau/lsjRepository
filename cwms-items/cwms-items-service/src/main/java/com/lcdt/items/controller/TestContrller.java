package com.lcdt.items.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemSpecificationDao;
import com.lcdt.items.service.ItemClassifyService;
import com.lcdt.items.service.ItemSpecificationService;
import com.lcdt.items.service.ItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/8
 */
@RestController
public class TestContrller {
    @Autowired
    private ItemClassifyService itemClassifyService;

    @Autowired
    private ItemsInfoService itemsInfoService;

    @Autowired
    private ItemSpecificationService itemSpecificationService;

    @RequestMapping("/hello")
    public String test() {
//        String str=itemClassifyService.queryCassifyIdAndAllChildrenClassifyIds(55L);
//        return "删除行数" + str;
        List<ItemSpecificationDao> itemSpecificationDaoList=itemSpecificationService.querySpecification(1L);
        JSONArray jsonArray= (JSONArray) JSON.toJSON(itemSpecificationDaoList);
        return jsonArray.toString();
    }
}
