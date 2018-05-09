package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InPlanGoodsInfoResultDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import com.lcdt.warehouse.mapper.InplanGoodsInfoMapper;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@Service
public class InWarehousePlanServiceImpl extends ServiceImpl<InWarehousePlanMapper, InWarehousePlan> implements InWarehousePlanService {

    @Autowired
    private InWarehousePlanMapper inWarehousePlanMapper;

    @Autowired
    private InplanGoodsInfoMapper inplanGoodsInfoMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<InWarehousePlan> inWarehousePlanList(InWhPlanSearchParamsDto dto, Page<InWarehousePlan> page) {
        List<InWarehousePlan> list = inWarehousePlanMapper.inWarehousePlanList(page, dto);
        if (null != list && list.size()>0) {
            for (InWarehousePlan obj : list) {
                List<InPlanGoodsInfoResultDto> goodsList = inplanGoodsInfoMapper.inWhPlanGoodsInfoList(new Page<InPlanGoodsInfoResultDto>(),obj.getPlanId());
                obj.setGoodsList(goodsList);
            }
        }
        return page.setRecords(list);
    }
}
