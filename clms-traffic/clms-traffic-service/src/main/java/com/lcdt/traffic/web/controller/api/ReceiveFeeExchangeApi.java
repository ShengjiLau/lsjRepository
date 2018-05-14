package com.lcdt.traffic.web.controller.api;


import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.lcdt.traffic.web.dto.FeeExchangeDto;
import com.lcdt.traffic.web.dto.FeeExchangeListDto;
import com.lcdt.traffic.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sheng-ji Lau
 * @date 2018年4月17日
 * @version
 * @Description: TODO 
 */
@RequestMapping("/feeExchange/receive")
@Api(description="收款记录操作API")
@RestController
public class ReceiveFeeExchangeApi {
	
	@Autowired
	private FeeExchangeService feeExchangeService;
	
	@PostMapping("/add")
	@ApiOperation("新增收款记录,receive_exchange_add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receive_exchange_add')")
	public JSONObject addFeeExchange(@RequestBody FeeExchangeListDto feeExchangeListDto) {
		JSONObject jsonObject =validRequestBody(feeExchangeListDto);
		if(jsonObject.size()>1) {
			return jsonObject;	
		}else {
			jsonObject.clear();
		}
		
		for(FeeExchange fe:feeExchangeListDto.getFeeExchangeList()) {
			fe.setType((short) 0);
		}
		
		int result=feeExchangeService.insertFeeExchangeByBatch(feeExchangeListDto.getFeeExchangeList());
		if(result>0) {
			jsonObject.put("code",0);
			jsonObject.put("message","新增收款记录成功");
			return jsonObject;
		}else {
			throw new RuntimeException("插入收款记录出现异常");
		}
	}
	
	
	
	@GetMapping("/list")
	@ApiOperation("查询收款记录列表,receive_exchange_list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receive_exchange_list')")
	public JSONObject getFeeExchange(FeeExchangeDto feeExchangeDto) {
		JSONObject jsonObject =new JSONObject();
		feeExchangeDto.setType((short) 0);
		PageInfo<FeeExchange> page=feeExchangeService.getFeeExchangeList(feeExchangeDto);
		PageBaseDto<FeeExchange> pageBase=new PageBaseDto<FeeExchange>();
		pageBase.setTotal(page.getTotal());
		pageBase.setList(page.getList());
		jsonObject.put("code",0);
		jsonObject.put("msg","收款记录列表");
		jsonObject.put("data",pageBase);
		
		return jsonObject;
	}
	
	
	
	@PostMapping("/cancel")
	@ApiOperation("批量取消收款记录,receive_exchange_cancel")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receive_exchange_cancel')")
	public JSONObject cancelFeeExchange(@ApiParam(value="收款记录id数组",required=true)@RequestParam String feeExchanges) {
		JSONObject jsonObject =new JSONObject();
		int result=feeExchangeService.updateSetCancelOk(feeExchanges);
		if(result>0) {
			jsonObject.put("code",0);
			jsonObject.put("msg","取消成功");
			return jsonObject;
		}else {
			throw new RuntimeException("取消收款记录出现异常");
		}	
	}
	
	
	@GetMapping("/select")
	@ApiOperation("单个收款记录详情,receive_exchange_select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receive_exchange_select')")
	public JSONObject selectFeeExchange(Long feeExchangeId) {
		JSONObject jsonObject =new JSONObject();
		FeeExchange fe=feeExchangeService.selectFeeExchangeById(feeExchangeId);
		if(null!=fe) {
			jsonObject.put("code",0);
			jsonObject.put("message","收款详情");
			jsonObject.put("data",fe);
		}else {
			throw new RuntimeException("查询出现异常");
		}
		return jsonObject;
	}
	
	
	/**
	 * 验证传入的收款信息
	 * @param feeExchangeListDto
	 * @return
	 */
	private JSONObject validRequestBody(FeeExchangeListDto feeExchangeListDto) {
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("code",-1);
		if(null==feeExchangeListDto.getFeeExchangeList()) {
			jsonObject.put("message","请至少添加一条收款信息");
		}
		if(null!=feeExchangeListDto.getFeeExchangeList()&&0==feeExchangeListDto.getFeeExchangeList().size()) {
			jsonObject.put("message","请至少添加一条收款信息");
		}
		if(null!=feeExchangeListDto.getFeeExchangeList()&&feeExchangeListDto.getFeeExchangeList().size()>0) {
			StringBuilder sd = new StringBuilder();
			for(FeeExchange fe:feeExchangeListDto.getFeeExchangeList()) {
				if(null==fe.getReconcileId()) {
					sd.append("对账单id不可为空,");
				}
				if(null==fe.getReconcileCode()) {
					sd.append("对账单单号不可为空,");
				}
				if(null==fe.getPayerName()) {
					sd.append("收款方名称不可为空,");
				}
				if(null==fe.getExchangeType()) {
					sd.append("支付方式不可为空,");
				}
				if(null==fe.getExchangeAccount()) {
					sd.append("收款账户不可为空,");
				}
				if(null==fe.getExchangeName()) {
					sd.append("收款账户名不可为空,");
				}
				if(null==fe.getOperateTime()) {
					sd.append("收款时间不可为空,");
				}else {
					String pattern ="^([0-9]{4})-([0-9]{2})-([0-9]{2})";
					if(!Pattern.matches(pattern,fe.getOperateTime())) {
						sd.append("时间格式为:yyyy-MM-dd");
					}
				}
				if(null==fe.getTransportationExpenses()&&null==fe.getOtherExpenses()) {
					sd.append("收款金额不可为空");
				}
			}
			 if(sd.length()>0) {
			 jsonObject.put("message",sd.toString());
			 }
		}
		return jsonObject;
	}
	
	/**
	 * 验证传入信息
	 * @param bindingResult
	 * @return
	 */
	private JSONObject validResponse(BindingResult bindingResult) {
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
