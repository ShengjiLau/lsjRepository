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
		
		List<Date> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		TreeMap<Date,Hashtable<Integer,BigDecimal>> trendDiagram = new TreeMap<Date,Hashtable<Integer,BigDecimal>>();
		for (Date date : dateList) {
			int m = 0;
			BigDecimal b1 = new BigDecimal(0);
			for (Order order : orderList) {
				if (DateUtils.isSameDay(date,dateCovertFromat(order.getCreateTime()))) {
					m++;
					b1 = b1.add(order.getSummation());
				}
			}
			Hashtable<Integer,BigDecimal> ht1 = new Hashtable<Integer,BigDecimal>();
			ht1.put(m, b1);
			trendDiagram.put(date, ht1);
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
		List<Date> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		TreeMap<Date, Integer> orderCountByDate = new TreeMap<Date, Integer>();
		for (Date date : dateList) {
			int m = 0;
			for (Order order : orderList) {
				if (DateUtils.isSameDay(date,dateCovertFromat(order.getCreateTime()))) {
					m++;
				}
			}
			orderCountByDate.put(date, m);
		}
		orderCountDto.setOrderCountByDate(orderCountByDate);
		
		return orderCountDto;
	}
	
	private HashMap<String, Object> ConvertDtoToMap(OverviewDto overviewDto) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("companyId", overviewDto.getCompanyId());
		map.put("beginTime", overviewDto.getBeginTime());
		map.put("endTime", overviewDto.getEndTime());
		map.put("groups", overviewDto.getGroups());
		
		return (HashMap<String, Object>) map;
	}
	
	private List<Date> finddatesList(String beginTime,String endTime){
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

		List<Date> datesList = new LinkedList<Date>();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		datesList.add(d1);
		while (d2.after(c1.getTime())) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			datesList.add(c1.getTime());
		}
		
		return datesList;
	}
	
	private Date dateCovertFromat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		Date newDate = null;
		try {
			newDate = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	
	
	
	
	

}
