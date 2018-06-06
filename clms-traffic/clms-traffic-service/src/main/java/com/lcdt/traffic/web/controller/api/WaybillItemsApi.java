package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.service.WaybillItemsRpcService;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2018/6/6
 */
@RestController
@RequestMapping("/api/waybillitems/")
@Api(value = "运单商品", description = "运单商品接口")
public class WaybillItemsApi {

    @Autowired
    WaybillItemsRpcService waybillItemsRpcService;

    @ApiOperation("我的运单商品--删除")
    @RequestMapping(value = "/own/{waybillItemsId}", method = RequestMethod.DELETE)
    public JSONObject ownWaybillItemsDelete(@PathVariable Long waybillItemsId){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        int result=waybillItemsRpcService.deleteWaybillItems(companyId,waybillItemsId);
        JSONObject jsonObject=new JSONObject();
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
