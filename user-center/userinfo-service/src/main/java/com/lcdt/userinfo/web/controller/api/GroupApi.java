package com.lcdt.userinfo.web.controller.api;

/**
 * Created by yangbinq on 2017/11/13.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.exception.GroupExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.web.dto.GroupDto;
import com.lcdt.userinfo.web.dto.GroupResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/9.
 */
@Api(value = "业务项目组",description = "业务项目组api")
@RestController
@RequestMapping("/api/group")
public class GroupApi {

    @Autowired
    private GroupManageService groupManageService;

    /**
     * 部门项目组
     *
     * @return
     */
    @ApiOperation("新增项目组")
    @RequestMapping(value = "/groupAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_add')")
    public Group groupAdd(@Validated GroupDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Group group = new Group();
        group.setCompanyId(companyId);
        group.setGroupName(dto.getGroupName());
        group.setCreatTime(new Date());
        group.setIsValid(dto.getIsValid());

        group.setGroupRemark(dto.getGroupRemark());
        try {
            groupManageService.createGroup(group);
        } catch (GroupExistException e) {
            throw new GroupExistException(e.getMessage());
        }
        return group;
    }

    /**
     * 部门项目组
     *
     * @return
     */
    @ApiOperation("项目组编辑")
    @RequestMapping(value = "/groupEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_edit')")
    public Group groupEdit(@ApiParam(value = "项目组ID",required = true) @RequestParam Long groupId,
                           @ApiParam(value = "项目组名称",required = true) @RequestParam String groupName,
                           @ApiParam(value = "状态",required = true) @RequestParam Boolean isValid) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Group group = new Group();
        group.setGroupId(groupId);
        group.setCompanyId(companyId);
        group.setGroupName(groupName);
        group.setIsValid(isValid);
        try {
            groupManageService.modifyGroup(group);
        } catch (GroupExistException e) {
            throw new GroupExistException(e.getMessage());
        }
        return group;
    }


    /**
     * 组移除
     *
     * @return
     */
    @ApiOperation("项目组移除")
    @RequestMapping(value = "/groupRemove",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_remove')")
    @ResponseBody
    public String deptRemove(@ApiParam(value = "项目组ID",required = true) @RequestParam Long groupId) {
        boolean flag = false;
        try {
            Group group = new Group();
            group.setGroupId(groupId);
            flag = groupManageService.deleteGroup(group);
        } catch (GroupExistException e) {
            throw new GroupExistException(e.getMessage());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag?"删除成功！":"删除失败！");
        jsonObject.put("code",flag?0:-1);
        return jsonObject.toString();
    }



    /**
     * 项目组列表
     *
     * @return
     */
    @ApiOperation("项目组列表")
    @RequestMapping(value = "/groupList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_list')")
    public GroupResultDto deptList(@ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                       @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId",companyId);
        map.put("page_no",pageNo);
        map.put("page_size",pageSize);
        map.put("deptPid",0); //获取一级栏目
        PageInfo pageInfo = groupManageService.groupList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }




}
