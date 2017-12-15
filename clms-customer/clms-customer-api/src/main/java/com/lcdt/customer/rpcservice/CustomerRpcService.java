package com.lcdt.customer.rpcservice;

import com.lcdt.customer.model.Customer;

/**
 * Created by ss on 2017/11/24.
 */
public interface CustomerRpcService {

    Customer findCustomerById(Long customerId);


}
