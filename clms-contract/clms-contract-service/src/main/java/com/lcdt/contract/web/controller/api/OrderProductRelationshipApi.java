package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderProductRelationshipDao;
import com.lcdt.contract.dto.OrderProductRelationshipParams;
import com.lcdt.contract.model.OrderProductRelationship;
import com.lcdt.contract.service.OrderProductRelationshipService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lyqishan on 2018/8/14
 */
@Api(value="商品关系api",description="商品关系操作")
@RestController
@RequestMapping("/product")
public class OrderProductRelationshipApi {
    @Autowired
    private OrderProductRelationshipService orderProductRelationshipService;

    @ApiOperation("修改匹配关系")
    @PostMapping(value = "relationship")
    public JSONObject modifyRelationship(OrderProductRelationshipParams params){
        User loginUser = SecurityInfoGetter.getUser();
        params.setCompanyId(SecurityInfoGetter.getCompanyId())
                .setCreateName(loginUser.getRealName())
                .setUpdateName(loginUser.getRealName());
        if(orderProductRelationshipService.modifyRelationship(params)>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","匹配成功");
            return jsonObject;
        }else{
            throw new RuntimeException("匹配失败");
        }
    }

    @ApiOperation("查询匹配关系和商品")
    @GetMapping(value = "relationship")
    public JSONObject getRelationship(@ApiParam(value = "product id") @RequestParam Long opId){
        OrderProductRelationshipDao orderProductRelationshipDao=orderProductRelationshipService.queryRelationshipDao(opId,SecurityInfoGetter.getCompanyId());
        JSONObject jsonObject=new JSONObject();
        if(null !=orderProductRelationshipDao){
            jsonObject.put("code",0);
            jsonObject.put("message","查询成功");
            jsonObject.put("data",orderProductRelationshipDao);
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","查询成功");
            jsonObject.put("data",orderProductRelationshipDao);
        }
        return jsonObject;
    }
}
