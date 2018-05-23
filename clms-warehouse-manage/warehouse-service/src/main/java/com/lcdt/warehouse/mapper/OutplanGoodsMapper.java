package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.entity.OutplanGoods;
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
public interface OutplanGoodsMapper extends BaseMapper<OutplanGoods> {

    List<OutplanGoods> outWhPlanGoodsInfoList(Pagination page, @Param("planId") Long planId);
}
