package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.service.WaybillService;
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

    @ApiOperation("我的运单--新增")
    @RequestMapping(value = "/own/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_add')")
    public JSONObject addOwnWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCompanyId(companyId);
        int result = waybillService.addWaybill(dto);
        if (result > 0) {
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_add')")
    public JSONObject addCustomerWaybill(WaybillDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setCarrierCompanyId(companyId);
        int result = waybillService.addWaybill(dto);
        if (result > 0) {
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_modify')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_modify')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_query')")
    public Waybill queryOwnWaybill(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return waybillService.queryOwnWaybill(waybillId, companyId);
    }

    @ApiOperation("客户运单--运单")
    @RequestMapping(value = "/customer/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_query')")
    public Waybill queryCustomerWaybill(@ApiParam(value = "运单id", required = true) @RequestParam Long waybillId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return waybillService.queryCustomerWaybill(waybillId, companyId);
    }

    @ApiOperation("我的运单--列表")
    @RequestMapping(value = "/own/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_list')")
    public PageBaseDto<List<Waybill>> ownWaybillList(WaybillOwnListParamsDto dto,
                                                     @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                     @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (dto.getGroupId()!=null&&dto.getGroupId()>0) {//传业务组，查这个组帮定的客户
            sb.append(" find_in_set('"+dto.getGroupId()+"',group_id)"); //项目组id
        } else {
            //没传组，查这个用户所有组帮定的客户

            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null&&groupList.size()>0){
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_id)"); //所有项目组ids
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }

        }
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        map.put("groupIds",sb.toString());
        PageInfo<List<Waybill>> listPageInfo = waybillService.queryOwnWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("客户运单--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_list')")
    public PageBaseDto<List<Waybill>> customerWaybilllist(WaybillCustListParamsDto dto,
                                                          @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                          @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();

        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (dto.getGroupId()!=null&&dto.getGroupId()>0) {//传业务组，查这个组帮定的客户
            sb.append(" find_in_set('"+dto.getGroupId()+"',group_ids)"); //客户表
        } else {
            //没传组，查这个用户所有组帮定的客户

            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null&&groupList.size()>0){
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }

        }
        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("isDeleted",0);
        map.put("groupIds",sb.toString());
        PageInfo<List<Waybill>> listPageInfo = waybillService.queryCustomerWaybillList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("我的运单--修改状态")
    @RequestMapping(value = "/own/modifystatus", method = RequestMethod.POST)
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
}
