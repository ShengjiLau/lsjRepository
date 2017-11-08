package com.lcdt.items.service;

import com.lcdt.items.model.ItemsExtendProperty;
import com.lcdt.items.model.ItemsExtendPropertyDao;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-06
 */
public interface ItemsExtendPropertyService {

    /**
     * 批量插入商品扩展属性
     * @param itemsExtendPropertyList
     * @return
     */
    int insertByBatch(List<ItemsExtendProperty> itemsExtendPropertyList);

    /**
     * 批量删除商品扩展属性
     * @param itemsExtendProperty
     * @return
     */
    int delByBatch(ItemsExtendProperty itemsExtendProperty);


    /**
     * 批量更新商品扩展属性
     * @param itemsExtendPropertyDao
     * @return
     */
    int updateByBatch(ItemsExtendPropertyDao itemsExtendPropertyDao);

}
