package com.lcdt.clms.permission.service;


import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public interface UserRoleService {

	List<Role> getUserRole(Long userId, Long companyId);

	Role createCompanyRole(Long companyId, Role insertRole);

	Role updateCompanyRole(Role updatedRole);

	Role addRolePermission(Role originalRole, Permission addedPermission);


}