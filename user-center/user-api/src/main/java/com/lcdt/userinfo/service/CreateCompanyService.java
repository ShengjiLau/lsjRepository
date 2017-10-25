package com.lcdt.userinfo.service;

import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.model.Company;

/**
 * Created by ss on 2017/10/24.
 */
public interface CreateCompanyService {

	Company createCompany(CompanyDto companyDto) throws CompanyExistException;

}
