package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.ConditionQueryMapper;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.dao.OverviewMapper;
import com.lcdt.contract.dao.PaymentApplicationMapper;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.dto.TrendDiagramDto;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.rpc.WarehouseRpcService;

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
	
	@Autowired
	private ConditionQueryMapper nonautomaticMapper;
	
	@Autowired
    private PaymentApplicationMapper paymentApplicationMapper;

    @Reference
    private TrafficRpc trafficRpc;
    
    @Reference
    private GroupWareHouseRpcService groupWareHouseRpcService;
    
    @Reference
    private WarehouseRpcService warehouseRpcService;

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
 /**
  * 查询订单合同概览，主要逻辑为查询相应时间段的订单，再在业务逻辑里进行各项统计，没有使用复杂的sql语句直接进行统计。
  */
	@Override
	public OrderOverviewDto getOverviewDtoList(OverviewDto overviewDto) {
		List<String> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		if (dateList.size() > 30) {
			throw new RuntimeException("超出30天最长时间段！");
		}
		OrderOverviewDto orderOverviewDto = new OrderOverviewDto();
		overviewDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		HashMap<String, Object> map = ConvertDtoToMap(overviewDto);
		List<Order> orderList = overviewMapper.getOrderOverviewList(map);
		//q统计未收款数量
		int q = 0;
		//w统计已收款数量
		int w = 0;
		//r统计订单总额
		BigDecimal r = new BigDecimal(0);
		List<Long> orderIds = new ArrayList<Long>(orderList.size());
		for (Order order : orderList) {
			orderIds.add(order.getOrderId());
			r = r.add(order.getSummation());
			//e统计所有确认收款的收款单的收款金额
			BigDecimal e = new BigDecimal(0);
			List<PaymentApplication> paymentApplicationList = overviewMapper.getPaymentApplicationListByOrderId(order.getOrderId());
			if (null == paymentApplicationList || 0 == paymentApplicationList.size()) {
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
		//countOrderProduct统计所有的商品数量
		int countOrderProduct = 0;
		if (orderIds.size() > 0) {
			countOrderProduct = orderProductMapper.getProductCountByOrderIds(orderIds);
		}
		//y 统计所有订单数量
		BigDecimal y = new BigDecimal(orderList.size());
		//u统计所有的商品数量
		BigDecimal u = new BigDecimal(countOrderProduct);
		//t计算得出客单价
		BigDecimal t = new BigDecimal(0);
		//i计算得出商品均价
		BigDecimal i = new BigDecimal(0);
		if (0 != y.intValue()) {
			 t =r.divide(y,2, BigDecimal.ROUND_HALF_DOWN);
		}
		if (0 != u.intValue()) {
			 i =r.divide(u,2, BigDecimal.ROUND_HALF_DOWN);//.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}
		orderOverviewDto.setMoneyReceivedOrder(w);
		//订单总数-已收款-未收款 = 收款中
		orderOverviewDto.setMoneyCollectingOrder(orderList.size()-w-q);
		orderOverviewDto.setWaitingMoneyGatheringOrder(q);
		orderOverviewDto.setNumOfOrders(orderList.size());
		orderOverviewDto.setTotalOrderAmount(r);
		orderOverviewDto.setCustomerUnitPrice(t);
		orderOverviewDto.setNumOfProduct(countOrderProduct);
		orderOverviewDto.setAveragePriceOfProduce(i);
		
		List<Contract> contractList = overviewMapper.getContractOverviewList(map);
		//合同待发布
		int p = 0;
		//合同待生效
		int a = 0;
		//合同已失效
		int d = 0;
		//合同已取消
		int g = 0;
		//计算合同生效中
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
		//根据日期获取概览订单数量和金额
		List<TrendDiagramDto> trendDiagramList = new ArrayList<TrendDiagramDto>(dateList.size());
		for (String s : dateList) {
			TrendDiagramDto trendDiagramDto = new TrendDiagramDto();
			trendDiagramDto.setDate(s);
			//m统计某日的订单数量
			int m = 0;
			//b1 统计某日的金额数量
			BigDecimal b1 = new BigDecimal(0);
			for (Order order : orderList) {
				if (s.equals(dateCovertFromat(order.getCreateTime()))) {
					m++;
					b1 = b1.add(order.getSummation());
				}
			}
			trendDiagramDto.setOrderCount(m);
			trendDiagramDto.setOrderMoneyAmount(b1);
			trendDiagramList.add(trendDiagramDto);
		}
		orderOverviewDto.setTrendDiagramList(trendDiagramList);
		
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
	
	
	/**
	 * 统计采购订单和销售订单某时间段内的数量以及对应的某天的数量，用于生成首页。
	 */
	@Override
	public OrderCountDto getOrderCount(OverviewDto overviewDto) {
		List<String> dateList = finddatesList(overviewDto.getBeginTime(), overviewDto.getEndTime());
		if (dateList.size() > 30) {
			throw new RuntimeException("超出30天最长时间段！");
		}
		OrderCountDto orderCountDto = new OrderCountDto();
		overviewDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		//HashMap<String, Object> map = ConvertDtoToMap(overviewDto);
		List<Order> orderList = overviewMapper.getOrderListByOverviewDto(overviewDto);
		
		Integer purchaseOrderCount = overviewMapper.countPurchaseOrderByOverviewDto(overviewDto);
		Integer salesOrderCount = overviewMapper.countSalesOrderByOverviewDto(overviewDto);
		orderCountDto.setPurchaseOrderCount(purchaseOrderCount);
		orderCountDto.setSalesOrderCount(salesOrderCount);
		
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
	
	/**
	 * 依据收付款状况查询订单
	 */
	@Override
	public PageBaseDto<OrderDto> getOrderListByPayment(OrderDto orderDto){
		orderDto.setCompanyId(SecurityInfoGetter.getCompanyId());
		orderDto.setIsDraft((short) 1);
		List<OrderDto> orderDtoList = nonautomaticMapper.selectByCondition(orderDto);
		if (null ==orderDto.getPaymentType() || 3 == orderDto.getPaymentType()) {
			return getpageInfo(orderDto.getPageSize(),orderDto.getPageNum(),getOrderDtoList(orderDtoList));
		}
		BigDecimal r = new BigDecimal(0);
		//List<Long> orderIds = new ArrayList<Long>(orderList.size());
		List<OrderDto> alreadyPayOrderList = new LinkedList<OrderDto>();
		List<OrderDto> notPayOrderList = new LinkedList<OrderDto>();
		List<OrderDto> payingOrderList = new LinkedList<OrderDto>();
		for (OrderDto orderDto2 : orderDtoList) {
			r = r.add(orderDto2.getSummation());
			//e统计所有确认收款的收款单的收款金额
			BigDecimal e = new BigDecimal(0);
			List<PaymentApplication> paymentApplicationList = overviewMapper.getPaymentApplicationListByOrderId(orderDto2.getOrderId());
			if (null == paymentApplicationList || 0 == paymentApplicationList.size()) {
				notPayOrderList.add(orderDto2);
				continue;
			}
			for (PaymentApplication PaymentApplication : paymentApplicationList) {
				if (null != PaymentApplication.getPaymentTimeSure()) {
					e = e.add(PaymentApplication.getPaymentSum());
				}
			}
			if (e == orderDto2.getSummation()) {
				alreadyPayOrderList.add(orderDto2);
				continue;
			}
			payingOrderList.add(orderDto2);
		}
		if (0 == orderDto.getPaymentType()) {
			return getpageInfo(orderDto.getPageSize(),orderDto.getPageNum(),getOrderDtoList(notPayOrderList));
		}
		if (1 == orderDto.getPaymentType()) {
			return getpageInfo(orderDto.getPageSize(),orderDto.getPageNum(),getOrderDtoList(payingOrderList));
		}
		if (2 == orderDto.getPaymentType()) {
			return getpageInfo(orderDto.getPageSize(),orderDto.getPageNum(),getOrderDtoList(alreadyPayOrderList));
		}
		
		return null;
	}
	
	/**
	 * 逻辑分页
	 */
	private PageBaseDto<OrderDto> getpageInfo(Integer pageSize,Integer pageNum,List<OrderDto> orderDtoList){
		List<OrderDto> orderDtoListNew = new LinkedList<OrderDto>();
		if (null == pageSize || 0 >= pageSize) {
			pageSize = 10;
		}
		if (null == pageNum || 0 >= pageNum) {
			pageNum = 1;
		}
		PageBaseDto<OrderDto> pageBaseDto = new PageBaseDto<OrderDto>();
		if (null == orderDtoList) {
			pageBaseDto.setTotal(0);
			pageBaseDto.setList(orderDtoListNew);
			return pageBaseDto;
		}
		pageBaseDto.setTotal(orderDtoList.size());
		if (pageSize*pageNum > orderDtoList.size()) {
			orderDtoListNew.addAll(orderDtoList.subList((pageNum - 1)*pageSize, orderDtoList.size()));
		}else {
			orderDtoListNew.addAll(orderDtoList.subList((pageNum - 1)*pageSize, pageSize*pageNum));
		}
		pageBaseDto.setList(orderDtoListNew);
		return pageBaseDto;
	}
	
	/**
	 * Order转化为OrderDto
	 */
	private List<OrderDto> getOrderDtoList(List<OrderDto> orderDtoList){
		if (0 == orderDtoList.size()) {
			return null;
		}
		for (OrderDto orderDto : orderDtoList) {
			List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(orderDto.getOrderId());
			orderDto.setOrderProductList(orderProductList);
			//通过RPC查询添加计划状态
        	if (null != orderDto.getTrafficPlan() && !"".equals(orderDto.getTrafficPlan())) {
        		WaybillPlan waybillPlan = trafficRpc.getWaybillPlanBySerialNo(orderDto.getTrafficPlan());
        		if (null != waybillPlan) {
        			orderDto.setTrafficPlanStatus(waybillPlan.getPlanStatus());
        		}
        	}
        	if (null != orderDto.getWarehousePlan() && !"".equals(orderDto.getWarehousePlan())) {
        		if (0 == orderDto.getOrderType()) {
        			InWarehousePlan inWarehousePlan = warehouseRpcService.getInWarehousePlanBySerialNo(orderDto.getWarehousePlan());
        			orderDto.setWarehousePlanStatus(inWarehousePlan.getPlanStatus());
        		}
        		if (1 == orderDto.getOrderType()) {
        			OutWarehousePlan outWarehousePlan = warehouseRpcService.getOutWarehousePlanBySerialNo(orderDto.getWarehousePlan());
        			if (null != outWarehousePlan) {
        				orderDto.setWarehousePlanStatus(outWarehousePlan.getPlanStatus());
        			}
        		}
        	}
		}
		 //获取付款状态 付款单记录 开票记录信息
        List<Map> paymentList = nonautomaticMapper.paymentInfo(orderDtoList,orderDtoList.get(0).getCompanyId());
        List<Map> billingRecordList = nonautomaticMapper.billingInfo(orderDtoList,orderDtoList.get(0).getCompanyId());
        for (OrderDto orderDto : orderDtoList) {
        	 //整合付款单信息
            if (paymentList.size() > 0) {
                for (int i = 0; i < paymentList.size(); i++) {
                    Map map = paymentList.get(i);
                    if ((long) map.get("order_id") == orderDto.getOrderId()) {
                            orderDto.setPaymentStatus(new Short(map.get("payment_status").toString()));
                        	orderDto.setPaymentNum(map.get("payment_num").toString());
                        	orderDto.setPaymentSum(map.get("payment_sum").toString());
                            break;
                        } else if (i == paymentList.size() - 1) {
                        	orderDto.setPaymentStatus(new Short("0"));
                        	orderDto.setPaymentNum("0");
                        	orderDto.setPaymentSum("0");
                        }
                    }
                } else {
                	orderDto.setPaymentStatus(new Short("0"));
                	orderDto.setPaymentNum("0");
                }
                //整合开票记录信息
                if (billingRecordList.size() > 0) {
                    for (int j = 0; j < billingRecordList.size(); j++) {
                        Map map = billingRecordList.get(j);
                        if ((long) map.get("order_id") == orderDto.getOrderId()) {
                        	orderDto.setBillingRecordNum(map.get("billing_record_num").toString());
                            break;
                        } else if (j == billingRecordList.size() - 1) {
                        	orderDto.setBillingRecordNum("0");
                        }
                    }
                } else {
                	orderDto.setBillingRecordNum("0");
                }
        }
		return orderDtoList;
	}
	
	/**
	 * 将OverviewDto转化为map集合
	 * @param overviewDto
	 * @return
	 */
	private HashMap<String, Object> ConvertDtoToMap(OverviewDto overviewDto) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("type", overviewDto.getType());
		map.put("companyId", overviewDto.getCompanyId());
		map.put("beginTime", overviewDto.getBeginTime());
		map.put("endTime", overviewDto.getEndTime());
		if (null != overviewDto.getGroups() && !"".equals(overviewDto.getGroups())) {
			map.put("groups", convertStringToLong(overviewDto.getGroups()));
		}
		
		return (HashMap<String, Object>) map;
	}
	
	/**
	 * 获取一段时间的每日的日期集合，并将日期转化为字符串，返回日期字符串集合
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
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
	
	/**
	 * 将日期转化为规定格式字符串
	 * @param date
	 * @return
	 */
	private String dateCovertFromat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		return s;
	}
	
	/**
	 * 将规定格式的String字符串转化为Long数组
	 * @param s
	 * @return
	 */
	private Long[] convertStringToLong(String s) {
		String sn = s+",";
		String pattern = "^(([0-9]+)([,])){0,}$";
		if (!Pattern.matches(pattern, sn)) {
			throw new RuntimeException("传入的业务组groups格式不正确！");
		}
		String[] ss = s.split(",");
		Long[] groups = new Long[ss.length];
		for (int i = 0; i < ss.length; i++) {
			groups[i] = Long.parseLong(ss[i]);
		}
		
		return groups;
	}



	
	

}
