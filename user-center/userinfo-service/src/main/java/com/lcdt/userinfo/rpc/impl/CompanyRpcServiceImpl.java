package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbinq on 2018/1/30.
 */
@Service(timeout = 5000)
public class CompanyRpcServiceImpl implements CompanyRpcService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private com.lcdt.userinfo.dao.UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Company findCompanyByCid(Long companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User findCompanyCreate(Long compId) {
        return userMapper.compCreateUserByCompId(compId);
    }


    @Override
    public User findUserByPhone(String phone) {
        return userMapper.queryByUserPhone(phone);
    }


}
