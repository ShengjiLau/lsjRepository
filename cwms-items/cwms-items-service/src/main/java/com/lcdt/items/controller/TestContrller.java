package com.lcdt.items.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.service.ItemClassifyService;
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

    @RequestMapping("/hello")
    public String test() {
        String str=itemClassifyService.queryCassifyIdAndAllChildrenClassifyIds(55L);
        return "删除行数" + str;
    }
}
