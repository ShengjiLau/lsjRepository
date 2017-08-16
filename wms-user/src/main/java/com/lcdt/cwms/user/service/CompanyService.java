package com.lcdt.cwms.user.service;

import com.lcdt.cwms.user.dto.CreateCompanyDto;
import com.lcdt.cwms.user.model.WmsCompany;
import com.lcdt.cwms.user.model.WmsCompanyUserRelation;
import com.lcdt.userinfo.exception.UserNotExistException;

import java.util.List;

/**
 * Created by ss on 2017/8/3.
 */
public interface CompanyService {

	 WmsCompany createCompany(CreateCompanyDto createCompanyDto) throws UserNotExistException;

	 List<WmsCompanyUserRelation> userCompanys(Long userId);

}
