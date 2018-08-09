package com.lcdt.pay.dao;

import com.lcdt.pay.model.BalanceLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BalanceLogMapper {
    int deleteByPrimaryKey(Integer balanceLogId);

    int insert(BalanceLog record);

    BalanceLog selectByPrimaryKey(Integer balanceLogId);

    List<BalanceLog> selectAll();

    int updateByPrimaryKey(BalanceLog record);


    List<BalanceLog> selectByCompanyId(@Param("companyId") Long companyId,
                                       @Param("beginTime") Date beginTime,
                                       @Param("endTime") Date endTime,
                                       @Param("orderType") Integer orderType,
                                       @Param("payType") Integer payType,
                                       @Param("logUser") String logUser);
}