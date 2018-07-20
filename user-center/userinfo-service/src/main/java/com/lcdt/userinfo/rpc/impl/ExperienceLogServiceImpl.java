package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.ExperienceLogMapper;
import com.lcdt.userinfo.dto.ExperienceLogListParams;
import com.lcdt.userinfo.model.ExperienceLog;
import com.lcdt.userinfo.rpc.ExperienceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 2018/7/20
 */
@Service
@Transactional
public class ExperienceLogServiceImpl implements ExperienceLogService {

    @Autowired
    private ExperienceLogMapper mapper;

    @Override
    public int addExperienceLog(ExperienceLog log) {
        return this.mapper.insert(log);
    }

    @Override
    public PageInfo<ExperienceLog> queryExperienceLogListByCondition(ExperienceLogListParams params) {
        PageHelper.startPage(params.getPageNo(),params.getPageSize());
        return new PageInfo<ExperienceLog>(this.mapper.selectByCondition(params));
    }
}
