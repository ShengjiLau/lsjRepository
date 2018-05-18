package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.ShiftInventoryListDO;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
public interface ShiftInventoryListDOMapper {
    int deleteByPrimaryKey(Long shiftId);

    int insert(ShiftInventoryListDO record);

    int insertSelective(ShiftInventoryListDO record);

    ShiftInventoryListDO selectByPrimaryKey(Long shiftId);

    int updateByPrimaryKeySelective(ShiftInventoryListDO record);

    int updateByPrimaryKey(ShiftInventoryListDO record);
}