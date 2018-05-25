package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.driver.dto.CustomerListParamsDto;
import com.lcdt.driver.dto.CustomerListResultDto;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.driver.wechat.api.util.GroupIdsUtil;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.UserGroupService;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("groupIds", GroupIdsUtil.getCustomerGroupIds(null));

        List<Customer> customerList = customerService.findBindCompanyIds(map);
        return new PageBaseDto<Customer>(customerList);
    }

    @ApiOperation("我的客户列表")
    @RequestMapping(value = "/customerList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    public CustomerListResultDto customerList(@Validated CustomerListParamsDto dto,
                                              @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                              @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();

        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getComplexContition())) {
            map.put("complexContition",dto.getComplexContition());
        }
        if (dto.getStatus()!=null) {
            map.put("status",dto.getStatus());
        }
        if (StringUtil.isNotEmpty(dto.getProvince())) {
            map.put("province",dto.getProvince());
        }
        if (StringUtil.isNotEmpty(dto.getCity())) {
            map.put("city",dto.getCity());
        }
        if (StringUtil.isNotEmpty(dto.getCounty())) {
            map.put("county",dto.getCounty());
        }

        if (StringUtil.isNotEmpty(dto.getCustomerTypes())) { //1-销售客户2-仓储客户3-运输客户4-仓储服务商5-运输服务商6-供应商7-其他
            String[] customTypeArray = dto.getCustomerTypes().split(",");
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i=0;i<customTypeArray.length;i++) {
                sb.append(" find_in_set('"+customTypeArray[i]+"',client_types)");
                if(i!=customTypeArray.length-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            map.put("customerType", sb.toString());
        }
        //竞价(抢单用)
        if (StringUtil.isNotEmpty(dto.getCollectionIds())) {
            String[] collectionIds = dto.getCollectionIds().split(",");
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i=0;i<collectionIds.length;i++) {
                sb.append(" find_in_set('"+collectionIds[i]+"',collection_ids)");
                if(i!=collectionIds.length-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");

            map.put("collectionIds", sb.toString());
        }
        //项目分组
        if (StringUtil.isNotEmpty(dto.getGroupIds())) {
            String[] groupIdArray = dto.getGroupIds().split(",");
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i=0;i<groupIdArray.length;i++) {
                sb.append(" find_in_set('"+groupIdArray[i]+"',group_ids)");
                if(i!=groupIdArray.length-1){
                    sb.append(" or ");
                }
            }
            sb.append(")");
            map.put("groupIds", sb.toString());
        } else {
            StringBuffer sb = new StringBuffer();
            List<Group> groupList = userCompRel.getGroups();
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
        }
        // map.remove("customerType");//去掉客户类型查询条件
        PageInfo pageInfo = customerService.customerList(map);
        CustomerListResultDto dto1 = new CustomerListResultDto();
        dto1.setList(pageInfo.getList());
        dto1.setTotal(pageInfo.getTotal());
        return dto1;
    }

}
