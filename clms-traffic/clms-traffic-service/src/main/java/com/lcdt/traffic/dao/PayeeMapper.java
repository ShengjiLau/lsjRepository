package com.lcdt.traffic.dao;

import java.util.List;

import com.lcdt.traffic.model.Payee;

public interface PayeeMapper {
    int deleteByPrimaryKey(Long payeeId);

    int insert(Payee record);

    int insertSelective(Payee record);

    Payee selectByPrimaryKey(Long payeeId);

    int updateByPrimaryKeySelective(Payee record);

    int updateByPrimaryKey(Payee record);
    
    int insertPayeeByBatch(List<Payee> payeeList);
}