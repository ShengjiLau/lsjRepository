package com.lcdt.contract.web.controller.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderProductService;
import com.lcdt.contract.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 订单商品管理
 * @author Sheng-ji Lau
 * @date 2018年3月6日下午1:21:01
 * @version
 */
@RestController
@Api(value="订单商品管理Api",description="订单商品管理操作")
@RequestMapping("/orderProduct")
public class OrderProductApi {
	
Logger logger = LoggerFactory.getLogger(OrderProductApi.class);
	
	@Autowired
	private OrderProductService orderProductService;
	
	
	/**
	 * 查询订单商品列表
	 * @param Long
	 * @return PageBaseDto
	 */
	@ApiOperation(value="订单商品列表",notes="商品列表数据")
	@GetMapping("/list")
	//@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('product_list')")
	public PageBaseDto<List<OrderProduct>> productList(@RequestParam Long orderId){
		PageInfo<List<OrderProduct>> pageInfoList =orderProductService.OrderProductList(orderId); 
		logger.debug("订单商品条目数"+pageInfoList.getTotal());
		logger.debug("pageInfoList:"+pageInfoList.toString());
		PageBaseDto pageBaseDto = new PageBaseDto(pageInfoList.getList(),pageInfoList.getTotal());
		return pageBaseDto;
	}
	
	
	/**
	 * 新增商品
	 * @param OrderProduct
	 * @return JSONObject
	 */
	@ApiOperation("新增商品")
	@PostMapping("/addProduct")
	//@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_product')")
	public JSONObject addProduct(@Validated OrderProduct orderProduct) {	
		int result =orderProductService.addOrderProduct(orderProduct);
		logger.debug("新增商品条目数:"+result);
		if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
	}
	
	/**
	 * 修改商品
	 * @param OrderProduct
	 * @return JSONObject
	 */
	@ApiOperation("修改商品信息")
	@PostMapping("/modProduct")
	//@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('mod_product')")
	public JSONObject modProduct(@Validated OrderProduct orderProduct) {
	int result=orderProductService.modOrderProduct(orderProduct);
	logger.debug("修改商品条目数:"+result);
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
	 * 删除商品
	 * @param Long
	 * @return JSONObject
	 */
	@ApiOperation("删除商品")
	@PostMapping("/delProduct")
	//@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('del_product')")
	public JSONObject delProduct(@ApiParam(value="订单商品id",required=true) @RequestParam Long opId) {
		int result =orderProductService.delOrderProduct(opId);
		logger.debug("删除商品条目数:"+result);
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
