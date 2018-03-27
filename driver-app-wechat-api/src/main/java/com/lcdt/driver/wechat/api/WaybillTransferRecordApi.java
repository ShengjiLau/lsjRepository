package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.traffic.dto.WaybillTransferRecordDto;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.service.WaybillTransferRecordRpcService;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
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
 * Created by lyqishan on 2018/3/26
 */
@RestController
@RequestMapping("/api/waybilltransferrecord")
@Api(value = "运单换车记录", description = "运单换车记录接口")
public class WaybillTransferRecordApi {
    @Reference
    private WaybillTransferRecordRpcService waybillTransferRecordService;

    @ApiOperation("客户运单--换车记录--新增")
    @RequestMapping(value = "/customer/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybilltransferrecord')")
    public JSONObject addCustomerWaybillTransferRecord(WaybillTransferRecordDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
        dto.setCarrierCompanyId(companyId);
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setType(2);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybilltransferrecord')")
    public JSONObject addOwnWaybillTransferRecord(WaybillTransferRecordDto dto) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(loginUser.getUserId());
        dto.setCreateName(loginUser.getRealName());
        dto.setType(1);
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

    @ApiOperation("客户运单--换车记录--列表")
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_customer_waybilltransferrecord')")
    public PageBaseDto<List<WaybillTransferRecord>> customerWaybillTransferRecordList(WaybillTransferRecordDto dto,
                                                                                      @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                                      @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_own_waybilltransferrecord')")
    public PageBaseDto<List<WaybillTransferRecord>> ownWaybillTransferRecordList(WaybillTransferRecordDto dto,
                                                                                 @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                                                 @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        User loginUser = userCompRel.getUser();
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
