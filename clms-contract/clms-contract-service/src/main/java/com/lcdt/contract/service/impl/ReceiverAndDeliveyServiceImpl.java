package com.lcdt.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcdt.contract.dao.ReceiverAndDeliveryMapper;
import com.lcdt.contract.model.DeliveryInfo;
import com.lcdt.contract.model.ReceiverInfo;
import com.lcdt.contract.service.ReceiverAndDeliveyService;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月12日下午3:58:33
 * @version
 */
@Service
@Transactional
public class ReceiverAndDeliveyServiceImpl implements ReceiverAndDeliveyService{

	@Autowired
	private ReceiverAndDeliveryMapper receiverAndDelivery;

	@Override
	public List<DeliveryInfo> getDeliveryInfo(String roughName) {
		
		return receiverAndDelivery.getDeliveryInfoList(roughName);
	}

	@Override
	public ReceiverInfo getReceiverInfo(Integer whId) {
		
		return receiverAndDelivery.getReceiverInfo(whId);
	}
	

	



	
	

}
