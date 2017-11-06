package com.lcdt.items.service;

import com.lcdt.items.model.ItemClassify;

import java.util.List;

/**
 * 商品分类服务 ItemClassifyService
 * Created by lyqishan on 2017/10/30
 */

public interface ItemClassifyService {

    /**
     * 增加商品分类 ItemClassify
     *
     * @param itemClassify
     * @return
     */
    int addItemClassify(ItemClassify itemClassify);

    /**
     * 根据商品分类classifyId删除商品分类
     *
     * @param classifyId
     * @return
     */
    int deleteItemClassify(Long classifyId);

    /**
     * 根据商品classifyId修改商品分类
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(ItemClassify record);

    /**
     * 根据classifyId查询商品分类
     *
     * @param classifyId
     * @return
     */
    ItemClassify selectByPrimaryKey(Long classifyId);

    /**
     * 根据企业companyId 查询这个企业下的分类
     * @param companyId
     * @return
     */
    List<ItemClassify> queryItemClassifyByCompanyId(Long companyId);
}
