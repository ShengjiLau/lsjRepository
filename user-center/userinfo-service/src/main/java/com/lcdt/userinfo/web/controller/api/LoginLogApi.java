package com.lcdt.userinfo.web.controller.api;

import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.LoginLogMapper;
import com.lcdt.userinfo.model.LoginLog;
import com.lcdt.userinfo.model.LoginLogDto;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/logs")
public class LoginLogApi {

    @Autowired
    LoginLogMapper dao;

    @RequestMapping(value = "/loginlog",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('login_log_list')")
    public PageResultDto companyUserLogs(
            @RequestParam(required = false) Date beginTime,
            @RequestParam(required = false) Date endTime,
            @RequestParam(required = false) String username,@ApiParam(required = true)@RequestParam Integer pageNo, @RequestParam @ApiParam(required = true) Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<LoginLogDto> loginLogs = dao.selectByCompanyId(companyId,username,null,beginTime,endTime);
        PageResultDto<LoginLogDto> userCompRelPageResultDto = new PageResultDto<>(loginLogs);
        return userCompRelPageResultDto;
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isEmpty(text)) {
                    setValue(null);
                    return;
                }
                setValue(new Date(Long.valueOf(text)));
            }
        });
    }
}
