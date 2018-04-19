package com.lcdt.traffic.web.controller.api;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.lcdt.traffic.model.Reconcile;
import com.lcdt.traffic.service.FeeExchangeService;
import com.lcdt.traffic.service.ReconcileService;
import com.lcdt.traffic.web.dto.ReconcileDto;
import com.lcdt.traffic.web.dto.ReconcileListDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author Sheng-ji Lau
 * @date 2018年4月9日下午4:02:37
 * @version
 */

@RestController
@Api(description="对账单api")
@RequestMapping("/fee/reconcile")
public class ReconcileApi {
	
	
	@Autowired
	private ReconcileService reconcileService;
	
	@Autowired
	private FeeExchangeService feeExchangeService;
	
	Logger logger = LoggerFactory.getLogger(ReconcileApi.class);
	

	@ApiOperation(value = "添加对账单,fee_reconcile_add")
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_add')")
	public JSONObject addReconcile(@Validated ReconcileListDto reconcileListDto,BindingResult bindingResult) {	 		
		JSONObject jsonObject=validResponse(bindingResult);
		if(!jsonObject.isEmpty()) {
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
	@ApiOperation("取消订单,fee_reconcile_cancel")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_cancel')")
	public JSONObject cancelReconcile(@ApiParam(value="一个或多个对账单id,多个时用','隔开")@Param(value = "reconcileIds") String reconcileIds) {
		logger.debug("?"+reconcileIds);
		Long[] reconcileIdList =new Long[reconcileIds.length()];
		String [] ss=reconcileIds.split(",");
		for(int i=0;i<ss.length;i++) {
			reconcileIdList[i]=Long.valueOf(ss[i]);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("code",0);
		JSONArray jsonArray1=new JSONArray();//存取数组中存在收付款记录的reconcileId
		JSONArray jsonArray2=new JSONArray();//存取数组中不存在收付款记录的reconcileId
		//查询对账单是否生成过收付款记录,如果对账单存在收付款记录,则不能取消,如果不存在收付款记录,则执行取消操作		
		for(int i=0;i<reconcileIdList.length;i++) {
			int j=feeExchangeService.querySumFeeExchangeByReconcileId(reconcileIdList[i]);
			if(j>0) {
				jsonArray1.add(reconcileIdList[i]);
			}else {
				jsonArray2.add(reconcileIdList[i]);
			}
		}
		if(jsonArray1.size()>0) {			
			jsonObject.put("msg1","存在收付款记录的对账单不能取消");
			jsonObject.put("data:无法取消的对账单id",jsonArray1);
		}
		if(jsonArray2.size()>0) {
			int i= jsonArray2.size();
			int result=reconcileService.setCancelOk((Long[]) jsonArray2.toArray());
			if(i==result) {
				jsonObject.put("msg","取消成功");
				return jsonObject;
		}else {
			throw new RuntimeException("取消对账单时出现异常");
		}
		}
		return jsonObject;			
	}
	
	
	@GetMapping("/list")
	@ApiOperation("查询对账单列表,fee_reconcile_list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_list')")
	public JSONObject getReconcileList(ReconcileDto reconcileDto) {
		JSONObject jsonObject =new JSONObject();
		JSONArray jsonArray =new JSONArray();
		PageInfo<Reconcile> page=reconcileService.getReconcileList(reconcileDto);
		jsonArray.add(page.getTotal());
		jsonArray.add(page.getList());
		jsonObject.put("code",0);
		jsonObject.put("msg","对账单列表信息");
		jsonObject.put("data",jsonArray);
		return jsonObject;
	}
	
	
	@GetMapping("/select")
	@ApiOperation("查询对账单详情,fee_reconcile_select")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_reconcile_select')")
	public JSONObject selectReconcile(@ApiParam("对账单id") Long reconcileId) {
		JSONObject jsonObject =new JSONObject();		
		Reconcile reconcile=reconcileService.selectReconcileByPk(reconcileId);
		if(reconcile!=null) {
			jsonObject.put("code",0);
			jsonObject.put("msg","对账单详细信息");
			jsonObject.put("data",reconcile);
			return jsonObject;
		}else {
			throw new RuntimeException("获取对账单详细信息失败");
		}
		
		
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
