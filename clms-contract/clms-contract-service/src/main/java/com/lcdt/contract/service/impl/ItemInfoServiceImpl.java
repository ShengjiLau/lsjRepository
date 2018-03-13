package com.lcdt.contract.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lcdt.contract.dao.ItemInfoMapper;
import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.model.WarehouseInfo;
import com.lcdt.contract.service.ItemInfoService;
import com.lcdt.contract.web.dto.ItemInfoDto;


/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:37:57
 * @version
 */

@Service
@Transactional
public class ItemInfoServiceImpl implements ItemInfoService {
	
	@Autowired
	private ItemInfoMapper itemInfoMapper;
	

	@Override
	public List<ItemInfo> getItemList(ItemInfoDto itemInfoDto) {
		return itemInfoMapper.SelectItemInfo(itemInfoDto);
	}

//	@Override
//	public List<String> getSupplierList(String roughName) {	
//		return itemInfoMapper.getSupplierList(roughName);
//	}

	@Override
	public List<String> getGroups(Long companyId) {
		return itemInfoMapper.getGroupsByCompanyId(companyId);
	}

	@Override
	public List<WarehouseInfo> getWarehouseInfo(Long companyId, Long createId) {
		
		return itemInfoMapper.getWarehouseList(companyId, createId);
	}
	
	
	
	
	
	
	
	
	
	
	

}
