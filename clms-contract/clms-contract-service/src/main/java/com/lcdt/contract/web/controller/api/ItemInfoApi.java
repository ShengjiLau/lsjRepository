package com.lcdt.contract.web.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.model.WarehouseInfo;
import com.lcdt.contract.service.ItemInfoService;
import com.lcdt.contract.web.dto.ItemInfoDto;
import com.lcdt.contract.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 新建订单时对于业务组信息和商品信息和仓库信息的查询
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:29:04
 * @version
 */

@RestController
@Api(value="信息查询Api",description="商品与业务组")
@RequestMapping("/getInfo")
public class ItemInfoApi {
	
	Logger logger = LoggerFactory.getLogger(ItemInfoApi.class);
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@ApiOperation(value="相关商品列表",notes="相关商品信息")
	@GetMapping("/itemInfo")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_info')")
	public PageBaseDto<ItemInfo> getItemInfo(ItemInfoDto itemInfoDto) {
		Long companyId = SecurityInfoGetter.getCompanyId(); 
		itemInfoDto.setCompanyId(companyId);
		Long createId=SecurityInfoGetter.getUser().getUserId();
		itemInfoDto.setCreateId(createId);
		List<ItemInfo> itemInfoList=itemInfoService.getItemList(itemInfoDto);
		PageBaseDto<ItemInfo>  pageBaseDto =new PageBaseDto<ItemInfo>(itemInfoList);
		logger.debug("查询到商品条数:"+itemInfoList.size());
		return pageBaseDto;
	}
	
	
//	@ApiOperation(value="获取供应商信息",notes="供应商信息列表")
//	@GetMapping("/supplierInfo")
//	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('supplier_info')")
//	public PageBaseDto getSupplierInfo(@RequestParam String roughName) {
//		List<String> supplierList=itemInfoService.getSupplierList(roughName);
//		PageBaseDto pageBaseDto =new PageBaseDto(supplierList);
//		return pageBaseDto;
//	}
	
	
	@ApiOperation(value="获取业务组信息",notes="业务组信息列表")
	@GetMapping("/groups")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('groups_info')")
	public PageBaseDto<String> getGroups() {
		Long companyId = SecurityInfoGetter.getCompanyId();
		List<String> groupList =itemInfoService.getGroups(companyId);
		PageBaseDto<String> pageBaseDto =new PageBaseDto<String>(groupList);
		logger.debug("查询到业务组数:"+groupList.size());
		return pageBaseDto;
	}

	
	
	@ApiOperation(value="获取仓库信息",notes="仓库信息列表")
	@GetMapping("/warehouse")
	@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('warehouse_info')")
	public PageBaseDto<WarehouseInfo> getWarehouList() {
		Long companyId = SecurityInfoGetter.getCompanyId();
		Long createId=SecurityInfoGetter.getUser().getUserId();
		List<WarehouseInfo> warehouseList=itemInfoService.getWarehouseInfo(companyId, createId);
		PageBaseDto<WarehouseInfo> pageBaseDto=new PageBaseDto(warehouseList);
		return pageBaseDto;
	}
	
}
