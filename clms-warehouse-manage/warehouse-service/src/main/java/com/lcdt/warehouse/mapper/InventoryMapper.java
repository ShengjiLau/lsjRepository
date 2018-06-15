package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.dto.ShiftGoodsListDTO;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.utils.ShiftGoodsBO;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InventoryMapper extends BaseMapper<Inventory> {

    List<Inventory> selectInventoryList(Pagination page, Inventory queryDto);

    List<Inventory> selectInventoryListByqueryDto(@Param("goodsIds") List<Long> goodsIds, Pagination page,@Param("querydto") InventoryQueryDto queryDto);
    
    int updateInventoryLockNum(Long inventoryId,Float inventoryNum,Float lockNum);
    
    List<Inventory> getInventoryListByIds(Long[] inventoryIds);
    
    List<ShiftGoodsListDTO> getInventoryAndGoodsInfo(Long[] inventoryIds);

    List<Inventory> selectInventoryListByqueryDto(@Param("goodsIds") List<Long> goodsIds,@Param("querydto") InventoryQueryDto queryDto);
    
    ShiftGoodsListDTO selectInventoryListByShiftGoodsBO(ShiftGoodsBO shiftGoodsBO);
}
