package com.lcdt.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderProductService;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月6日下午2:31:48
 * @version
 */
@Service
@Transactional
public class OrderProductServiceimpl implements OrderProductService {
	@Autowired
	private OrderProductMapper orderProductMapper;

	@Override
	public int addOrderProduct(OrderProduct orderProduct) {
		int i =orderProductMapper.insert(orderProduct);
		return i;
	}

	@Override
	public int modOrderProduct(OrderProduct orderProduct) {
		int i=orderProductMapper.updateByPrimaryKey(orderProduct);
		return i;
	}

	@Override
	public int delOrderProduct(Long OrderProductId) {
		int i=orderProductMapper.deleteByPrimaryKey(OrderProductId);
		return i;
	}

	@Override
	public PageInfo<List<OrderProduct>> OrderProductList(Long orderId) {
		 PageInfo pageInfo = new PageInfo(orderProductMapper.selectByOrderId(orderId));
	        return pageInfo;
		
	}

}
