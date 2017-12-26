package com.lcdt.userinfo.dao;

import com.lcdt.customer.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {

    Customer selectByPrimaryKey(Long customerId, Long companyId);

    int updateByPrimaryKey(Customer record);

    List<Customer> selectByCondition(Map map);

}