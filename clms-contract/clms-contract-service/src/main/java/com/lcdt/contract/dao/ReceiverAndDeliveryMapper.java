package com.lcdt.contract.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lcdt.contract.model.DeliveryInfo;
import com.lcdt.contract.model.ReceiverInfo;
/**
 * @author Sheng-ji Lau
 * @date 2018年3月13日下午5:51:58
 * @version
 */
public interface ReceiverAndDeliveryMapper {
	
	List<DeliveryInfo> getDeliveryInfoList(@Param(value = "roughName") String roughName);
	
	ReceiverInfo getReceiverInfo(Integer whId);
	
	
	

}
