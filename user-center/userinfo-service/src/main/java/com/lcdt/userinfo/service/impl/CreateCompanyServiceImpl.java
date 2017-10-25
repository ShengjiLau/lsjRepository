package com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.CreateCompanyService;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by ss on 2017/10/24.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CreateCompanyServiceImpl implements CreateCompanyService {

	public final String defaultGroupName = "默认业务组";
	public final String defaultGroupRemark = "公司默认业务组";

	public static final String ADMIN_ROLE_CODE = "role_sys_admin";

	@Autowired
	CompanyService companyService;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	GroupManageService groupManageService;

	@Autowired
	UserGroupService userGroupService;

	private SysRole sysAdminRole;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company createCompany(CompanyDto companyDto) throws CompanyExistException {
		Company company = companyService.createCompany(companyDto);
		//默认添加创建者为管理员权限
		sysRoleService.addUserSysRole(getSysAdminRole(), company.getCreateId(), company.getCompId());
		Group defaultCompanyGroup = createDefaultCompanyGroup(company);
		//创建者加入默认新建组
		addToGroup(defaultCompanyGroup, company.getCompId(), company.getCreateId());
		return company;
	}

	@Transactional(rollbackFor = Exception.class)
	private Group createDefaultCompanyGroup(Company company) {
		Group group = new Group();
		group.setGroupName(defaultGroupName);
		group.setCompanyId(company.getCompId());
		group.setCreatTime(new Date());
		group.setGroupRemark(defaultGroupRemark);
		group.setIsValid(true);
		groupManageService.createGroup(group);
		return group;
	}


	@Transactional(rollbackFor = Exception.class)
	private void addToGroup(Group group, Long userId, Long companyId) {
		UserGroupRelation userGroupRelation = userGroupService.addUserToGroup(companyId, userId, group);
	}


	private SysRole getSysAdminRole(){
		if (sysAdminRole != null) {
			return sysAdminRole;
		}else{
			SysRole sysRole = sysRoleService.selectBySysRoleCode(ADMIN_ROLE_CODE);
			return sysRole;
		}
	}

}
