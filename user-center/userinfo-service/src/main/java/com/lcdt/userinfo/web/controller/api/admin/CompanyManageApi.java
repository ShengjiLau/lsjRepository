package com.lcdt.userinfo.web.controller.api.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.model.AdminUser;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.controller.api.admin.dto.CompanyQueryDto;
import com.lcdt.userinfo.web.dto.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/company")
public class CompanyManageApi {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;

    @PostMapping("/usercomps")
    @PreAuthorize("hasAnyAuthority('admin_company_select')")
    public ResponseMessage userCompRels(String userId){
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(userId));
        return JSONResponseUtil.success(userCompRels);
    }

    @PostMapping("/comps")
    @PreAuthorize("hasAnyAuthority('admin_company_select')")
    public ResponseMessage list(CompanyQueryDto companyQueryDto){
        PageInfo<AdminUser> pageInfo = PageHelper.startPage(companyQueryDto.getPageNo(), companyQueryDto.getPageSize()).doSelectPageInfo(() -> companyMapper.selectByCompanyDto(companyQueryDto));
        return JSONResponseUtil.success(pageInfo);
    }

    @PostMapping("/auth")
    public ResponseMessage auth(String remark, Short auth,Long companyId) {
        Company company = companyMapper.selectByPrimaryKey(companyId);
        company.setAuthentication(auth);
        company.setAuthenticationRemark(remark);
        companyMapper.updateByPrimaryKey(company);
        return JSONResponseUtil.success(company);
    }

    @PostMapping("/updateStatus")
    @PreAuthorize("hasAnyAuthority('admin_company_status')")
    public ResponseMessage updateStatus(Long companyId,Boolean enable) {
        Company company = companyMapper.selectByPrimaryKey(companyId);
        company.setEnable(enable);
        companyMapper.updateByPrimaryKey(company);
        return JSONResponseUtil.success(company);
    }

}
