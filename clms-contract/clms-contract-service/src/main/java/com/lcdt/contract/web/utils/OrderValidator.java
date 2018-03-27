package com.lcdt.contract.web.utils;

import java.util.HashMap;
import java.util.Map;

import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.web.dto.OrderDto;

/**
 * 此类对新增订单或者更新编辑订单时提交的信息进行规范要求验证
 * @author Sheng-ji Lau
 * @date 2018年3月22日上午10:53:38
 * @version
 */
public class OrderValidator {
	
	
	public static Map<String,String> validator(OrderDto orderDto) {
		Map<String,String> map =new HashMap<String,String>();
		if(orderDto.getOrderType()==0) {
			if(null==orderDto.getSender()) {
				map.put("sender","收货联系人不可为空");
			}
			if(null==orderDto.getSenderPhone()) {
    			map.put("senderPhone","收货联系方式不可为空");
    		}
			if(null==orderDto.getSendProvince()) {
				map.put("sendProvince","收货地址省不可为空");
			}
			if(null==orderDto.getSendCity()) {
				map.put("sendCity","收货地址市不可为空");
			}
			if(null==orderDto.getSendDistrict()) {
				map.put("sendDistrict","收货地址区不可为空");
			}
			if(null==orderDto.getSendAddress()) {
				map.put("sengAddress","收货详细地址不可为空");
			}
		} 
        if(orderDto.getOrderType()==1){
			if(null==orderDto.getReceiver()) {
				map.put("receiver","收货联系人不可为空");
			}
			if(null==orderDto.getReceiverPhone()) {
				map.put("receiverPhone","收货联系方式不可为空");
			}
			if(null==orderDto.getReceiverProvince()) {
				map.put("receiverProvince","收货地址省不可为空");
			}
			if(null==orderDto.getReceiverCity()) {
				map.put("receiverCity","收货地址市不可为空");
			}
			if(null==orderDto.getReceiveDistrict()) {
				map.put("receiveDistrict","收货地址区不可为空");
			}
			if(null==orderDto.getReceiveAddress()) {
				map.put("receiveAddress","收货详细地址不可为空");
			}
        }
        if(null==orderDto.getOrderProductList()||orderDto.getOrderProductList().size()==0) {
        	map.put("orderProductList","请至少添加一条商品");
        }
        if(null!=orderDto.getOrderProductList()&&orderDto.getOrderProductList().size()!=0) {
        	for(OrderProduct orderProduct:orderDto.getOrderProductList()) {
        		if(null==orderProduct.getName()) {
        			map.put("name","商品名称不可为空");
        		}
        		if(null==orderProduct.getNum()) {
        			map.put("num","商品数量不可为空");
        		}
        		if(null==orderProduct.getPrice()) {
        			map.put("price","商品单价不可为空");
        		}
        		if(null==orderProduct.getSku()) {
        			map.put("sku","商品单位不可为空");
        		}
        	}
        }
		return map;
		
        }
	}
	
	
	
	
	
	
	
	
	


