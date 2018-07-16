package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.permission.dto.PermissionParamsDto;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.service.PermissionService;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lyqishan on 2018/7/16
 */
@Api(value = "权限", description = "权限api")
@RestController
@RequestMapping("/api")

public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping(value = "/permission")
    @ApiOperation("权限新增")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public JSONObject addPermission(Permission permission) {
        int result = permissionService.addPermission(permission);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "添加失败");
        }
        return jsonObject;
    }

    @DeleteMapping(value = "/permission/{permissionId}")
    @ApiOperation("删除权限")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public JSONObject deletePermission(@PathVariable Integer permissionId){
        int result=permissionService.deletePermission(permissionId);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "删除失败");
        }
        return jsonObject;
    }

    @PatchMapping(value = "/permission")
    @ApiOperation("修改权限")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public JSONObject modifyPermission(Permission permission){
        int result=permissionService.modifyPermission(permission);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "修改失败");
        }
        return jsonObject;
    }

    @GetMapping(value = "/permission/{permissionId}")
    @ApiOperation("查询权限")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public Permission queryPermission(@PathVariable Integer permissionId){
        return permissionService.queryPermission(permissionId);
    }

    @GetMapping(value = "/permission")
    @ApiOperation("列表查询")
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('wh_out_order_cancel')")
    public PageResultDto queryPermissionList(PermissionParamsDto params){
        return new PageResultDto(permissionService.queryPermissionList(params).getList());
    }
}
