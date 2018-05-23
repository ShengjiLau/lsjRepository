package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.model.SubItemsInfoDao;

import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2017/11/9
 */

public interface SubItemsInfoService {
    /**
     * 新增子商品
     *
     * @param subItemsInfoDao
     * @return
     */
    int addSubItemsInfo(SubItemsInfoDao subItemsInfoDao);

    /**
     * 根据子商品 subItemId 删除子商品
     *
     * @param subItemId
     * @return
     */
    int deleteSubItemsInfo(Long subItemId,Long companyId);

    /**
     * 根据主商品 itemId 删除此主商品下的所有子商品
     * @param itemId
     * @return
     */
    int deleteSubItemsInfoByItemId(Long itemId,Long companyId);

    /**
     * 根据子商品 subItemId 修改子商品
     *
     * @param subItemsInfoDao
     * @return
     */
    int modifySubItemsInfo(SubItemsInfoDao subItemsInfoDao);

    /**
     * 根据子商品 subItemId 查询单个子商品
     *
     * @param subItemId
     * @return
     */
    SubItemsInfoDao querySubItemsInfoBySubItemId(Long subItemId,Long companyId);
    /**
     * 查询列表
     * @param itemId
     * @param companyId
     * @return
     */
    PageInfo<List<SubItemsInfoDao>> querySubAndSpecAndPropListByItemId(Long itemId,Long companyId,PageInfo pageInfo);


    /**
     * 查询列表
     * @param itemId
     * @param companyId
     * @return
     */
    List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId,Long companyId);

    /**
     * 根据条件查询列表
     * @param map
     * @return
     */
    PageInfo<List<GoodsInfoDao>> queryByCondition(Map map);


    List<Long> queryGoodsIdsByCondition(GoodsListParamsDto dto);
}
