package com.lcdt.pay.dao;

import com.lcdt.pay.model.PayBalance;

import java.util.List;

public interface PayBalanceMapper {
    int deleteByPrimaryKey(Long balanceId);

    int insert(PayBalance record);

    PayBalance selectByPrimaryKey(Long balanceId);

    List<PayBalance> selectAll();

    int updateByPrimaryKey(PayBalance record);
}