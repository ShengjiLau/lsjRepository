package com.lcdt.items.service;

import com.lcdt.items.model.CustomValue;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-13
 */
public interface CustomValueService {

    /**
     * 批量新增
     * @param customValueList
     * @return
     */
    int addForBatch(List<CustomValue> customValueList);

    /**
     * 修改商品时：一次性获取商品主商品和子商品自定义属性值
     * @param itemId    主商品id
     * @param subItemIds    子商品id串，英文逗号分隔;
     * @return
     */
    List<CustomValue> getItemAndSubItem(String itemId,String subItemIds);

    /**
     * 批量更新
     * @param customValueList
     * @return
     */
    int updateForBatch(List<CustomValue> customValueList);

}
