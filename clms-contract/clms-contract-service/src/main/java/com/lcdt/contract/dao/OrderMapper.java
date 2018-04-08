package com.lcdt.contract.dao;

import com.lcdt.contract.model.Order;
import com.lcdt.contract.web.dto.OrderDto;


public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    int insertOrder(Order order);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    OrderDto selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Order record);

    /**
     * 更新审批状态
     * @param orderId
     * @param companyId
     * @param status
     * @return
     */
    int updateApprovalStatus(Long orderId,Long companyId,Short status);
    
    /**
     * 取消订单 将is_draft 设置为2
     * @param orderId
     * @return
     */
    int updateIsDraft(Long orderId,Short isDraft);
    
}