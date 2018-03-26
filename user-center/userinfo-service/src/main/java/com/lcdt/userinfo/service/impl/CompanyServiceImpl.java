package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.CompanyCertificateMapper;
import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.*;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.GroupManageService;
import com.lcdt.userinfo.service.UserGroupService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * Created by ybq on 2017/8/15.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private UserCompRelMapper userCompRelMapper;

	@Autowired
	public CompanyCertificateMapper certificateDao;

	@Autowired
	public GroupManageService groupService;

	@Autowired
	UserService userService;

	@Transactional(rollbackFor = Exception.class)
	public UserCompRel findByUserCompRelId(Long userCompRelId) {
		UserCompRel userCompRel = userCompRelMapper.selectByPrimaryKey(userCompRelId);
		return userCompRel;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public CompanyCertificate getCompanyCert(Long companyId){
		List<CompanyCertificate> companyCertificate = certificateDao.selectByCompanyId(companyId);
		if (companyCertificate != null && !companyCertificate.isEmpty()) {
			return companyCertificate.get(0);
		}else{
			return new CompanyCertificate();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company selectById(Long companyId){
		return companyMapper.selectByPrimaryKey(companyId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company updateCompany(Company company) {

		Company result = companyMapper.selectByCondition(company);

		if (result != null) {
			if (!result.getCompId().equals(company.getCompId())) {
				throw new RuntimeException("公司名已存在");
			}
		}

		int i = companyMapper.updateByPrimaryKey(company);
		return company;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company createCompany(CompanyDto dto) throws CompanyExistException {
		Map map = new HashMap<String, Object>(2);
		map.put("userId", dto.getUserId());
		map.put("fullName", dto.getCompanyName());
		List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
		if (memberList != null && memberList.size() > 0) {
			throw new CompanyExistException();
		}
		Date dt = new Date();
		//创建企业
		Company company = new Company();
		company.setFullName(dto.getCompanyName());
		Company company1 = companyMapper.selectByCondition(company);
		if (company1 != null) {
			throw new RuntimeException("公司名称已被注册");
		}



		if (!StringUtils.isEmpty(dto.getShortName())) {
			//企业简称默认取企业全称的前六位
			if (dto.getCompanyName().length() <= 6) {
				dto.setShortName(dto.getCompanyName());
			}
			else{
				dto.setShortName(dto.getCompanyName().substring(0,6));
			}
		}

		company.setIndustry(dto.getIndustry());
		company.setShortName(dto.getShortName());
		if (StringUtils.isEmpty(dto.getShortName())) {
			if (company.getFullName().length() <= 4) {
				company.setShortName(company.getFullName());
			}else{
				company.setShortName(company.getFullName().substring(0,4));
			}
		}


		//未认证
		company.setAuthentication((short)0);
		company.setCreateId(dto.getUserId());
		company.setCreateName(dto.getCreateName());
		company.setCreateDate(dt);

		try {
			User user = userService.queryByUserId(company.getCreateId());
			company.setLinkMan(user.getRealName());
			company.setLinkTel(user.getPhone());
			company.setLinkEmail(user.getEmail());
		} catch (UserNotExistException e) {
			e.printStackTrace();
		}


		companyMapper.insert(company);


		//创建关系
		if (company != null && company.getCompId() != null) {
			UserCompRel companyMember = new UserCompRel();
			companyMember.setFullName(company.getFullName());
			companyMember.setUserId(dto.getUserId());
			companyMember.setCompId(company.getCompId());
			companyMember.setIsCreate((short)1); //企业创建者
			companyMember.setCreateDate(dt);

			userCompRelMapper.insert(companyMember);
		}
		return company;
	}


	@Transactional(readOnly = true,rollbackFor = Exception.class)
	@Override
	public UserCompRel joinCompany(CompanyDto dto) throws CompanyExistException {
		Map map = new HashMap<String, Object>();
		map.put("userId", dto.getUserId());
		map.put("fullName", dto.getCompanyName());
		List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
		if (memberList != null && memberList.size() > 0) {
			throw new CompanyExistException();
		}
		UserCompRel companyMember = new UserCompRel();
		companyMember.setFullName(dto.getCompanyName());
		companyMember.setUserId(dto.getUserId());
		companyMember.setCompId(dto.getCompanyId());
		companyMember.setCreateDate(new Date());
		companyMember.setIsCreate((short)1); //加入者
		userCompRelMapper.insert(companyMember);
		return companyMember;
	}


	@Transactional(readOnly = true)
	@Override
	public List<UserCompRel> companyList(Long userId){
		HashMap conditions = new HashMap(2);
		conditions.put("userId", userId);
		return userCompRelMapper.selectByCondition(conditions);
	}

	@Transactional(readOnly = true)
	@Override
	public UserCompRel queryByUserIdCompanyId(Long userId, Long companyId) {
		HashMap hashMap = new HashMap();
		hashMap.put("userId", userId);
		hashMap.put("compId", companyId);
		List<UserCompRel> members = userCompRelMapper.selectByCondition(hashMap);
		if (members == null || members.isEmpty()) {
			return null;
		}else{
			UserCompRel compRel = members.get(0);
			List<Group> groups = groupService.userCompanyGroups(compRel.getUserId(), compRel.getCompId());
			compRel.setGroups(groups);
			return compRel;
		}
	}


	@Transactional(readOnly = true)
	@Override
	public PageInfo companyList(Map m) {
		int pageNo = 1;
		int pageSize = 0; //0表示所有

		if (m.containsKey("page_no")) {
			if (m.get("page_no") != null) {
				pageNo = (Integer) m.get("page_no");
			}
		}
		if (m.containsKey("page_size")) {
			if (m.get("page_size") != null) {
				pageSize = (Integer) m.get("page_size");
			}
		}
		PageHelper.startPage(pageNo, pageSize);
		List<UserCompRel> list = userCompRelMapper.selectByCondition(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}



	@Transactional(readOnly = true)
	@Override
	public Company findCompany(CompanyDto dto) {
		Company vo = new Company();
		vo.setFullName(dto.getCompanyName());
		return companyMapper.selectByCondition(vo);
	}

	@Override
	public boolean removeCompanyRel(Long relId) {
		UserCompRel userCompRel = userCompRelMapper.selectByPrimaryKey(relId);
		short isCreate = userCompRel.getIsCreate();
		if (isCreate == 1) {
			return false;
		}
		userCompRelMapper.deleteByPrimaryKey(relId);
		return true;
	}


	@Override
	public CompanyCertificate updateCompanyCert(CompanyCertificate companyCertificate){
		if (companyCertificate.getCertiId() == null) {
			certificateDao.insert(companyCertificate);
		}else{
			certificateDao.updateByPrimaryKey(companyCertificate);
		}
		Long compId = companyCertificate.getCompId();
		Company company = companyMapper.selectByPrimaryKey(compId);
		company.setAuthentication((short) 2);
		companyMapper.updateByPrimaryKey(company);

		return companyCertificate;
	}

	@Override
	public CompanyCertificate queryCertByCompanyId(Long companyId) {
		List<CompanyCertificate> companyCertificates = certificateDao.selectByCompanyId(companyId);
		if (!CollectionUtils.isEmpty(companyCertificates)) {
			return companyCertificates.get(0);
		}else {
			return null;
		}
	}


}
