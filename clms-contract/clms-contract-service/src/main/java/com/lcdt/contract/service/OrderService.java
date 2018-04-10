package com.lcdt.contract.service;




import com.github.pagehelper.PageInfo;

import com.lcdt.contract.web.dto.OrderDto;



/**
 * @author Sheng-ji Lau
 * @date 2018年3月1日下午5:20:58
 * @version
 */
public interface OrderService {
	 /**
     * 新增订单
     * @param Order
     * @return
     */
    int addOrder(OrderDto orderDto);

    /**
     * 修改订单
     * @param Order
     * @return
     */
    int modOrder(OrderDto orderDto);

    /**
     * 删除单个订单
     * @param Long
     * @return
     */
    int delOrder(Long OrderId);

    /**
     * 获取订单列表
     * @return PageInfo<List<Order>>
     */
    PageInfo<OrderDto> OrderList(OrderDto OrderDto);

	
    /**
     * 获取单个订单
     * @return OrderDto
     */
	OrderDto selectByPrimaryKey(Long orderId);
	
	
	/**
     * 取消订单
     * @return int
     */
	int updateOrderIsDraft(Long orderId,Short isDraft);
	
	
	
	
		

}
