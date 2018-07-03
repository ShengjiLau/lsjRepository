package com.lcdt.contract.dao;

import java.util.HashMap;
import java.util.List;

import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月28日
 * @version
 * @Description: TODO 
 */
public interface OverviewMapper {
	
	OrderOverviewDto getOverviewOrderList(OrderOverviewDto orderOverviewDto);
	
	List<Order> getOrderOverviewList(HashMap<String,Object> map);
	
	List<PaymentApplication> getPaymentApplicationListByOrderId(Long orderId);
	
	List<Contract> getContractOverviewList(HashMap<String,Object> map);
	
	Integer countPurchaseOrderByOverviewDto(OverviewDto overviewDto);
	
	Integer countSalesOrderByOverviewDto(OverviewDto overviewDto);
	
	Integer countContractByOverviewDto(OverviewDto overviewDto);
}
