package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.driver.wechat.api.util.GroupIdsUtil;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lyqishan on 2018/3/19
 */
@RestController
@RequestMapping("/waybill")
@Api(value = "运单", description = "运单接口，我的运单，客户运单，司机运单")
public class WaybillApi {
    @Reference
    WaybillRpcService waybillRpcService;

    @ApiOperation("我的运单--新增")
    @RequestMapping(value = "/v1/own", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_add')")
    public JSONObject addOwnWaybill(WaybillDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setCarrierCompanyId(companyId);
        dto.setWaybillStatus((short)1);
        dto.setCreatePhone(loginUser.getPhone());
        Waybill result = waybillRpcService.addWaybill(dto);
        if (result != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("我的运单--列表")
    @RequestMapping(value = "/v1/own", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_list')")
    public PageBaseDto<List<Waybill>> ownWaybillList(WaybillOwnListParamsDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
        StringBuffer sb = new StringBuffer();
        dto.setCompanyId(companyId);
        dto.setIsDelete((short) 0);
        dto.setGroupIds(GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryOwnWaybillList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("我的运单--上传回单")
    @RequestMapping(value = "/v1/own/receipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyOwnWaybillReceipt(WaybillModifyReceiptDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        dto.setUpdatePhone(loginUser.getPhone());
        return waybillRpcService.modifyOwnWaybillReceipt(dto);

    }

    @ApiOperation("我的运单--修改状态")
    @RequestMapping(value = "/v1/own/status", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyOwnWaybillStatus(WaybillModifyStatusDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setUpdatePhone(loginUser.getPhone());
        dto.setCompanyId(companyId);

        return waybillRpcService.modifyOwnWaybillStatus(dto);

    }

    @ApiOperation("客户运单--列表")
    @RequestMapping(value = "/v1/customer", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybill_list')")
    public PageBaseDto<List<Waybill>> customerWaybilllist(WaybillCustListParamsDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        dto.setCompanyId(companyId);
        dto.setIsDelete((short) 0);
        dto.setGroupIds(GroupIdsUtil.getCustomerGroupIds(dto.getGroupId()));
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryCustomerWaybillList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("客户运单--修改状态")
    @RequestMapping(value = "/v1/customer/status", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_modify_status')")
    public Waybill modifyCustomerWaybillStatus(WaybillModifyStatusDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);
        dto.setUpdatePhone(loginUser.getPhone());

        return waybillRpcService.modifyCustomerWaybillStatus(dto);
    }

    @ApiOperation("客户运单--上传回单")
    @RequestMapping(value = "/v1/customer/receipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_modify_status')")
    public Waybill modifyCustomerReceipt(WaybillModifyReceiptDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setUpdatePhone(loginUser.getPhone());
        dto.setCarrierCompanyId(companyId);

        return waybillRpcService.modifyCustomerWaybillReceipt(dto);

    }

    @ApiOperation("司机运单--列表")
    @RequestMapping(value = "/driver/list", method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_list')")
    public PageBaseDto<List<Waybill>> driverWaybillList(DriverWaybillListParsmsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setDriverId(loginUser.getUserId());
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryDriverWaybillList(dto);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("司机运单--修改状态")
    @RequestMapping(value = "/driver/modifystatus", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyDriverWaybillStatus(DriverWaybillParamsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setDriverId(loginUser.getUserId());
        dto.setUpdatePhone(loginUser.getPhone());
        return waybillRpcService.modifyWaybillStatusByDriver(dto);

    }

    @ApiOperation("司机运单--上传电子回单")
    @RequestMapping(value = "/driver/modifyreceipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyDriverWaybillReceipt(DriverWaybillParamsDto dto) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setDriverId(loginUser.getUserId());
        dto.setUpdatePhone(loginUser.getPhone());
        return  waybillRpcService.modifyWaybillReceiptByDriver(dto);
    }

}
