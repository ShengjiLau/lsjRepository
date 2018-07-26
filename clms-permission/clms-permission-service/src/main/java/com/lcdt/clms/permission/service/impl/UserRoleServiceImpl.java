package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.PermissionMapper;
import com.lcdt.clms.permission.dao.RoleMapper;
import com.lcdt.clms.permission.dao.RolePermissionMapper;
import com.lcdt.clms.permission.dao.RoleUserRelationMapper;
import com.lcdt.clms.permission.exception.EmployeeExistException;
import com.lcdt.clms.permission.exception.RoleExistException;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.model.RolePermission;
import com.lcdt.clms.permission.model.RoleUserRelation;
import com.lcdt.clms.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	PermissionMapper permissionMapper;

	@Transactional(rollbackFor = Exception.class)
	public void removeUserRole(Long userId,Long companyId){
		roleUserRelationDao.removeUserRoleRelation(userId,companyId);
	}

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
	public List<Role> getCompanyRole(Long companyId,Boolean valid) {
		List<Role> roles = userRoleDao.selectByCompanyId(companyId,valid);
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
		if(isExistEmployee(updatedRole)){
			throw new EmployeeExistException("有隶属与该角色的员工，不能停用！");
		}
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

	@Override
	public void addInitRole(Long compId) {
		Role cgRole = new Role();
		cgRole.setRoleName("采购经理");
		cgRole.setRoleCompanyId(compId);
		cgRole.setValid(true);
		userRoleDao.insert(cgRole);
		Role xsRole = new Role();
		xsRole.setRoleName("销售经理");
		xsRole.setRoleCompanyId(compId);
		xsRole.setValid(true);
		userRoleDao.insert(xsRole);
		Role ysRole = new Role();
		ysRole.setRoleName("运输经理");
		ysRole.setRoleCompanyId(compId);
		ysRole.setValid(true);
		userRoleDao.insert(ysRole);
		Role ccRole = new Role();
		ccRole.setRoleName("仓储经理");
		ccRole.setRoleCompanyId(compId);
		ccRole.setValid(true);
		userRoleDao.insert(ccRole);
		List<Permission> cgPermissions = permissionMapper.selectByCategory("purchase");
		if(cgPermissions!=null&&cgPermissions.size()>0)
		{
			Map map = new HashMap<>();
			map.put("role",cgRole.getRoleId());
			map.put("permissions",cgPermissions);
			rolePermissionDao.insertInitRole(map);
		}
		List<Permission> xsPermissions = permissionMapper.selectByCategory("sales");
		if(xsPermissions!=null&&xsPermissions.size()>0)
		{
			Map map = new HashMap<>();
			map.put("role",xsRole.getRoleId());
			map.put("permissions",xsPermissions);
			rolePermissionDao.insertInitRole(map);
		}
		List<Permission> ysPermissions = permissionMapper.selectByCategory("tms");
		if(ysPermissions!=null&&ysPermissions.size()>0)
		{
			Map map = new HashMap<>();
			map.put("role",ysRole.getRoleId());
			map.put("permissions",ysPermissions);
			rolePermissionDao.insertInitRole(map);
		}
		List<Permission> ccPermissions = permissionMapper.selectByCategory("warehouse");
		if(ccPermissions!=null&&ccPermissions.size()>0)
		{
			Map map = new HashMap<>();
			map.put("role",ccRole.getRoleId());
			map.put("permissions",ccPermissions);
			rolePermissionDao.insertInitRole(map);
		}
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
		if (roleIds != null && !CollectionUtils.isEmpty(roleIds)) {
			roleUserRelationDao.deleteNotInRoles(userId,companyId,roleIds);
		}

		//添加未设置的role
		List<RoleUserRelation> relations = roleUserRelationDao.selectByUserAndCompany(userId, companyId);
		ArrayList<Long> ids = new ArrayList<>();
		for (RoleUserRelation relation : relations) {
			ids.add(relation.getRoleId());
		}
		if (roleIds != null) {
			roleIds.removeAll(ids);
			if (!CollectionUtils.isEmpty(roleIds)) {
				roleUserRelationDao.insertRoles(roleIds,userId,companyId);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Role> userCompanyRole(Long userId, Long companyId) {
		return userRoleDao.selectUserCompanyRoles(userId, companyId);
	}

	/**
	 * 如果停用需要判断有没有员工，如果有，不能停用，如果没有，可以停用
	 * @param role
	 * @return
	 */
	private Boolean isExistEmployee(Role role){
		if(null != role.getValid() && !role.getValid()){
			List<RoleUserRelation> list=roleUserRelationDao.selectByRoleIdAndCompId(role.getRoleId(),role.getRoleCompanyId());
			if(null != list && !list.isEmpty()){
				return true;
			}
		}
		return false;
	}

}
