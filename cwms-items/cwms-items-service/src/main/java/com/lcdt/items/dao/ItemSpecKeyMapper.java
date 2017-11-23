package com.lcdt.items.dao;

import com.lcdt.items.model.ItemSpecKey;
import com.lcdt.items.model.ItemSpecificationDao;

import java.util.List;

public interface ItemSpecKeyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_key
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long spkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_key
     *
     * @mbg.generated
     */
    int insert(ItemSpecKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_key
     *
     * @mbg.generated
     */
    ItemSpecKey selectByPrimaryKey(Long spkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_key
     *
     * @mbg.generated
     */
    List<ItemSpecKey> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_key
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ItemSpecKey record);

    /**
     * 根据企业 companyId 获取此企业下的所有自定义规格
     * @param companyId
     * @return
     */
    List<ItemSpecificationDao> selectSpecificationList(Long companyId);
}