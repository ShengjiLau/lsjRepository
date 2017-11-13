package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.SubItemsInfo;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/9
 */

public interface SubItemsInfoService {
    /**
     * 新增子商品
     *
     * @param subItemsInfoDto
     * @return
     */
    int addSubItemsInfo(SubItemsInfoDto subItemsInfoDto);

    /**
     * 根据子商品 subItemId 删除子商品
     *
     * @param subItemId
     * @return
     */
    int deleteSubItemsInfo(Long subItemId);

    /**
     * 根据子商品 subItemId 修改子商品
     *
     * @param subItemsInfoDto
     * @return
     */
    int modifySubItemsInfo(SubItemsInfoDto subItemsInfoDto);

    /**
     * 根据子商品 subItemId 查询单个子商品
     *
     * @param subItemId
     * @return
     */
    SubItemsInfo querySubItemsInfoBySubItemId(Long subItemId);

    /**
     * 根据主商品 itemId 查询此主商品下的所有子商品
     *
     * @param itemId
     * @return
     */
    List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId);

    /**
     * 根据主商品 itemId 查询此主商品下的所有子商品，包含分页
     *
     * @param itemsId
     * @param pageInfo
     * @return
     */
    List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemsId, PageInfo pageInfo);

    /**
     * 根据主商品 itemId 删除此主商品下的所有子商品
     * @param itemId
     * @return
     */
    int deleteSubItemsInfoByItemId(Long itemId);

    /**
     * 传入SubItemsInfo列表，批量插入数据
     * @param subItemsInfoList
     * @return
     */
    int addSubItemsInfoBatch(List<SubItemsInfo> subItemsInfoList);


}
