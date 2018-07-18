package com.lcdt.contract.web.controller.api;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.util.ResponseJsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月2日
 * @version
 * @Description: TODO 
 */

@Api(value = "订单合同统计Api",description = "统计")
@RestController
@RequestMapping("/overview")
public class OverviewApi {
	
	@Autowired
	private OverviewService overviewService;
	
	@ApiOperation("采购销售数量统计")
	@GetMapping("/order/count")
	public JSONObject getOrderCount(@Validated OverviewDto overviewDto, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
			 ResponseJsonUtils.failedResponseJsonWithoutData(bindingResult.getFieldError().getDefaultMessage());
	        }
		OrderCountDto orderCountDto = overviewService.getOrderCount(overviewDto);
		return ResponseJsonUtils.successResponseJson(orderCountDto, "订单数量统计");
	}
	
	
	@ApiOperation("合同订单概览")
	@GetMapping("/overview/get")
	public JSONObject getOverview(@Validated OverviewDto overviewDto, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
			 ResponseJsonUtils.failedResponseJsonWithoutData(bindingResult.getFieldError().getDefaultMessage());
	        }
		
		OrderOverviewDto orderOverviewDto = overviewService.getOverviewDtoList(overviewDto);
		return ResponseJsonUtils.successResponseJson(orderOverviewDto, "采购销售概览");
	}
	
	
	@ApiOperation("根据收付款状况查询订单")
	@GetMapping("/payment/order")
	public JSONObject getOrderListByPayment(OrderDto orderDto) {
		PageBaseDto<OrderDto> pageBaseDto = overviewService.getOrderListByPayment(orderDto);
		return ResponseJsonUtils.successResponseJson(pageBaseDto, "采购销售概览");
	}
	
	
	
	
	

}
