package com.lcdt.contract.web.controller.api;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.utils.OrderValidator;
import com.lcdt.contract.web.utils.RequestValidated;
import com.lcdt.util.ResponseJsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 采购订单的管理
 * @author Sheng-ji Lau
 * @date 2018年3月1日下午6:22:02
 * @version
 */
@Api(value="采购订单管理Api",description="采购订单操作")
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderApi {
		
    Logger logger = LoggerFactory.getLogger(PurchaseOrderApi.class);
	
	@Autowired
	private OrderService orderService;
	
	
	@ApiOperation(value="采购订单列表",notes="采购订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_get')")
	public JSONObject OrderList(OrderDto orderDto){
		Long UserId = SecurityInfoGetter.getUser().getUserId();//get 创建者
		Long companyId = SecurityInfoGetter.getCompanyId();//get 公司id	
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		
		PageInfo<OrderDto> pageInfo = orderService.OrderList(orderDto);
		PageBaseDto<OrderDto> pageBaseDto = new PageBaseDto<OrderDto>();
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		logger.debug("采购订单条目数"+pageInfo.getTotal());
		
		return ResponseJsonUtils.successResponseJson(pageBaseDto, "采购订单列表");
	}
	
	
	@ApiOperation(value="获取单个采购订单",notes="单个采购订单")
	@GetMapping("/selporder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_get')")
	public  JSONObject selectOrder(@ApiParam(value = "订单id")@RequestParam Long orderId){
		OrderDto orderDto = orderService.selectByPrimaryKey(orderId);
		if (orderDto != null) {
			return ResponseJsonUtils.successResponseJson(orderDto, "采购订单详情");
		}else {
			throw new RuntimeException("获取失败");
		}
	}
	
	
	@ApiOperation("新增采购订单")
	@PostMapping("/addOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_add')")
	public JSONObject addOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindingResult) {
		  //Validated自动验证
		Map<String,String> map = RequestValidated.validatedRequest(bindingResult);
		if (!map.isEmpty()) {
        	return ResponseJsonUtils.failedResponseJson(map, "验证信息未能通过");
        }
	    //采用OrderValidator(封装对Order各属性的验证)
	      Map<String,String> validateMap = OrderValidator.validator(orderDto);
	       if (!validateMap.isEmpty()) {
	    		return ResponseJsonUtils.failedResponseJson(validateMap, "验证信息未能通过");
	      }
		Long UserId = SecurityInfoGetter.getUser().getUserId();
		Long companyId = SecurityInfoGetter.getCompanyId();
		orderDto.setCompanyId(companyId);
		logger.debug("创建人UserId:" + UserId);
		logger.debug("所属公司companyId:" + companyId);
		orderDto.setCreateUserId(UserId);
		orderDto.setCreateTime(new Date());
		orderDto.setOrderType(new Short("0"));	//设置订单类型为采购单
		int result = orderService.addOrder(orderDto);
		logger.debug("新增采购订单条目数:"+result);
		if (result > 0) {
			return ResponseJsonUtils.successResponseJson(null, "添加成功!");
        } else {
            throw new RuntimeException("添加失败");
        }
	}
	
	
	@ApiOperation("编辑采购订单")
	@PostMapping("/modifyOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_modify')")
	public JSONObject modifyOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindingResult) {
		//Validated自动验证
		Map<String,String> map = RequestValidated.validatedRequest(bindingResult);
		if (!map.isEmpty()) {
        	return ResponseJsonUtils.failedResponseJson(map, "验证信息未能通过");
        }
		    //采用OrderValidator(封装对Order各属性的验证)
	     Map<String,String> validateMap = OrderValidator.validator(orderDto);
	       if (!validateMap.isEmpty()) {
	    	   return ResponseJsonUtils.failedResponseJson(validateMap, "验证信息未能通过");
	        }
	    int result = orderService.modOrder(orderDto);
	    logger.debug("修改采购订单条目数:"+result);
	    if (result > 0) {
	    	return ResponseJsonUtils.successResponseJson(null, "修改成功!");
       } else {
          throw new RuntimeException("修改失败");
       }
	}
	
	
	@ApiOperation("取消采购订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_delete')")
	public JSONObject delOrder(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId,(short) 2);
		logger.debug("取消销售订单条目数:"+result);
		if (result > 0) {    
			return ResponseJsonUtils.successResponseJson(null, "取消成功!");
	    } else {
	    	if (0 == result) {
	    		return ResponseJsonUtils.failedResponseJson(null, "存在付款单的订单不能取消!");
	    	}else {
	    		throw new RuntimeException("取消失败");
	    	}  
	    }
	 }
	
	
	@ApiOperation("发布采购订单")
	@PostMapping("/releaseOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_release')")
	public JSONObject releaseOrder(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId,(short) 1);
		logger.debug("取消销售订单条目数:"+result);
		if (result > 0) {
			return ResponseJsonUtils.successResponseJson(null, "发布成功!");
	    } else {
	        throw new RuntimeException("发布失败");
	    }
	}
	
	
	@ApiOperation("采购单生成运输计划")
	@PostMapping("/trafficPlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_traffic_plan')")
	public JSONObject generateTrafficPlan(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateTrafficPlan(orderId);
		if (flag) {
			return ResponseJsonUtils.successResponseJson(null, "操作成功!");
		}else {
			throw new RuntimeException("操作失败");
		}

	}
	
	@ApiOperation("采购单生成入库计划")
	@PostMapping("/inWarehousePlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_warehouse_plan')")
	public JSONObject generateInWarehousePlan(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateInWarehousePlan(orderId);
		if (flag) {
			return ResponseJsonUtils.successResponseJson(null, "操作成功!");
		}else {
			throw new RuntimeException("操作失败");
		}
		
	}	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
