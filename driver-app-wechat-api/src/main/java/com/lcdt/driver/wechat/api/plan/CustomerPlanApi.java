package com.lcdt.driver.wechat.api.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.CustomerPlan4ParamsDto;
import com.lcdt.traffic.dto.CustomerPlanDto;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.service.ICustomerPlanRpcService4Wechat;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/3.
 */
@RestController
@RequestMapping("/customer/plan")
public class CustomerPlanApi {

    @com.alibaba.dubbo.config.annotation.Reference(check = false)
    private ICustomerPlanRpcService4Wechat iCustomerPlanRpcService4Wechat;


    @ApiOperation("客户计划-竞价")
    @RequestMapping(value = "/customerPlanList4Bidding", method = RequestMethod.GET)
    public PageBaseDto customerPlanList4Bidding(CustomerPlan4ParamsDto customerPlan4ParamsDto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
          Map map = new HashMap<String,String>();


        PageInfo pg =iCustomerPlanRpcService4Wechat.customerPlanList4Bidding(map);
        PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
        return pageBaseDto;
    }


}
