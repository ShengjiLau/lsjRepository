package com.lcdt.userinfo.service.impl;

import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.userinfo.web.dto.CreateEmployeeAccountDto;
import com.lcdt.userinfo.web.dto.SearchEmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		UserCompRel userCompRel = new UserCompRel();
		userCompRel.setCompId(companyId);
		userCompRel.setUserId(user.getUserId());
		userCompRel.setCreateDate(new Date());
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
		return userCompRels;
	}


}
