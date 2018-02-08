package com.lcdt.userinfo.web.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.CompanyCertificate;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.web.dto.ModifyCompanyAuthDto;
import com.lcdt.userinfo.web.dto.ModifyCompanyInfoDto;
import com.lcdt.userinfo.web.dto.ModifyContactDto;
import com.lcdt.userinfo.web.dto.ModifyInvoiceDto;
import com.lcdt.userinfo.web.exception.CompanyNameExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/11/3.
 */
@Api(value = "公司相关api",description = "公司元数据相关api")
@RestController
@RequestMapping("/api/company")
public class CompanyApi {

	@Autowired
	CompanyService companyService;

	@ApiOperation("获取公司认证图片信息")
	@RequestMapping(value = "/getauthinfo",method = RequestMethod.GET)
	public CompanyCertificate getAuthInfo(){
		Long companyId = SecurityInfoGetter.getCompanyId();
		return companyService.getCompanyCert(companyId);
	}


	/**
	 * 提交认证图片信息
	 *
	 * @return
	 */
	@ApiOperation("提交公司认证图片信息")
	@RequestMapping(value = "/updateAuth",method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_cert')")
	public CompanyCertificate updateAuthInfo(@Validated ModifyCompanyAuthDto dto) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		CompanyCertificate companyCertificate = companyService.queryCertByCompanyId(companyId);
		if (companyCertificate == null) {
			companyCertificate = new CompanyCertificate();
			companyCertificate.setCompId(companyId);
		}
		BeanUtils.copyProperties(dto,companyCertificate);
		companyService.updateCompanyCert(companyCertificate);
		return companyCertificate;
	}

	@ApiOperation("修改开票信息")
	@RequestMapping(value = "/updateInvoice",method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_invoice')")
	public Company updateInvoiceInfo(@Validated ModifyInvoiceDto dto) {
		Company company = updateCompanyWithDto(dto);
		return company;
	}


	/**
	 * 编辑公司联系人信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/update_contact", method = RequestMethod.POST)
	@ApiOperation("修改公司联系人信息")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_contact')")
	public Company updateCompanyContact(@Validated ModifyContactDto modifyContactDto) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Company company = companyService.selectById(companyId);

		company.setLinkMan(modifyContactDto.getContactName());
		company.setLinkTel(modifyContactDto.getContacTel());

		if (!StringUtils.isEmpty(modifyContactDto.getContactRemark())) {
			company.setLinkRemark(modifyContactDto.getContactRemark());
		}

		if (!StringUtils.isEmpty(modifyContactDto.getContactEmail())) {
			company.setLinkEmail(modifyContactDto.getContactEmail());
		}

		if (!StringUtils.isEmpty(modifyContactDto.getContactDuty())) {
			company.setLinkDuty(modifyContactDto.getContactDuty());
		}

		companyService.updateCompany(company);

		return company;
	}


	@ApiOperation("修改企业信息")
	@RequestMapping(value = "/update_compamy", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_company_info')")
	public Company updateCompanyInfo(@Validated ModifyCompanyInfoDto modifyCompanyInfoDto) throws CompanyNameExistException {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Company company = companyService.selectById(companyId);
		company.setShortName(modifyCompanyInfoDto.getShortName());
		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getFullName())) {

			CompanyDto companyDto = new CompanyDto();
			companyDto.setCompanyName(modifyCompanyInfoDto.getFullName());

			Company company1 = companyService.findCompany(companyDto);
			if (company1 != null) {
				throw new CompanyNameExistException();
			}

		}

		company.setFullName(modifyCompanyInfoDto.getFullName());
		company.setIndustry(modifyCompanyInfoDto.getIndustry());
		company.setProvince(modifyCompanyInfoDto.getProvince());
		company.setCity(modifyCompanyInfoDto.getCity());
		company.setCounty(modifyCompanyInfoDto.getCounty());
		company.setDetailAddress(modifyCompanyInfoDto.getDetailAddress());

		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getCompanyLogo())) {
			company.setCompanyLogo(modifyCompanyInfoDto.getCompanyLogo());
		}

		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getFax())) {
			company.setFax(modifyCompanyInfoDto.getFax());
		}

		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getPostCode())) {
			company.setPostCode(modifyCompanyInfoDto.getPostCode());
		}

		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getCompIntro())) {
			company.setCompIntro(modifyCompanyInfoDto.getCompIntro());
		}

		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getTelNo())) {
			company.setTelNo(modifyCompanyInfoDto.getTelNo());
		}


		if (!StringUtils.isEmpty(modifyCompanyInfoDto.getTelNo1())) {
			company.setTelNo(modifyCompanyInfoDto.getTelNo1());
		}

		company.setFax(company.getFax());
		companyService.updateCompany(company);
		return company;
	}


	@RequestMapping(value = "/companyinfo", method = RequestMethod.GET)
	@ApiOperation("获取企业信息")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('get_companyinfo')")
	public Company companyInfo() {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Company company = companyService.selectById(companyId);
		return company;
	}

	private Company updateCompanyWithDto(Object dto) {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Company company = companyService.selectById(companyId);
		BeanUtils.copyProperties(dto,company);
		companyService.updateCompany(company);
		return company;
	}
}
