package com.lcdt.warehouse.service.impl;

import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.lcdt.warehouse.mapper.OutOrderGoodsInfoMapper;
import com.lcdt.warehouse.service.OutOrderGoodsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
@Service
public class OutOrderGoodsInfoServiceImpl extends ServiceImpl<OutOrderGoodsInfoMapper, OutOrderGoodsInfo> implements OutOrderGoodsInfoService {

    @Override
    public OutOrderGoodsInfo queryOutboundQuantity(Long companyId, Long outPlanId, Long outplanRelationId) {
        return baseMapper.selectOutboundQuantity(companyId,outPlanId,outplanRelationId);
    }
}
