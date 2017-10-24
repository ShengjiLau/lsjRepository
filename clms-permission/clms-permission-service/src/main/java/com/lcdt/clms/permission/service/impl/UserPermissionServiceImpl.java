package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.RolePermissionMapper;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.model.RolePermission;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/10/12.
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	RolePermissionMapper rolePermissionDao;


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


	public List<Permission> selectRolePermissions(Long roleId) {
		List<Permission> rolePermissions = rolePermissionDao.selectByRoleId(roleId);
		return rolePermissions;
	}


}
