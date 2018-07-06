package com.lcdt.notify.rpcservice;

import com.github.pagehelper.PageInfo;
import com.lcdt.notify.dto.TimerQuartzLogListParams;
import com.lcdt.notify.model.TimerQuartzLog;

import java.util.List;

/**
 * Created by lyqishan on 2018/7/6
 */

public interface TimerQuartzLogService {

    /**
     * 新增日志
     * @param log
     */
    int add(TimerQuartzLog log);

    /**
     * 查询列表
     * @param params
     * @return
     */
    PageInfo<TimerQuartzLog> queryTimerQuartzLogList(TimerQuartzLogListParams params);
}
