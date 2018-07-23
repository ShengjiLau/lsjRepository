package com.lcdt.userinfo.rpc;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dto.ExperienceLogListParams;
import com.lcdt.userinfo.model.ExperienceLog;

/**
 * Created by lyqishan on 2018/7/20
 */

public interface ExperienceLogService {

    /**
     * 新增
     * @param log
     * @return
     */
    int addExperienceLog(ExperienceLog log);

    /**
     * 根据条件查询列表
     * @param params
     * @return
     */
    PageInfo<ExperienceLog> queryExperienceLogListByCondition(ExperienceLogListParams params);

}
