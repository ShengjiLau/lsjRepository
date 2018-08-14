package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dto.OrderProductRelationshipParams;
import com.lcdt.contract.model.OrderProductRelationship;
import com.lcdt.contract.service.OrderProductRelationshipService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2018/8/14
 */
@Api(value="商品关系api",description="商品关系操作")
@RestController
@RequestMapping("/product")
public class OrderProductRelationshipApi {
    @Autowired
    private OrderProductRelationshipService orderProductRelationshipService;

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
}
