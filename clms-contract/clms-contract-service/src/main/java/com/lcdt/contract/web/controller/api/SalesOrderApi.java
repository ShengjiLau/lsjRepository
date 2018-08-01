package com.lcdt.contract.web.controller.api;

import java.util.Map;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Order;
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
 * 销售订单管理
 * @author Sheng-ji Lau
 * @date 2018年3月5日下午6:18:55
 * @version
 */
@Api(value="销售订单管理Api",description="销售订单操作")
@RestController
@RequestMapping("/salesOrder")
public class SalesOrderApi {

	Logger logger = LoggerFactory.getLogger(SalesOrderApi.class);
	
	@Autowired
	private OrderService orderService;
	
	
	@ApiOperation(value="销售订单列表",notes="销售订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_get')")
	public JSONObject OrderList(OrderDto orderDto){
		PageBaseDto<OrderDto> pageBaseDto = orderService.OrderList(orderDto);
		String message = "销售订单列表";
		return ResponseJsonUtils.successResponseJson(pageBaseDto, message);
	}
	
	@ApiOperation(value="获取单个销售订单",notes="单个销售订单")
	@GetMapping("/selsorder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_get')")
	public JSONObject selectOrder(@ApiParam(value = "订单id") @RequestParam Long orderId){
		OrderDto orderDto = orderService.selectByPrimaryKey(orderId);
		String message = null;
		if (orderDto != null) {
			message = "销售订单详情";
			return ResponseJsonUtils.successResponseJson(orderDto, message);
		}else {
			message = "获取失败";
			throw new RuntimeException(message);
		}
	}
	
	@ApiOperation("新增销售订单")
	@PostMapping("/addOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_add')")
	public JSONObject addOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindingResult) {
        //Validated自动验证
		Map<String,String> map = RequestValidated.validatedRequest(bindingResult);
		if (!map.isEmpty()) {
        	return ResponseJsonUtils.failedResponseJson(map, "验证信息未能通过");
        }
        //采用OrderValidator  (OrderValidator中封装对Order各属性的验证)
        Map<String,String> validateMap = OrderValidator.validator(orderDto);
        if (!validateMap.isEmpty()) {
        	return ResponseJsonUtils.failedResponseJson(validateMap, "验证信息未能通过");
        }
        orderDto.setOrderType(OrderVO.SALES_ORDER);	//设置订单类型为销售单
		int result = orderService.addOrder(orderDto);
		logger.debug("新增销售订单条目数:"+result);
		String message = null;
		if (result > 0) {
			message = "添加成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
        } else {
        	message = "添加失败";
            throw new RuntimeException(message);
        }
	}
	
	@ApiOperation("修改销售订单")
	@PostMapping("/modifyOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_modify')")
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
        logger.debug("修改销售订单条目数:"+result);
        String message = null;
        if (result > 0) {
        	message = "修改成功!";
        	return ResponseJsonUtils.successResponseJsonWithoutData(message);
        } else {
        	message = "修改失败";
        	throw new RuntimeException(message);
        }
		}
	
	@ApiOperation("取消销售订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_delete')")
	public JSONObject delOrder(@ApiParam(value="销售订单id",required=true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId,OrderVO.CANCEL_STATUS);
		logger.debug("取消销售订单条目数:"+result);
		String message = null;
		if (result > 0) {  
			message = "取消成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
	    } else {
	    	if (OrderVO.ZERO_INTEGER == result) {
	    		message = "存在付款单的订单不能取消!";
	    		return ResponseJsonUtils.failedResponseJsonWithoutData(message);
	    	}else {
	    		message = "取消失败";
	    		throw new RuntimeException(message);
	    	}  
	    }
	}
	
	@ApiOperation("发布销售订单")
	@PostMapping("/releaseOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_release')")
	public JSONObject releaseOrder(@ApiParam(value = "销售订单id",required = true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId, OrderVO.ALREADY_PUBLISHI);
		logger.debug("发布销售订单条目数:"+result);
		String message = null;
		if (result > 0) {
			message = "发布成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
	    } else {
	    	message = "发布失败";
	        throw new RuntimeException(message);
	    }
	}
	
	@ApiOperation("销售单生成运输计划")
	@PostMapping("/trafficPlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_traffic_plan')")
	public JSONObject generateTrafficPlan(@ApiParam(value = "销售订单id",required = true) @RequestParam Long orderId) {
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
	
	@ApiOperation("销售单生成出库计划")
	@PostMapping("/outWarehousePlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_warehouse_plan')")
	public JSONObject generateOutWarehousePlan(@ApiParam(value = "销售订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateOutWarehousePlan(orderId);
		String message = null;
		if (flag) {
			message = "操作成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "操作失败";
			throw new RuntimeException(message);
		}
	}
	
	
	@ApiOperation("销售单推送采购单")
	@PostMapping("/topurchase")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_topurchase')")
	public JSONObject salesOrderToPurchaseOrder(Long orderId) {
		int result = orderService.salesOrderToPurchaseOrder(orderId);
		String message = null;
		if (result > 0) {
			message = "操作成功!";
			return ResponseJsonUtils.successResponseJsonWithoutData(message);
		}else {
			message = "操作失败";
			throw new RuntimeException(message);
		}
	}


	@ApiOperation("销售单新增物流信息记录")
	@PostMapping("/addLogistics")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_get')")
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
