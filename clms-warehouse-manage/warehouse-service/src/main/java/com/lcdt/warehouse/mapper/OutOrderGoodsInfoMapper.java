package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutOrderGoodsInfoMapper extends BaseMapper<OutOrderGoodsInfo> {
    OutOrderGoodsInfo selectOutboundQuantity(@Param("companyId") Long companyId,@Param("outPlanId") Long outPlanId,@Param("outplanRelationId") Long outplanRelationId);
}
