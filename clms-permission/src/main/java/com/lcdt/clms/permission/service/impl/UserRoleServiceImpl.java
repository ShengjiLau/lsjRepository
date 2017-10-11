package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.bean.Role;
import com.lcdt.clms.permission.model.WmsUserRole;
import com.lcdt.clms.permission.dao.WmsUserRoleMapper;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	WmsUserRoleMapper userRoleDao;

	@Transactional(readOnly = true)
	@Override
	public List<WmsUserRole> getUserRole(Integer userId, Long companyId) {
		List<WmsUserRole> wmsUserRoles = userRoleDao.selctRoleByUser(userId, companyId);
		return wmsUserRoles;
	}

	@Override
	public Role createCompanyRole(Long companyId, Role insertRole) {
		return null;
	}

}
