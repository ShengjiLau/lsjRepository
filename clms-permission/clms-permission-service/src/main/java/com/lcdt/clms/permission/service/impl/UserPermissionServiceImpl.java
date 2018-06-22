package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.AdminPermissionRelationMapper;
import com.lcdt.clms.permission.dao.PermissionMapper;
import com.lcdt.clms.permission.dao.RolePermissionMapper;
import com.lcdt.clms.permission.model.*;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ss on 2017/10/12.
 */
@com.alibaba.dubbo.config.annotation.Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	RolePermissionMapper rolePermissionDao;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	PermissionMapper permissionDao;


	@Autowired
	AdminPermissionRelationMapper adminPermissionRelationMapper;


	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public List<Permission> adminPermission(Long userId){
		List<AdminPermissionRelation> adminPermissionRelations = adminPermissionRelationMapper.selectByUserId(userId);
		ArrayList<Permission> permissions = new ArrayList<>();
		for (AdminPermissionRelation adminPermissionRelation : adminPermissionRelations) {
			AdminPermission adminPermission = adminPermissionRelation.getAdminPermission();
			Permission permission = new Permission();
			permission.setPermissionCode(adminPermission.getAdminPermissionCode());
			permission.setPermissionId(Integer.valueOf(adminPermission.getAdminPermissionId().toString()));
			permissions.add(permission);
		}
		return permissions;
	}



	@Transactional
	public List<Permission> rolePermissions(){
		return null;
	}


	@Transactional(readOnly = true)
	@Override
	public List<Permission> getAllPermissionInfo(){
		List<Permission> permissions = permissionDao.selectAll();
		return permissions;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Permission> getPermissionByCategory(String category) {
		List<Permission> permissions = permissionDao.selectByCategory(category);
		return permissions;
	}

	/**
	 * 获取用户权限
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Permission> userPermissions(Long userId, Long companyId) {
		List<Role> userRole = userRoleService.getUserRole(userId, companyId);
		if (userRole == null || userRole.isEmpty()) {
			return null;
		}
		ArrayList<Permission> permissions = new ArrayList<>();
		for (Role role : userRole) {
			List<Permission> pers = selectRolePermissions(role.getRoleId());
			if (pers != null) {
				permissions.addAll(pers);
			}
		}
		return permissions;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SysRole> userSysRoles(Long userId, Long companyId) {
		return sysRoleService.userSystemRole(userId, companyId);
	}

	@Transactional(readOnly = true)
	public List<Permission> selectRolePermissions(Long roleId) {
		List<Permission> rolePermissions = rolePermissionDao.selectByRoleId(roleId);
		return rolePermissions;
	}

	@Override
	public int insertAdminPermissions(AdminPermissionRelation relation) {
		adminPermissionRelationMapper.delete(relation);
		return adminPermissionRelationMapper.insertBatch(relation);
	}
}
