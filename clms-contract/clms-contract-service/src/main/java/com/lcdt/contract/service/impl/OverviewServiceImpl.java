package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.dao.OverviewMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月29日
 * @version
 * @Description: TODO 
 */
public class OverviewServiceImpl implements OverviewService {
	
	@Autowired
	private OverviewMapper overviewMapper;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	

	@Override
	public OrderOverviewDto getOverviewDtoList(OverviewDto overviewDto) {
		OrderOverviewDto orderOverviewDto = new OrderOverviewDto();
		HashMap<String, Object> map = ConvertDtoToMap(overviewDto);
		List<Order> orderList = overviewMapper.getOrderOverviewList(map);
		int q = 0;
		int w = 0;
		BigDecimal r = new BigDecimal(0);
		List<String> orderIds = new ArrayList<String>(orderList.size());
		for (Order order : orderList) {
			orderIds.add(order.getOrderId().toString());
			r = r.add(order.getSummation());
			BigDecimal e = new BigDecimal(0);
			List<PaymentApplication> paymentApplicationList = overviewMapper.getPaymentApplicationListByOrderId(order.getOrderId());
			if (null == paymentApplicationList || 0 ==paymentApplicationList.size()) {
				q++;
				continue;
			}
			for (PaymentApplication PaymentApplication : paymentApplicationList) {
				
				if (null != PaymentApplication.getPaymentTimeSure()) {
					e = e.add(PaymentApplication.getPaymentSum());
				}
			}
			if (e == order.getSummation()) {
				w++;
			}
		}
		String [] ss = new String[orderIds.size()];
		orderIds.toArray(ss);
		List<OrderProduct> orderProductList = orderProductMapper.selectProductByOrderIds(ss);
		
		BigDecimal y = new BigDecimal(orderList.size());
		BigDecimal u = new BigDecimal(orderProductList.size());
		BigDecimal t =r.divide(y).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal i =r.divide(u).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		orderOverviewDto.setMoneyReceivedOrder(w);
		orderOverviewDto.setMoneyCollectingOrder(orderList.size()-w-q);
		orderOverviewDto.setWaitingMoneyGatheringOrder(q);
		orderOverviewDto.setNumOfOrders(orderList.size());
		orderOverviewDto.setTotalOrderAmount(r);
		orderOverviewDto.setCustomerUnitPrice(t);
		orderOverviewDto.setNumOfProduct(orderProductList.size());
		orderOverviewDto.setAveragePriceOfProduce(i);
		
		
		
		return orderOverviewDto;
	}
	
	
	private HashMap<String, Object> ConvertDtoToMap(OverviewDto overviewDto) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("companyId", overviewDto.getCompanyId());
		map.put("beginTime", overviewDto.getBeginTime());
		map.put("endTime", overviewDto.getEndTime());
		map.put("groups", overviewDto.getGroups());
		
		return (HashMap<String, Object>) map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
