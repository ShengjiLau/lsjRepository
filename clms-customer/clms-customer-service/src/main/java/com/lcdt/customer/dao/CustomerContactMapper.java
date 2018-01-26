package com.lcdt.customer.dao;

import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerContact;

import java.util.List;
import java.util.Map;

public interface CustomerContactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_contact
     *
     * @mbg.generated Fri Nov 24 10:56:38 CST 2017
     */
    int deleteByPrimaryKey(Map map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_contact
     *
     * @mbg.generated Fri Nov 24 10:56:38 CST 2017
     */
    int insert(CustomerContact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_contact
     *
     * @mbg.generated Fri Nov 24 10:56:38 CST 2017
     */
    CustomerContact selectByPrimaryKey(Map map);

    CustomerContact selectByCustomerId(Long contactId);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_contact
     *
     * @mbg.generated Fri Nov 24 10:56:38 CST 2017
     */
    List<CustomerContact> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_contact
     *
     * @mbg.generated Fri Nov 24 10:56:38 CST 2017
     */
    int updateByPrimaryKey(CustomerContact record);
    int updateByPrimaryKeySelective(CustomerContact record);

    /***
     * 按条件查询
     * @param map
     * @return
     */
    List<CustomerContact> selectByCondition(Map map);


    /**
     * 把旧的默认联系人置空
     * @param customerContact
     * @return
     */
    int oldCustomerContactIsNull(CustomerContact customerContact);
}