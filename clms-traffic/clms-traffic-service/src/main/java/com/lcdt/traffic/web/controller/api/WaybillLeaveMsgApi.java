package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.WaybillLeaveMsg;
import com.lcdt.traffic.service.WaybillLeaveMsgService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillLeaveMsgDto;
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

import java.util.List;

/**
 * Created by lyqishan on 2017/12/28
 */
@RestController
@RequestMapping("/api/waybillleavemsg")
@Api(value = "运单留言", description = "运单留言接口")
public class WaybillLeaveMsgApi {
    @Autowired
    private WaybillLeaveMsgService waybillLeaveMsgService;

    @ApiOperation("我的运单--留言--新增")
    @RequestMapping(value = "/own/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_leave_msg')")
    public JSONObject addOwnWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setLeaveMsgMan(loginUser.getRealName());
        int result = waybillLeaveMsgService.addWaybillLeaveMsg(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("我的运单--留言--修改")
    @RequestMapping(value = "/own/modify", method = RequestMethod.POST)
   // @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_modify')")
    public JSONObject modifyWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result = waybillLeaveMsgService.modifyWaybillLeaveMsg(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("我的运单--留言--根据id查询")
    @RequestMapping(value = "/own/query", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_query')")
    public WaybillLeaveMsg queryWaybillLeaveMsg(@ApiParam(value = "运单id", required = true) @RequestParam Long msgId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillLeaveMsgDto dto = new WaybillLeaveMsgDto();
        dto.setCompanyId(companyId);
        dto.setId(msgId);
        return waybillLeaveMsgService.queryWaybillLeaveMsg(dto);
    }

    @ApiOperation("我的运单--留言--列表")
    @RequestMapping(value = "/own/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybill_leave_msg')")
    public PageBaseDto<List<WaybillLeaveMsg>> waybillLeaveMsgList(WaybillLeaveMsgDto dto,
                                                                  @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                  @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short) 0);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        PageInfo<List<WaybillLeaveMsg>> listPageInfo = waybillLeaveMsgService.queryWaybillLeaveMsgList(dto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("客户运单--留言--新增")
    @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybill_leave_msg')")
    public JSONObject addCustomerWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        int result = waybillLeaveMsgService.addWaybillLeaveMsg(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("客户运单--留言--修改")
    @RequestMapping(value = "/customer/modify", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_modify')")
    public JSONObject modifyCustomerWaybillLeaveMsg(WaybillLeaveMsgDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result = waybillLeaveMsgService.modifyWaybillLeaveMsg(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("客户运单--留言--根据id查询")
    @RequestMapping(value = "/customer/query", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_query')")
    public WaybillLeaveMsg queryCustomerWaybillLeaveMsg(@ApiParam(value = "运单id", required = true) @RequestParam Long msgId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        WaybillLeaveMsgDto dto = new WaybillLeaveMsgDto();
        dto.setCarrierCompanyId(companyId);
        dto.setId(msgId);
        return waybillLeaveMsgService.queryWaybillLeaveMsg(dto);
    }

    @ApiOperation("客户运单--留言--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybill_leave_msg')")
    public PageBaseDto<List<WaybillLeaveMsg>> customerWaybillLeaveMsgList(WaybillLeaveMsgDto dto,
                                                                          @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                          @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setIsDeleted((short) 0);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        PageInfo<List<WaybillLeaveMsg>> listPageInfo = waybillLeaveMsgService.queryWaybillLeaveMsgList(dto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }
}
