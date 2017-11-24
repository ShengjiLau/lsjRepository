package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.service.ItemsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * Created by lyqishan on 2017/11/24
 */
@Api("商品信息api")
@RestController
@RequestMapping("/items/itemsinfo")
public class ItemsInfoApi {

   Logger log = LoggerFactory.getLogger(ItemsInfoApi.class);

   @Autowired
   private ItemsInfoService itemsInfoService;

   @ApiOperation("新增商品")
   @PostMapping("/add")
   public JSONObject addItemInfo(ItemsInfoDto itemsInfoDto, HttpSession httpSession){
       int result=itemsInfoService.addItemsInfo(itemsInfoDto);
       if(result>0){
           JSONObject jsonObject=new JSONObject();
           jsonObject.put("code",'1');
           jsonObject.put("message","添加成功");
           return jsonObject;
       }else {
           throw new RuntimeException("添加失败");
       }
   }

}
