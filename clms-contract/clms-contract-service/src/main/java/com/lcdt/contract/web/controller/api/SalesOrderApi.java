package com.lcdt.contract.web.controller.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.utils.OrderProductUtils;

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
	
	
	/**
	 * 依据条件查询订单列表
	 * @param orderDto
	 * @return PageBaseDto
	 */
	@ApiOperation(value="销售订单列表",notes="销售订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('order_list')")
	public PageBaseDto<List<Order>> OrderList(@RequestParam @Validated OrderDto orderDto,
			@ApiParam(value="第几页",required=true,defaultValue="1") @RequestParam Integer pageNum,
			@ApiParam(value="每页条目数量",required=true,defaultValue="1")@RequestParam Integer pagesize){
		Long UserId=SecurityInfoGetter.getUser().getUserId();
		Long companyId=SecurityInfoGetter.getCompanyId();
		orderDto.setPageNum(pageNum);
		orderDto.setPageSize(pagesize);
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		PageInfo<List<Order>> pageInfoList = orderService.OrderList(orderDto);
		logger.debug("订单条目数"+pageInfoList.getTotal());
		logger.debug("pageInfoList:"+pageInfoList.toString());
		PageBaseDto pageBaseDto = new PageBaseDto(pageInfoList.getList(),pageInfoList.getTotal());
		return pageBaseDto;
	}
	
	
	/**
	 * 新增销售订单
	 * @param Order
	 * @return JSONObject
	 */
	@ApiOperation("新增销售订单")
	@PostMapping("/addOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_order')")
	public JSONObject addOrder(@Validated Order order,@Validated OrderProduct orderProduct,OrderDto orderDto) {
		Long UserId=SecurityInfoGetter.getUser().getUserId();
		Long companyId=SecurityInfoGetter.getCompanyId();
		order.setCompanyId(companyId);
		order.setCreateUserId(UserId);
		order.setCreateTime(new Date());
		int result =orderService.addOrder(order);
		int i=OrderProductUtils.addProduct(orderProduct);
		logger.debug("新增销售订单条目数:"+result);
		if (result>0 && i>0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
	}
	
	
	/**
	 * 修改销售订单
	 * @param Order
	 * @return JSONObject
	 */
	@ApiOperation("修改销售订单")
	@PostMapping("/modOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('mod_order')")
	public JSONObject modifyOrder(@Validated Order order) {
	int result=orderService.modOrder(order);
	logger.debug("修改销售订单条目数:"+result);
	if (result > 0) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");
        return jsonObject;
    } else {
        throw new RuntimeException("修改失败");
    }
	}
	
	
	/**
	 * 删除销售订单
	 * @param Long
	 * @return JSONObject
	 */
	@ApiOperation("删除销售订单")
	@PostMapping("/delOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('del_order')")
	public JSONObject delOrder(@ApiParam(value="采购订单id",required=true) @RequestParam Long orderId) {
		int result =orderService.delOrder(orderId);
		logger.debug("删除销售订单条目数:"+result);
		if (result > 0) {
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("code", 0);
	        jsonObject.put("message", "删除成功");
	        return jsonObject;
	    } else {
	        throw new RuntimeException("删除失败");
	    }
	}
	
	
	
	
	
	
	
	

}
