package com.lcdt.clms.permission.service.impl;

import com.lcdt.clms.permission.dao.SysRoleMapper;
import com.lcdt.clms.permission.dao.SysRoleUserCompanyMapper;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.model.SysRoleUserCompany;
import com.lcdt.clms.permission.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ss on 2017/10/23.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	SysRoleMapper sysRoleMapper;

	@Autowired
	SysRoleUserCompanyMapper sysRoleUserCompanyDao;

	@Override
	public List<SysRole> userSystemRole(Long userId, Long companyId) {
		List<SysRoleUserCompany> roles = sysRoleUserCompanyDao.selectByUserCompany(userId, companyId);
		List<SysRole> sysRoles = new ArrayList<>(1);
		for (SysRoleUserCompany company : roles) {
			Integer sysRoleId = company.getSysRoleId();
			SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysRoleId);
			sysRoles.add(sysRole);
		}
		return sysRoles;
	}

	/**
	 * 为用户设置系统角色
	 * @return
	 */
	@Override
	public SysRoleUserCompany addUserSysRole(SysRole role,Long userId,Long companyId){
		SysRoleUserCompany sysRoleUserCompany = new SysRoleUserCompany();
		sysRoleUserCompany.setCompanyId(companyId);
		sysRoleUserCompany.setUserId(userId);
		sysRoleUserCompany.setSysRoleId(role.getSysRoleId());
		sysRoleUserCompanyDao.insert(sysRoleUserCompany);
		return sysRoleUserCompany;
	}

	@Override
	public SysRole selectBySysRoleCode(String roleCode) {
		List<SysRole> sysRoles = sysRoleMapper.selectByRoleCode(roleCode);
		if (sysRoles == null || sysRoles.isEmpty()) {
			return null;
		}else{
			return sysRoles.get(0);
		}
	}

}
