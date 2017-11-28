package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.items.web.dto.SubItemsInfoAddDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by lyqishan on 2017/11/28
 */
@Api("子商品信息api")
@RestController
@RequestMapping("/items/subitemsinfo")
public class SubItemsInfoApi {
    @Autowired
    private SubItemsInfoService subItemsInfoService;

    @ApiOperation("删除子商品")
    @PostMapping("/delete")
    public JSONObject deleteSubItemsInfo(HttpSession httpSession, @ApiParam(value = "子商品Id", required = true) @RequestParam Long subItemId){
        int result=subItemsInfoService.deleteSubItemsInfo(subItemId);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","删除成功");
            return jsonObject;
        }else {
            throw new RuntimeException("删除失败");
        }
    }

}
