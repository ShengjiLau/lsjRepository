package com.lcdt.contract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ConditionQueryMapper;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
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
	
	@Autowired
	private ConditionQueryMapper nonautomaticMapper;
	
	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
//	@Autowired
//	private OrderProductService orderProductService;

	@Override
	public int addOrder(OrderDto orderDto) {
		Order order=new Order();
		BeanUtils.copyProperties(orderDto,order);
		int result=orderMapper.insert(order);
		if(orderDto.getOrderProductList()!=null&&orderDto.getOrderProductList().size()!=0){
			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
				orderProduct.setOrderId(order.getOrderId());
			}
			int i=nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
			logger.debug("新增订单商品数量:"+i);
		}
		return result;
	}

	
	@Override
	public int modOrder(OrderDto orderDto) {
	Order order =new Order();
	BeanUtils.copyProperties(orderDto, order);	
	int result=orderMapper.updateByPrimaryKey(order);
	int i=0;
	List<Long> orderProductIdList=nonautomaticMapper.selectOrderProductIdByOrderId(order.getOrderId());
	List<OrderProduct> list1=new ArrayList<OrderProduct>();
	List<OrderProduct> list2=new ArrayList<OrderProduct>();
	if(orderDto.getOrderProductList()!=null&&orderDto.getOrderProductList().size()!=0) {
		for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
			if(orderProduct.getOpId()==null) {
				orderProduct.setOrderId(orderDto.getOrderId());
				list1.add(orderProduct);
			}else {
				list2.add(orderProduct);
				if(orderProductIdList!=null&&orderProductIdList.size()!=0) {
					for(Long opId:orderProductIdList) {
						if(orderProduct.getOpId()==opId) {
							orderProductIdList.remove(opId);
						}
					}
				}
			}
		}
	}
	if(list1.size()>0) {
		i+= nonautomaticMapper.insertOrderProductByBatch(list1);
	}
	if(list2.size()>0) {
		i+= nonautomaticMapper.updateOrderProductByBatch(list2);
	}
	if(orderProductIdList.size()>0) {
		i+= nonautomaticMapper.deleteOrderProducByBatch(orderProductIdList);
	}
	logger.debug("修改订单商品数量为:"+i);
		return result;
	}

	@Override
	public int delOrder(Long orderId) {
		int result=orderMapper.deleteByPrimaryKey(orderId);
		//result+=orderProductService.delOrderProductByOrderId(orderId);
		return result;
	}

	@Override
	public PageInfo<List<Order>> OrderList(OrderDto orderDto) {
		if(orderDto.getPageNum()<=0) {
			orderDto.setPageNum(1);
		}
		if(orderDto.getPageSize()<=0) {
			orderDto.setPageSize(6);
		}
		 PageHelper.startPage(orderDto.getPageNum(),orderDto.getPageSize());
	        PageInfo<List<Order>> page = new PageInfo(nonautomaticMapper.selectByCondition(orderDto));
	        return page;
	}

	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
