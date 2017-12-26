package com.lcdt.userinfo.web.controller.api;

/**
 * Created by yangbinq on 2017/11/13.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.exception.GroupExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;
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
        User loginUser = SecurityInfoGetter.getUser();
        Group group = new Group();
        group.setCompanyId(companyId);
        group.setGroupName(dto.getGroupName());
        group.setCreateId(loginUser.getUserId());
        group.setCreateName(loginUser.getRealName());
        group.setCreateDate(new Date());
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



    /**
     * 组员工列表
     *
     * @return
     */
    @ApiOperation("组员工列表")
    @RequestMapping(value = "/groupUserList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_list')")
    public GroupResultDto groupUserList(@ApiParam(value = "组ID",required = true) @RequestParam Long groupId,
                                    @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                   @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId",companyId);
        map.put("groupId",groupId);
        map.put("page_no",pageNo);
        map.put("page_size",pageSize);
        PageInfo pageInfo = groupManageService.selectGroupUserList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setUserList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 组成员添加
     *
     * @return
     */
    @ApiOperation("组成员添加")
    @RequestMapping(value = "/groupUserAdd",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_add')")
    public String groupUserAdd(@ApiParam(value = "员工ID",required = true) @RequestParam Long userId,
                                       @ApiParam(value = "组ID",required = true) @RequestParam Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        UserGroupRelation vo = new UserGroupRelation();
        vo.setCompanyId(companyId);
        vo.setUserId(userId);
        vo.setGroupId(groupId);
        Integer flag = groupManageService.groupUserAdd(vo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag==1?"添加成功！":"添加失败！");
        jsonObject.put("code",flag==1?0:-1);
        return jsonObject.toString();
    }

    /**
     * 组成员删除
     *
     * @return
     */
    @ApiOperation("组成员删除")
    @RequestMapping(value = "/groupUserdelete",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_delete')")
    public String groupUserdelete(@ApiParam(value = "员工ID",required = true) @RequestParam Long userId,
                                  @ApiParam(value = "组ID",required = true) @RequestParam Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        UserGroupRelation vo = new UserGroupRelation();
        vo.setCompanyId(companyId);
        vo.setUserId(userId);
        vo.setGroupId(groupId);
        Integer flag = groupManageService.groupUserDelete(vo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag==1?"删除成功！":"删除失败！");
        jsonObject.put("code",flag==1?0:-1);
        return jsonObject.toString();
    }



    /**
     * 组-不存在客户列表
     *
     * @return
     */
    @ApiOperation("组-不存在客户列表")
    @RequestMapping(value = "/groupCustomerList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_list')")
    public GroupResultDto groupCustomerList(@ApiParam(value = "组ID",required = true) @RequestParam Long groupId,
                                        @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                        @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId",companyId);
        map.put("page_no",pageNo);
        if (groupId>0) {
            StringBuffer sb = new StringBuffer();
            sb.append(" NOT find_in_set('"+groupId+"',group_ids)");
            map.put("groupIds", sb.toString());
        }

        map.put("page_size",pageSize);
        PageInfo pageInfo = groupManageService.selectGroupClientList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setCustomerList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 组客户添加
     *
     * @return
     */
    @ApiOperation("组客户添加")
    @RequestMapping(value = "/groupCustomerAdd",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_add')")
    public String groupCustomerAdd(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId,
                                   @ApiParam(value = "组ID",required = true) @RequestParam Long groupId,
                                   @ApiParam(value = "组名",required = true) @RequestParam String groupName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap<String,String>();
        map.put("companyId",companyId);
        map.put("customerId",customerId);
        map.put("groupId",groupId);
        map.put("groupName",groupName);

        Integer flag = groupManageService.groupCustomerAdd(map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag==1?"添加成功！":"添加失败！");
        jsonObject.put("code",flag==1?0:-1);
        return jsonObject.toString();
    }


    /**
     * 组客户删除
     *
     * @return
     */
    @ApiOperation("组客户删除")
    @RequestMapping(value = "/groupCustomerDelete",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_delete')")
    public String groupCustomerDelete(@ApiParam(value = "客户ID",required = true) @RequestParam Long customerId,
                                   @ApiParam(value = "组ID",required = true) @RequestParam Long groupId,
                                   @ApiParam(value = "组名",required = true) @RequestParam String groupName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap<String,String>();
        map.put("companyId",companyId);
        map.put("customerId",customerId);
        map.put("groupId",groupId);
        map.put("groupName",groupName);
        Integer flag = groupManageService.groupCustomerdelete(map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",flag==1?"删除成功！":"删除失败！");
        jsonObject.put("code",flag==1?0:-1);
        return jsonObject.toString();
    }




}
