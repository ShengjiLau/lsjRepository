package com.lcdt.customer.rpcservice;

import com.github.pagehelper.PageInfo;
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

    /**
     * 企业初始化增加客户接口
     * */
    int customerAdd(Customer customer) ;


    /**
     * 根据查询条件获取客户列表
     * @param m
     * @return
     */
    PageInfo customerList(Map m);


    /**
     * 计划、运单等创建共用
     * @param  customerType--客户类型（1-销售客户
                                    2-仓储客户
                                    3-运输客户
                                    4-仓储服务商
                                    5-运输服务商
                                    6-供应商
                                    7-其他
                                    ）
     *  customerName --客户名称
     *  companyId --企业ID
     *  province --省（默认联系人收货人）
     *  city --市（默认联系人收货人）
     *  county -- 县（默认联系人收货人）
     *  details --详细地址（默认联系人收货人）
     *  userId -- 登录人ID
     *  userName -- 登录人
     *  mobile-- 联系人手机（默认联系人收货人）
     *  mobile-- 联系人手机（默认联系人收货人）
     *
     * @return （error-返回空，存在或新增的返回加入对象）
     */
    Customer createCustomer(Map map);

}
