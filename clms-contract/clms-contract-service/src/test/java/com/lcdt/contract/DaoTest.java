package com.lcdt.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcdt.contract.dao.ConditionQueryMapper;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月15日下午8:49:02
 * @version
 */
public class DaoTest {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ConditionQueryMapper condition;
	
	@Test
	public void testOrderMapper() {
		Order order =new Order();
		order.setCreateTime(new Date());
		order.setDispatchingType((short) 1);
		order.setCompanyId((long) 1);
		order.setContractCode("ewqwqe");
		order.setGroupId((long) 9);
		orderMapper.insertOrder(order);
	
	}
	
	@Test
	public void testOpMapper() {
	
		OrderProduct orPro=new OrderProduct();
		orPro.setCode("fdaf");
		//orPro.setOpId((long) 1);
		orPro.setName("dsarw");
		orPro.setOpId((long) 1);
		orPro.setNum(2);
		orPro.setOrderId((long) 2);
		orPro.setPrice(new BigDecimal(23));
		orPro.setSku("ewq");
		orPro.setTotal(new BigDecimal(56));
		System.out.println(orPro.toString());
		List<OrderProduct> orderProductList=new ArrayList<OrderProduct>();
		
		orderProductList.add(orPro);
		System.out.println(orderProductList.toString());
		System.out.println(condition);
		int i=condition.insertOrderProductByBatch(orderProductList);
		System.out.println(i);
		
	}
	
	@Test
	public void testJ() {
		
		int i=1;
		int j=2;
		int c=j+i;
		System.out.println(c);
		
		
	}
	
	
	
	
	
	

}
