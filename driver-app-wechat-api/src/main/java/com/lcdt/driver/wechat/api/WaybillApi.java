package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/19
 */
@RestController
@RequestMapping("/waybill")
@Api(value = "运单", description = "运单接口，我的运单，客户运单，司机运单")
public class WaybillApi {
    @Reference
    WaybillRpcService waybillRpcService;


    @ApiOperation("我的运单--列表")
    @RequestMapping(value = "/own/list", method = RequestMethod.GET)
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

    @ApiOperation("客户运单--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
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

    @ApiOperation("我的运单--修改状态")
    @RequestMapping(value = "/own/modifystatus", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyOwnWaybillStatus(WaybillModifyStatusDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);

       return waybillRpcService.modifyOwnWaybillStatus(dto);

    }

    @ApiOperation("客户运单--修改状态")
    @RequestMapping(value = "/customer/modifystatus", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_modify_status')")
    public JSONObject modifyCustomerWaybillStatus(WaybillModifyStatusDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);

        int result = waybillRpcService.modifyCustomerWaybillStatus(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("我的运单--上传回单")
    @RequestMapping(value = "/own/modifyreceipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public Waybill modifyOwnWaybillReceipt(WaybillModifyReceiptDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);

        return waybillRpcService.modifyOwnWaybillReceipt(dto);

    }

    @ApiOperation("客户运单--上传回单")
    @RequestMapping(value = "/customer/modifyreceipt", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_modify_status')")
    public JSONObject modifyCustomerReceipt(WaybillModifyReceiptDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();

        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);

        int result = waybillRpcService.modifyCustomerWaybillReceipt(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
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
        return  waybillRpcService.modifyWaybillReceiptByDriver(dto);
    }

}
