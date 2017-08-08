package com.lcdt.wms.permission.service.impl;

import com.lcdt.wms.permission.dao.WmsUserRoleMapper;
import com.lcdt.wms.permission.model.WmsUserRole;
import com.lcdt.wms.permission.service.UserRoleService;
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

}
