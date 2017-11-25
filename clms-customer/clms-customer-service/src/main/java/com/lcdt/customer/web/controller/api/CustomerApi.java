package com.lcdt.customer.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.customer.exception.CustomerContactException;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerContact;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.customer.web.dto.CustomerParamsDto;
import com.lcdt.customer.web.dto.CustomerContactParamsDto;
import com.lcdt.customer.web.dto.CustomerListResultDto;
import com.lcdt.customer.web.dto.CustomerListParamsDto;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
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
    public CustomerListResultDto customerList(@Validated CustomerListParamsDto dto,
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
        PageInfo pageInfo = customerService.customerList(map);
        CustomerListResultDto dto1 = new CustomerListResultDto();
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
        Long companyId = SecurityInfoGetter.getCompanyId();
        Customer customer = new Customer();
        BeanUtils.copyProperties(customer, dto);
        customer.setCompanyId(companyId);
        try {
            customerService.addCustomer(customer);
        } catch (CustomerException e) {
            throw new CustomerException(e.getMessage());
        }
        return customer;
    }


    /**
     * 客户编辑
     *
     * @return
     */
    @ApiOperation("客户编辑")
    @RequestMapping(value = "/customerEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_edit')")
    public Customer customerEdit(@Validated CustomerParamsDto dto) {
        //客户主表、联系人表、客户类型关系部分
        Long companyId = SecurityInfoGetter.getCompanyId();
        Customer customer = new Customer();
        BeanUtils.copyProperties(customer, dto);
        customer.setCompanyId(companyId);
        try {
            customerService.updateCustomer(customer);
        } catch (CustomerException e) {
            throw new CustomerException(e.getMessage());
        }
        return customer;
    }


    /**
     * 客户状态修改
     * @param customerId
     * @param status
     * @return
     */
    @ApiOperation("客户修改状态")
    @RequestMapping(value = "/customStatusModify",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_status_modify')")
    public String customStatusModify(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId,
                                      @ApiParam(value = "状态(1-启，0-停)",required = true) @RequestParam short status) {
        Customer customer = customerService.getCustomerDetail(customerId);
        customer.setStatus(status);
        Integer flag = customerService.updateCustomer(customer);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag>0) {
           if(status==0) { //停止
               message = "停用后无法继续业务处理！";
           } else { //启动
               message = "已启用！";
           }
          code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }



    /**
     * 客户删除
     * @param customerId
     * @return
     */
    @ApiOperation("客户删除")
    @RequestMapping(value = "/customerRemove",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_remove')")
    public String customerRemove(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId) {
        Customer customer = customerService.getCustomerDetail(customerId);
        if (customer.getStatus()==1) {
            throw new CustomerException("此客户未停用，不能删除！");
        }
        if (customer.getBindCpid()!=null) {
            throw new CustomerException("此客户已绑定cLMS企业，不能删除！");
        }

        return null;
    }



    @ApiOperation("客户联系人列表")
    @RequestMapping(value = "/customerContactList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_contact_list')")
    public CustomerListResultDto customerContactList(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId,
                                                     @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                                     @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("customerId", customerId); //客户ID
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        PageInfo pageInfo = customerService.customerContactList(map);
        CustomerListResultDto dto = new CustomerListResultDto();
        dto.setCustomerContactList(pageInfo.getList());
        dto.setTotal(pageInfo.getTotal());
        return dto;
    }



    /***
     * 客户联系人默认状态修改
     * @param contactId
     * @param isDefault
     * @return
     */
    @ApiOperation("客户联系人默认状态修改")
    @RequestMapping(value = "/customerContactIsDefault",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_contact_is_default')")
    public String customerContactIsDefault(@ApiParam(value = "客户联系人ID",required = true) @RequestParam Long contactId,
                                     @ApiParam(value = "状态(1-设置默认，0-取消默认)",required = true) @RequestParam short isDefault) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        CustomerContact customerContact = customerService.customerContactDetail(contactId);
        if (customerContact==null) {
            throw new CustomerContactException("联系人不存在！");
        }
        customerContact.setIsDefault(isDefault);
        Integer flag = customerService.updateCustomerContact(customerContact);
        JSONObject jsonObject = new JSONObject();
        String message = null;
        int code = -1;
        if (flag>0) {
            if(isDefault==0) { //停止
            } else { //启动
                //此联系人成为默认，原来的默认联系人要取消
                CustomerContact vo = new CustomerContact();
                vo.setCompanyId(companyId);
                vo.setContactId(customerContact.getContactId());
                customerService.oldCustomerContactIsNull(vo);
                message = "已启用！";
            }
            code = 0;
        } else {
            message = "操作失败，请重试！";
        }
        jsonObject.put("message",message);
        jsonObject.put("code",code);
        return jsonObject.toString();
    }

    /**
     * 新增客户联系人
     *
     * @return
     */
    @ApiOperation("新增客户联系人")
    @RequestMapping(value = "/customerContactAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_contact_add')")
    public CustomerContact customerContactAdd(@Validated CustomerContactParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        CustomerContact vo = new CustomerContact();
        BeanUtils.copyProperties(vo, dto);
        vo.setCompanyId(companyId);
        customerService.addCustomerContact(vo);
        return vo;
    }


    /**
     * 编辑客户联系人
     *
     * @return
     */
    @ApiOperation("编辑客户联系人")
    @RequestMapping(value = "/customerContactEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_contact_add')")
    public CustomerContact customerContactEdit(@Validated CustomerContactParamsDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        CustomerContact vo = new CustomerContact();
        BeanUtils.copyProperties(vo, dto);
        vo.setCompanyId(companyId);
        customerService.updateCustomerContact(vo);
        return vo;
    }



    /**
     * 客户联系人删除
     * @param contactId
     * @return
     */
    @ApiOperation("客户联系人删除")
    @RequestMapping(value = "/customerContactRemove",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_contact_remove')")
    public String customerContactRemove(@ApiParam(value = "客户联系人ID",required = true) @RequestParam Long contactId) {
        int flag = customerService.customerContactRemove(contactId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag==1?"删除成功！":"删除失败！");
        jsonObject.put("code",flag==1?0:-1);
        return jsonObject.toString();
    }


}
