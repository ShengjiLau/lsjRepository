package com.lcdt.items.utils;

import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.web.dto.ItemsInfoDto;

/**
 * Created by lyqishan on 2017/11/13
 */

public class ItemsInfoDtoToItemsInfoUtil {

    /**
     * 工具类，把 ItemsInfoDto 转换成 ItemsInfo
     * @param itemsInfoDto
     * @return
     */
    public static ItemsInfo parseItemsInfo(ItemsInfoDto itemsInfoDto) {
        ItemsInfo itemsInfo = new ItemsInfo();

        itemsInfo.setItemId(itemsInfoDto.getItemId());
        itemsInfo.setSubject(itemsInfoDto.getSubject());
        itemsInfo.setCode(itemsInfoDto.getCode());
        itemsInfo.setBarCode(itemsInfoDto.getBarCode());
        itemsInfo.setTradeType(itemsInfoDto.getTradeType());
        itemsInfo.setPurchasePrice(itemsInfoDto.getPurchasePrice());
        itemsInfo.setRetailPrice(itemsInfoDto.getRetailPrice());
        itemsInfo.setRetailPrice(itemsInfoDto.getRetailPrice());
        itemsInfo.setIntroduction(itemsInfoDto.getIntroduction());
        itemsInfo.setClassifyId(itemsInfoDto.getClassifyId());
        itemsInfo.setClassifyName(itemsInfoDto.getClassifyName());
        itemsInfo.setUnitId(itemsInfoDto.getUnitId());
        itemsInfo.setUnitName(itemsInfoDto.getUnitName());
        itemsInfo.setConverId(itemsInfoDto.getConverId());
        itemsInfo.setSubItemProperty(itemsInfoDto.getSubItemProperty());
        itemsInfo.setStoreRule(itemsInfoDto.getStoreRule());
        itemsInfo.setItemBatch(itemsInfoDto.getItemBatch());
        itemsInfo.setCombinationInfo(itemsInfoDto.getCombinationInfo());
        itemsInfo.setCompanyId(itemsInfoDto.getCompanyId());
        itemsInfo.setItemType(itemsInfoDto.getItemType());
        return itemsInfo;
    }
}
