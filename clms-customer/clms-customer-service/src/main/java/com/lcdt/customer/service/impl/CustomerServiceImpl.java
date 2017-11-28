package com.lcdt.customer.service.impl;


import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.dao.CustomerContactMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.dao.CustomerTypeRelationMapper;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.CustomerContact;
import com.lcdt.customer.model.CustomerTypeRelation;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Autowired
    private CustomerTypeRelationMapper contomerTypeRelation;


    @Override
    public Customer selectByCustomerId(Long customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        return customer;
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

    @Transactional(readOnly = true)
    @Override
    public Customer getCustomerDetail(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCustomerContact(CustomerContact customerContact) {
        return customerContactMapper.insert(customerContact);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addCustomer(Customer customer) throws CustomerException {
        Map map = new HashMap();
        map.put("companyId", customer.getCompanyId());
        map.put("customerName", customer.getCustomerName());
        List<Customer> list = customerMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("客户已存在，请联系管理员分配！");
        }
        customer.setStatus((short)1);
        int flag = customerMapper.insert(customer);
        if (flag>0) {
            //默认联系人
            CustomerContact customerContact = new CustomerContact();
            customerContact.setName(customer.getLinkMan());
            customerContact.setDuty(customer.getLinkDuty());
            customerContact.setMobile(customer.getLinkTel());
            customerContact.setMail(customer.getLinkEmail());
            customerContact.setIsDefault((short) 1); //默认联系人
            customerContact.setCompanyId(customer.getCompanyId());
            customerContact.setProvince(customer.getProvince());
            customerContact.setCity(customer.getCity());
            customerContact.setCounty(customer.getCounty());
            customerContact.setDetailAddress(customer.getDetailAddress());
            customerContact.setCustomerId(customer.getCustomerId());
            customerContactMapper.insert(customerContact);

            //组关系表
            if (!StringUtils.isEmpty(customer.getClientTypes())) {
                String[] typeArrays = customer.getClientTypes().split(",");  //传过来的值用逗号隔开
                for (int i=0; i<typeArrays.length; i++) {
                    CustomerTypeRelation relationObj = new CustomerTypeRelation();
                    relationObj.setCustomerId(customer.getCustomerId());
                    relationObj.setCustomerName(customer.getCustomerName());
                    relationObj.setCustomerType(Short.valueOf(typeArrays[i]));
                    relationObj.setCompanyId(customer.getCompanyId());
                    contomerTypeRelation.insert(relationObj);
                }
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateCustomer(Customer customer) throws CustomerException  {
        Map map = new HashMap();
        map.put("companyId", customer.getCompanyId());
        map.put("customerId", customer.getCustomerId());
        map.put("customerName", customer.getCustomerName());
        List<Customer> list = customerMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("客户已存在，请联系管理员分配！");
        }
        int flag = customerMapper.updateByPrimaryKeySelective(customer);
        if (flag>0) {
             //组关系表
            if (!StringUtils.isEmpty(customer.getClientTypes())) {
                String[] typeArrays = customer.getClientTypes().split(",");  //传过来的值用逗号隔开
                //先清楚原来的组关系，再更新
                CustomerTypeRelation tempObj = new CustomerTypeRelation();
                tempObj.setCustomerId(customer.getCustomerId());
                tempObj.setCustomerId(customer.getCompanyId());
                contomerTypeRelation.deleteCustomerType(tempObj);

                 for (int i=0; i<typeArrays.length; i++) {
                    CustomerTypeRelation relationObj = new CustomerTypeRelation();
                    relationObj.setCustomerId(customer.getCustomerId());
                    relationObj.setCustomerName(customer.getCustomerName());
                    relationObj.setCustomerType(Short.valueOf(typeArrays[i]));
                    relationObj.setCompanyId(customer.getCompanyId());
                    contomerTypeRelation.insert(relationObj);
                }
            }
        }
        return flag;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyCustomer(Customer customer) {
        int flag = customerMapper.updateByPrimaryKey(customer);
          return flag;
    }

    @Transactional
    @Override
    public PageInfo customerContactList(Map m) {
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
        List<CustomerContact> list = customerContactMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return  pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateCustomerContact(CustomerContact CustomerContact) {
        return customerContactMapper.updateByPrimaryKeySelective(CustomerContact);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerContact customerContactDetail(Long contactId) {
        return customerContactMapper.selectByPrimaryKey(contactId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int oldCustomerContactIsNull(CustomerContact customerContact) {
        return customerContactMapper.oldCustomerContactIsNull(customerContact);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerContactRemove(Long contactId) {
        return customerContactMapper.deleteByPrimaryKey(contactId);
    }


}
