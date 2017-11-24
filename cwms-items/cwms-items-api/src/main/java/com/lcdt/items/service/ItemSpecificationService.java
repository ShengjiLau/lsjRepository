package com.lcdt.items.service;

import com.lcdt.items.dto.ItemSpecKeyDto;
import com.lcdt.items.model.ItemSpecificationDao;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */

public interface ItemSpecificationService {
    /**
     * 添加自定义商品规格
     * @param itemSpecKeyDto
     * @return
     */
    int addSpecification(ItemSpecKeyDto itemSpecKeyDto);

    /**
     * 根据自定义商品规格键的id删除
     * @param spkId
     * @return
     */
    int deleteSpecification(Long spkId);

    /**
     * 根据自定义规格 value 的 spvId 删除某个自定义规格的value
     * @param spvId
     * @return
     */
    int deleteItemSpecvalue(Long spvId);

    /**
     * 修改自定义规格
     * @param itemSpecKeyDto
     * @return
     */
    int modifySpecification(ItemSpecKeyDto itemSpecKeyDto);

    /**
     * 根据企业 companyId 获取此企业下的所有自定义规格
     * @param companyId
     * @return
     */
    List<ItemSpecificationDao> querySpecification(Long companyId);

    /**
     * 根据自定义规格名主键删除 规格名和规格值 add by liuh
     * @param spkId
     * @return
     */
    int deleteSpecificationKeyAndValueBySpkId(Long spkId);

}
