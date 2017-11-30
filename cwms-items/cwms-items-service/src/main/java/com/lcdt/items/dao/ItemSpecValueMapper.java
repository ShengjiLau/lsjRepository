package com.lcdt.items.dao;

import com.lcdt.items.model.ItemSpecValue;

import java.util.List;

public interface ItemSpecValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_value
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long spvId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_value
     *
     * @mbg.generated
     */
    int insert(ItemSpecValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_value
     *
     * @mbg.generated
     */
    ItemSpecValue selectByPrimaryKey(Long spvId,Long companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_value
     *
     * @mbg.generated
     */
    List<ItemSpecValue> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_spec_value
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ItemSpecValue record);

    /**
     * 根据自定义规格键的spkId,companyId删除规格值
     * @param spkId
     * @param companyId
     * @return
     */
    int deleteBySpkIdAndCompanyId(Long spkId,Long companyId);

    /**
     * 根据主键和公司id删除
     * @param spkvId
     * @param companyId
     * @return
     */
    int deleteBySpvIdAndCompanyId(Long spkvId,Long companyId);
}