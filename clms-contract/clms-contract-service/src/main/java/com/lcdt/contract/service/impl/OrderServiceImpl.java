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
		BigDecimal aTotal =new 	BigDecimal(0);// aTotal为所有商品总价格
		if(null!=orderDto.getOrderProductList()&&orderDto.getOrderProductList().size()!=0){
			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
				BigDecimal num = orderProduct.getNum();
				BigDecimal price=orderProduct.getPrice();
				//计算单个商品总价
				BigDecimal total=num.multiply(price);
				aTotal=aTotal.add(total);
				orderProduct.setTotal(total);
			}
		}
		Order order=new Order();
		BeanUtils.copyProperties(orderDto,order);
		order.setSummation(aTotal);
		int result=orderMapper.insertOrder(order);
		if(null!=orderDto.getOrderProductList()&&orderDto.getOrderProductList().size()!=0){
			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
				//为每个商品添加OrderId
				orderProduct.setOrderId(order.getOrderId());
			}
			int i=nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
			logger.debug("新增订单商品数量:"+i);
		}
		return result;
	}

	
	@Override
	public int modOrder(OrderDto orderDto) {
		BigDecimal aTotal =new 	BigDecimal(0);
		if(null!=orderDto.getOrderProductList()&&orderDto.getOrderProductList().size()!=0){
			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
				BigDecimal num = orderProduct.getNum();
				BigDecimal price=orderProduct.getPrice();
				BigDecimal total=num.multiply(price);
				aTotal=aTotal.add(total);
				orderProduct.setTotal(total);
			}
		}
		Order order =new Order();
		BeanUtils.copyProperties(orderDto, order);
		order.setSummation(aTotal);
		int result=orderMapper.updateByPrimaryKey(order);
		int i=0;
		List<Long> orderProductIdList=nonautomaticMapper.selectOrderProductIdByOrderId(order.getOrderId());
		nonautomaticMapper.deleteOrderProducByBatch(orderProductIdList);
		i+= nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
		
		
		//得到数据库中商品表中orderId等于此需要修改的订单的orderId的所有商品的opId
//		List<Long> orderProductIdList=nonautomaticMapper.selectOrderProductIdByOrderId(order.getOrderId());
//		List<OrderProduct> list1=new ArrayList<OrderProduct>();
//		List<OrderProduct> list2=new ArrayList<OrderProduct>();
//		if(null!=orderDto.getOrderProductList()&&orderDto.getOrderProductList().size()!=0) {
//			for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
//				if(null==orderProduct.getOpId()) {
//					orderProduct.setOrderId(orderDto.getOrderId());
//					list1.add(orderProduct);
//				}else {
//					list2.add(orderProduct);
//					logger.debug("测试输出list2");
//					if(null!=orderProductIdList&&orderProductIdList.size()!=0) {
//						 Iterator<Long> it = orderProductIdList.iterator();
//						 logger.debug("测试输出点位a");
//						 while(it.hasNext()) {
//							 logger.debug("测试输出点位b");
//							 Long ipl=it.next();
//							 logger.debug("in.next():"+ipl);
//							 logger.debug("orderProduct.getOpId():"+orderProduct.getOpId());
//							 if(ipl==orderProduct.getOpId()) {
//								 logger.debug("测试输出点位c");
//								 it.remove();
//								 logger.debug("测试输出点位d");
//							 }
//						 }
	// for循环效率高 , 但遍历过程不可执行remove操作					 
	//					for(Long opId:orderProductIdList) {
	//						if(orderProduct.getOpId()==opId) {
	//							orderProductIdList.remove(opId);
	//						}
	//					}
//					}
//				}
//			}
//		}
		//list1为没有opId的,是新增的商品
//		if(list1.size()>0) {
//			i+= nonautomaticMapper.insertOrderProductByBatch(list1);
//			logger.debug("新增订单商品"+i);
//		}
		//orderProductIdList剩余的商品id不存在于修改订单中,是删除商品
//		if(orderProductIdList.size()>0) {
//			i+= nonautomaticMapper.deleteOrderProducByBatch(orderProductIdList);
//			logger.debug("删除订单商品"+i);
//		}
		//list2为有数据库和修改订单中均有相同商品id,是修改商品
//		if(list2.size()>0) {
//			i+= nonautomaticMapper.updateOrderProductByBatch(list2);
//			logger.debug("修改订单商品"+i);
//		}
//		
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
			orderDto.setPageSize(0);//设置为0是查询全部
		}	
		PageHelper.startPage(orderDto.getPageNum(),orderDto.getPageSize());//分页
		List<OrderDto> orderDtoList= nonautomaticMapper.selectByCondition(orderDto);
		if(null!=orderDtoList&&orderDtoList.size()!=0) {
			for(OrderDto order:orderDtoList) {
				//获取订单商品
				List<OrderProduct> orderProductList=orderProductMapper.getOrderProductByOrderId(order.getOrderId());
				if(null!=orderProductList&&orderProductList.size()!=0) {
					order.setOrderProductList(orderProductList);
				}
			}   
		}
		PageInfo<OrderDto> pageInfo=new PageInfo<OrderDto>(orderDtoList);
		return pageInfo;
	}


	@Override
	public OrderDto selectByPrimaryKey(Long orderId) {
		OrderDto orderDto=orderMapper.selectByPrimaryKey(orderId);
		if(null!=orderDto) {
			logger.debug("订单id为"+orderDto.getOrderId());
			//获取订单下商品
			List<OrderProduct> orderProductList=orderProductMapper.getOrderProductByOrderId(orderDto.getOrderId());
			if(null!=orderProductList&&orderProductList.size()!=0) {
				orderDto.setOrderProductList(orderProductList);
			}
		}	
		
		
		return orderDto;
	}

	
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
