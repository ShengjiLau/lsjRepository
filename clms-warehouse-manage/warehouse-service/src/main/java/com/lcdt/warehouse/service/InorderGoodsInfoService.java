package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.entity.InorderGoodsInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public interface InorderGoodsInfoService extends IService<InorderGoodsInfo> {
    /**
     * 删除商品信息
     * @param companyId
     * @param relationId
     * @return
     */
    int deleteGoodsInfo(Long companyId,Long relationId);
}
