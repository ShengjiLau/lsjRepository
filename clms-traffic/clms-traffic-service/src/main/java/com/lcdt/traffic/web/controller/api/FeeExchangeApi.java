package com.lcdt.traffic.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.web.dto.FeeExchangeDto;
import com.lcdt.traffic.web.dto.FeeExchangeListDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	public JSONObject addFeeExchange(@Validated FeeExchangeListDto feeExchangeListDto,BindingResult bindingResult) {
		List<FeeExchange> feeExchangeList =feeExchangeListDto.getFeeExchangeList();
		JSONObject jsonObject =validResponse(bindingResult);
		if(!jsonObject.isEmpty()) {
			return jsonObject;
		}		
		int result=feeExchangeService.insertFeeExchangeByBatch(feeExchangeList);
		if(result>0) {
			jsonObject.put("code",0);
			jsonObject.put("message","新增收付款记录成功");
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
	public JSONObject cancelFeeExchange(@ApiParam(value="收付款记录id数组",required=true)@RequestParam Long[] feeExchanges) {
		JSONObject jsonObject =new JSONObject();
		int result=feeExchangeService.updateSetCancelOk(feeExchanges);
		if(result>0) {
			jsonObject.put("code",0);
			jsonObject.put("msg","取消成功");
			return jsonObject;
		}else {
			throw new RuntimeException("取消收付款记录出现异常");
		}	
	}
	
	
	@GetMapping("/select")
	@ApiOperation("单个收付款记录详情")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_exchange_select')")
	public JSONObject selectFeeExchange(Long feeExchangeId) {
		JSONObject jsonObject =new JSONObject();
		FeeExchange fe=feeExchangeService.selectFeeExchangeById(feeExchangeId);
		if(null!=fe) {
			jsonObject.put("code",0);
			jsonObject.put("msg","收付款详情");
			jsonObject.put("data",fe);
		}else {
			throw new RuntimeException("查询出现异常");
		}
		return jsonObject;
	}
	
	
	/**
	 * 验证传入信息
	 * @param bindingResult
	 * @return
	 */
	public JSONObject validResponse(BindingResult bindingResult) {
		JSONObject jsonObject =new JSONObject();
		JSONArray jsonArray =new JSONArray();
		if(bindingResult.hasErrors()) {
			jsonObject.put("code",-1);
			jsonObject.put("msg","验证信息未能通过");
			bindingResult.getAllErrors().forEach(x->jsonArray.add(x.getDefaultMessage()));
			jsonObject.put("data",jsonArray);
		}
		return jsonObject;
	}
	
	

}
