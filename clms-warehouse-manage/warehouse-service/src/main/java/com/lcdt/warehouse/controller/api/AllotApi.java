package com.lcdt.warehouse.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.ClmsBeanUtil;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.service.AllotService;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by liz on 2018/5/8.
 */
@RestController
@RequestMapping("/api/allot")
@Api(value = "调拨api", description = "调拨操作")
public class AllotApi {
    @Autowired
    private AllotService allotService;
    @Reference
    CompanyServiceCountService companyServiceCountService;

    @ApiOperation(value = "调拨单列表", notes = "调拨单列表数据")
    @GetMapping("/allotList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_list')")
    public ResponseMessage allotList(@Validated AllotDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);

        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        Page<AllotDto> listPageInfo = allotService.allotDtoList(map);
        return JSONResponseUtil.success(listPageInfo);
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/addAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_add')")
    public JSONObject addAllot(@Validated @RequestBody AllotDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
//        int storageServiceCount = companyServiceCountService.companyProductCount(companyId, "storage_service");
//        if(storageServiceCount > 0) {
            User user = SecurityInfoGetter.getUser();
            dto.setAllotStatus((short) 1);//在途
            dto.setCreateId(user.getUserId());
            dto.setCreateName(user.getRealName());
            dto.setCreateTime(new Date());
            dto.setCompanyId(companyId);
            dto.setIsDeleted((short) 0);

            boolean result = allotService.addAllot(dto);
            if (result) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("message", "添加成功");
                return jsonObject;
            } else {
                throw new RuntimeException("添加失败");
            }
//        }else {
//            throw new RuntimeException("仓单服务余额不足");
//        }
    }

    @ApiOperation("编辑")
    @RequestMapping(value = "/modifyAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_modify')")
    public JSONObject modifyAllot(@Validated @RequestBody AllotDto dto) {
        boolean result = allotService.modifyAllot(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("取消")
    @RequestMapping(value = "/cancelAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_delete')")
    public JSONObject cancelAllot(@ApiParam(value = "调拨单id",required = true) @RequestParam Long allotId) {
        boolean result = allotService.cancelAllot(allotId);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消成功");
            return jsonObject;
        } else {
            throw new RuntimeException("取消失败");
        }
    }

    @ApiOperation("详情")
    @RequestMapping(value = "/allotDetail", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_detail')")
    public JSONObject allotDetail(@ApiParam(value = "调拨单id",required = true) @RequestParam Long allotId) {
        AllotDto dto = allotService.getAllotInfo(allotId);
        if (dto != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", dto);
            jsonObject.put("code", 0);
            jsonObject.put("message", "调拨单详情");
            return jsonObject;
        } else {
            throw new RuntimeException("获取失败");
        }
    }

    @ApiOperation("入库")
    @RequestMapping(value = "/allotPutInStorage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_put_in_storage')")
    public JSONObject allotPutInStorage(@Validated @RequestBody AllotDto dto) {
        boolean result = allotService.allotPutInStorage(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "入库成功");
            return jsonObject;
        } else {
            throw new RuntimeException("入库失败");
        }
    }
}
