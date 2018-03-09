package com.lcdt.contract.dao;

import java.util.List;

import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.web.dto.ItemInfoDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日上午11:28:55
 * @version
 */
public interface ItemInfoMapper {
	List<ItemInfo> SelectItemInfo(ItemInfoDto itemInfoDto);
}
