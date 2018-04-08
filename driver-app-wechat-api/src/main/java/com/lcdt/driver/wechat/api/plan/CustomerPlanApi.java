package com.lcdt.driver.wechat.api.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.CustomerPlan4ParamsDto;
import com.lcdt.traffic.dto.CustomerPlanDto;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.service.ICustomerPlanRpcService4Wechat;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.Group;
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
          map.put("companyId", userCompRel.getCompany().getCompId());//登录企业ID
          map.put("page_no", customerPlan4ParamsDto.getPageNo());
          map.put("page_size", customerPlan4ParamsDto.getPageSize());
          map.put("isDeleted",0); //未删除的
          map.put("orderFiled",customerPlan4ParamsDto.getOrderFiled());
          map.put("orderDesc",customerPlan4ParamsDto.getOrderDesc());
          if (StringUtil.isNotEmpty(customerPlan4ParamsDto.getCustomerName())) { //客户名称
                map.put("customerName",customerPlan4ParamsDto.getCustomerName());
            }
            StringBuffer sb = new StringBuffer();
            List<Group> groupList = TokenSecurityInfoGetter.getUserCompRel().getGroups();
            if(groupList!=null && groupList.size()>0) {
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)");
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }


        PageInfo pg =iCustomerPlanRpcService4Wechat.customerPlanList4Bidding(map);
        PageBaseDto pageBaseDto = new PageBaseDto(pg.getList(), pg.getTotal());
        return pageBaseDto;
    }


}
