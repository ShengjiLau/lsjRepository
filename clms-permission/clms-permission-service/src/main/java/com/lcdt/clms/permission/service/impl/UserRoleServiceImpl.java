package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.RoleMapper;
import com.lcdt.clms.permission.dao.RolePermissionMapper;
import com.lcdt.clms.permission.dao.RoleUserRelationMapper;
import com.lcdt.clms.permission.exception.RoleExistException;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.model.RolePermission;
import com.lcdt.clms.permission.model.RoleUserRelation;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by ss on 2017/8/8.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	RoleMapper userRoleDao;

	@Autowired
	RoleUserRelationMapper roleUserRelationDao;

	@Autowired
	RolePermissionMapper rolePermissionDao;

	@Transactional(readOnly = true,rollbackFor = Exception.class)
	@Override
	public Role selectById(Long roleId){
		return userRoleDao.selectByPrimaryKey(roleId);
	}

	/**
	 * 获取用户角色
	 * @param userId
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly = true,rollbackFor = Exception.class)
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

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Role> getCompanyRole(Long companyId) {
		List<Role> roles = userRoleDao.selectByCompanyId(companyId);
		return roles;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public Role createCompanyRole(Long companyId, Role insertRole) throws RoleExistException {
		List<Role> roles = userRoleDao.selectByRoleName(insertRole.getRoleName(), companyId);
		if (roles != null && roles.size() > 0) {
			throw new RoleExistException();
		} else {
			insertRole.setRoleCompanyId(companyId);
			userRoleDao.insert(insertRole);
			return insertRole;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean removeCompanyRole(Long roleId) {
		List<RoleUserRelation> roleUserRelations = roleUserRelationDao.selectByRoleId(roleId);
		if (roleUserRelations != null && !roleUserRelations.isEmpty()) {
			return false;
		}else{
			userRoleDao.deleteByPrimaryKey(roleId);
			return true;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Role updateCompanyRole(Role updatedRole) {
		userRoleDao.updateByPrimaryKey(updatedRole);
		return updatedRole;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addRolePermission(Long roleId, Long permissionId,Long createId,String createName) {
		List<RolePermission> rolePermissions = rolePermissionDao.selectByRolePermission(roleId, permissionId);
		if (rolePermissions != null && !rolePermissions.isEmpty()) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(permissionId);
			rolePermission.setRoleId(roleId);
			rolePermission.setCreateId(createId);
			rolePermission.setCreateName(createName);
			rolePermission.setCreateDate(new Date());
			rolePermissionDao.insert(rolePermission);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateRolePermissions(Long roleId, List<Long> permissions) {
		rolePermissionDao.deleteeRolePermissions(roleId);
		if (permissions != null && !permissions.isEmpty()) {
			rolePermissionDao.insertRolePermission(roleId,permissions);
		}
	}

	@Override
	public boolean isRoleNameExist(String roleName,Long companyId) {
		List<Role> roles = userRoleDao.selectByRoleName(roleName, companyId);
		return !CollectionUtils.isEmpty(roles);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeRolePermission(Long roleId, Long permissionId) {
		List<RolePermission> rolePermissions = rolePermissionDao.selectByRolePermission(roleId, permissionId);
		if (rolePermissions != null && rolePermissions.size() > 0) {
			for (RolePermission rolePermission : rolePermissions) {
				rolePermissionDao.deleteByPrimaryKey(rolePermission.getRolePermissionId());
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setCompanyUserRole(Long userId, Long companyId, List<Long> roleId) {
		if (roleId == null) {
			return;
		}
		for (Long id : roleId) {
			RoleUserRelation roleUserRelation = new RoleUserRelation();
			roleUserRelation.setUserId(userId);
			roleUserRelation.setCompanyId(companyId);
			roleUserRelation.setRoleId(id);
			roleUserRelation.setRelationCreateDate(new Date());
			roleUserRelationDao.insert(roleUserRelation);
		}
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateCompanyUserRole(Long userId,Long companyId,List<Long> roleIds) {
		//删除不在list中的已关联的role
		roleUserRelationDao.deleteNotInRoles(userId,companyId,roleIds);
		//添加未设置的role
		List<RoleUserRelation> relations = roleUserRelationDao.selectByUserAndCompany(userId, companyId);
		ArrayList<Long> ids = new ArrayList<>();
		for (RoleUserRelation relation : relations) {
			ids.add(relation.getRoleId());
		}
		if (roleIds != null) {
			roleIds.removeAll(ids);
			roleUserRelationDao.insertRoles(roleIds);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Role> userCompanyRole(Long userId, Long companyId) {
		return userRoleDao.selectUserCompanyRoles(userId, companyId);
	}

}
