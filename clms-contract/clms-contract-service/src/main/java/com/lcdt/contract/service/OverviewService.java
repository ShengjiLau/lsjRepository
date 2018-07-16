package com.lcdt.contract.service;

import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;
import com.lcdt.contract.web.dto.PageBaseDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年6月29日
 * @version
 * @Description: TODO 
 */
public interface OverviewService {

	OrderOverviewDto getOverviewDtoList(OverviewDto overviewDto);
	
	
	HashMap<String,Integer> countOrder(OverviewDto overviewDto);
	
	OrderCountDto getOrderCount(OverviewDto overviewDto);
	
	PageBaseDto<OrderDto> getOrderListByPayment(OrderDto orderDto);
	
	
}
