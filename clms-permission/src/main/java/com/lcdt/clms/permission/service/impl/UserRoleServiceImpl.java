package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.RoleMapper;
import com.lcdt.clms.permission.dao.RoleUserRelationMapper;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.model.RoleUserRelation;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	RoleMapper userRoleDao;

	@Autowired
	RoleUserRelationMapper roleUserRelationDao;


	/**
	 * 获取用户角色
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Role> getUserRole(Long userId, Long companyId) {
		List<RoleUserRelation> relations = roleUserRelationDao.selectByUserAndCompany(userId, companyId);

		if (relations == null || relations.isEmpty()) {
			return null;
		}
		ArrayList<Role> roles = new ArrayList<>();
		for (RoleUserRelation relation : relations) {
			roles.add(relation.getRole());
		}

		return roles;
	}



	@Override
	public Role createCompanyRole(Long companyId, Role insertRole) {

		return null;
	}

	@Override
	public Role updateCompanyRole(Role updatedRole) {
		return null;
	}

	@Override
	public Role addRolePermission(Role originalRole, Permission addedPermission) {
		return null;
	}

}
