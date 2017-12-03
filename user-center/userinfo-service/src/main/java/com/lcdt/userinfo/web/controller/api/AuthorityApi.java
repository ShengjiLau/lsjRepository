package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.web.dto.CreateRoleDto;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

	@ApiOperation("获取所有已定义的权限")
	@RequestMapping(value = "allpermissions",method = RequestMethod.GET)
	public PageResultDto<Permission> getAllPermissions(@RequestParam(required = false) Integer pageNo){
		if (pageNo != null) {
			PageHelper.startPage(pageNo, 10);
			List<Permission> allPermissionInfo = permissionService.getAllPermissionInfo();
			return new PageResultDto(allPermissionInfo);
		}else{
			List<Permission> allPermissionInfo = permissionService.getAllPermissionInfo();
			return new PageResultDto(allPermissionInfo);
		}
	}


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
	public PageResultDto<Role> getCompanyRole(@ApiParam(required = true) Integer pageNo, @ApiParam(required = true) Integer pageSize) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		PageHelper.startPage(pageNo, pageSize);
		List<Role> companyRole = roleService.getCompanyRole(companyId);
		PageResultDto pageResultDto = new PageResultDto(companyRole);
		return pageResultDto;
	}


	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	@ApiOperation("添加角色")
	public Role addRole(@Validated CreateRoleDto roleDto) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		User loginUser = SecurityInfoGetter.getUser();
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		role.setRoleCompanyId(companyId);
		role.setCreateId(loginUser.getUserId());
		role.setCreateDate(new Date());
		role.setCreateName(loginUser.getRealName());
		Role companyRole = roleService.createCompanyRole(companyId, role);
		return companyRole;
	}

	static EmptyResponseDate emptyResponseData = new EmptyResponseDate();

	@RequestMapping(value = "/removerole", method = RequestMethod.POST)
	@ApiOperation("删除角色")
	public String removeRole(Long roleId) {
		boolean result = roleService.removeCompanyRole(roleId);
		JSONObject jsonObject = new JSONObject();
		if (!result) {
			jsonObject.put("code", -1);
			jsonObject.put("message","存在用户设置为该角色，请先修改用户角色后删除");
		}
		else{
			jsonObject.put("code", 0);
			jsonObject.put("messgae","操作成功");
		}


		return jsonObject.toString();
	}

	@RequestMapping(value = "/addRolePermission", method = RequestMethod.POST)
	@ApiOperation("增加角色权限")
	public EmptyResponseDate addPermissionForRole(Long roleId, Long permissionId) {
		User loginUser = SecurityInfoGetter.getUser();
		roleService.addRolePermission(roleId, permissionId,loginUser.getUserId(),loginUser.getRealName());
		return emptyResponseData;
	}


	@RequestMapping(value = "/updateRolePermission", method = RequestMethod.POST)
	@ApiOperation("批量编辑角色权限")
	public String addPermissionsForRole(Long roleId, HttpServletRequest request) {
		String permissions = request.getParameter("permissions");
		List<Long> permissionlist = JSONArray.parseArray(permissions, Long.class);
		roleService.updateRolePermissions(roleId, permissionlist);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 0);
		jsonObject.put("messgae", "操作成功");
		return jsonObject.toJSONString();
	}


	@RequestMapping(value = "/removeRolePermission", method = RequestMethod.POST)
	@ApiOperation("删除角色权限")
	public EmptyResponseDate removePermissionRole(Long roleId, Long permissionId) {
		roleService.removeRolePermission(roleId, permissionId);
		return emptyResponseData;
	}

	static class EmptyResponseDate implements ResponseData{
		String result = "请求成功";
	}

}
