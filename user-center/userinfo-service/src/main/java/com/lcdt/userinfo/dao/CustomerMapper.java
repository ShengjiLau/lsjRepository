package com.lcdt.userinfo.dao;

import com.lcdt.customer.model.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {

    Customer selectByPrimaryKey(@Param("customerId")Long customerId, @Param("companyId")Long companyId);

    int updateByPrimaryKey(Customer record);

    List<Customer> selectByCondition(Map map);

}