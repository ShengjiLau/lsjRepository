package com.lcdt.customer.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.web.dto.CustomerListDto;
import com.lcdt.customer.web.dto.CustomerParamsDto;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/20.
 */
@RestController
@RequestMapping("/api/customer")
@Api(value = "客户接口",description = "客户模块接口")
public class CustomerApi {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("我的客户列表")
    @RequestMapping(value = "/customerList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_list')")
    public CustomerListDto customerList(@Validated CustomerParamsDto dto,
                                        @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                        @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if (StringUtil.isNotEmpty(dto.getComplexContition())) {
           map.put("complexStr",dto.getComplexContition());
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
        PageInfo pageInfo = customerService.getCustomerList(map);
        CustomerListDto dto1 = new CustomerListDto();
        dto1.setList(pageInfo.getList());
        dto1.setTotal(pageInfo.getTotal());
        return dto1;
    }

    /**
     * 客户详情
     * @return
     */

    @ApiOperation("客户详情")
    @RequestMapping(value = "/customerDetail",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_list')")
    public Customer customerDetail(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return customerService.getCustomerDetail(customerId);
    }

    /**
     * 客户新增
     *
     * @return
     */
    @ApiOperation("新增客户")
    @RequestMapping(value = "/customerAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_add')")
    public Customer customerAdd(@Validated CustomerParamsDto dto) {
        //客户主表、联系人表、客户类型关系部分
        return null;
    }

    /**
     * 客户编辑
     *
     * @return
     */
    @ApiOperation("客户编辑")
    @RequestMapping(value = "/customerEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_add')")
    public Customer customerEdit(@Validated CustomerParamsDto dto) {

        return null;
    }


}
