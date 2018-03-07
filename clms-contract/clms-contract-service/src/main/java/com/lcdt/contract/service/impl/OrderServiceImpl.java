package com.lcdt.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.dto.OrderDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月2日下午2:31:06
 * @version 
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderMapper orderMapper;

	@Override
	public int addOrder(Order order) {
		int i =orderMapper.insert(order);
		return i;
	}

	@Override
	public int modOrder(Order order) {
	int i=orderMapper.updateByPrimaryKey(order);
		return i;
	}

	@Override
	public int delOrder(Long orderId) {
		int i=orderMapper.deleteByPrimaryKey(orderId);
		return i;
	}

	@Override
	public PageInfo<List<Order>> OrderList(OrderDto orderDto) {
		 PageHelper.startPage(orderDto.getPageNum(),orderDto.getPageSize());
	        PageInfo page = new PageInfo(orderMapper.selectByCondition(orderDto));
	        return page;
	}

	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
