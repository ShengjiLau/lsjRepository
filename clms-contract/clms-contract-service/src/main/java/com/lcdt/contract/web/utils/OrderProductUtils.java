package com.lcdt.contract.web.utils;


import javax.annotation.Resource;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderProductService;

/**
 * 商品增删改查
 * @author Sheng-ji Lau
 * @date 2018年3月7日上午9:51:36
 * @version
 */
public class OrderProductUtils{
	

	
	@Resource
	private static OrderProductService orderProductService;
	

	public static int addProduct(OrderProduct orderProduct) {
	int i=orderProductService.addOrderProduct(orderProduct);
	return i;
	}
	
	

	
	
	

	
	
	
	
	
	
	
	
	
	
	

}
