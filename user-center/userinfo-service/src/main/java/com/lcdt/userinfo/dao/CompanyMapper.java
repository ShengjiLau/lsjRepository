package com.lcdt.userinfo.dao;


import com.lcdt.userinfo.model.Company;

import java.util.List;

public interface CompanyMapper {

	int deleteByPrimaryKey(Long compId);

	int insert(Company record);

	Company selectByPrimaryKey(Long compId);

	List<Company> selectAll();

	int updateByPrimaryKey(Company record);

	Company selectByCondition(Company record);

}