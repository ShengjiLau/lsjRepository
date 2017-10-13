package com.lcdt.clms.permission.service;

import com.lcdt.clms.permission.model.Permission;

import java.util.List;

/**
 * Created by ss on 2017/10/12.
 */
public interface UserPermissionService {

	List<Permission> userPermissions(Long userId, Long companyId);

}
