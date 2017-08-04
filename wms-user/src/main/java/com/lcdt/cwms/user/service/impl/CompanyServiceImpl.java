package com.lcdt.cwms.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.cwms.user.dao.WmsCompanyMapper;
import com.lcdt.cwms.user.dao.WmsCompanyUserRelationMapper;
import com.lcdt.cwms.user.dto.CreateCompanyDto;
import com.lcdt.cwms.user.model.WmsCompany;
import com.lcdt.cwms.user.model.WmsCompanyUserRelation;
import com.lcdt.cwms.user.service.CompanyService;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ss on 2017/8/3.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	WmsCompanyMapper wmsCompanyDao;

	@Reference
	UserService userService;

	@Autowired
	WmsCompanyUserRelationMapper userCompanyDao;


	/**
	 * 用户创建公司时，创建一个默认部门，默认部门不显示
	 * @param createCompanyDto
	 * @return
	 */
	@Transactional
	@Override
	public WmsCompany createCompany(CreateCompanyDto createCompanyDto) throws UserNotExistException {
		userService.queryByUserId(createCompanyDto.getUserId());
		WmsCompany wmsCompany = new WmsCompany();
		wmsCompany.setCreateUserId(createCompanyDto.getUserId());
		wmsCompany.setBusinessScope(createCompanyDto.getBusinessScope());
		wmsCompany.setAuthStatus((short) 0);
		wmsCompany.setFullName(createCompanyDto.getCompanyName());
		int insert = wmsCompanyDao.insert(wmsCompany);
		return wmsCompany;
	}

	/**
	 * 查询用户所属公司
	 * @param userId
	 * @return
	 */
	@Transactional
	@Override
	public List<WmsCompany> userCompanys(Integer userId) {
//		List<WmsCompanyUserRelation> relations = userCompanyDao.selectByUserId(userId);

		return null;
	}
}
