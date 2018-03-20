package com.lcdt.contract.service;

import java.util.List;

import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.model.WarehouseInfo;
import com.lcdt.contract.web.dto.ItemInfoDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:32:07
 * @version
 */
public interface ItemInfoService {
	
	List<ItemInfo> getItemList(ItemInfoDto itemInfoDto);
	
//	List<String> getSupplierList(String roughName);
	
	List<String> getGroups(Long companyId);
	
	List<WarehouseInfo> getWarehouseInfo(Long CompanyId,Long createId);
	
}
