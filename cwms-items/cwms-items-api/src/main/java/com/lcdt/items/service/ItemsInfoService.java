package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.model.ItemsInfoDao;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/26
 */

public interface ItemsInfoService {
    /**
     * 增加新商品,在调用此方法之前需要先调用 queryItemsInfoByCodeAndCompanyId() 判断此企业下的商品编码是否存在,确认本企业内商品编码的唯一性
     * @param itemsInfoDao
     * @return
     */
    int addItemsInfo(ItemsInfoDao itemsInfoDao);

    /**
     * 删除商品，同时需要删除主商品下的全部子商品
     * @param itemId
     * @return
     */
    int deleteItemsInfo(Long itemId,Long companyId);

    /**
     * 根据商品 itemId 修改商品 在调用此方法之前需要先调用 queryItemsInfoByCodeAndCompanyId() 判断此企业下的商品编码是否存在,确认本企业内商品编码的唯一性
     * @param itemsInfoDao
     * @return
     */
    int modifyItemsInfo(ItemsInfoDao itemsInfoDao);


    /**
     * 根据itemId查询商品详情
     * @param itemId
     * @return
     */
    ItemsInfoDao queryIetmsInfoDetails(Long itemId,Long companyId);

    /**
     * 查询商品列表
     * @param itemsInfo
     * @param pageInfo
     * @return
     */
    PageInfo<List<ItemsInfoDao>> queryItemsByCondition(ItemsInfo itemsInfo, PageInfo pageInfo);

    /**
     * 查询商品列表
     * @param itemsInfo
     * @param pageInfo
     * @return
     */
    PageInfo<List<ItemsInfoDao>> queryItemsByItemsInfo(ItemsInfo itemsInfo, PageInfo pageInfo);
}
