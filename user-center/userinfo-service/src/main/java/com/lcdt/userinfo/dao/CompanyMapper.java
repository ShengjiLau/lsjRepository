package com.lcdt.userinfo.dao;


import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.web.controller.api.admin.dto.CompanyQueryDto;

import java.util.List;

public interface CompanyMapper {

	int deleteByPrimaryKey(Long compId);

	int insert(Company record);

	Company selectByPrimaryKey(Long compId);

	List<Company> selectAll();

	int updateByPrimaryKey(Company record);

	Company selectByCondition(Company record);

	List<Company> selectByCompanyDto(CompanyQueryDto companyQueryDto);

}