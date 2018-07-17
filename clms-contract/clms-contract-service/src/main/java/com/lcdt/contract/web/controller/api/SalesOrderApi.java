package com.lcdt.contract.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
		Long UserId = SecurityInfoGetter.getUser().getUserId();//get 创建者
		Long companyId = SecurityInfoGetter.getCompanyId();//get 公司id	
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		
		PageInfo<OrderDto> pageInfo = orderService.OrderList(orderDto);
		PageBaseDto<OrderDto> pageBaseDto = new PageBaseDto<OrderDto>();
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		
		logger.debug("销售订单条目数"+pageInfo.getTotal());
		
		return ResponseJsonUtils.successResponseJson(pageBaseDto, "销售订单列表");
	}
	
	
	@ApiOperation(value="获取单个销售订单",notes="单个销售订单")
	@GetMapping("/selsorder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_get')")
	public JSONObject selectOrder(@ApiParam(value = "订单id")@RequestParam Long orderId){
		OrderDto orderDto = orderService.selectByPrimaryKey(orderId);
		if (orderDto != null) {
			return ResponseJsonUtils.successResponseJson(orderDto, "销售订单详情");
		}else {
			throw new RuntimeException("获取失败");
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
		Long UserId = SecurityInfoGetter.getUser().getUserId();
		Long companyId = SecurityInfoGetter.getCompanyId();
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		orderDto.setCreateTime(new Date());
		orderDto.setOrderType(new Short("1"));	//设置订单类型为销售单
		int result = orderService.addOrder(orderDto);
		logger.debug("新增销售订单条目数:"+result);
		if (result > 0) {
			return ResponseJsonUtils.successResponseJson(null, "添加成功!");
        } else {
            throw new RuntimeException("添加失败");
        }
	}
	
	
	/**
	 * 修改编辑销售订单
	 * @param OrderDto
	 * @return JSONObject
	 */
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
        if (result > 0) {
        	return ResponseJsonUtils.successResponseJson(null, "修改成功!");
        } else {
        	throw new RuntimeException("修改失败");
        }
		}
	
	
	@ApiOperation("取消销售订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_delete')")
	public JSONObject delOrder(@ApiParam(value="销售订单id",required=true) @RequestParam Long orderId) {
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
	
	
	@ApiOperation("发布销售订单")
	@PostMapping("/releaseOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_release')")
	public JSONObject releaseOrder(@ApiParam(value="销售订单id",required=true) @RequestParam Long orderId) {
		int result = orderService.updateOrderIsDraft(orderId,(short) 1);
		logger.debug("发布销售订单条目数:"+result);
		if (result > 0) {
			return ResponseJsonUtils.successResponseJson(null, "发布成功!");
	    } else {
	        throw new RuntimeException("发布失败");
	    }
	}
	
	
	@ApiOperation("销售单生成运输计划")
	@PostMapping("/trafficPlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_traffic_plan')")
	public JSONObject generateTrafficPlan(@ApiParam(value = "销售订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateTrafficPlan(orderId);
		if (flag) {
			return ResponseJsonUtils.successResponseJson(null, "操作成功!");
		}else {
			throw new RuntimeException("操作失败");
		}

	}
	
	
	@ApiOperation("销售单生成出库计划")
	@PostMapping("/outWarehousePlan")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_warehouse_plan')")
	public JSONObject generateOutWarehousePlan(@ApiParam(value = "销售订单id",required = true) @RequestParam Long orderId) {
		Boolean flag = orderService.generateOutWarehousePlan(orderId);
		if (flag) {
			return ResponseJsonUtils.successResponseJson(null, "操作成功!");
		}else {
			throw new RuntimeException("操作失败");
		}

	}
	
	
	
	
	
	
	

}
