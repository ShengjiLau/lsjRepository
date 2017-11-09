package com.lcdt.items.dao;

import com.lcdt.items.model.SubItemsInfo;
import java.util.List;

public interface SubItemsInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sub_items_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long subItemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sub_items_info
     *
     * @mbggenerated
     */
    int insert(SubItemsInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sub_items_info
     *
     * @mbggenerated
     */
    SubItemsInfo selectByPrimaryKey(Long subItemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sub_items_info
     *
     * @mbggenerated
     */
    List<SubItemsInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sub_items_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SubItemsInfo record);
}