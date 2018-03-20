package com.lcdt.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ConditionQueryMapper;
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
public class OrderProductServiceImpl implements OrderProductService {
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	private ConditionQueryMapper nonautomaticMapper;

	@Override
	public int addOrderProduct(OrderProduct orderProduct) {
		return orderProductMapper.insert(orderProduct);
	}

	@Override
	public int modOrderProduct(OrderProduct orderProduct) {
		return orderProductMapper.updateByPrimaryKey(orderProduct);
	}

	@Override
	public int delOrderProduct(Long OrderProductId) {
		return orderProductMapper.deleteByPrimaryKey(OrderProductId);
	}

	@Override
	public PageInfo<List<OrderProduct>> OrderProductList(Long orderId) {
		 PageInfo<List<OrderProduct>> pageInfo = new PageInfo(nonautomaticMapper.selectByOrderId(orderId));
	        return pageInfo;
	}

	@Override
	public int delOrderProductByOrderId(Long orderId) {
		return orderProductMapper.deleteByOrderId(orderId);
	}

}
