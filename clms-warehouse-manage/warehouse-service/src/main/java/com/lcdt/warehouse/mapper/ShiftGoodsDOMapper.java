package com.lcdt.warehouse.mapper;

import java.util.List;

import com.lcdt.warehouse.entity.ShiftGoodsDO;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月18日
 * @version
 * @Description: TODO 
 */
public interface ShiftGoodsDOMapper {
    int deleteByPrimaryKey(Long shiftGoodsId);

    int insert(ShiftGoodsDO record);

    int insertSelective(ShiftGoodsDO record);

    ShiftGoodsDO selectByPrimaryKey(Long shiftGoodsId);

    int updateByPrimaryKeySelective(ShiftGoodsDO record);

    int updateByPrimaryKey(ShiftGoodsDO record);
    
    int insertShiftGoodsByBatch(List<ShiftGoodsDO> shiftGoodsDOList);
    
    int updateShiftGoodsByBatch(List<ShiftGoodsDO> shiftGoodsDOList);
    
    
}