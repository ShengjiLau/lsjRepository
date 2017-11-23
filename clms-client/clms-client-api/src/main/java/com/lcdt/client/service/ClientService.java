package com.lcdt.client.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.client.model.Client;

import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
public interface ClientService {

    /**
     * 根据查询条件获取客户列表
     * @param m
     * @return
     */
    PageInfo getMyClientList(Map m);

    /**
     * 获取客户详情信息
     * @param myClientId
     * @return
     */
    Client getMyClientDetail(Long myClientId);

   /* *//**
     * 更新客户信息
     * @param myClient
     * @return
     *//*
    int updateMyClient(MyClient myClient);

    *//**
     * 新增客户信息
     * @param myClient
     * @return
     *//*
    int addMyClient(MyClient myClient);

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
