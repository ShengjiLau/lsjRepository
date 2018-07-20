package com.lcdt.traffic.web.controller.api;



import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.FeeExchange;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.FeeExchangeDto;
import com.lcdt.traffic.web.dto.FeeExchangeListDto;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.util.ResponseJsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
@RequestMapping("/feeExchange/pay")
@Api(description = "付款记录操作API")
@RestController
public class PayFeeExchangeApi {
	
	@Autowired
	private FeeExchangeService feeExchangeService;
	
	@PostMapping("/add")
	@ApiOperation("新增付款记录, receive_exchange_add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('pay_exchange_add')")
	public JSONObject addFeeExchange(@RequestBody FeeExchangeListDto feeExchangeListDto) {
		String validateMessage = validRequestBody(feeExchangeListDto);
		if (null != validateMessage) {
			return ResponseJsonUtils.failedResponseJsonWithoutData(validateMessage);
		}
				
		for(FeeExchange fe : feeExchangeListDto.getFeeExchangeList()) {
			fe.setType(ConstantVO.EXCHANGE_PAYABLE);
		}
		
		int result = feeExchangeService.insertFeeExchangeByBatch(feeExchangeListDto);
		String message = null;
		if(result > 0) {
			message = "新增付款记录成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "插入付款记录出现异常";
			throw new RuntimeException(message);
		}
	}
	
	
	
	@GetMapping("/list")
	@ApiOperation("查询付款记录列表, receive_exchange_list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('pay_exchange_get')")
	public JSONObject getFeeExchange(FeeExchangeDto feeExchangeDto) {
		feeExchangeDto.setType(ConstantVO.EXCHANGE_PAYABLE);
		PageBaseDto<FeeExchange> pageBaseDto = feeExchangeService.getFeeExchangeList(feeExchangeDto);
		String message = "请求成功!";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	
	@PostMapping("/cancel")
	@ApiOperation("批量取消付款记录,receive_exchange_cancel")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('pay_exchange_cancel')")
	public JSONObject cancelFeeExchange(@ApiParam(value = "付款记录id数组", required = true) @RequestParam String feeExchanges) {
		int result = feeExchangeService.updateSetCancelOk(feeExchanges);
		String message = null;
		if(result > 0) {
			message = "取消成功！";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "取消付款记录出现异常";
			throw new RuntimeException(message);
		}	
	}
	
	
	@GetMapping("/select")
	@ApiOperation("单个付款记录详情,receive_exchange_select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('pay_exchange_get')")
	public JSONObject selectFeeExchange(Long feeExchangeId) {
		FeeExchange fe = feeExchangeService.selectFeeExchangeById(feeExchangeId);
		String message = null;
		if(null != fe) {
			message = "付款详情";
			return ResponseJsonUtils.successResponseJson(fe, message);
		}else {
			message = "查询出现异常";
			throw new RuntimeException(message);
		}
	}
	
	
	
	
	/**
	 * 验证传入的付款信息
	 */
	private String validRequestBody(FeeExchangeListDto feeExchangeListDto) {
		if(null == feeExchangeListDto.getFeeExchangeList() || 0 == feeExchangeListDto.getFeeExchangeList().size()) {
			return "请至少添加一条付款信息";
		}
		if(null == feeExchangeListDto.getReconcileId()) {
			return "对账单id不可为空";
		}
		StringBuilder sd = new StringBuilder();
		for(FeeExchange fe:feeExchangeListDto.getFeeExchangeList()) {
			if(null == fe.getExchangeAccount()) {
				sd.append("付款账户不可为空,");
			}
			if(null == fe.getOperateTime()) {
				sd.append("付款时间不可为空,");
			}else {
				String pattern ="^([0-9]{4})-([0-9]{2})-([0-9]{2})";
				if(!Pattern.matches(pattern,fe.getOperateTime())) {
					sd.append("时间格式为:yyyy-MM-dd");
				}
			}
			if(null == fe.getTransportationExpenses() && null == fe.getOtherExpenses()) {
				sd.append("付款金额不可为空");
			}
		}
		if(sd.length()>0) {
			 return sd.toString();
		 }
	    return null;
	}

	
	/**
	 * 验证传入信息
	 */
	public JSONObject validResponse(BindingResult bindingResult) {
		JSONArray jsonArray = new JSONArray();
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().
			       forEach(x -> jsonArray.
			    		   add(x.getDefaultMessage()));
			String message = "验证信息失败！";
			return ResponseJsonUtils.failedResponseJson(jsonArray, message);
		}
		return null;
	}
	
	

}
