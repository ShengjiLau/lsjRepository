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
import com.lcdt.contract.web.utils.SerialNumAutoGenerator;

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
	
	/**
	 * 依据条件查询采购订单列表
	 * @param OrderDto
	 * @return JSONObject
	 */
	@ApiOperation(value="采购订单列表",notes="采购订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_list')")
	public JSONObject OrderList(OrderDto orderDto
	//	,@ApiParam(value="第几页",required=true,defaultValue="1") @RequestParam Integer pageNum,
   //	@ApiParam(value="每页条目数量",required=true,defaultValue="6")@RequestParam Integer pagesize
			){
		Long UserId=SecurityInfoGetter.getUser().getUserId();//get 创建者
		Long companyId=SecurityInfoGetter.getCompanyId();//get 公司id	
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		PageInfo<OrderDto> pageInfo = orderService.OrderList(orderDto);
		PageBaseDto<OrderDto> pageBaseDto =new PageBaseDto<OrderDto>();
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		logger.debug("采购订单条目数"+pageInfo.getTotal());
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("code",0);
		jsonObject.put("message","采购订单列表");
		jsonObject.put("data",pageBaseDto);
		//PageBaseDto<List<OrderDto>> pageBaseDto = new PageBaseDto<List<OrderDto>>(pageInfoList.getList(),pageInfoList.getTotal());
		return jsonObject;
	}
	
	/**
	 * 查询单个订单
	 * @param Long
	 * @return JSONObject
	 */
	@ApiOperation(value="获取单个采购订单",notes="单个采购订单")
	@GetMapping("/selporder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_select')")
	public  JSONObject selectOrder(@ApiParam(value="订单id")@RequestParam Long orderId){
		OrderDto orderDto= orderService.selectByPrimaryKey(orderId);
		JSONObject jsonObject=new JSONObject();
		if(orderDto!=null) {
			jsonObject.put("code",0);
			jsonObject.put("message","单个采购订单");
			jsonObject.put("data",orderDto);
		}else {
			throw new RuntimeException("获取失败");
		}
		return jsonObject;
	}
	
	
	/**
	 * 新增采购订单
	 * @param Order
	 * @return JSONObject
	 */
	@ApiOperation("新增采购订单")
	@PostMapping("/addOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_add')")
	public JSONObject addOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindResult) {
		JSONObject jsonObject = new JSONObject();
		  //Validated自动验证
	      if(bindResult.hasErrors()) {
	        Map<String,String> map=new HashMap<String,String>();//此map用于盛装验证时所验证的实体类各属性(Field)和验证属性反馈的error
	        List<FieldError> list=bindResult.getFieldErrors();
	        for(FieldError error:list) {
	        String n=error.getField();//作为map的key
	        String m=error.getDefaultMessage();//作为map的value
	        map.put(n,m);
	        }
	        jsonObject.put("code",-1);
	        jsonObject.put("message","验证信息未能通过");
	        jsonObject.put("data",map);
	        return jsonObject;
	      }
	    //采用OrderValidator(封装对Order各属性的验证)
	      Map<String,String> validateMap =OrderValidator.validator(orderDto);
	       if(!validateMap.isEmpty()) {
	        	jsonObject.put("code",-1);
	           	jsonObject.put("message","验证信息未能通过");
	           	jsonObject.put("data",validateMap);
	           	return jsonObject;
	      }
		Long UserId=SecurityInfoGetter.getUser().getUserId();
		Long companyId=SecurityInfoGetter.getCompanyId();
//		String orderSerialNum =SerialNumAutoGenerator.serialNoGenerate();
		orderDto.setCompanyId(companyId);
		logger.debug("创建人UserId:"+UserId);
		logger.debug("所属公司companyId:"+companyId);
		orderDto.setCreateUserId(UserId);
//		orderDto.setOrderSerialNo(orderSerialNum);
		orderDto.setCreateTime(new Date());
		orderDto.setOrderType(new Short("0"));	//设置订单类型为采购单
		int result =orderService.addOrder(orderDto);
		logger.debug("新增采购订单条目数:"+result);
		if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");      
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
	}
	
	
	/**
	 * 修改采购订单
	 * @param Order
	 * @return JSONObject
	 */
	@ApiOperation("修改编辑采购订单")
	@PostMapping("/modifyOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_modify')")
	public JSONObject modifyOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindResult) {
		JSONObject jsonObject = new JSONObject();
		//Validated自动验证
	      if(bindResult.hasErrors()) {
	        Map<String,String> map=new HashMap<String,String>();
	        List<FieldError> list=bindResult.getFieldErrors();
	        for(FieldError error:list) {
	        	String n=error.getField();
	        	String m=error.getDefaultMessage();
	        	map.put(n,m);
	        }
	        jsonObject.put("code",-1);
	        jsonObject.put("message","验证信息未能通过");
	        jsonObject.put("data",map);
	        return jsonObject;
	        }
		    //采用OrderValidator(封装对Order各属性的验证)
	     Map<String,String> validateMap =OrderValidator.validator(orderDto);
	       if(!validateMap.isEmpty()) {
	        jsonObject.put("code",-1);
	        jsonObject.put("message","验证信息未能通过");
	        jsonObject.put("data",validateMap);
	        return jsonObject;
	        }
	    int result=orderService.modOrder(orderDto);
	    logger.debug("修改采购订单条目数:"+result);
	    if (result > 0) {
           jsonObject.put("code", 0);
           jsonObject.put("message", "修改成功");
          return jsonObject;
       } else {
          throw new RuntimeException("修改失败");
       }
	}
	
	
	/**
	 * 取消采购订单
	 * @param Long
	 * @return JSONObject
	 */
	@ApiOperation("取消采购订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_delete')")
	public JSONObject delOrder(@ApiParam(value="采购订单id",required=true) @RequestParam Long orderId) {
		int result =orderService.updateOrderIsDraft(orderId,(short) 2);
		logger.debug("取消销售订单条目数:"+result);
		if (result > 0) {
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("code",0);
	        jsonObject.put("message", "取消成功");
	        return jsonObject;
	    } else {
	        throw new RuntimeException("取消失败");
	    }
	 }
	
	
	/**
	 * 发布采购订单
	 * @param Long
	 * @return JSONObject
	 */
	@ApiOperation("发布采购订单")
	@PostMapping("/releaseOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_release')")
	public JSONObject releaseOrder(@ApiParam(value="采购订单id",required=true) @RequestParam Long orderId) {
		int result =orderService.updateOrderIsDraft(orderId,(short) 1);
		logger.debug("取消销售订单条目数:"+result);
		if (result > 0) {
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("code", 0);
	        jsonObject.put("message", "发布成功");
	        return jsonObject;
	    } else {
	        throw new RuntimeException("发布失败");
	    }
	}
	
	
	
	
	
	
	

}
