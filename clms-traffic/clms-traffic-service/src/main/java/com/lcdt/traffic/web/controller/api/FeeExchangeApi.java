package com.lcdt.traffic.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.web.dto.FeeExchangeDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
@RequestMapping("/fee/feeExchange")
@Api(description="收付款记录操作API")
@RestController
public class FeeExchangeApi {
	
	@Autowired
	private FeeExchangeService feeExchangeService;
	
	@PostMapping("/add")
	@ApiOperation("新增收付款记录")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_exchange_add')")
	public JSONObject addFeeExchange(List<FeeExchange> feeExchangeList) {
		JSONObject jsonObject =new JSONObject();
		int i=feeExchangeList.size();		
		int j=feeExchangeService.insertFeeExchangeByBatch(feeExchangeList);
		if(i==j) {
			jsonObject.put("code",0);
			jsonObject.put("msg","新增收付款记录成功");
			return jsonObject;
		}else {
			throw new RuntimeException("插入收付款记录出现异常");
		}
	}
	
	
	
	@GetMapping("/list")
	@ApiOperation("查询收付款记录列表")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_exchange_list')")
	public JSONObject getFeeExchange(FeeExchangeDto feeExchangeDto) {
		JSONObject jsonObject =new JSONObject();
		JSONArray jsonArray =new JSONArray();
		PageInfo<FeeExchange> page=feeExchangeService.getFeeExchangeList(feeExchangeDto);
		jsonArray.add(page.getTotal());
		jsonArray.add(page.getList());
		jsonObject.put("code",0);
		jsonObject.put("msg","收付款记录列表");
		jsonObject.put("data",jsonArray);
		
		
		return jsonObject;
	}
	
	
	@PostMapping("/cancel")
	@ApiOperation("批量取消收付款记录")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_exchange_cancel')")
	public JSONObject cancelFeeExchange(Long[] feeExchanges) {
		JSONObject jsonObject =new JSONObject();
		
		
		
		
		return jsonObject;
	}
	
	
	
	
	
	
	
	

}
