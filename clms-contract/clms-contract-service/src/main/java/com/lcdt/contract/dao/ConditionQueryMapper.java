package com.lcdt.contract.dao;

import java.util.List;


import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.web.dto.OrderDto;

/**
 * 条件查询的Mapper接口
 * @author Sheng-ji Lau
 * @date 2018年3月8日上午9:20:01
 * @version
 */
public interface ConditionQueryMapper {
	
	 /**
     * Order条件查询
     * @param OrderDto
     * @return 
     */
    List<Order> selectByCondition(OrderDto orderDto);
	
	
   
    /**
     * OrderProduct条件查询
     * @param Long
     * @return 
     */
    List<OrderProduct> selectByOrderId(Long orderId);
	
    
    /**
     * OrderProductId条件查询
     * @param Long
     * @return 
     */
    List<Long> selectOrderProductIdByOrderId(Long orderId);
	
	
    /**
     * 批量插入订单商品
     * @param OrderProductList
     * @return int
     */
    int insertOrderProductByBatch(List<OrderProduct> orderProductList);
	
    
    /**
     * 批量修改订单商品
     * @param OrderProductList
     * @return int
     */
    int updateOrderProductByBatch(List<OrderProduct> orderProductList);
	
    
    /**
     * 批量删除订单商品
     * @param OrderProductList
     * @return int
     */
    int deleteOrderProducByBatch(List<Long> orderProductIdList);
    
    
    
    

}
