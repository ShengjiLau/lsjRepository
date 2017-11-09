package com.lcdt.items.controller;

import com.lcdt.items.service.ItemClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2017/11/8
 */
@RestController
public class TestContrller {
    @Autowired
    private ItemClassifyService itemClassifyService;

    @RequestMapping("/hello")
    public String test() {
        int i = itemClassifyService.deleteItemsClassifyAndChildren(45L);
        return "删除行数" + i;
    }
}
