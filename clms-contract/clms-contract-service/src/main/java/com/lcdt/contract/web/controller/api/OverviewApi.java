package com.lcdt.contract.web.controller.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.contract.service.OverviewService;
import com.lcdt.contract.web.dto.OrderCountDto;
import com.lcdt.contract.web.dto.OrderOverviewDto;
import com.lcdt.contract.web.dto.OverviewDto;

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
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('order_count_get')")
	public JSONObject getOrderCount(OverviewDto overviewDto) {
		JSONObject jsonObject = new JSONObject();
		OrderCountDto orderCountDto = overviewService.getOrderCount(overviewDto);
			jsonObject.put("code", 0);
			jsonObject.put("message", "订单数量统计");
			jsonObject.put("data", orderCountDto);
		return jsonObject;
	}
	
	
	@ApiOperation("合同订单概览")
	@GetMapping("/overview/get")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('order_overview_get')")
	public JSONObject getOverview(OverviewDto overviewDto) {
		JSONObject jsonObject = new JSONObject();
		OrderOverviewDto orderOverviewDto = overviewService.getOverviewDtoList(overviewDto);
		jsonObject.put("code", 0);
		jsonObject.put("message", "采购销售概览");
		jsonObject.put("data", orderOverviewDto);
		return jsonObject;
	}
	
	

}
