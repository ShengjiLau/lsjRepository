package com.lcdt.werehouse.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.web.dto.PageBaseDto;
import com.lcdt.util.WebProduces;
import com.lcdt.werehouse.entity.Warehouse;
import com.lcdt.werehouse.entity.WarehouseLinkman;
import com.lcdt.werehouse.entity.WarehouseLoc;
import com.lcdt.werehouse.service.WarehouseService;
import com.lcdt.werehouse.web.dto.WarehouseDto;
import com.lcdt.werehouse.web.dto.WarehouseLinkmanDto;
import com.lcdt.werehouse.web.dto.WarehouseLocDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONObject;
import com.lcdt.userinfo.model.User;
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
 * Created by yangbinq on 2018/1/10.
 */
@RestController
@RequestMapping("/api/warehouse")
@Api(value = "仓库api", description = "仓库操作")
public class WarehouseApi {

    @Autowired
    private WarehouseService warehouseService;

    public int initWarehouse(User user) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Warehouse dto = new Warehouse();
        dto.setWhName("默认仓库");
        dto.setWhType((short)0);
        dto.setPrincipal(user.getRealName());
        dto.setMobile(user.getPhone());
        dto.setWhStatus((short)0);
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateDate(new Date());
        dto.setIsDeleted((short)0);
        dto.setCompanyId(companyId);
        int result = warehouseService.addWarehouse(dto);
        return result;
    }

    @ApiOperation("仓库管理——列表")
    @RequestMapping(value = "/warehouseList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_list')")
    public PageBaseDto warehouseList(@Validated WarehouseDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if(StringUtility.isNotEmpty(dto.getWhName())){
            map.put("whName", dto.getWhName());
        }
        if (dto.getWhType()!=null) {
            map.put("whType",dto.getWhType());
        }
        if (dto.getWhStatus()!=null) {
            map.put("whStatus",dto.getWhStatus());
        }
        if (dto.getIsDeleted()!=null) {
            map.put("isDeleted",dto.getIsDeleted());
        }
        PageInfo pageInfo = warehouseService.warehouseList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }

    @ApiOperation("仓库管理——新增")
    @RequestMapping(value = "/addWarehouse", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_warehouse')")
    public JSONObject addWarehouse(@Validated Warehouse dto) {
        User user = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setWhStatus((short)0);
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateDate(new Date());
        dto.setIsDeleted((short)0);
        dto.setCompanyId(companyId);

        int result = warehouseService.addWarehouse(dto);
        if (result == 2) {//同时添加仓库及默认联系人
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("仓库管理——修改")
    @RequestMapping(value = "/modifyWarehouse", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse')")
    public JSONObject modifyWarehouse(@Validated Warehouse dto) {
        User user = SecurityInfoGetter.getUser();
        dto.setUpdateId(user.getUserId());
        dto.setUpdateName(user.getRealName());
        dto.setUpdateTime(new Date());
        dto.setIsDeleted((short)0);
        int result = warehouseService.modifyWarehouse(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("仓库管理——删除")
    @RequestMapping(value = "/deleteWarehouse", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delete_warehouse')")
    public JSONObject deleteWarehouse(@ApiParam(value = "仓库ID",required = true) @RequestParam Long whId) {
        int result = warehouseService.modifyWarehouseIsDelete(whId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("仓库管理——启用/禁用")
    @RequestMapping(value = "/modifyWarehouseWhStatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse_whStatus')")
    public JSONObject modifyWarehouseWhStatus(@ApiParam(value = "仓库ID",required = true) @RequestParam Long whId) {
        int result = warehouseService.modifyWarehouseWhStatus(whId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("仓库管理——联系人列表")
    @RequestMapping(value = "/warehouseLinkmanList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_linkman_list')")
    public PageBaseDto warehouseLinkmanList(@Validated WarehouseLinkmanDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);

        if (dto.getIsDeleted()!=null) {
            map.put("isDeleted",dto.getIsDeleted());
        }
        if (dto.getWhId()!=null) {
            map.put("whId",dto.getWhId());
        }
        PageInfo pageInfo = warehouseService.warehouseLinkManList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }

    @ApiOperation("仓库管理——联系人新增")
    @RequestMapping(value = "/addWarehouseLinkman", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_warehouse_linkman')")
    public JSONObject addWarehouseLinkman(@Validated WarehouseLinkman dto) {
        User user = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateDate(new Date());
        dto.setIsDefault((short)0);
        dto.setIsDeleted((short)0);
        dto.setCompanyId(companyId);
        int result = warehouseService.addWarehouseLinkMan(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("仓库管理——联系人修改")
    @RequestMapping(value = "/modifyWarehouseLinkman", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse_linkman')")
    public JSONObject modifyWarehouseLinkman(@Validated WarehouseLinkman dto) {
        User user = SecurityInfoGetter.getUser();
        dto.setUpdateId(user.getUserId());
        dto.setUpdateName(user.getRealName());
        dto.setUpdateTime(new Date());
        dto.setIsDeleted((short)0);
        int result = warehouseService.modifyWarehouseLinkMan(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("仓库管理——联系人删除")
    @RequestMapping(value = "/deleteWarehouseLinkman", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delete_warehouse_linkman')")
    public JSONObject deleteWarehouseLinkman(@ApiParam(value = "联系人ID",required = true) @RequestParam Long whLinkmanId) {
        int result = warehouseService.modifyWarehouseLinkManIsDelete(whLinkmanId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("仓库管理——设置/取消默认联系人")
    @RequestMapping(value = "/modifyWarehouseLinkmanIsDefault", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse_linkman_isDefault')")
    public JSONObject modifyWarehouseLinkmanIsDefault(@ApiParam(value = "联系人ID",required = true) @RequestParam Long whLinkmanId,
                                                        @ApiParam(value = "状态(1-设置默认，0-取消默认)",required = true) @RequestParam short isDefault) {
        WarehouseLinkman linkman = new WarehouseLinkman();
        linkman.setWhLinkmanId(whLinkmanId);
        linkman.setIsDefault(isDefault);
        int result = warehouseService.modifyWarehouseLinkManIsDefault(linkman);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", (isDefault==1?"设置":"取消")+"成功");
            return jsonObject;
        } else {
            throw new RuntimeException((isDefault==1?"设置":"取消")+"失败");
        }
    }

    @ApiOperation("库位管理——列表")
    @RequestMapping(value = "/warehouselocList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_loc_list')")
    public PageBaseDto warehouseLocList(@Validated WarehouseLocDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if(StringUtility.isNotEmpty(dto.getCode())){
            map.put("code", dto.getCode());
        }
        if (dto.getLocType()!=null) {
            map.put("locType",dto.getLocType());
        }
        if (dto.getWhId()!=null) {
            map.put("whId",dto.getWhId());
        }
        if (dto.getIsDeleted()!=null) {
            map.put("isDeleted",dto.getIsDeleted());
        }
        PageInfo pageInfo = warehouseService.warehouseLocList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }

    @ApiOperation("库位管理——新增")
    @RequestMapping(value = "/addWarehouseLoc", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_warehouse_loc')")
    public JSONObject addWarehouseLoc(@Validated WarehouseLoc dto) {
        User user = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse_loc')")
    public JSONObject modifyWarehouseLoc(@Validated WarehouseLoc dto) {
        User user = SecurityInfoGetter.getUser();
        dto.setUpdateId(user.getUserId());
        dto.setUpdateName(user.getRealName());
        dto.setUpdateTime(new Date());
        dto.setIsDeleted((short)0);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delete_warehouse_loc')")
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_warehouse_loc_status')")
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
