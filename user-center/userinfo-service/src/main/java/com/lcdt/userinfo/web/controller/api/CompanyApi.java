package com.lcdt.userinfo.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/11/3.
 */
@Api("公司相关api")
@RestController
@RequestMapping("/api/company")
public class CompanyApi {

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/companyinfo",method = RequestMethod.GET)
	@ApiOperation("获取企业信息")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
	public Company companyInfo(){
		Long companyId = SecurityInfoGetter.getCompanyId();
		Company company = companyService.selectById(companyId);
		return company;
	}

	@RequestMapping(value = "/authinfo",method = RequestMethod.GET)
	@ApiOperation("获取企业认证信息")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
	public String companyAuthInfo() {
		return "";
	}




}
