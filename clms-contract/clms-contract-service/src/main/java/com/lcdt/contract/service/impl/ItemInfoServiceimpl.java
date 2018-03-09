package com.lcdt.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lcdt.contract.dao.ItemInfoMapper;
import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.service.ItemInfoService;
import com.lcdt.contract.web.dto.ItemInfoDto;


/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:37:57
 * @version
 */
@Service
@Transactional
public class ItemInfoServiceimpl implements ItemInfoService {
	
	@Autowired
	private ItemInfoMapper itemInfoMapper;

	@Override
	public List<ItemInfo> getItemList(ItemInfoDto itemInfoDto) {
		
		return itemInfoMapper.SelectItemInfo(itemInfoDto);
	}

}
