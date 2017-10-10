package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ybq on 2017/8/15.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private UserCompRelMapper userCompRelMapper;

	@Transactional
	@Override
	public Company createCompany(CompanyDto dto) throws CompanyExistException {
		Map map = new HashMap<String, Object>();
		map.put("userId", dto.getUserId());
		map.put("compId", dto.getCompanyId());
		List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
		if (memberList != null && memberList.size() > 0) {
			throw new CompanyExistException();
		}
		Date dt = new Date();
		//创建企业
		Company company = new Company();
	/*	company.setCompanyName(dto.getCompanyName());
		company.setCreateId(dto.getUserId());
		company.setCreateDt(dt);
		company.setCreateName(dto.getCreateUserName());
		companyMapper.insert(company);

		//创建关系
		if (company != null && company.getCompanyId() != null) {
			CompanyMember companyMember = new CompanyMember();
			companyMember.setCompanyId(company.getCompanyId());
			companyMember.setUserId(dto.getUserId());
			companyMember.setCompanyId(company.getCompanyId());
			companyMember.setCompanyName(company.getCompanyName());
			companyMember.setRegDt(dt);
			companyMemberMapper.insert(companyMember);
		}*/
		return company;
	}


	@Transactional
	@Override
	public UserCompRel joinCompany(CompanyDto dto) throws CompanyExistException {
		Map map = new HashMap<String, Object>();
		map.put("userId", dto.getUserId());
		map.put("fullName", dto.getCompanyName());
		List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
		if (memberList != null && memberList.size() > 0) {
			throw new CompanyExistException();
		}

		UserCompRel userCompRel = new UserCompRel();
/*		companyMember.setCompanyId(dto.getCompanyId());
		companyMember.setUserId(dto.getUserId());
		companyMember.setCompanyId(dto.getCompanyId());
		companyMember.setCompanyName(dto.getCompanyName());
		companyMember.setRegDt(new Date());
		companyMemberMapper.insert(companyMember);*/

		return userCompRel;
	}


	@Transactional(readOnly = true)
	public List<UserCompRel> companyList(Long userId){
		HashMap conditions = new HashMap(2);
		conditions.put("userId", userId);
		return userCompRelMapper.selectByCondition(conditions);
	}

	@Transactional(readOnly = true)
	public UserCompRel queryByUserIdCompanyId(Long userId, Integer companyId) {
		HashMap hashMap = new HashMap();
		hashMap.put("userId", userId);
		hashMap.put("compId", companyId);
		List<UserCompRel> members = userCompRelMapper.selectByCondition(hashMap);
		if (members == null || members.isEmpty()) {
			return null;
		}else{
			return members.get(0);
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
}
