package com.lcdt.items.dao;

import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.model.SubItemsInfoDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据 subItemId 和 companyId 删除子商品
     * @param subItemsInfo
     * @return
     */
    int deleteBySubItemIdAndCompanyId(SubItemsInfo subItemsInfo);

    /**
     * 根据商品 itemId 和 companyId 删除子商品
     * @param subItemsInfo
     * @return
     */
    int deleteSubItemsInfoByItemIdAndCompanyId(SubItemsInfo subItemsInfo);

    /**
     * 批量插入数据
     * @param subItemsInfoList
     * @return
     */
    int insertByBatch(List<SubItemsInfo> subItemsInfoList);

    /**
     * 查询列表
     * @param itemId
     * @return
     */
    List<SubItemsInfoDao> selectSubAndSpecAndPropListByItemId(Long itemId);

    /**
     * 根据itemId 和 companyId 查询列表
     * @param subItemsInfo
     * @return
     */
    List<SubItemsInfoDao> selectSubAndSpecAndPropListByItemIdAndCompanyId(SubItemsInfo subItemsInfo);

    /**
     * 根据 subItemId 和 companyId 修改
     */
    int updateBySubItemIdAndCompanyId(SubItemsInfo subItemsInfo);

    /**
     * 根据 subItemId 和 companyId 查询
     * @param subItemsInfo
     * @return
     */
    SubItemsInfoDao selectBySubItemIdAndCompanyId(SubItemsInfo subItemsInfo);


    /**
     * 根据itemId 和 companyId 查询列表
     * @param subItemsInfo
     * @return
     */
    List<SubItemsInfo> selectSubItemInfotByItemIdAndCompanyId(SubItemsInfo subItemsInfo);

    /**
     * 查询货物列表
     * @param dto
     * @return
     */
    List<GoodsInfoDao> selectByCondition(GoodsListParamsDto dto);


    List<Long> selectGoodsIdsByCondition(GoodsListParamsDto dto);

    /**
     * 根据商品编码查询
     * @param code
     * @param companyId
     * @return
     */
    List<SubItemsInfo> selectSubItemInfoByCode(@Param("subItemId") Long subItemId,@Param("code") String code,@Param("companyId") Long companyId);
}