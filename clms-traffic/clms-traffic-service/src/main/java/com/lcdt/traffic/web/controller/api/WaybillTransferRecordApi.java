package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.service.WaybillTransferRecordService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.WaybillTransferRecordDto;
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
@RequestMapping("/api/waybilltransferrecord")
@Api(value = "运单换车记录", description = "运单换车记录接口")
public class WaybillTransferRecordApi {
    @Autowired
    private WaybillTransferRecordService waybillTransferRecordService;

    @ApiOperation("客户运单--换车记录--新增")
    @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_add')")
    public JSONObject addCustomerWaybillTransferRecord(WaybillTransferRecordDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User logidUser = SecurityInfoGetter.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setCreateId(logidUser.getUserId());
        dto.setCreateName(logidUser.getRealName());
        int result = waybillTransferRecordService.addWaybillTransferRecord(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("我的运单--换车记录--新增")
    @RequestMapping(value = "/own/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_add')")
    public JSONObject addOwnWaybillTransferRecord(WaybillTransferRecordDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User logidUser = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(logidUser.getUserId());
        dto.setCreateName(logidUser.getRealName());
        int result = waybillTransferRecordService.addWaybillTransferRecord(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("客户运单--换车记录--修改")
    @RequestMapping(value = "/customer/modify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_modify')")
    public JSONObject modifyCustomerWaybillTransferRecord(WaybillTransferRecordDto dto){
        Long companyId=SecurityInfoGetter.getCompanyId();
        User loginUser=SecurityInfoGetter.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result=waybillTransferRecordService.modifyWaybillTransferRecord(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("我的运单--换车记录--修改")
    @RequestMapping(value = "/own/modify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_modify')")
    public JSONObject modifyOwnWaybillTransferRecord(WaybillTransferRecordDto dto){
        Long companyId=SecurityInfoGetter.getCompanyId();
        User loginUser=SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setUpdateId(loginUser.getUserId());
        dto.setUpdateName(loginUser.getRealName());
        int result=waybillTransferRecordService.modifyWaybillTransferRecord(dto);
        if(result>0){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
            return jsonObject;
        }else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("客户运单--换车记录--查询")
    @RequestMapping(value = "/customer/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_query')")
    public WaybillTransferRecord queryCustomerWaybillTransferRecord(@ApiParam(value = "换车记录id", required = true) @RequestParam Long recordId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        WaybillTransferRecordDto waybillTransferRecordDto = new WaybillTransferRecordDto();
        waybillTransferRecordDto.setCarrierCompanyId(companyId);
        waybillTransferRecordDto.setId(recordId);
        return waybillTransferRecordService.queryWaybillTransferRecord(waybillTransferRecordDto);
    }

    @ApiOperation("我的运单--换车记录--查询")
    @RequestMapping(value = "/own/query", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_query')")
    public WaybillTransferRecord queryOwnWaybillTransferRecord(@ApiParam(value = "换车记录id", required = true) @RequestParam Long recordId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        WaybillTransferRecordDto waybillTransferRecordDto = new WaybillTransferRecordDto();
        waybillTransferRecordDto.setCompanyId(companyId);
        waybillTransferRecordDto.setId(recordId);
        return waybillTransferRecordService.queryWaybillTransferRecord(waybillTransferRecordDto);
    }

    @ApiOperation("客户运单--换车记录--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_list')")
    public PageBaseDto<List<WaybillTransferRecord>> customerWaybillTransferRecordList(WaybillTransferRecordDto dto,
                                                                   @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                   @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setIsDeleted((short)0);
        dto.setCarrierCompanyId(companyId);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        PageInfo<List<WaybillTransferRecord>> listPageInfo = waybillTransferRecordService.queryWaybillTransferRecordList(dto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }
    @ApiOperation("我的运单--换车记录--列表")
    @RequestMapping(value = "/own/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_waybilltransferrecord_list')")
    public PageBaseDto<List<WaybillTransferRecord>> ownWaybillTransferRecordList(WaybillTransferRecordDto dto,
                                                                              @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                              @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User loginUser = SecurityInfoGetter.getUser();
        dto.setIsDeleted((short)0);
        dto.setCompanyId(companyId);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        PageInfo<List<WaybillTransferRecord>> listPageInfo = waybillTransferRecordService.queryWaybillTransferRecordList(dto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }
}
