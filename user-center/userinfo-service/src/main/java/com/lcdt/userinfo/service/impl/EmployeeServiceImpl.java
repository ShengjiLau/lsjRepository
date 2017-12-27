package com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.DepartmentService;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import com.lcdt.userinfo.web.dto.SearchEmployeeDto;
import com.lcdt.userinfo.web.dto.UpdateEmployeeAccountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ss on 2017/11/14.
 */
@Service
public class EmployeeServiceImpl {

	@Autowired
	UserService userService;

	@Autowired
	UserCompRelMapper userCompanyDao;

	@Autowired
	UserRoleService roleService;

	@Autowired
	GroupManageService groupService;

	@Autowired
	DepartmentService departmentService;

	@Transactional(rollbackFor = Exception.class)
	public boolean addEmployee(CreateEmployeeAccountDto dto) {
		String phone = dto.getUserPhoneNum();
		boolean phoneBeenRegister = userService.isPhoneBeenRegister(phone);
		User user = null;
		if (!phoneBeenRegister) {
			//注册
			try {
				user = userService.registerUser(dto);
			} catch (PhoneHasRegisterException e) {
			}
		} else {
			user = userService.selectUserByPhone(phone);
		}
		//创建公司关联
		Long companyId = SecurityInfoGetter.getCompanyId();

		//是否已加入公司
		boolean userInCompany = isUserInCompany(user.getUserId(), companyId);
		if (userInCompany) {
			return false;
		}

		String department = departmentService.getIdsNames(dto.getDepartIds());
		UserCompRel userCompRel = new UserCompRel();
		userCompRel.setCompId(companyId);
		userCompRel.setUserId(user.getUserId());
		userCompRel.setCreateDate(new Date());
		userCompRel.setDeptIds(dto.getDepartIds());
		userCompRel.setDeptNames(department);
		if (!StringUtils.isEmpty(dto.getDuty())) {
			userCompRel.setDuty(dto.getDuty());
		}


		userCompanyDao.insert(userCompRel);

		if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
			roleService.setCompanyUserRole(user.getUserId(), companyId, dto.getRoles());
		}

		if (dto.getGroups() != null && !dto.getGroups().isEmpty()) {
			groupService.setCompanyUserGroup(user.getUserId(), companyId, dto.getGroups());
		}
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<UserCompRel> queryAllEmployee(SearchEmployeeDto search) {
		List<UserCompRel> userCompRels = userCompanyDao.selectBySearchDto(search);
		for (UserCompRel compRel : userCompRels) {
			Long userId = compRel.getUser().getUserId();
			List<Group> groups = groupService.userCompanyGroups(userId,compRel.getCompId());
			compRel.setGroups(groups);
			List<Role> roles = roleService.userCompanyRole(userId, compRel.getCompId());
			compRel.setRoles(roles);
		}
		return userCompRels;
	}


	@Transactional(rollbackFor = Exception.class)
	public List<UserCompRel> queryNotInGroupEmp(SearchEmployeeDto search) {
		List<UserCompRel> userCompRels = userCompanyDao.selectBySearchDto(search);
		List<UserCompRel> resultCompRels = new ArrayList<UserCompRel>();

		for (UserCompRel compRel : userCompRels) {
			Long userId = compRel.getUser().getUserId();
			List<Group> groups = groupService.userCompanyGroups(userId,compRel.getCompId());
			compRel.setGroups(groups);
			List<Role> roles = roleService.userCompanyRole(userId, compRel.getCompId());
			compRel.setRoles(roles);
		}
		return resultCompRels;
	}




	@Transactional(rollbackFor = Exception.class)
	public boolean isUserInCompany(Long userId, Long companyId) {
		List<UserCompRel> userCompRels = userCompanyDao.selectByUserIdCompanyId(userId, companyId);
		return userCompRels != null && !userCompRels.isEmpty();
	}

	@Transactional(rollbackFor = Exception.class)
	public UserCompRel updateEmployee(UpdateEmployeeAccountDto dto){
		Long userCompRelId = dto.getUserCompRelId();
		UserCompRel userCompRel = userCompanyDao.selectByPrimaryKey(userCompRelId);
		User user = userCompRel.getUser();
		BeanUtils.copyProperties(dto, user);
		userService.updateUser(user);
		//更新用户部门信息

		String departIds = dto.getDepartIds();
		if (!StringUtils.isEmpty(departIds)) {
			String idsNames = departmentService.getIdsNames(departIds);
			userCompRel.setDeptNames(idsNames);
		}

		userCompanyDao.updateByPrimaryKey(userCompRel);

		//更新权限
		roleService.updateCompanyUserRole(user.getUserId(),userCompRel.getCompany().getCompId(),dto.getRoles());
		//更新用户组
		groupService.updateCompanyUsergroup(user.getUserId(),userCompRel.getCompany().getCompId(),dto.getGroups());
		userCompRel = userCompanyDao.selectByPrimaryKey(userCompRelId);
		return userCompRel;
	}



}
