package com.lcdt.notify.rpcserviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.notify.dao.TimerQuartzLogMapper;
import com.lcdt.notify.dto.TimerQuartzLogListParams;
import com.lcdt.notify.model.TimerQuartzLog;
import com.lcdt.notify.rpcservice.TimerQuartzLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lyqishan on 2018/7/6
 */
@Service
public class TimerQuartzLogServiceImpl implements TimerQuartzLogService {

    @Autowired
    private TimerQuartzLogMapper timerQuartzLogMapper;

    @Override
    public int add(TimerQuartzLog log) {
        return timerQuartzLogMapper.insert(log);
    }

    @Override
    public PageInfo<TimerQuartzLog> queryTimerQuartzLogList(TimerQuartzLogListParams params) {

        int pageNo = 1;
        int pageSize = 10; //默认10条

        if (params.getPageNo() != null) {
            pageNo = params.getPageNo();
        }
        if (params.getPageSize() != null) {
            pageSize = params.getPageSize();
        }
        PageHelper.startPage(pageNo, pageSize);

        return new PageInfo(timerQuartzLogMapper.selectByCondition(params));
    }
}
