package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.InPlanGoodsInfoResultDto;
import com.lcdt.warehouse.dto.InWhPlanSearchParamsDto;
import com.lcdt.warehouse.dto.InWhPlanStatusEnum;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import com.lcdt.warehouse.mapper.InplanGoodsInfoMapper;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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



    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean inWhPlanPublish(InWarehousePlan obj, UserCompRel userCompRel) {
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        if (inWarehousePlan == null) {
            throw new RuntimeException("发布计划不存在！");
        }
        inWarehousePlan.setCompanyId(userCompRel.getCompany().getCompId());
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.publish.getValue());
        inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        inWarehousePlan.setUpdateDate(new Date());
        return this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(obj));


    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean inWhPlanCancel(InWarehousePlan obj, UserCompRel userCompRel) {
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        if (inWarehousePlan == null) {
            throw new RuntimeException("计划不存在！");
        }
        //检查是否在存未取消的入库单
        if (!inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())
             && !inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.watting.getValue()) ) { //配仓状态下是否存在入库单
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())) { //检查是否存在未取消的入库单


            }
        }
        inWarehousePlan.setCompanyId(userCompRel.getCompany().getCompId());
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.cancel.getValue());
        inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        inWarehousePlan.setUpdateDate(new Date());
        return this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(obj));
    }
}
