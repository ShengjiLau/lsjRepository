package com.lcdt.contract.service;


import java.util.List;

import com.lcdt.contract.model.DeliveryInfo;
import com.lcdt.contract.model.ReceiverInfo;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月12日下午3:56:33
 * @version
 */
public interface ReceiverAndDeliveyService {
	
	
	List<DeliveryInfo> getDeliveryInfo(String roughName);
	
	
	ReceiverInfo getReceiverInfo(Integer whId);
	
	
	
	
	

}
