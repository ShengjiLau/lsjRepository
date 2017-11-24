package com.lcdt.customer.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;

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
    PageInfo getCustomerList(Map m);

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
    int addCustomer(Customer customer);




    /**




   /* *//**
     * 更新客户信息
     * @param myClient
     * @return
     *//*
    int updateMyClient(MyClient myClient);



    *//**
     * 删除客户信息
     * @param myClientId
     * @return
     *//*
    int delMyClient(Long myClientId);

    *//**
     * 启用/停用客户状态
     * @param myClient
     * @return
     *//*
    int toggleMyClientStatus(MyClient myClient);

    *//**
     * 获取客户联系人
     * @param myClientId
     * @return
     *//*
    List<MyClientLinkman> getMyClientLinkman(Long myClientId);

    *//**
     * 更新客户联系人
     * @param myClientLinkman
     * @return
     *//*
    int updateMyClientLinkman(MyClientLinkman myClientLinkman);

    *//**
     * 新增客户联系人
     * @param myClientLinkman
     * @return
     *//*
    int addMyClientLinkman(MyClientLinkman myClientLinkman);

    *//**
     * 删除客户联系人
     * @param myClientLinkmanId
     * @return
     *//*
    int delMyClientLinkman(Long myClientLinkmanId);

    *//**
     * 设置默认联系人
     * @param myClientLinkmanId
     * @return
     *//*
    int setDefaultLinkman(Long myClientLinkmanId);*/
}
