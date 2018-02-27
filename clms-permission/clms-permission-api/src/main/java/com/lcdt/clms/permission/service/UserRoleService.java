package com.lcdt.clms.permission.service;


import com.lcdt.clms.permission.exception.RoleExistException;
import com.lcdt.clms.permission.model.Role;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public interface UserRoleService {

	void removeUserRole(Long userId,Long companyId);
	Role selectById(Long roleId);

	List<Role> getUserRole(Long userId, Long companyId);

	List<Role> getCompanyRole(Long companyId);

	Role createCompanyRole(Long companyId, Role insertRole) throws RoleExistException, RoleExistException;

	boolean removeCompanyRole(Long roleId);

	Role updateCompanyRole(Role updatedRole);

	void addRolePermission(Long roleId, Long addedPermission,Long createId,String createName);

	void removeRolePermission(Long roleId, Long permissionId);

	void setCompanyUserRole(Long userId, Long companyId, List<Long> roleId);

	void updateCompanyUserRole(Long userId,Long companyId,List<Long> roleIds);

	List<Role> userCompanyRole(Long userId, Long companyId);

	void updateRolePermissions(Long roleId, List<Long> permissions);

	boolean isRoleNameExist(String roleName,Long companyId);

}