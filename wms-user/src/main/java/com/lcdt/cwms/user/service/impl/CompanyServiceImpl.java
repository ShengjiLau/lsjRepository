package com.lcdt.cwms.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.cwms.user.dao.WmsCompanyMapper;
import com.lcdt.cwms.user.dao.WmsCompanyUserRelationMapper;
import com.lcdt.cwms.user.dto.CreateCompanyDto;
import com.lcdt.cwms.user.exception.GroupNotExistException;
import com.lcdt.cwms.user.model.WmsCompany;
import com.lcdt.cwms.user.model.WmsCompanyUserGroup;
import com.lcdt.cwms.user.model.WmsCompanyUserRelation;
import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.cwms.user.service.GroupService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.FrontUserInfo;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ss on 2017/8/3.
 */
@Transactional
@Service
public class CompanyServiceImpl implements CompanyService {

/*	@Autowired
	WmsCompanyMapper wmsCompanyDao;

	@Reference
	UserService userService;

	@Autowired
	GroupService groupService;

	@Autowired
	WmsCompanyUserRelationMapper userCompanyDao;

	*//**
	 * 用户创建公司时，创建一个默认部门，默认部门不显示
	 * @param createCompanyDto
	 * @return
	 *//*
	@Transactional
	@Override
	public WmsCompany createCompany(CreateCompanyDto createCompanyDto) throws UserNotExistException {
		FrontUserInfo frontUserInfo = userService.queryByUserId(createCompanyDto.getUserId());
		if (frontUserInfo == null) {
			throw new UserNotExistException();
		}
		WmsCompany wmsCompany = new WmsCompany();
		wmsCompany.setCreateUserId(createCompanyDto.getUserId());
		wmsCompany.setBusinessScope(createCompanyDto.getBusinessScope());
		wmsCompany.setAuthStatus((short) 0);
		wmsCompany.setFullName(createCompanyDto.getCompanyName());
		int insert = wmsCompanyDao.insert(wmsCompany);
		WmsCompanyUserRelation wmsCompanyUserRelation = new WmsCompanyUserRelation();
		wmsCompanyUserRelation.setUserId(createCompanyDto.getUserId());
		wmsCompanyUserRelation.setCompanyId(wmsCompany.getCompId());

		//创建公司时在创建一个默认的顶级组
		WmsCompanyUserGroup group = groupService.createGroup(null, wmsCompany.getFullName(), wmsCompany.getCompId());

		//将用户加入默认组
		try {
			groupService.addUserToGroup(group.getGroupId(), createCompanyDto.getUserId());
		} catch (GroupNotExistException e) {
			e.printStackTrace();
		}
		return wmsCompany;
	}

	*//**
	 * 查询用户所属公司
	 * @param userId
	 * @return
	 *//*
	@Transactional(readOnly = true)
	@Override
	public List<WmsCompanyUserRelation> userCompanys(Integer userId) {
		List<WmsCompanyUserRelation> wmsCompanyUserRelations = userCompanyDao.selectCompanyByUserId(userId);
		return wmsCompanyUserRelations;
	}*/
}
