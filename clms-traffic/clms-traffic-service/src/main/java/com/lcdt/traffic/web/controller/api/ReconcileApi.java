package com.lcdt.traffic.web.controller.api;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.service.ReconcileService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.ReconcileDto;
import com.lcdt.traffic.web.dto.ReconcileListDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日
 * @version
 */

@RestController
@Api(description="对账单api")
@RequestMapping("/fee/reconcile")
public class ReconcileApi {
	
	
	@Autowired
	private ReconcileService reconcileService;
	
//	@Autowired
//	private FeeExchangeService feeExchangeService;
	
	Logger logger = LoggerFactory.getLogger(ReconcileApi.class);
	

	@ApiOperation(value = "添加对账单,fee_reconcile_add")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_add')")
	public JSONObject addReconcile(@RequestBody ReconcileListDto reconcileListDto) {
		//验证生成的对账单信息
		JSONObject jsonObject=validReconcile(reconcileListDto);
		if(jsonObject.size()>1) {
			return jsonObject;
		}else {
			jsonObject.clear();
		}
		//传入的应生成的记账单数量
		int i= reconcileListDto.getReconcileList().size();
		//result 为返回的对账单插入数量
		int result =reconcileService.insertReconcileBatch(reconcileListDto.getReconcileList());
		logger.debug("插入对账单数量:"+result);
		if(result==i) {
			jsonObject.put("code",0);
			jsonObject.put("message","添加成功");
		}else {
				throw new RuntimeException("添加对账单出现异常");
		}
		return jsonObject;
	}
	
	
	@PostMapping("/cancel")
	@ApiOperation("取消订单,fee_reconcile_cancel")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_cancel')")
	public JSONObject cancelReconcile(@ApiParam(value="一个或多个对账单id,多个时用','隔开",required=true)@RequestParam String reconcileIds) {
		JSONObject jsonObject =new JSONObject();	
		Map<Integer,String> map=reconcileService.setCancelOk(reconcileIds);
		StringBuilder sb = new StringBuilder();
		if(map.containsKey(1)) {
			sb.append("存在收付款记录的对账单不能取消,对账单id:"+map.get(1));
			sb.append(";");
		}
		if(map.containsKey(2)) {
			sb.append("取消成功");
		}
		jsonObject.put("code",0);
		jsonObject.put("message",sb.toString());	    
		return jsonObject;			
	}
	
	
	@GetMapping("/list")
	@ApiOperation("查询对账单列表,fee_reconcile_list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_list')")
	public JSONObject getReconcileList(ReconcileDto reconcileDto) {
		JSONObject jsonObject =new JSONObject();
		PageInfo<Reconcile> page=reconcileService.getReconcileList(reconcileDto);
		PageBaseDto<Reconcile> pagebase=new PageBaseDto<Reconcile>();
		pagebase.setTotal(page.getTotal());
		pagebase.setList(page.getList());
		jsonObject.put("code",0);
		jsonObject.put("message","请求成功");
		jsonObject.put("data",pagebase);
		return jsonObject;
	}
	
	
	@GetMapping("/select")
	@ApiOperation("查询对账单详情,fee_reconcile_select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_select')")
	public JSONObject selectReconcile(@ApiParam(value="对账单id",required=true)@RequestParam Long reconcileId) {
		JSONObject jsonObject =new JSONObject();		
		Reconcile reconcile=reconcileService.selectReconcileByPk(reconcileId);
		if(reconcile!=null) {
			jsonObject.put("code",0);
			jsonObject.put("message","请求成功");
			jsonObject.put("data",reconcile);
			return jsonObject;
		}else {
			throw new RuntimeException("获取对账单详细信息失败");
		}
		
		
	}
	


	
	
	/**
	 * 验证生成对账单时对账单传入信息
	 * @param bindingResult
	 * @return
	 */
	public JSONObject validReconcile(ReconcileListDto reconcileListDto) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code",-1);	
		if(null==reconcileListDto.getReconcileList()) {
			jsonObject.put("message","请至少添加一条对账单信息");
		}
		if(null!=reconcileListDto.getReconcileList()&&0==reconcileListDto.getReconcileList().size()) {
			jsonObject.put("message","请至少添加一条对账单信息");
		}
		if(null!=reconcileListDto.getReconcileList()&&reconcileListDto.getReconcileList().size()>0) {
			 StringBuilder sd = new StringBuilder();
		 for(Reconcile re:reconcileListDto.getReconcileList()) {
			 if(null==re.getGroupId()) {
				 sd.append("业务组信息groupId不可为空,");
			 }
			 if(null==re.getPayeeType()) {
				 sd.append("收付款类型payeeType不可为空,");                  
			 }
			 if(null==re.getPayerId()) {
				 sd.append("收付款方id不可为空,"); 
			 }
			 if(null==re.getPayerName()) {
				 sd.append("收付款方名称payerName不可为空,"); 
			 }
			 if(null==re.getWaybillId()) {
				 sd.append("运单waybillId不可为空,"); 
			 }
			 if(null==re.getAccountAmount()) {
				 sd.append("对账金额accountAmount不可为空,"); 
			 }
			 if(null==re.getAccountId()) {
				 sd.append("记账单accountId不可为空"); 
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
	public JSONObject validResponse(BindingResult bindingResult) {
		JSONObject jsonObject =new JSONObject();
		JSONArray jsonArray =new JSONArray();
		if(bindingResult.hasErrors()) {
			jsonObject.put("code",-1);
			jsonObject.put("message","验证信息未能通过");
			bindingResult.getAllErrors().forEach(x->jsonArray.add(x.getDefaultMessage()));
			jsonObject.put("data",jsonArray);
		}
		return jsonObject;
	}
	
	
	
	
	
	
	
	
	
	
	

}
