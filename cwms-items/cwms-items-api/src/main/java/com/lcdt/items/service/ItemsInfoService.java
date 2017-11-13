package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.model.ItemsInfoDao;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/26
 */

public interface ItemsInfoService {
    /**
     * 增加新商品,在调用此方法之前需要先调用 queryItemsInfoByCodeAndCompanyId() 判断此企业下的商品编码是否存在,确认本企业内商品编码的唯一性
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
     * 根据商品 itemId 修改商品 在调用此方法之前需要先调用 queryItemsInfoByCodeAndCompanyId() 判断此企业下的商品编码是否存在,确认本企业内商品编码的唯一性
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
     * 系统自动生成商品编码
     * @return
     */
    String getAutoItemCode();

    /**
     * 根据商品编码和企业ID查询商品是,判断本企业内的商品编码是否唯一
     * @param code
     * @param companyId
     * @return
     */
    ItemsInfo queryItemsInfoByCodeAndCompanyId(String code ,Long companyId);

    /**
     * 查询商品列表
     * @param itemsInfoDao
     * @param pageInfo
     * @return
     */
    List<ItemsInfo> queryItemsByCondition(ItemsInfoDao itemsInfoDao, PageInfo pageInfo);

}
