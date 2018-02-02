package com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.*;
import com.lcdt.userinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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

	@Autowired
	DepartmentService departmentService;

	@Autowired
	UserService userService;

	@Autowired
	UserCompRelMapper userCompRelMapper;

	private SysRole sysAdminRole;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company createCompany(CompanyDto companyDto) throws CompanyExistException, DeptmentExistException {
		Company company = companyService.createCompany(companyDto);

		//默认添加创建者为管理员权限
		sysRoleService.addUserSysRole(getSysAdminRole(), company.getCreateId(), company.getCompId());
		Group defaultCompanyGroup = createDefaultCompanyGroup(company);
		//创建者加入默认新建组
		addToGroup(defaultCompanyGroup, company);

		List<UserCompRel> userCompRels = userCompRelMapper.selectByUserIdCompanyId(company.getCreateId(), company.getCompId());
		Department department = setUpDepartMent(company);
		if (CollectionUtils.isEmpty(userCompRels)) {

			UserCompRel userCompRel = new UserCompRel();
			userCompRel.setCompId(company.getCompId());
			userCompRel.setUserId(company.getCreateId());
			userCompRel.setIsCreate((short) 1);
			userCompRel.setDeptIds(String.valueOf(department.getDeptId()));

			userCompRelMapper.insert(userCompRel);
		}else{
			UserCompRel userCompRel = userCompRels.get(0);
			userCompRel.setDeptIds(String.valueOf(department.getDeptId()));
			userCompRel.setDeptNames(department.getDeptName());
			userCompRel.setIsEnable(true);
			userCompRel.setIsCreate((short) 1);
			userCompRelMapper.updateByPrimaryKey(userCompRel);
		}


		return company;
	}

	@Transactional(rollbackFor = Exception.class)
	public Group createDefaultCompanyGroup(Company company) {
		Group group = new Group();
		group.setGroupName(defaultGroupName);
		group.setCompanyId(company.getCompId());
		group.setCreateDate(new Date());
		group.setGroupRemark(defaultGroupRemark);
		group.setIsValid(true);
		groupManageService.createGroup(group);
		return group;
	}


	/**
	 * 设置初始部门
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Department setUpDepartMent(Company company) throws DeptmentExistException {
		Department department = new Department();
		department.setCompanyId(company.getCompId());
		department.setDeptName(company.getFullName());
		department.setCreateId(company.getCreateId());
		department.setCreateName(company.getCreateName());

		departmentService.createDepartment(department);
		return department;
	}


	@Transactional(rollbackFor = Exception.class)
	public void addToGroup(Group group, Company company) {
		UserGroupRelation userGroupRelation = userGroupService.addUserToGroup(company.getCompId(), company.getCreateId(), group);
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
