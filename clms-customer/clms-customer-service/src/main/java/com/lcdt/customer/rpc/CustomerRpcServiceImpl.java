package com.lcdt.customer.rpc;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public PageInfo customerList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Customer> list = customerMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return  pageInfo;
    }
}
