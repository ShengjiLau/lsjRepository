package com.lcdt.items.utils;

import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.SubItemsInfo;

/**
 * Created by lyqishan on 2017/11/13
 */

public class SubItemsInfoDtoToSubItemsInfoUtil {

    /**
     * 工具类，把 SubItemsInfoDto 转换成 SubItemsInfo
     * @param subItemsInfoDto
     * @return
     */
    public static SubItemsInfo parseSubItemsInfo(SubItemsInfoDto subItemsInfoDto){
        SubItemsInfo subItemsInfo=new SubItemsInfo();

        subItemsInfo.setSubItemId(subItemsInfoDto.getSubItemId());
        subItemsInfo.setItemId(subItemsInfo.getItemId());
        subItemsInfo.setImage(subItemsInfoDto.getImage());
        subItemsInfo.setCode(subItemsInfoDto.getCode());
        subItemsInfo.setBarCode(subItemsInfoDto.getBarCode());
        subItemsInfo.setPurchasePrice(subItemsInfoDto.getPurchasePrice());
        subItemsInfo.setWholesalePrice(subItemsInfoDto.getWholesalePrice());
        subItemsInfo.setRetailPrice(subItemsInfoDto.getRetailPrice());
        subItemsInfo.setSpecComb(subItemsInfoDto.getSpecComb());
        subItemsInfo.setCompanyId(subItemsInfoDto.getCompanyId());

        return subItemsInfo;
    }
}
