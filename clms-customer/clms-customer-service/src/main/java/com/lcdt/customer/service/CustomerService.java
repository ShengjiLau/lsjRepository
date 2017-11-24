package com.lcdt.customer.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerContact;

import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
public interface CustomerService {

    /**
     * 根据查询条件获取客户列表
     * @param m
     * @return
     */
    PageInfo customerList(Map m);

    /**
     * 查询客户下的联系人
     * @param m
     * @return
     */
    PageInfo customerContactList(Map m);

    /**
     * 获取客户详情信息
     * @param customerId
     * @return
     */
    Customer getCustomerDetail(Long customerId);

    /***
     * 新增客户
     * @param customer
     * @return
     */
    int addCustomer(Customer customer) throws CustomerException;


    /***
     * 更新客户信息
     * @param customer
     * @return
     */
    int updateCustomer(Customer customer);


    /**
     * 更新客户联系人信息
     * @param CustomerContact
     * @return
     */
    int updateCustomerContact(CustomerContact CustomerContact);

    /***
     * 获取客户联系人信息
     * @param contactId
     * @return
     */
    CustomerContact getCustomerContactDetail(Long contactId);


    /***
     * 把旧的默认联系人取消
     * @param customerContact
     * @return
     */
    int oldCustomerContactIsNull(CustomerContact customerContact);



}
