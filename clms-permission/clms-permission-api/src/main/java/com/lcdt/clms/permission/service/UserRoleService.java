package com.lcdt.clms.permission.service;


import com.lcdt.clms.permission.model.Role;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public interface UserRoleService {


	Role selectById(Long roleId);

	List<Role> getUserRole(Long userId, Long companyId);

	List<Role> getCompanyRole(Long companyId);

	Role createCompanyRole(Long companyId, Role insertRole);

	boolean removeCompanyRole(Long roleId);

	Role updateCompanyRole(Role updatedRole);

	void addRolePermission(Long roleId, Long addedPermission);

	void removeRolePermission(Long roleId, Long permissionId);

	void setCompanyUserRole(Long userId, Long companyId, List<Long> roleId);

	void updateCompanyUserRole(Long userId,Long companyId,List<Long> roleIds);
}