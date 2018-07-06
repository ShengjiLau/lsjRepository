package com.lcdt.notify.dao;

import com.lcdt.notify.dto.TimerQuartzLogListParams;
import com.lcdt.notify.model.TimerQuartzLog;

import java.util.List;

public interface TimerQuartzLogMapper {
    /**
     * 删除日志
     * @param logId
     * @return
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * 新增日志
     * @param record
     * @return
     */
    int insert(TimerQuartzLog record);

    /**
     * 查询日志
     * @param logId
     * @return
     */
    TimerQuartzLog selectByPrimaryKey(Long logId);

    /**
     * 查询所有日志
     * @return
     */
    List<TimerQuartzLog> selectAll();

    /**
     * 修改日志
     * @param record
     * @return
     */
    int updateByPrimaryKey(TimerQuartzLog record);

    /**
     * 根据条件查询
     * @param params
     * @return
     */
    List<TimerQuartzLog> selectByCondition(TimerQuartzLogListParams params);
}