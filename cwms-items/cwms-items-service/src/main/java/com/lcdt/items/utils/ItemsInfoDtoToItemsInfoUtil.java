package com.lcdt.items.utils;

import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.ItemsInfo;

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
        itemsInfo.setImage1(itemsInfoDto.getImage1());
        itemsInfo.setImage2(itemsInfoDto.getImage2());
        itemsInfo.setImage3(itemsInfoDto.getImage3());
        itemsInfo.setImage4(itemsInfoDto.getImage4());
        itemsInfo.setImage5(itemsInfoDto.getImage5());
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
