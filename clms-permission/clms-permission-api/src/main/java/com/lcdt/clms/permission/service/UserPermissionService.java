package com.lcdt.clms.permission.service;

import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.SysRole;

import java.util.List;

/**
 * Created by ss on 2017/10/12.
 */
public interface UserPermissionService {

	List<Permission> userPermissions(Long userId, Long companyId);

	List<SysRole> userSysRoles(Long userId, Long companyId);

	List<Permission> getAllPermissionInfo();

	List<Permission> getPermissionByCategory(String category);

	List<Permission> adminPermission(Long userId);
}
