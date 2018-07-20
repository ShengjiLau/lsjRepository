package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.userinfo.dto.ExperienceLogListParams;
import com.lcdt.userinfo.model.ExperienceLog;
import com.lcdt.userinfo.rpc.ExperienceLogService;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2018/7/20
 */
@RestController
@Api(value = "体验者信息日志", description = "体验者信息日志api")
@RequestMapping("/api/experience")
public class ExperienceLogApi {

    @Autowired
    private ExperienceLogService experienceLogService;

    @GetMapping("/log")
    public PageResultDto<ExperienceLog> experienceLogList(ExperienceLogListParams params){
        return new PageResultDto<>(experienceLogService.queryExperienceLogListByCondition(params).getList());
    }
}
