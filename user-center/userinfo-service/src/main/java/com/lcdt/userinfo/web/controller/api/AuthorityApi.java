package com.lcdt.userinfo.web.controller.api;

import com.alibaba.dubbo.common.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.converter.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ss on 2017/11/13.
 */
@RestController
@RequestMapping("/api/authority")
@Api(value = "角色权限接口",description = "权限模块相关接口")
public class AuthorityApi {

	@Autowired
	UserPermissionService permissionService;

	@Autowired
	UserRoleService roleService;

	@RequestMapping(value = "/rolePermissons", method = RequestMethod.GET)
	@ApiOperation("获取某个角色下的权限信息")
	public ArrayListResponseWrapper<Permission> rolePermissions(Long roleId) {
		Role role = roleService.selectById(roleId);
		List<Permission> permissions = role.getPermissions();
		return new ArrayListResponseWrapper<Permission>(permissions);
	}


	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ApiOperation("获取某个分类中的权限")
	public ArrayListResponseWrapper<Permission> allAuthority(String category) {
		List<Permission> allPermissionInfo = permissionService.getPermissionByCategory(category);
		ArrayListResponseWrapper<Permission> permissions = new ArrayListResponseWrapper<>(allPermissionInfo);
		return permissions;
	}


	@RequestMapping(value = "/getcompanyRole", method = RequestMethod.GET)
	@ApiOperation("获取所有角色信息")
	public ArrayListResponseWrapper<Role> getCompanyRole(Integer pageNo) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		PageHelper.startPage(pageNo, 10);
		List<Role> companyRole = roleService.getCompanyRole(companyId);
		return new ArrayListResponseWrapper<Role>(companyRole);
	}


	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	@ApiOperation("添加角色")
	public Role addRole(Role role) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Role companyRole = roleService.createCompanyRole(companyId, role);
		return companyRole;
	}

	static EmptyResponseDate emptyResponseDate = new EmptyResponseDate();

	@RequestMapping(value = "/removerole", method = RequestMethod.POST)
	@ApiOperation("删除角色")
	public String removeRole(Long roleId) {
		boolean result = roleService.removeCompanyRole(roleId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", -1);
		jsonObject.put("message","存在用户设置为该角色，请先修改用户角色后删除");
		return jsonObject.toString();
	}

	@RequestMapping(value = "/addRolePermission", method = RequestMethod.POST)
	@ApiOperation("增加角色权限")
	public ResponseData addPermissionForRole(Long roleId, Long permissionId) {
		roleService.addRolePermission(roleId, permissionId);
		return emptyResponseDate;
	}

	@RequestMapping(value = "/removeRolePermission", method = RequestMethod.POST)
	@ApiOperation("删除角色权限")
	public ResponseData removePermissionRole(Long roleId, Long permissionId) {
		roleService.removeRolePermission(roleId, permissionId);
		return emptyResponseDate;
	}

	static class EmptyResponseDate implements ResponseData{


	}


}
