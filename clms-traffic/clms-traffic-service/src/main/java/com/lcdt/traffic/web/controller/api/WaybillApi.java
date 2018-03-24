package com.lcdt.traffic.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.quartz.rpc.QuartzRpc;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.GroupIdsUtil;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillCustListParamsDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillOwnListParamsDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.ClmsBeanUtil;
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
 * Created by lyqishan on 2017/12/21
 */
@RestController
@RequestMapping("/api/waybill")
@Api(value = "运单", description = "运单接口")
public class WaybillApi {
    @Autowired
    private WaybillService waybillService;

    @Reference
    private QuartzRpc quartzRpc;

    @Autowired
    private WaybillRpcService waybillRpcService;

    @ApiOperation("我的运单--新增")
    @RequestMapping(value = "/own/add", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_add')")
    public JSONObject addOwnWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        Waybill  result = waybillService.addWaybill(dto);
        if (result !=null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("客户运单--新增")
    @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_add')")
    public JSONObject addCustomerWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);
        Waybill  result = waybillService.addWaybill(dto);
        if (result !=null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("我的运单--修改")
    @RequestMapping(value = "/own/modify", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_modify')")
    public JSONObject modifyOwnWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        int result = waybillService.modifyOwnWaybill(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("客户运单--修改")
    @RequestMapping(value = "/customer/modify", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_modify')")
    public JSONObject modifyCustomerWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);
        int result = waybillService.modifyCustomerWaybill(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("我的运单--运单")
    @RequestMapping(value = "/own/query", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_query')")
    public Waybill queryOwnWaybill(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return waybillService.queryOwnWaybill(waybillId, companyId);
    }

    @ApiOperation("客户运单--运单")
    @RequestMapping(value = "/customer/query", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_query')")
    public Waybill queryCustomerWaybill(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return waybillService.queryCustomerWaybill(waybillId, companyId);
    }

    @ApiOperation("我的运单--列表")
    @RequestMapping(value = "/own/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_list')")
    public PageBaseDto<List<Waybill>> ownWaybillList(WaybillOwnListParamsDto dto,
                                                     @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                     @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        StringBuffer sb = new StringBuffer();
        Map map= ClmsBeanUtil.beanToMap(dto);

        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        map.put("groupIds",GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryOwnWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("客户运单--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybill_list')")
    public PageBaseDto<List<Waybill>> customerWaybilllist(WaybillCustListParamsDto dto,
                                                          @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                          @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();


        StringBuffer sb = new StringBuffer();
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        //组权限信息
        map.put("groupIds",GroupIdsUtil.getCustomerGroupIds(dto.getGroupId()));
        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryCustomerWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("我的运单--修改状态")
    @RequestMapping(value = "/own/modifystatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_modify_status')")
    public JSONObject modifyOwnWaybillStatus(@ApiParam(value = "状态", required = true) @RequestParam Short waybillStatus,
                                                     @ApiParam(value = "运单id字符串，多个 id 以 , 隔开", required = true) @RequestParam String waybillIds) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("waybillStatus",waybillStatus);
        map.put("waybillIds",waybillIds);
        map.put("updateId",loginUser.getUserId());
        map.put("updateName",loginUser.getRealName());
        map.put("companyId",companyId);
        int result=waybillService.modifyOwnWaybillStatus(map);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else{
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("客户运单--修改状态")
    @RequestMapping(value = "/customer/modifystatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_modify_status')")
    public JSONObject modifyCustomerWaybillStatus(@ApiParam(value = "状态", required = true) @RequestParam Short waybillStatus,
                                          @ApiParam(value = "运单id字符串，多个 id 以 , 隔开", required = true) @RequestParam String waybillIds) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("waybillStatus",waybillStatus);
        map.put("waybillIds",waybillIds);
        map.put("updateId",loginUser.getUserId());
        map.put("updateName",loginUser.getRealName());
        map.put("carrierCompanyId",companyId);
        int result=waybillService.modifyCustomerWaybillStatus(map);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else{
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("运单手动定时任务")
    @RequestMapping(value = "/timer", method = RequestMethod.GET)
    public JSONObject startPoistionTimer() {
        quartzRpc.startWaybillPositionTimer();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "开启成功");
        return jsonObject;
    }
}
