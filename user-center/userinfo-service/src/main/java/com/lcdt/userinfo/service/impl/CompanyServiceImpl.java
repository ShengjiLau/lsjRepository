package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.CompanyMemberMapper;
import com.lcdt.userinfo.dao.WmsCompanyMapper;
import com.lcdt.userinfo.dto.WmsCompanyDto;
import com.lcdt.userinfo.model.CompanyMember;
import com.lcdt.userinfo.model.WmsCompany;
import com.lcdt.userinfo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by ybq on 2017/8/15.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private WmsCompanyMapper wmsCompanyMapper;


    @Autowired
    private CompanyMemberMapper companyMemberMapper;



    @Transactional
    @Override
    public Company createCompany(CompanyDto dto) throws CompanyExistException {
        Map map = new HashMap<String, Object>();
        map.put("userId", dto.getUserId());
        map.put("companyName", dto.getCompanyName());
        List<CompanyMember> memberList = companyMemberMapper.selectByCondition(map);
        if (memberList!=null && memberList.size()>0) {
           throw new CompanyExistException();
        }
        Date dt = new Date();
        //创建企业
        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        company.setCreateId(dto.getUserId());
        company.setCreateDt(dt);
        company.setCreateName(dto.getCreateUserName());
        companyMapper.insert(company);

        //创建关系
        if (company != null && company.getCompanyId()!=null) {
            CompanyMember companyMember = new CompanyMember();
            companyMember.setCompanyId(company.getCompanyId());
            companyMember.setUserId(dto.getUserId());
            companyMember.setCompanyId(company.getCompanyId());
            companyMember.setCompanyName(company.getCompanyName());
            companyMember.setRegDt(dt);
            companyMemberMapper.insert(companyMember);
        }
        return company;
    }

    @Transactional
    @Override
    public CompanyMember joinCompany(WmsCompanyDto dto) {
        return null;
    }


}
