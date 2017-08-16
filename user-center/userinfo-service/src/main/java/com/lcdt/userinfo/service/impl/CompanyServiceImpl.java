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
    public WmsCompany createWmsCompany(WmsCompanyDto dto) {




        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<WmsCompany> wmsCompayList(WmsCompanyDto dto) {
        return null;
    }

    @Transactional
    @Override
    public CompanyMember joinCompany(WmsCompanyDto dto) {
        return null;
    }


}
