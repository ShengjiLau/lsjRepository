package com.lcdt.items.service;

import com.lcdt.items.model.CustomProperty;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017/11/06
 */
public interface CustomPropertyService {
    /**
     * 获取该企业下所有的自定义属性内容
     * @return
     */
    List<CustomProperty> customPropertyList(Long companyId);

    /**
     * 根据主键更新记录
     * @param customProperty
     * @return
     */
    int updateByCustomId(CustomProperty customProperty);


    /**
     * 根据主键删除记录
     * @param customId
     * @return
     */
    int delByCustomId(Long customId);



}
