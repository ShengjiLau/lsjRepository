package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.dao.OverviewMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月29日
 * @version
 * @Description: TODO 
 */
@Service
public class OverviewServiceImpl implements OverviewService {
	
	@Autowired
	private OverviewMapper overviewMapper;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	

	@Override
	public OrderOverviewDto getOverviewDtoList(OverviewDto overviewDto) {
		OrderOverviewDto orderOverviewDto = new OrderOverviewDto();
		overviewDto.setCompanyId(SecurityInfoGetter.getCompanyId());
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
		List<OrderProduct> orderProductList = null;
		String [] ss = new String[orderIds.size()];
		orderIds.toArray(ss);
		if (ss.length > 0) {
			orderProductList = orderProductMapper.selectProductByOrderIds(ss);
		}
		
		
		BigDecimal y = new BigDecimal(orderList.size());
		BigDecimal u = new BigDecimal(0);
		if (null != orderProductList) {
			 u = new BigDecimal(orderProductList.size());
		}
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
		
		List<Contract> contractList = overviewMapper.getContractOverviewList(map);
		int p = 0;
		int a = 0;
		int d = 0;
		int g = 0;
		int k = 0;
		for (Contract contract : contractList) {
			//待发布
			if (2 == contract.getContractStatus()) {
				p++;
				continue;
			}
			//待生效
			if (1 == contract.getContractStatus()) {
				a++;
				continue;
			}
			//已失效
			if (3 == contract.getContractStatus()) {
				d++;
			}
			//已取消
			if (4 == contract.getContractStatus()) {
				g++;
			}
		}
		k = contractList.size()-p-a-d-g;
		orderOverviewDto.setWaitingReleasedContract(p);
		orderOverviewDto.setWaitingEffectiveContract(a);
		orderOverviewDto.setInEffectContract(k);
		orderOverviewDto.setDefunctContract(d);
		
		List<String> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		TreeMap<String,Hashtable<Integer,BigDecimal>> trendDiagram = new TreeMap<String,Hashtable<Integer,BigDecimal>>();
		for (String s : dateList) {
			int m = 0;
			BigDecimal b1 = new BigDecimal(0);
			for (Order order : orderList) {
				if (s.equals(dateCovertFromat(order.getCreateTime()))) {
					m++;
					b1 = b1.add(order.getSummation());
				}
			}
			Hashtable<Integer,BigDecimal> ht1 = new Hashtable<Integer,BigDecimal>();
			ht1.put(m, b1);
			trendDiagram.put(s, ht1);
		}
		
		orderOverviewDto.setTrendDiagram(trendDiagram);
		return orderOverviewDto;
	}
	
	@Override
	public HashMap<String,Integer> countOrder(OverviewDto overviewDto){
		overviewDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		Integer purchaseOrderCount = overviewMapper.countPurchaseOrderByOverviewDto(overviewDto);
		Integer salesOrderCount = overviewMapper.countSalesOrderByOverviewDto(overviewDto);
		map.put("purchaseOrderCount", purchaseOrderCount);
		map.put("salesOrderCount", salesOrderCount);
		
		return map;
	}
	
	
	@Override
	public OrderCountDto getOrderCount(OverviewDto overviewDto) {
		OrderCountDto orderCountDto = new OrderCountDto();
		overviewDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		HashMap<String, Object> map = ConvertDtoToMap(overviewDto);
		List<Order> orderList = overviewMapper.getOrderOverviewList(map);
		
		Integer purchaseOrderCount = overviewMapper.countPurchaseOrderByOverviewDto(overviewDto);
		Integer salesOrderCount = overviewMapper.countSalesOrderByOverviewDto(overviewDto);
		orderCountDto.setPurchaseOrderCount(purchaseOrderCount);
		orderCountDto.setSalesOrderCount(salesOrderCount);
		List<String> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		TreeMap<String, Integer> purchaseOrderCountByDate = new TreeMap<String, Integer>();
		TreeMap<String, Integer> salesOrderCountByDate = new TreeMap<String, Integer>();
		for (String s : dateList) {
			int m = 0;
			int n = 0;
			for (Order order : orderList) {
				if (0 == order.getOrderType()) {
					if (s.equals(dateCovertFromat(order.getCreateTime()))) {
						m++;
					}
				}
				if (1 == order.getOrderType()) {
					if (s.equals(dateCovertFromat(order.getCreateTime()))) {
						n++;
					}
				}
			}
			purchaseOrderCountByDate.put(s, m);
			salesOrderCountByDate.put(s, n);
		}
		orderCountDto.setPurchaseOrderCountByDate(purchaseOrderCountByDate);
		orderCountDto.setSalesOrderCountByDate(salesOrderCountByDate);
		return orderCountDto;
	}
	
	private HashMap<String, Object> ConvertDtoToMap(OverviewDto overviewDto) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("type", overviewDto.getType());
		map.put("companyId", overviewDto.getCompanyId());
		map.put("beginTime", overviewDto.getBeginTime());
		map.put("endTime", overviewDto.getEndTime());
		map.put("groups", overviewDto.getGroups());
		
		return (HashMap<String, Object>) map;
	}
	
	private List<String> finddatesList(String beginTime,String endTime){
		String pattern = "^[0-9]{4}[-][0-9]{2}[-][0-9]{2}";
		if (!Pattern.matches(pattern, beginTime) || !Pattern.matches(pattern, endTime)) {
			throw new RuntimeException("时间格式不正确!");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse(beginTime);
			d2 = sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<String> datesList = new LinkedList<String>();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		datesList.add(beginTime);
		while (d2.after(c1.getTime())) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			datesList.add(dateCovertFromat(c1.getTime()));
		}
		
		return datesList;
	}
	
	private String dateCovertFromat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		
		return s;
	}
	
	
	
	
	
	

}
