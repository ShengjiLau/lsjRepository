package com.lcdt.notify.rpcserviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.notify.dao.TimerQuartzLogMapper;
import com.lcdt.notify.dto.TimerQuartzLogListParams;
import com.lcdt.notify.model.TimerQuartzLog;
import com.lcdt.notify.rpcservice.TimerQuartzLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lyqishan on 2018/7/6
 */

public class TimerQuartzLogServiceImpl implements TimerQuartzLogService {

    @Autowired
    private TimerQuartzLogMapper timerQuartzLogMapper;

    @Override
    public int add(TimerQuartzLog log) {
        int result = 0;
        result = timerQuartzLogMapper.insert(log);
        return result;
    }

    @Override
    public PageInfo<TimerQuartzLog> queryTimerQuartzLogList(TimerQuartzLogListParams params) {
        List<TimerQuartzLog> resultList = null;

        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (params.getPageNo() != null) {
            pageNo = params.getPageNo();
        }
        if (params.getPageSize() != null) {
            pageSize = params.getPageSize();
        }
        PageHelper.startPage(pageNo, pageSize);
        resultList = timerQuartzLogMapper.selectByCondition(params);
        page = new PageInfo(resultList);
        return page;
    }
}
