package com.lcdt.contract.dao;

import java.util.List;

import com.lcdt.contract.model.OrderProduct;

public interface OrderProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long opId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    int insert(OrderProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    int insertSelective(OrderProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderProduct record);
    
    
    /**
     *删除订单下的商品
     */
    int deleteByOrderId(Long orderId);
    
    
    
    
    
    
}