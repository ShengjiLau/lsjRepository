package com.lcdt.userinfo.web.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.dto.AdminPermissionQueryDto;
import com.lcdt.clms.permission.model.AdminPermission;
import com.lcdt.clms.permission.model.AdminPermissionRelation;
import com.lcdt.clms.permission.service.AdminPermissionService;
import com.lcdt.clms.permission.service.UserPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */
@Api(value = "字典api",description = "字典api")
@RestController
@RequestMapping("/adminPermission")
public class AdminPermissionApi {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AdminPermissionService adminPermissionService;

    @Autowired
    UserPermissionService userPermissionService;

    @ApiOperation("权限列表")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public JSONObject list(@RequestBody AdminPermissionQueryDto dto){
        JSONObject jo = new JSONObject();
        try {
            jo.put("code",0);
            if(dto.getPageNo() != null){
                PageInfo<AdminPermission> pageInfo = PageHelper.startPage(dto.getPageNo(),dto.getPageSize()).doSelectPageInfo(()->adminPermissionService.selectAll(dto));
                jo.put("data",pageInfo);
            }else{
                jo.put("data",adminPermissionService.selectAll(dto));
            }

        }catch(Exception e){
            jo.put("code",-1);
            jo.put("message","查询列表出错");
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("权限添加")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JSONObject add(AdminPermission dic){
        JSONObject jo = new JSONObject();
        try {
            boolean result = false;
            if(dic.getAdminPermissionId() != null){
                result = adminPermissionService.updateByPrimaryKey(dic)>0;
            }else{
                result = adminPermissionService.insert(dic)>0;
            }
            if(result){
                jo.put("code",0);
            }else{
                jo.put("code",-1);
                jo.put("message","操作失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("权限删除")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public JSONObject del(Long dictionaryId){
        JSONObject jo = new JSONObject();
        try {
            boolean result = false;
            result = adminPermissionService.deleteByPrimaryKey(dictionaryId)>0;
            if(result){
                jo.put("code",0);
            }else{
                jo.put("code",-1);
                jo.put("message","操作失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("账号权限关系列表")
    @RequestMapping(value = "/accountPermissionList",method = RequestMethod.POST)
    public JSONObject accountPermissionList(Long userId){
        JSONObject jo = new JSONObject();
        try {
            jo.put("code",0);

            jo.put("data",userPermissionService.adminPermission(userId));

        }catch(Exception e){
            jo.put("code",-1);
            jo.put("message","查询权限关系列表出错");
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }

    @ApiOperation("设置账号权限")
    @RequestMapping(value = "/permissionSet",method = RequestMethod.POST)
    public JSONObject permissionSet(@RequestBody AdminPermissionRelation relation){
        JSONObject jo = new JSONObject();
        try {
            boolean result = false;
            if(relation.getUserId() != null){

                userPermissionService.insertAdminPermissions(relation);
                jo.put("code",0);
            }else{
                jo.put("code",-2);
                jo.put("message","参数不正确！");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return jo;
    }
}
