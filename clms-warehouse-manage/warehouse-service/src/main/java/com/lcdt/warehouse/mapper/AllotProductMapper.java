package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.AllotProduct;

import java.util.List;
import java.util.Map;

public interface AllotProductMapper {
    int deleteByPrimaryKey(Long apId);

    int insert(AllotProduct record);

    int insertSelective(AllotProduct record);

    AllotProduct selectByPrimaryKey(Long apId);

    int updateByPrimaryKeySelective(AllotProduct record);

    int updateByPrimaryKey(AllotProduct record);
    /**
     * 批量插入
     * @param allotProductList
     * @return
     */
    int insertBatch(List<AllotProduct> allotProductList);
    /**
     * 批量更新
     * @param allotProductList
     * @return
     */
    int updateBatch(List<AllotProduct> allotProductList);
    /**
     * 根据主键批量删除
     * @param map
     * @return
     */
    int deleteByBatch(Map<String, Object> map);
    /**
     * 根据调拨单id查询该调拨单下的商品apIds
     * @param allotId
     * @return
     */
    List<Long> selectApIdsByAllotId(Long allotId);
    /**
     * 取消/删除（is_deleted=1）
     * @param allotId
     * @return
     */
    int updateAllotProductIsDelete(Long allotId);
}