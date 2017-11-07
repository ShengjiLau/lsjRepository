package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;
import java.util.Map;

/**
 * Created by ybq on 2017/8/15.
 */
public interface CompanyService {


	Company updateCompany(Company company);


	Company selectById(Long companyId);


	/**
	 * 创建企业
	 *
	 * @param dto
	 * @return
	 * @throws CompanyExistException
	 */
	Company createCompany(CompanyDto dto) throws CompanyExistException;

	/***
	 * 加入企业
	 * @param dto
	 * @return
	 * @throws CompanyExistException
	 */
	UserCompRel joinCompany(CompanyDto dto) throws CompanyExistException;

	/***
	 * 获取企业列表
	 * @param m
	 * @return
	 */
	PageInfo companyList(Map m);


	List<UserCompRel> companyList(Long userId);


	UserCompRel queryByUserIdCompanyId(Long userId, Long companyId);


	/***
	 * 根据条件查询公司信息
	 * @param dto
	 * @return
	 */
	Company findCompany(CompanyDto dto);

	/***
	 * 解除公司关系
	 * @param relId
	 * @return
	 */
	int removeCompanyRel(Long relId);

}
