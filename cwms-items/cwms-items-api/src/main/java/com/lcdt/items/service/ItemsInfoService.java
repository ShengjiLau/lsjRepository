package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.ItemsInfo;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/26
 */

public interface ItemsInfoService {
    /**
     * 增加新商品
     * @param itemsInfoDto
     * @return
     */
    int addItemsInfo(ItemsInfoDto itemsInfoDto);

    /**
     * 删除商品，同时需要删除主商品下的全部子商品
     * @param itemId
     * @return
     */
    int deleteItemsInfo(Long itemId);

    /**
     * 根据商品 itemId 修改商品
     * @param itemsInfoDto
     * @return
     */
    int modifyItemsInfo(ItemsInfoDto itemsInfoDto);

    /**
     * 根据商品itemId查询单个子商品
     * @param itemId
     * @return
     */
    ItemsInfo queryItemsInfoByItemId(Long itemId);

    /**
     * 查询商品列表
     * @param companyId
     * @param pageInfo
     * @return
     */
    List<ItemsInfo> queryItemsInfoByCompanyId(Long companyId, PageInfo pageInfo);
}
