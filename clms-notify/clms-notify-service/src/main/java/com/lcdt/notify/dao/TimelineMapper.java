package com.lcdt.notify.dao;

import com.lcdt.notify.model.Timeline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimelineMapper {
    int deleteByPrimaryKey(Long timelineId);

    int insert(Timeline record);

    Timeline selectByPrimaryKey(Long timelineId);

    List<Timeline> selectAll();

    int updateByPrimaryKey(Timeline record);

    List<Timeline> selectByTag(@Param("companyId") Long companyId,@Param("tag") String tag);
}