package com.lcdt.notify.web.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.notify.dto.TimerQuartzLogListParams;
import com.lcdt.notify.model.TimerQuartzLog;
import com.lcdt.notify.rpcservice.TimerQuartzLogService;
import com.lcdt.notify.web.PageResultDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2018/7/6
 */
@RestController
@RequestMapping("/api/timer")
@Api(value = "定时任务日志",description = "定时任务日志")
public class TimerQuartzLogApi {

    @Autowired
    private TimerQuartzLogService timerQuartzLogService;

    @GetMapping("/list")
    public PageResultDto<TimerQuartzLog> timerQuartzLogList(TimerQuartzLogListParams params){
        params.setCompanyId(SecurityInfoGetter.getCompanyId());
        return new PageResultDto(timerQuartzLogService.queryTimerQuartzLogList(params).getList());
    }
}
