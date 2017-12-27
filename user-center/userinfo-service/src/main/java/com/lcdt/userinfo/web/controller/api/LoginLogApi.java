package com.lcdt.userinfo.web.controller.api;

import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.LoginLogMapper;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/logs")
public class LoginLogApi {

    @Autowired
    LoginLogMapper dao;

    @RequestMapping(value = "/loginlog",method = RequestMethod.GET)
    public PageResultDto companyUserLogs(@ApiParam(required = true) Integer pageNo, @ApiParam(required = true) Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<LoginLog> loginLogs = dao.selectByCompanyId(companyId);
        PageResultDto<LoginLog> userCompRelPageResultDto = new PageResultDto<>(loginLogs);
        return userCompRelPageResultDto;
    }


}
