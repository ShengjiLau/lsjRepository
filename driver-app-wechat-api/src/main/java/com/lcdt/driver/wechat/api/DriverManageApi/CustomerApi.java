package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.service.UserGroupService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2018/4/9.
 * 客户运单下客户分组
 */

@RestController
@RequestMapping("/api/customer")
@Api(value = "客户", description = "企业客户")
public class CustomerApi {
    @Reference
    private CustomerRpcService customerService;
    @Reference
    private UserGroupService userGroupService;

    Logger logger = LoggerFactory.getLogger(CustomerApi.class);
    @GetMapping(value = "/list")
    private PageBaseDto<Customer> carrierCustomer(){
        Long companyId = TokenSecurityInfoGetter.getUserCompRel().getCompany().getCompId();//  获取companyId
        Long userId = TokenSecurityInfoGetter.getUser().getUserId();//  获取companyId
        Map map = new HashMap();
        map.put("companyId", companyId);

        StringBuffer sb = new StringBuffer();
        List<Group> groupList =  userGroupService.userGroups(userId, companyId);
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
        map.put("groupIds", sb.toString());


        List<Customer> customerList = customerService.findBindCompanyIds(map);
        return new PageBaseDto<Customer>(customerList);
    }

}
