package com.lcdt.contract.web.controller.api;

import java.util.Map;

import com.lcdt.contract.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.vo.OrderVO;
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
	
	
	@ApiOperation(value = "采购订单列表",notes = "采购订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_get')")
	public JSONObject OrderList(OrderDto orderDto){
		PageBaseDto<OrderDto> pageBaseDto = orderService.OrderList(orderDto);
		String message = "采购订单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	@ApiOperation(value="获取单个采购订单",notes="单个采购订单")
	@GetMapping("/selporder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_get')")
	public  JSONObject selectOrder(@ApiParam(value = "订单id") @RequestParam Long orderId){
		OrderDto orderDto = orderService.selectByPrimaryKey(orderId);
		String message = null;
		if (orderDto != null) {
			message = "采购订单详情";
			return ResponseJsonUtils.successResponseJson(orderDto, message);
		}else {
			message = "获取失败";
			throw new RuntimeException(message);
		}
	}
	
	@ApiOperation("新增采购订单")
	@PostMapping("/addOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_add')")
	public JSONObject addOrder(@Validated @RequestBody OrderDto orderDto, BindingResult bindingResult) {
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
		orderDto.setOrderType(OrderVO.PURCHASE_ORDER);	//设置订单类型为采购单
		int result = orderService.addOrder(orderDto);
		logger.debug("新增采购订单条目数:"+result);
		String message = null;
		if (result > 0) {
			message = "添加成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
        } else {
        	message = "添加失败";
            throw new RuntimeException(message);
        }
	}
	
	@ApiOperation("编辑采购订单")
	@PostMapping("/modifyOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_modify')")
	public JSONObject modifyOrder(@Validated @RequestBody OrderDto orderDto, BindingResult bindingResult) {
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
	    String message = null;
	    if (result > 0) {
	      message = "修改成功!";
	      return ResponseJsonUtils.successResponseJsonWithoutData(message);
       } else {
    	  message = "修改失败";
          throw new RuntimeException(message);
       }
	}
	
	@ApiOperation("取消采购订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_delete')")
	public JSONObject delOrder(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId, OrderVO.CANCEL_STATUS);
		logger.debug("取消销售订单条目数:"+result);
		String message = null;
		if (result > 0) {    
			message = "取消成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
	    } else {
	    	if (0 == result) {
	    		message = "存在付款单的订单不能取消!";
	    		return ResponseJsonUtils.failedResponseJsonWithoutData(message);
	    	}else {
	    		message = "取消失败";
	    		throw new RuntimeException(message);
	    	}  
	    }
	 }
	
	@ApiOperation("发布采购订单")
	@PostMapping("/releaseOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_release')")
	public JSONObject releaseOrder(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId, OrderVO.ALREADY_PUBLISHI);
		logger.debug("取消销售订单条目数:"+result);
		String message = null;
		if (result > 0) {
			message = "发布成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
	    } else {
	    	message = "发布失败";
	        throw new RuntimeException(message);
	    }
	}
	
	@ApiOperation("采购单生成运输计划")
	@PostMapping("/trafficPlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_traffic_plan')")
	public JSONObject generateTrafficPlan(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateTrafficPlan(orderId);
		String message = null;
		if (flag) {
			message = "操作成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "操作失败";
			throw new RuntimeException(message);
		}

	}
	
	@ApiOperation("采购单生成入库计划")
	@PostMapping("/inWarehousePlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_warehouse_plan')")
	public JSONObject generateInWarehousePlan(@ApiParam(value = "采购订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateInWarehousePlan(orderId);
		String message = null;
		if (flag) {
			message = "操作成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "操作失败";
			throw new RuntimeException(message);
		}
	}


	@ApiOperation("采购单新增物流信息记录")
	@PostMapping("/addLogistics")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_get')")
	public JSONObject addLogistics(@RequestBody Order order) {
		order.setCompanyId(SecurityInfoGetter.getCompanyId());
		int rows = orderService.addLogisticsInfo(order);
		String message = null;
		if (rows>0) {
			message = "操作成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "操作失败";
			throw new RuntimeException(message);
		}
	}










}
