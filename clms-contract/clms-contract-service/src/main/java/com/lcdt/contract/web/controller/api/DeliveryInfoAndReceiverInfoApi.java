package com.lcdt.contract.web.controller.api;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.userinfo.dto.CompanyInfoDto;
import com.lcdt.userinfo.dto.WarehouseInfoDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.WarehouseLinkman;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.WarehouseService;

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
	
	
//	@Autowired
//	private CompanyService companyService;
//	
//	@Autowired
//	private WarehouseService warehouseService;
//	
//	
//	
//	
//	@ApiOperation(value="供应商名称信息列表",notes="相关供应商名称信息")
//	@GetMapping("/supplier")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('supplier_info')")
//	public PageBaseDto<CompanyInfoDto> getDeliveryInfo(@ApiParam(value="粗略名称") @RequestParam String roughName) {
//		//companyService.getCompanyByRoughName(roughName);
//		PageBaseDto<CompanyInfoDto> pageBaseDto=new PageBaseDto<CompanyInfoDto>();
//		pageBaseDto.setList(companyService.getCompanyByRoughName(roughName));
//		return pageBaseDto;
//	}
//	
//	
//	@ApiOperation(value="供应商信息列表",notes="相关供应商信息")
//	@GetMapping("/delivery")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delivery_info')")
//	public Company getCompanyById(@RequestParam @ApiParam("供应商id")Long companyId) {
//		return companyService.selectById(companyId);
//	}
//	
//	
//	
//	@ApiOperation(value="获取我的仓库信息",notes="我的仓库信息列表")
//	@GetMapping("/warehouseList")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_list')")
//	public PageBaseDto<WarehouseInfoDto> getWarehouList() {
//		Long companyId = SecurityInfoGetter.getCompanyId();
//		//Long createId=SecurityInfoGetter.getUser().getUserId();
//		PageBaseDto<WarehouseInfoDto> pageBaseDto=new PageBaseDto<WarehouseInfoDto>();
//		pageBaseDto.setList(warehouseService.selectWarehouseInfoByCompanyId(companyId)); 
//		return pageBaseDto;
//	
//	}
//	
//	@ApiOperation(value="收货人信息列表",notes="相关收货信息")
//	@GetMapping("/receiver")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('receiver_info')")
//	public PageBaseDto<WarehouseLinkman> getReceiverInfo(@ApiParam(value="仓库id") @RequestParam Long whId){
//		PageBaseDto<WarehouseLinkman> pageBaseDto=new PageBaseDto<WarehouseLinkman>();
//		pageBaseDto.setList(warehouseService.getWarehouseLinkmanInfoByWhId(whId));
//		return pageBaseDto;
//	}
//	
//	
	
	
	
	
	
	
	
	
}
