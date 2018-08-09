package com.lcdt.pay.dao;

import com.lcdt.pay.model.PayBalance;
import com.lcdt.userinfo.model.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayBalanceMapper {

    List<PayBalance> selectByCompanyIds(@Param("companyIds") List<Long> companyIds);

    int deleteByPrimaryKey(Long balanceId);

    int insert(PayBalance record);

    PayBalance selectByPrimaryKey(Long balanceId);

    List<PayBalance> selectAll();

    int updateByPrimaryKey(PayBalance record);

    PayBalance selectByCompanyId(Long companyId);

}