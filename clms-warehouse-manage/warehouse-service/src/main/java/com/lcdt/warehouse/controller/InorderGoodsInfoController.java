package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.warehouse.dto.InWarehouseOrderDetailDto;
import com.lcdt.warehouse.service.InorderGoodsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@RestController
@RequestMapping("/wh/in")
@Api(value = "入库单商品api", description = "入库单商品操作")
public class InorderGoodsInfoController {
    @Autowired
    private InorderGoodsInfoService inorderGoodsInfoService;

    @ApiOperation("删除入库单商品")
    @RequestMapping(value = "/goods/{relationId}", method = RequestMethod.DELETE)
    public JSONObject inWarehouseOrderDetail(@PathVariable long relationId) {
        int result=inorderGoodsInfoService.deleteGoodsInfo(SecurityInfoGetter.getCompanyId(),relationId);
        JSONObject jsonObject=new JSONObject();
        System.out.println("result"+result);
        if(result>0){
            jsonObject.put("code",0);
            jsonObject.put("message","删除成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","删除失败");
        }
        return jsonObject;
    }
}

