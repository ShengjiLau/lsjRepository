package com.lcdt.contract.web.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcdt.contract.model.DeliveryInfo;
import com.lcdt.contract.model.ReceiverInfo;
import com.lcdt.contract.service.ReceiverAndDeliveyService;
import com.lcdt.contract.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

	/**
	 * @author Sheng-ji Lau
	 * @date 2018年3月11日下午5:39:05
	 * @version
	 */

	@RestController
	@Api(value="发货收货信息查询Api",description="发货收货信息查询")
	@RequestMapping("/getDar")
	public class DeliveryInfoAndReceiverInfoApi {
		
	Logger logger = LoggerFactory.getLogger(DeliveryInfoAndReceiverInfoApi.class);
	
	@Autowired
	private ReceiverAndDeliveyService receAndDeliv;
	
	
	@ApiOperation(value="供应商信息列表",notes="相关供应商信息")
	@GetMapping("/delivery")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delivery_info')")
	public PageBaseDto<DeliveryInfo> getDeliveryInfo(@ApiParam(value="粗略名称") @RequestParam String roughName) {
		List<DeliveryInfo> deliveryInfoList =receAndDeliv.getDeliveryInfo(roughName);
		PageBaseDto<DeliveryInfo> pageBaseDto =new PageBaseDto<DeliveryInfo>(deliveryInfoList);
		logger.debug("查询到供应商的信息条数:"+deliveryInfoList.size());
		return pageBaseDto;
	}
	
	
	@ApiOperation(value="收货人信息列表",notes="相关收货信息")
	@GetMapping("/receiver")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receiver_info')")
	public ReceiverInfo getReceiverInfo(@ApiParam(value="仓库id") @RequestParam Integer whId){
		ReceiverInfo receiverInfo=receAndDeliv.getReceiverInfo(whId);
		logger.debug("收货联系人:"+receiverInfo.getLinkMan());
		return receiverInfo;
	}
	
	
}
