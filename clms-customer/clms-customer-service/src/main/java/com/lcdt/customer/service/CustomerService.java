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


    Customer selectByCustomerId(Long customerId);

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
     * 新增客户联系人
     * @param customerContact
     * @return
     */
    int addCustomerContact(CustomerContact customerContact);

    /***
     * 更新客户信息
     * @param customer
     * @return
     */
    int updateCustomer(Customer customer) throws CustomerException ;

    /**
     * 更新客户联系人信息
     * @param customerContact
     * @return
     */
    int updateCustomerContact(CustomerContact customerContact);


    /***
     * 获取客户联系人信息
     * @param contactId
     * @return
     */
    CustomerContact customerContactDetail(Long contactId);


    /***
     * 把旧的默认联系人取消
     * @param customerContact
     * @return
     */
    int oldCustomerContactIsNull(CustomerContact customerContact);


    /***
     * 删除客户联系人
     * @param contactId
     * @return
     */
    int customerContactRemove(Long contactId);

    /**
     * 修改用户状态
     * @param customer
     * @return
     */
    int modifyCustomer(Customer customer);


}
