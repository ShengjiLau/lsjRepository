package com.lcdt.clms.permission.service;

import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.model.SysRoleUserCompany;

import java.util.List;

/**
 * Created by ss on 2017/10/23.
 */
public interface SysRoleService {

	List<SysRole> userSystemRole(Long userId, Long companyId);

	SysRoleUserCompany addUserSysRole(SysRole role, Long userId, Long companyId);

	SysRole selectBySysRoleCode(String roleCode);

}
