package com.lcdt.items.controller;

import com.lcdt.items.service.ItemClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyqishan on 2017/11/8
 */
@Controller
public class TestContrller {
    @Autowired
    private ItemClassifyService itemClassifyService;

    @RequestMapping("/hello")
    public void test(){
        itemClassifyService.deleteItemsClassifyAndchildren(1L);
    }
}
