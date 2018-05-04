package com.lcdt.customer.rpc;


import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/11/24.
 */
@Service(timeout = 5000)
public class CustomerRpcServiceImpl implements CustomerRpcService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    CustomerService customerService;

    @Override
    public Customer findCustomerById(Long customerId) {
        Customer vo = customerMapper.selectInnerByPrimaryKey(customerId);
        return vo;
    }

    @Override
    public Customer findCustomerById(Long customerId, Long companyId) {
        return customerMapper.selectByPrimaryKey(customerId,companyId);
    }


    @Override
    public List<Customer> findBindCompanyIds(Map map) {
        return customerMapper.selectByCondition(map);
    }

    @Override
    public Map<String,Object> selectCarrierAndCustomer(Long companyId,String groupIds){
        return customerMapper.selectCarrierAndCustomer(companyId, groupIds);
    }

    @Override
    public Customer queryCustomer(Long companyId, Long bindCompanyId) {
        Customer customer = customerMapper.selectByCustomerBindCompanyId(companyId, bindCompanyId);
        return customer;
    }

    @Override
    public int customerAdd(Customer customer) {
        return  customerService.customerAdd(customer);
    }
}
