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
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.PageBaseDto;
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
	 * @param orderDto
	 * @return JSONObject
	 */
	@ApiOperation(value="采购订单列表",notes="采购订单列表数据")
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_order_list')")
	public JSONObject OrderList(OrderDto orderDto
	//	,@ApiParam(value="第几页",required=true,defaultValue="1") @RequestParam Integer pageNum,
   //	@ApiParam(value="每页条目数量",required=true,defaultValue="6")@RequestParam Integer pagesize
			){
		Long UserId=SecurityInfoGetter.getUser().getUserId();
		Long companyId=SecurityInfoGetter.getCompanyId();	
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		PageInfo<OrderDto> pageInfo = orderService.OrderList(orderDto);
		PageBaseDto<OrderDto> pageBaseDto =new PageBaseDto<OrderDto>();
		pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		logger.debug("采购订单条目数"+pageInfo.getTotal());
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("code","0");
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
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('select_sales_order')")
	public  JSONObject selectOrder(@ApiParam(value="订单id")@RequestParam Long orderId){
		OrderDto orderDto= orderService.selectByPrimaryKey(orderId);
		JSONObject jsonObject=new JSONObject();
		if(orderDto!=null) {
			jsonObject.put("code","0");
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
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_purchase_order')")
	public JSONObject addOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindResult) {
		 JSONObject jsonObject = new JSONObject();
	        Map map=new HashMap();
	        if(bindResult.hasErrors()) {  	 
	        	List<FieldError> list=bindResult.getFieldErrors();
	        	for(FieldError error:list) {
	        		String n=error.getField();
	        		String m=error.getDefaultMessage();
	        		map.put(n,m);
	        	}
	        	if(orderDto.getOrderType()!=null) {
	        		if(orderDto.getOrderType()==1) {
	        			if(orderDto.getSender()==null) {
	        				map.put("sender","收货联系人不可为空");
	        			}
	        			if(orderDto.getSenderPhone()==null) {
	            			map.put("senderPhone","联系方式不可为空");
	            		}
	        			if(orderDto.getSendProvince()==null) {
	        				map.put("sendProvince","地址省不可为空");
	        			}
	        			if(orderDto.getSendCity()==null) {
	        				map.put("sendCity","地址市不可为空");
	        			}
	        			if(orderDto.getSendDistrict()==null) {
	        				map.put("sendDistrict","地址区不可为空");
	        			}
	        			if(orderDto.getSendAddress()==null) {
	        				map.put("sengAddress","详细地址不可为空");
	        			}
	        		} 
	        		if(orderDto.getOrderType()==0){
	        			if(orderDto.getReceiver()==null) {
	        				map.put("receiver","收货联系人不可为空");
	        			}
	        			if(orderDto.getReceiverPhone()==null) {
	        				map.put("receiverPhone","收货联系方式不可为空");
	        			}
	        			if(orderDto.getReceiverProvince()==null) {
	        				map.put("receiverProvince","地址省不可为空");
	        			}
	        			if(orderDto.getReceiverCity()==null) {
	        				map.put("receiverCity","地址市不可为空");
	        			}
	        			if(orderDto.getReceiveDistrict()==null) {
	        				map.put("receiveDistrict","地址区不可为空");
	        			}
	        			if(orderDto.getReceiveAddress()==null) {
	        				map.put("receiveAddress","详细地址不可为空");
	        			}
	        		}
	        	}
	            jsonObject.put("code",0);
	          	jsonObject.put("message","验证信息未能通过");
	          	jsonObject.put("data",map);
	          	return jsonObject;
	        }
	        if(orderDto.getOrderType()==1) {
				if(orderDto.getSender()==null) {
					map.put("sender","收货联系人不可为空");
				}
				if(orderDto.getSenderPhone()==null) {
	    			map.put("senderPhone","联系方式不可为空");
	    		}
				if(orderDto.getSendProvince()==null) {
					map.put("sendProvince","地址省不可为空");
				}
				if(orderDto.getSendCity()==null) {
					map.put("sendCity","地址市不可为空");
				}
				if(orderDto.getSendDistrict()==null) {
					map.put("sendDistrict","地址区不可为空");
				}
				if(orderDto.getSendAddress()==null) {
					map.put("sengAddress","详细地址不可为空");
				}
				 jsonObject.put("code",0);
		         jsonObject.put("message","验证信息未能通过");
		         jsonObject.put("data",map);
		         return jsonObject;
			} 
	        if(orderDto.getOrderType()==0){
				if(orderDto.getReceiver()==null) {
					map.put("receiver","收货联系人不可为空");
				}
				if(orderDto.getReceiverPhone()==null) {
					map.put("receiverPhone","收货联系方式不可为空");
				}
				if(orderDto.getReceiverProvince()==null) {
					map.put("receiverProvince","地址省不可为空");
				}
				if(orderDto.getReceiverCity()==null) {
					map.put("receiverCity","地址市不可为空");
				}
				if(orderDto.getReceiveDistrict()==null) {
					map.put("receiveDistrict","地址区不可为空");
				}
				if(orderDto.getReceiveAddress()==null) {
					map.put("receiveAddress","详细地址不可为空");
				}
				 jsonObject.put("code",0);
		         jsonObject.put("message","验证信息未能通过");
		         jsonObject.put("data",map);
		         return jsonObject;
			}
	        if(orderDto.getOrderProductList()==null||orderDto.getOrderProductList().size()==0) {
	        	map.put("orderProductList","至少拥有一条商品");
	        	jsonObject.put("code",0);
		        jsonObject.put("message","验证信息未能通过");
		        jsonObject.put("data",map);
		        return jsonObject;	
	        }
	        if(orderDto.getOrderProductList()!=null&&orderDto.getOrderProductList().size()!=0) {
	        	for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
	        		if(orderProduct.getName()==null) {
	        			map.put("name","商品名称不可为空");
	        		}
	        		if(orderProduct.getNum()==null) {
	        			map.put("num","商品数量不可为空");
	        		}
	        		if(orderProduct.getPrice()==null) {
	        			map.put("price","商品单价不可为空");
	        		}
	        		if(orderProduct.getSku()==null) {
	        			map.put("sku","商品单位不可为空");
	        		}
	        	}
	        	jsonObject.put("code",0);
		        jsonObject.put("message","验证信息未能通过");
		        jsonObject.put("data",map);
		        return jsonObject;
	        }
		Long UserId=SecurityInfoGetter.getUser().getUserId();
		Long companyId=SecurityInfoGetter.getCompanyId();
		String orderSerialNum =SerialNumAutoGenerator.serialNoGenerate();
		orderDto.setCompanyId(companyId);
		logger.debug("创建人UserId:"+UserId);
		logger.debug("所属公司companyId:"+companyId);
		orderDto.setCreateUserId(UserId);
		orderDto.setOrderSerialNo(orderSerialNum);
		orderDto.setCreateTime(new Date());
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
	@ApiOperation("修改采购订单")
	@PostMapping("/modifyOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('mod_purchase_order')")
	public JSONObject modifyOrder(@Validated @RequestBody OrderDto orderDto,BindingResult bindResult) {
        JSONObject jsonObject = new JSONObject();
		if(bindResult.hasErrors()) {
			jsonObject.put("code","-1");
			jsonObject.put("message",bindResult.getFieldError().getDefaultMessage());
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
	 * 删除采购订单
	 * @param Order
	 * @return JSONObject
	 */
	@ApiOperation("删除采购订单")
	@PostMapping("/deleteOrder")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('del_purchase_order')")
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
