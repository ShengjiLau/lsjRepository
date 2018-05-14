package com.lcdt.contract.dao;

import com.lcdt.contract.model.PaymentRecord;

public interface PaymentRecordMapper {
    int deleteByPrimaryKey(Long prId);

    int insert(PaymentRecord record);

    int insertSelective(PaymentRecord record);

    PaymentRecord selectByPrimaryKey(Long prId);

    int updateByPrimaryKeySelective(PaymentRecord record);

    int updateByPrimaryKey(PaymentRecord record);
}