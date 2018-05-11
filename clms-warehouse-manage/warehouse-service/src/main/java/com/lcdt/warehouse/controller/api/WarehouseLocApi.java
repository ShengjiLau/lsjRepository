package com.lcdt.warehouse.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.WebProduces;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.entity.WarehouseLoc;
import com.lcdt.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tl.commons.util.StringUtility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liz on 2018/5/7.
 */
@RestController
@RequestMapping("/api/warehouseLoc")
@Api(value = "库位api", description = "库位操作")
public class WarehouseLocApi {
    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation("库位管理——列表")
    @RequestMapping(value = "/warehouselocList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_list')")
    public PageBaseDto warehouseLocList(@Validated WarehouseLoc dto,
                                        @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                        @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isDeleted", (short)0);

        if(StringUtility.isNotEmpty(dto.getCode())){
            map.put("code", dto.getCode());
        }
        if (dto.getLocType()!=null) {
            map.put("locType",dto.getLocType());
        }
        if (dto.getWhId()!=null) {
            map.put("whId",dto.getWhId());
        }
        PageInfo pageInfo = warehouseService.warehouseLocList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }

    @ApiOperation("库位管理——新增")
    @RequestMapping(value = "/addWarehouseLoc", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_add')")
    public JSONObject addWarehouseLoc(@Validated WarehouseLoc dto) {
        User user = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setStatus((short)0);
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateDate(new Date());
        dto.setIsDeleted((short)0);
        dto.setCompanyId(companyId);
        int result = warehouseService.addWarehouseLoc(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("库位管理——修改")
    @RequestMapping(value = "/modifyWarehouseLoc", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_modify')")
    public JSONObject modifyWarehouseLoc(@Validated WarehouseLoc dto) {
        User user = SecurityInfoGetter.getUser();
        dto.setUpdateId(user.getUserId());
        dto.setUpdateName(user.getRealName());
        dto.setUpdateTime(new Date());
        int result = warehouseService.modifyWarehouseLoc(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("库位管理——删除")
    @RequestMapping(value = "/deleteWarehouseLoc", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_delete')")
    public JSONObject deleteWarehouseLoc(@ApiParam(value = "库位ID",required = true) @RequestParam Long whLocId) {
        int result = warehouseService.modifyWarehouseLocIsDelete(whLocId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("库位管理——启用/禁用")
    @RequestMapping(value = "/modifyWarehouseLocStatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_status_modify')")
    public JSONObject modifyWarehouseLocStatus(@ApiParam(value = "库位ID",required = true) @RequestParam Long whLocId) {
        int result = warehouseService.modifyWarehouseLocStatus(whLocId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }
}
