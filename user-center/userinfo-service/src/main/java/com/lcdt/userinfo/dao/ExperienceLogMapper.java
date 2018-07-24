package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.dto.ExperienceLogListParams;
import com.lcdt.userinfo.model.ExperienceLog;

import java.util.List;

public interface ExperienceLogMapper {
    /**
     * 删除
     * @param logId
     * @return
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(ExperienceLog record);

    /**
     * 根据主键查询
     * @param logId
     * @return
     */
    ExperienceLog selectByPrimaryKey(Long logId);
    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(ExperienceLog record);

    /**
     * 查询列表
     * @param params
     * @return
     */
    List<ExperienceLog> selectByCondition(ExperienceLogListParams params);
}