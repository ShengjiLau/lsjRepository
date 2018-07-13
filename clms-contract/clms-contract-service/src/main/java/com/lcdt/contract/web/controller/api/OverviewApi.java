package com.lcdt.contract.web.controller.api;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;
import com.lcdt.contract.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月2日
 * @version
 * @Description: TODO 
 */

@Api(value="订单合同统计Api",description="统计")
@RestController
@RequestMapping("/overview")
public class OverviewApi {
	
	@Autowired
	private OverviewService overviewService;
	
	@ApiOperation("采购销售数量统计")
	@GetMapping("/order/count")
	public JSONObject getOrderCount(@Validated OverviewDto overviewDto,BindingResult bindingResult) {
		JSONObject jsonObject = new JSONObject();
		 if (bindingResult.hasErrors()) {
	            jsonObject.put("code", -1);
	            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
	            return jsonObject;
	        }
		
		OrderCountDto orderCountDto = overviewService.getOrderCount(overviewDto);
			jsonObject.put("code", 0);
			jsonObject.put("message", "订单数量统计");
			jsonObject.put("data", orderCountDto);
		return jsonObject;
	}
	
	
	@ApiOperation("合同订单概览")
	@GetMapping("/overview/get")
	public JSONObject getOverview(@Validated OverviewDto overviewDto,BindingResult bindingResult) {
		JSONObject jsonObject = new JSONObject();
		 if (bindingResult.hasErrors()) {
	            jsonObject.put("code", -1);
	            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
	            return jsonObject;
	        }
		
		OrderOverviewDto orderOverviewDto = overviewService.getOverviewDtoList(overviewDto);
		jsonObject.put("code", 0);
		jsonObject.put("message", "采购销售概览");
		jsonObject.put("data", orderOverviewDto);
		return jsonObject;
	}
	
	@ApiOperation("根据收付款状况查询订单")
	@GetMapping("/overview/getOrder")
	public JSONObject getOrderListByPayment(@Validated OverviewDto overviewDto,BindingResult bindingResult) {
		JSONObject jsonObject = new JSONObject();
		 if (bindingResult.hasErrors()) {
	            jsonObject.put("code", -1);
	            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
	            return jsonObject;
	        }
		PageBaseDto<OrderDto> pageBaseDto = overviewService.getOrderListByPayment(overviewDto);
		jsonObject.put("code", 0);
		jsonObject.put("message", "采购销售概览");
		jsonObject.put("data", pageBaseDto);
		return jsonObject;
		
	}
	
	
	
	
	

}
