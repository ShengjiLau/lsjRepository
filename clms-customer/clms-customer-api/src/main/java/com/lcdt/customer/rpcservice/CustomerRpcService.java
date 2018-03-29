package com.lcdt.customer.rpcservice;

import com.lcdt.customer.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/11/24.
 */
public interface CustomerRpcService {


    Customer findCustomerById(Long customerId);

    Customer findCustomerById(Long customerId, Long companyId);

    /**
     * 查询企业绑定客户ID
     *
     * @param map
     * @return
     */
    List<Customer> findBindCompanyIds(Map map);


    /**
     * 运输首页右侧客户相关的概览统计
     * @param companyId
     * @return
     */
    Map<String,Object> selectCarrierAndCustomer(Long companyId,String groupIds);


    /**
     * 根据企业Id和绑定企业Id查询客户
     * @param companyId
     * @param bindCompanyId
     * @return
     */
    Customer queryCustomer(Long companyId,Long bindCompanyId);

}
