package com.lcdt.userinfo.dao;


import com.lcdt.userinfo.model.TNoticeEmail;

import java.util.List;

public interface TNoticeEmailMapper {
    int deleteByPrimaryKey(Long tId);

    int insert(TNoticeEmail record);

    TNoticeEmail selectByPrimaryKey(Long tId);

    List<TNoticeEmail> selectAll();

    int updateByPrimaryKey(TNoticeEmail record);
}