package com.lcdt.userinfo.web.controller.api;

/**
 * Created by yangbinq on 2017/11/13.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.UserGroupRelationMapper;
import com.lcdt.userinfo.exception.GroupExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
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
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/9.
 */
@Api(value = "业务项目组", description = "业务项目组api")
@RestController
@RequestMapping("/api/group")
public class GroupApi {

    @Autowired
    private GroupManageService groupManageService;

    @Autowired
    private UserGroupRelationMapper userGroupRelationMapper;


    /**
     * 部门项目组
     *
     * @return
     */
    @ApiOperation("新增项目组")
    @RequestMapping(value = "/groupAdd", method = RequestMethod.POST)
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
    @RequestMapping(value = "/groupEdit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_edit')")
    public Group groupEdit(@ApiParam(value = "项目组ID", required = true) @RequestParam Long groupId,
                           @ApiParam(value = "项目组名称", required = true) @RequestParam String groupName,
                           @ApiParam(value = "状态", required = true) @RequestParam Boolean isValid) {
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
    @RequestMapping(value = "/groupRemove", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_remove')")
    @ResponseBody
    public String deptRemove(@ApiParam(value = "项目组ID", required = true) @RequestParam Long groupId) {
        boolean flag = false;
        Group group = new Group();
        group.setGroupId(groupId);
        flag = groupManageService.deleteGroup(group);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", flag ? "删除成功！" : "有员工或客户隶属该项目组不可删除！");
        jsonObject.put("code", flag ? 0 : -1);
        return jsonObject.toString();
    }


    /**
     * 项目组列表
     *
     * @return
     */
    @ApiOperation("项目组列表")
    @RequestMapping(value = "/groupList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_list')")
    public GroupResultDto groupList(@ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                    @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize,
                                    @ApiParam(value = "是否停用") @RequestParam(required = false) Boolean isValid) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        map.put("isValid",isValid);
        PageInfo pageInfo = groupManageService.groupList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 用户项目组列表
     *
     * @return
     */
    @ApiOperation("用户项目组列表")
    @RequestMapping(value = "/userGroupList", method = RequestMethod.GET)
/*    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_list')")*/
    public JSONObject deptUserList() {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        List<Group> groupsList = null;
        GroupResultDto rDto = new GroupResultDto();
/*        if (userCompRel.getIsCreate() == 1 ) { //企业者
            Map map = new HashMap();
            map.put("companyId", companyId);
            map.put("page_no", 1);
            map.put("page_size", 0);
            PageInfo pageInfo = groupManageService.groupList(map);
            groupsList = pageInfo.getList();
            rDto.setList(groupsList);
            rDto.setTotal(pageInfo.getTotal());
        } else { }*/
        groupsList = userGroupRelationMapper.selectByUserCompany(userId, companyId);
        rDto.setList(groupsList);
        rDto.setTotal(groupsList!=null?groupsList.size():0);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",rDto);
        jsonObject.put("code", 0);
        jsonObject.put("message","请求成功");

        return jsonObject;
    }


    /**
     * 组员工列表
     *
     * @return
     */
    @ApiOperation("组员工列表")
    @RequestMapping(value = "/groupUserList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_list')")
    public GroupResultDto groupUserList(@ApiParam(value = "组ID", required = true) @RequestParam Long groupId,
                                        @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                        @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("groupId", groupId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        PageInfo pageInfo = groupManageService.selectGroupUserList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setUserList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 组员工不存在列表
     * 组员工不存在，权限合并到组员工已存在的里面
     *
     * @return
     */
    @ApiOperation(value = "组员工不存在列表v1")
    @RequestMapping(value = "/groupUserNotList", method = RequestMethod.GET)
/*    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_not_list')")*/
    public GroupResultDto groupUserNotList(@ApiParam(value = "组ID", required = true) @RequestParam Long groupId,
                                           @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                           @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("groupIdNot", groupId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        PageInfo pageInfo = groupManageService.selectGroupUserList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setUserList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 组成员添加
     *@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_add')")
     * @return
     */
    @ApiOperation("组成员添加")
    @RequestMapping(value = "/groupUserAdd", method = RequestMethod.GET)

    public String groupUserAdd(@ApiParam(value = "员工ID", required = true) @RequestParam Long userId,
                               @ApiParam(value = "组ID", required = true) @RequestParam Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        UserGroupRelation vo = new UserGroupRelation();
        vo.setCompanyId(companyId);
        vo.setUserId(userId);
        vo.setGroupId(groupId);
        vo.setGroupCreateDate(new Date());
        Integer flag = groupManageService.groupUserAdd(vo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", flag == 1 ? "添加成功！" : "添加失败！");
        jsonObject.put("code", flag == 1 ? 0 : -1);
        return jsonObject.toString();
    }

    /**
     * 组成员删除
     *@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_user_delete')")
     * @return
     */
    @ApiOperation("组成员删除")
    @RequestMapping(value = "/groupUserdelete", method = RequestMethod.GET)

    public String groupUserdelete(@ApiParam(value = "员工ID", required = true) @RequestParam Long userId,
                                  @ApiParam(value = "组ID", required = true) @RequestParam Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        UserGroupRelation vo = new UserGroupRelation();
        vo.setCompanyId(companyId);
        vo.setUserId(userId);
        vo.setGroupId(groupId);
        Integer flag = groupManageService.groupUserDelete(vo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", flag == 1 ? "删除成功！" : "删除失败！");
        jsonObject.put("code", flag == 1 ? 0 : -1);
        return jsonObject.toString();
    }


    /**
     * 组-不存在客户列表
     * groupId>0是已存在客户列表，包含未添加的客户列表权限
     * @return
     */
    @ApiOperation("组-存在客户列表")
    @RequestMapping(value = "/groupCustomerList", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_list')")
    public GroupResultDto groupCustomerList(@ApiParam(value = "组ID", required = true) @RequestParam Long groupId,
                                            @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                            @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();

        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        if (groupId > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(" NOT find_in_set('" + groupId + "',group_ids)");
            map.put("groupIds", sb.toString());
        }

        map.put("page_size", pageSize);
        PageInfo pageInfo = groupManageService.selectGroupClientList(map);
        GroupResultDto rDto = new GroupResultDto();
        rDto.setCustomerList(pageInfo.getList());
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }


    /**
     * 组客户添加
     * @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_add')")
     * @return
     */
    @ApiOperation("组客户添加")
    @RequestMapping(value = "/groupCustomerAdd", method = RequestMethod.GET)

    public String groupCustomerAdd(@ApiParam(value = "客户ID", required = true) @RequestParam Long customerId,
                                   @ApiParam(value = "组ID", required = true) @RequestParam Long groupId,
                                   @ApiParam(value = "组名", required = true) @RequestParam String groupName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap<String, String>();
        map.put("companyId", companyId);
        map.put("customerId", customerId);
        map.put("groupId", groupId);
        map.put("groupName", groupName);

        Integer flag = groupManageService.groupCustomerAdd(map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", flag == 1 ? "添加成功！" : "添加失败！");
        jsonObject.put("code", flag == 1 ? 0 : -1);
        return jsonObject.toString();
    }


    /**
     * 组客户删除
     * @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('group_customer_delete')")
     * @return
     */
    @ApiOperation("组客户删除")
    @RequestMapping(value = "/groupCustomerDelete", method = RequestMethod.GET)

    public String groupCustomerDelete(@ApiParam(value = "客户ID", required = true) @RequestParam Long customerId,
                                      @ApiParam(value = "组ID", required = true) @RequestParam Long groupId,
                                      @ApiParam(value = "组名", required = true) @RequestParam String groupName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap<String, String>();
        map.put("companyId", companyId);
        map.put("customerId", customerId);
        map.put("groupId", groupId);
        map.put("groupName", groupName);
        Integer flag = groupManageService.groupCustomerdelete(map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", flag == 1 ? "删除成功！" : "删除失败！");
        jsonObject.put("code", flag == 1 ? 0 : -1);
        return jsonObject.toString();
    }


}
