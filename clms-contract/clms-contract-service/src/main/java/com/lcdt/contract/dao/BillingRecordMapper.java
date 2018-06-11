package com.lcdt.contract.dao;

import com.lcdt.contract.model.BillingRecord;
import com.lcdt.contract.web.dto.BillingRecordDto;

import java.util.List;

public interface BillingRecordMapper {
    int deleteByPrimaryKey(Long brId);

    int insert(BillingRecord record);

    int insertSelective(BillingRecord record);

    BillingRecord selectByPrimaryKey(Long brId);

    int updateByPrimaryKeySelective(BillingRecord record);

    int updateByPrimaryKey(BillingRecord record);

    List<BillingRecord> selectByCondition(BillingRecordDto billingRecordDto);
    
    List<BillingRecord> selectByOrderId(Long orderId);
}