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
import com.lcdt.traffic.util.ConvertStringAndLong;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.ReconcileDto;
import com.lcdt.traffic.web.dto.ReconcileListDto;
import com.lcdt.util.ResponseJsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日
 * @version
 */

@RestController
@Api(description = "应收对账单api")
@RequestMapping("/reconcile/receivable")
public class ReceivableReconcileApi {
	
	
	@Autowired
	private ReconcileService reconcileService;
	
	Logger logger = LoggerFactory.getLogger(ReceivableReconcileApi.class);
	

	@ApiOperation(value = "添加对账单, receivable_reconcile_add")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_reconcile_add')")
	public JSONObject addReconcile(@RequestBody ReconcileListDto reconcileListDto) {
		//验证生成的对账单信息
		String validateMessage = validReconcile(reconcileListDto);
		if (null != validateMessage) {
			return ResponseJsonUtils.failedResponseJsonWithoutData(validateMessage);
		}
		//传入的应生成的记账单数量
		int  reconcileSize = reconcileListDto.getReconcileList().size();
		for(Reconcile reconcile:reconcileListDto.getReconcileList()) {
			reconcile.setPayeeType(ConstantVO.RECONCILE_RECEIVABLE);
		}
		//result 为返回的对账单插入数量
		int result = reconcileService.insertReconcileBatch(reconcileListDto.getReconcileList());
		logger.debug("插入对账单数量:"+result);
		String message = null;
		if(result == reconcileSize) {
			message = "添加成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "添加对账单出现异常";
			throw new RuntimeException(message);
		}
	}
	
	
	@PostMapping("/cancel")
	@ApiOperation("取消对账单, receivable_reconcile_cancel")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_reconcile_cancel')")
	public JSONObject cancelReconcile(@ApiParam(value = "一个或多个对账单id,多个时用','隔开",required = true) @RequestParam String reconcileIds) {
		Map<Integer,String> map = reconcileService.setCancelOk(reconcileIds);
		String message = null;
		if (map.containsKey(ConstantVO.ALREADY_PAYMENT)) {
			Long[] Ids = ConvertStringAndLong.convertStrToLong(map.get(1));
			message = "有" + Ids.length + "条对账单因为存在收付款记录不能取消!";
			return ResponseJsonUtils.failedResponseJson(map.get(ConstantVO.ALREADY_PAYMENT), message);
		}else {
			message = "取消成功！";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}
	}
	
	
	@GetMapping("/list")
	@ApiOperation("查询对账单列表,receivable_reconcile_list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_reconcile_get')")
	public JSONObject getReconcileList(ReconcileDto reconcileDto) {
		reconcileDto.setPayeeType(ConstantVO.RECONCILE_RECEIVABLE);
		PageInfo<ReconcileDto> page = reconcileService.getReconcileList(reconcileDto);
		PageBaseDto<ReconcileDto> pageBaseDto = new PageBaseDto<ReconcileDto>();
		pageBaseDto.setTotal(page.getTotal());
		pageBaseDto.setList(page.getList());
		String message = "请求成功!";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	
	@GetMapping("/select")
	@ApiOperation("查询对账单详情,receivable_reconcile_select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receivable_reconcile_get')")
	public JSONObject getReconcileDetail(@ApiParam(value = "对账单id",required = true) @RequestParam Long reconcileId) {
		ReconcileDto reconcileDto = reconcileService.selectReconcileByPk(reconcileId);
		String message = null;
		if(null != reconcileDto) {
			message = "请求成功";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "获取对账单详细信息失败";
			throw new RuntimeException(message);
		}
	}
	

	/**
	 * 验证生成对账单时对账单传入信息
	 * @param bindingResult
	 * @return
	 */
	public String validReconcile(ReconcileListDto reconcileListDto) {
		if(null == reconcileListDto.getReconcileList() || 0 == reconcileListDto.getReconcileList().size()) {
			return "请至少添加一条对账单信息";
		}
	    StringBuilder sd = new StringBuilder();
		for(Reconcile re : reconcileListDto.getReconcileList()) {
			if(null == re.getTransportationExpenses() && null == re.getOtherExpenses()) {
					return "对账单金额不可为空";
			}
			if(null == re.getGroupId()) {
			    sd.append("业务组信息groupId不可为空,");
			 }
			 if(null == re.getPayerId()) {
				 sd.append("收款方id不可为空,"); 
			 }
			 if(null == re.getPayerName()) {
				 sd.append("收款方名称payerName不可为空,"); 
			 }
			 if(null == re.getWaybillId()) {
				 sd.append("运单waybillId不可为空,"); 
			 }
			 if(null == re.getAccountId()) {
				 sd.append("记账单accountId不可为空"); 
			 }
		}	
		if(sd.length() > 0) {
			return sd.toString();
	    }
		return null;
	}
	
	
	/**
	 * 验证传入信息
	 * @param bindingResult
	 * @return
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
