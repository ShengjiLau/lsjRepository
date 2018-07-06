package com.lcdt.warehouse.service;

import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutOrderGoodsInfoService extends IService<OutOrderGoodsInfo> {

    /**
     * 查询此计划下的已出库的货物量
     *
     * @param companyId
     * @param outPlanId
     * @param outplanRelationId
     * @return
     */
    OutOrderGoodsInfo queryOutboundQuantity(Long companyId, Long outPlanId, Long outplanRelationId);

}
