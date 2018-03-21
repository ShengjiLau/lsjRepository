package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.lcdt.contract.dao.OrderProductMapper;
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
	
	@Autowired
	private OrderProductMapper orderProductMapper;


	@Override
	public int addOrder(OrderDto orderDto) {
		Order order=new Order();
		BeanUtils.copyProperties(orderDto,order);
		int result=orderMapper.insertOrder(order);
		if(orderDto.getOrderProductList()!=null&&orderDto.getOrderProductList().size()!=0){
			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
//				int num=orderProduct.getNum();
//				BigDecimal price=orderProduct.getPrice();
//				BigDecimal total=price*num;
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
	//得到数据库中商品表中orderId等于此需要修改的订单的orderId的所有商品的opId
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
					 Iterator<Long> it = orderProductIdList.iterator();
					 while(it.hasNext()) {
						 if(it.next()==orderProduct.getOpId()) {
							 it.remove();
						 }
					 }					 
//					for(Long opId:orderProductIdList) {
//						if(orderProduct.getOpId()==opId) {
//							orderProductIdList.remove(opId);
//						}
//					}
				}
			}
		}
	}
	//list1为没有opId的,是新增的商品
	if(list1.size()>0) {
		i+= nonautomaticMapper.insertOrderProductByBatch(list1);
	}
	//list2为有数据库和修改订单中均有相同商品id,是修改商品
	if(list2.size()>0) {
		i+= nonautomaticMapper.updateOrderProductByBatch(list2);
	}
	//orderProductIdList剩余的商品id不存在于修改订单中,是删除商品
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
	public PageInfo<OrderDto> OrderList(OrderDto orderDto) {
		if(orderDto.getPageNum()<=0) {
			orderDto.setPageNum(1);
		}
		if(orderDto.getPageSize()<=0) {
			orderDto.setPageSize(6);
		}		 
		List<OrderDto> orderDtoList= nonautomaticMapper.selectByCondition(orderDto);
		if(orderDtoList!=null&&orderDtoList.size()!=0) {
			for(OrderDto order:orderDtoList) {
				//获取订单商品
				List<OrderProduct> orderProductList=orderProductMapper.getOrderProductByOrderId(order.getOrderId());
				if(orderProductList!=null&&orderProductList.size()!=0) {
					order.setOrderProductList(orderProductList);
				}
			}   
		}
		PageInfo<OrderDto> pageInfo =new PageInfo<OrderDto>(orderDtoList);
		 PageHelper.startPage(orderDto.getPageNum(),orderDto.getPageSize());   
	        return pageInfo;
	}


	@Override
	public OrderDto selectByPrimaryKey(Long orderId) {
		OrderDto orderDto=orderMapper.selectByPrimaryKey(orderId);
		logger.debug(orderDto.getCreateTime().toString());
		//获取订单下商品
		List<OrderProduct> orderProductList=orderProductMapper.getOrderProductByOrderId(orderDto.getOrderId());
		if(orderProductList!=null&&orderProductList.size()!=0) {
			orderDto.setOrderProductList(orderProductList);
		}
		return orderDto;
	}

	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
