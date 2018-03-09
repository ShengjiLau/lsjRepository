/**
 * 
 */
package com.lcdt.contract.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.OrderProduct;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月6日下午2:20:28
 * @version
 */
public interface OrderProductService {
	
	 /**
     * 新增订单商品
     * @param OrderProduct
     * @return int
     */
    int addOrderProduct(OrderProduct orderProduct);

    /**
     * 修改订单商品
     * @param OrderProduct
     * @return int
     */
    int modOrderProduct(OrderProduct orderProduct);

    /**
     * 删除订单商品 
     * @param Long
     * @return int
     */
    int delOrderProduct(Long OrderProductId);

    /**
     * 获取订单商品列表
     * @return PageInfo<List<OrderProduct>>
     */
    PageInfo<List<OrderProduct>> OrderProductList(Long orderId);


}
