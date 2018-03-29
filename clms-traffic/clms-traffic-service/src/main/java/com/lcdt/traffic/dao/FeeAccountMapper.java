package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.FeeAccount;

public interface FeeAccountMapper {

    int deleteByPrimaryKey(Long accountId);

    int insert(FeeAccount record);

    FeeAccount selectByPrimaryKey(Long accountId);

    int updateByPrimaryKey(FeeAccount record);
}