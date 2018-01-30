package com.lcdt.userinfo.rpc.impl;

import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbinq on 2018/1/30.
 */
public class CompanyRpcServiceImpl implements CompanyRpcService {

    @Autowired
    private CompanyMapper companyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Company findCompanyByCid(Long companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }


}
