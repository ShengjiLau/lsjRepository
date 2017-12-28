package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.WaybillLeaveMsg;
import com.lcdt.traffic.service.WaybillLeaveMsgService;
import com.lcdt.traffic.service.WaybillService;
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

    @ApiOperation("运单留言--新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_add')")
    public JSONObject addWaybillLeaveMsg(WaybillLeaveMsgDto dto){
        Long companyId= SecurityInfoGetter.getCompanyId();
        User loginUser=SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        int result=waybillLeaveMsgService.addWaybillLeaveMsg(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","添加成功");
            return jsonObject;
        }else {
            throw new RuntimeException("添加失败");
        }
    }
    @ApiOperation("运单留言--修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_modify')")
    public JSONObject modifyWaybillLeaveMsg(WaybillLeaveMsgDto dto){
        Long companyId=SecurityInfoGetter.getCompanyId();
        User loginUser=SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result=waybillLeaveMsgService.modifyWaybillLeaveMsg(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else {
            throw new RuntimeException("修改失败");
        }
    }
    @ApiOperation("运单留言--留言")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_query')")
    public WaybillLeaveMsg queryWaybillLeaveMsg(@ApiParam(value = "运单id", required = true) @RequestParam Long msgId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        return waybillLeaveMsgService.queryWaybillLeaveMsg(msgId,companyId);
    }

    @ApiOperation("我的运单--列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybill_leave_msg_list')")
    public PageBaseDto<List<WaybillLeaveMsg>> waybillLeaveMsgList(WaybillLeaveMsgDto dto,
                                                             @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                             @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        PageInfo<List<WaybillLeaveMsg>> listPageInfo = waybillLeaveMsgService.queryWaybillLeaveMsgList(dto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }
}
