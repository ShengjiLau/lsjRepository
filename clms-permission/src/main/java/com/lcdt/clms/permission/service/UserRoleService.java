package com.lcdt.clms.permission.service;

import com.lcdt.clms.permission.bean.Role;
import com.lcdt.clms.permission.model.WmsUserRole;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public interface UserRoleService {

	List<WmsUserRole> getUserRole(Integer userId, Long companyId);

	Role createCompanyRole(Long companyId,Role insertRole);

}