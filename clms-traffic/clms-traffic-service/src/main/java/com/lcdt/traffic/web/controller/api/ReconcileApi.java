package com.lcdt.traffic.web.controller.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.service.ReconcileService;
import com.lcdt.traffic.web.dto.ReconcileListDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:37
 * @version
 */

@RestController
@Api("对账单api")
@RequestMapping("/fee/reconcile")
public class ReconcileApi {
	
	@Autowired
	private ReconcileService reconcileService;
	
	@ApiOperation(value = "添加对账单")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_add')")
	public JSONObject addReconcile(@Validated @RequestBody ReconcileListDto reconcileListDto,BindingResult bindingResult) {

		JSONObject jsonObject =new JSONObject();
		JSONArray jsonArray =new JSONArray();
		if(bindingResult.hasErrors()) {
			jsonObject.put("code",-1);
			jsonObject.put("msg","验证信息未能通过");
			for(ObjectError objectError:bindingResult.getAllErrors()) {
				jsonArray.add(objectError);
			}
			jsonObject.put("data",jsonArray);
			return jsonObject;
		}

		int i= reconcileListDto.getReconcileList().size();
		int result =reconcileService.insertReconcileBatch(reconcileListDto);
		if(result==i) {
			jsonObject.put("code",0);
			jsonObject.put("msg","添加成功");
		}else {
				throw new RuntimeException("添加对账单出现异常");
		}
		return jsonObject;
	}
	
	@PostMapping("/cancel")
	@ApiOperation("取消订单")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_cancel')")
	public JSONObject cancelReconcile(List<Long> reconcileIdList) {
		JSONObject jsonObject =new JSONObject();
		int i= reconcileIdList.size();
		int result=reconcileService.setCancelOk(reconcileIdList);
		if(i==result) {
			jsonObject.put("code",0);
			jsonObject.put("msg","取消成功");
			return jsonObject;
		}else {
			throw new RuntimeException("取消对账单出现异常");
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
