package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.WaybillPositionSetting;
import com.lcdt.traffic.service.WaybillPositionSettingService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillPositionSettingDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/1/16
 */
@RestController
@RequestMapping("/api/waybill/positionsetting")
@Api(value = "运单", description = "运单接口")
public class WaybillPositionSettingApi {
    @Autowired
    WaybillPositionSettingService waybillPositionSettingService;

    @ApiOperation("我的运单--定位设置--新增")
    @RequestMapping(value = "/own/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public JSONObject addOwnWaybillPositionSetting(WaybillPositionSettingDto dto) {
        return addWaybillPositionSetting(dto);
    }

    @ApiOperation("客户运单--定位设置--新增")
    @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybillpositionsetting')")
    public JSONObject addCustomerWaybillPositionSetting(WaybillPositionSettingDto dto) {
        return addWaybillPositionSetting(dto);
    }

    @ApiOperation("我的运单--定位设置--修改")
    @RequestMapping(value = "/own/modify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public JSONObject modifyOwnWaybillPositionSetting(WaybillPositionSettingDto dto) {
        return updateAddWaybillPositionSetting(dto);
    }

    @ApiOperation("客户运单--定位设置--修改")
    @RequestMapping(value = "/customer/modify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybillpositionsetting')")
    public JSONObject modifyCustomerWaybillPositionSetting(WaybillPositionSettingDto dto) {
        return updateAddWaybillPositionSetting(dto);
    }

    @ApiOperation("我的运单--定位设置--查询")
    @RequestMapping(value = "/own/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public PageBaseDto<List<WaybillPositionSetting>> queryOwnWaybillPositionSetting(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        return waybillPositionSettingList(waybillId);
    }

    @ApiOperation("客户运单--定位设置--查询")
    @RequestMapping(value = "/customer/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybillpositionsetting')")
    public PageBaseDto<List<WaybillPositionSetting>> queryCustomerWaybillPositionSetting(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        return waybillPositionSettingList(waybillId);
    }

    @ApiOperation("司机管理--定位设置--新增")
    @RequestMapping(value = "/driver/add", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public JSONObject addDriverPositionSetting(WaybillPositionSettingDto dto) {
        return addWaybillPositionSetting(dto);
    }

    @ApiOperation("司机管理--定位设置--修改")
    @RequestMapping(value = "/driver/modify", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public JSONObject modifyDriverPositionSetting(WaybillPositionSettingDto dto) {
        return updateAddWaybillPositionSetting(dto);
    }

    @ApiOperation("司机管理--定位设置--查询")
    @RequestMapping(value = "/driver/query", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybillpositionsetting')")
    public PageBaseDto<List<WaybillPositionSetting>> queryDriverPositionSetting(@ApiParam(value = "司机手机号", required = true) @RequestParam String driverPhone) {
        PageInfo<List<WaybillPositionSetting>> listPageInfo=waybillPositionSettingService.queryDriverPositionSettingList(driverPhone,SecurityInfoGetter.getCompanyId());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());;
        return pageBaseDto;
    }

    private JSONObject addWaybillPositionSetting(WaybillPositionSettingDto dto){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        int result = waybillPositionSettingService.addWaybillPositionSetting(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    private JSONObject updateAddWaybillPositionSetting(WaybillPositionSettingDto dto){
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        int result = waybillPositionSettingService.modifyWaybillPositionSetting(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    private PageBaseDto waybillPositionSettingList(Long waybillId){
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map=new HashMap();
        map.put("companyId",companyId);
        map.put("waybillId",waybillId);
        PageInfo<List<WaybillPositionSetting>> listPageInfo=waybillPositionSettingService.queryWaybillPositionSettingList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());;
        return pageBaseDto;
    }

}
