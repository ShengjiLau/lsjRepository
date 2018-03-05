package com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.model.Role;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.DepartmentService;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.LoginLogService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.web.dto.*;
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

	@Autowired
	LoginLogService loginLogService;




	@Transactional(rollbackFor = Exception.class)
	public boolean removeUserCompRel(Long userCompId){

		UserCompRel userCompRel = userCompanyDao.selectByPrimaryKey(userCompId);
		if (userCompRel != null) {
			if (userCompRel.getIsCreate() == 1) {
				return false;
			}
			userCompanyDao.deleteByPrimaryKey(userCompRel.getUserCompRelId());
			roleService.removeUserRole(userCompRel.getUserId(),userCompRel.getCompId());
			groupService.deleteUserGroupRelation(userCompRel.getUserId(), userCompRel.getCompId());
			return true;
		}

		return false;
	}


	@Transactional(rollbackFor = Exception.class)
	public boolean addEmployee(CreateEmployeeAccountDto dto,Long companyId) {
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
		userCompRel.setDuty(dto.getDuty());
		userCompRel.setComment(dto.getComment());
		userCompRel.setEmail(dto.getEmail());
		userCompRel.setNickName(dto.getNickName());
		userCompRel.setName(dto.getName());


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
		ArrayList<EmployeeDto> employeeDtos = new ArrayList<>(userCompRels.size());
		for (UserCompRel compRel : userCompRels) {
			EmployeeDto employeeDto = new EmployeeDto();
			Long userId = compRel.getUser().getUserId();
			List<Group> groups = groupService.userCompanyGroups(userId,compRel.getCompId());
			compRel.setGroups(groups);
			List<Role> roles = roleService.userCompanyRole(userId, compRel.getCompId());
			compRel.setRoles(roles);
			//获取员工所在部门
			List<UserCompRel> ucrs = userCompanyDao.selectByUserIdCompanyId(userId,compRel.getCompId());

			if(ucrs != null && ucrs.size() > 0) {
				compRel.setDeptNames(ucrs.get(0).getDeptNames());
			}

			employeeDtos.add(employeeDto);
			LoginLog loginLog = loginLogService.userLastLogin(userId, compRel.getCompId());
			compRel.setLoginLog(loginLog);
		}
		return userCompRels;
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

//		BeanUtils.copyProperties(dto, user);
//		userService.updateUser(user);
		//更新用户部门信息

		userCompRel.setComment(dto.getComment());
		userCompRel.setDuty(dto.getDuty());
		userCompRel.setEmail(dto.getEmail());
		userCompRel.setNickName(dto.getNickName());
		userCompRel.setName(dto.getName());
		String departIds = dto.getDepartIds();

		if (!StringUtils.isEmpty(departIds)) {
			String idsNames = departmentService.getIdsNames(departIds);
			userCompRel.setDeptNames(idsNames);
			userCompRel.setDeptIds(departIds);
		}

		userCompanyDao.updateByPrimaryKey(userCompRel);

		//更新权限
		roleService.updateCompanyUserRole(user.getUserId(),userCompRel.getCompany().getCompId(),dto.getRoles());
		//更新用户组
		groupService.updateCompanyUsergroup(user.getUserId(),userCompRel.getCompany().getCompId(),dto.getGroups());
		userCompRel = userCompanyDao.selectByPrimaryKey(userCompRelId);
		return userCompRel;
	}


	@Transactional(rollbackFor = Exception.class)
	public UserCompRel toggleEnableEmployee(ToggleEmployeeEnableDto dto){
		UserCompRel userCompRel = userCompanyDao.selectByPrimaryKey(dto.getUserCompRelId());
		if (userCompRel.getIsCreate() == 1) {
			throw new RuntimeException("管理员无法禁用");
		}

		userCompRel.setEnable(dto.getEnable());
		userCompanyDao.updateByPrimaryKey(userCompRel);
		return userCompRel;
	}

}
