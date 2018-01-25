package com.lcdt.customer.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerCollection;
import com.lcdt.customer.model.CustomerContact;

import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
public interface CustomerService {


    Customer selectByCustomerId(Long customerId, Long companyId);

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
    Customer getCustomerDetail(Long customerId, Long companyId);


    /***
     * 新增客户
     * @param customer
     * @return
     */
    int customerAdd(Customer customer) throws CustomerException;

    /***
     * 新增客户联系人
     * @param customerContact
     * @return
     */
    int customerContactAdd(CustomerContact customerContact);

    /***
     * 更新客户信息
     * @param customer
     * @return
     */
    int customerUpdate(Customer customer) throws CustomerException ;

    int customerCollectionBind(Customer customer);

    /**
     * 更新客户联系人信息
     * @param customerContact
     * @return
     */
    int customerContactUpdate(CustomerContact customerContact);


    /***
     * 获取客户联系人信息
     * @param contactId
     * @return
     */
    CustomerContact customerContactDetail(Long contactId, Long companyId);


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
    int customerContactRemove(Long contactId, Long companyId);

    /**
     * 修改用户状态
     * @param customer
     * @return
     */
    int customerModify(Customer customer);



    /**
     * 客户组列表
     * @param m
     * @return
     */
    PageInfo customerCollectionList(Map m);

    /***
     * 客户组添加
     * @param customerCollection
     * @return
     */
    int customerCollectionAdd(CustomerCollection customerCollection);


    /***
     * 客户组编辑
     * @param customerCollection
     * @return
     */
    int customerCollectionUpdate(CustomerCollection customerCollection);


    /**
     * 客户组删除
     * @param collectionId
     * @param companyId
     * @return
     */
    int customerCollectionRemove(Long collectionId, Long companyId);


}
