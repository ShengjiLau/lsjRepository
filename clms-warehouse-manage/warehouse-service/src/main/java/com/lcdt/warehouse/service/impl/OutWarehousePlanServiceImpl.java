package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.InplanGoodsInfo;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.entity.OutplanGoods;
import com.lcdt.warehouse.mapper.OutWarehousePlanMapper;
import com.lcdt.warehouse.mapper.OutplanGoodsMapper;
import com.lcdt.warehouse.service.OutWarehousePlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.OutplanGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tl.commons.util.DateUtility;

import java.text.ParseException;
import java.util.ArrayList;
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
public class OutWarehousePlanServiceImpl extends ServiceImpl<OutWarehousePlanMapper, OutWarehousePlan> implements OutWarehousePlanService {

    @Autowired
    private OutWarehousePlanMapper outWarehousePlanMapper;

    @Autowired
    private OutplanGoodsMapper outplanGoodsMapper;

    @Autowired
    private OutplanGoodsService outplanGoodsService;


    @Transactional(readOnly = true)
    @Override
    public Page<OutWarehousePlan> outWarehousePlanList(OutWhPlanSearchParamsDto dto, Page<OutWarehousePlan> page) {
        List<OutWarehousePlan> list = outWarehousePlanMapper.outWarehousePlanList(page, dto);
        if (null != list && list.size()>0) {
            for (OutWarehousePlan obj : list) {
                //计划划货物详细信息
                List<OutPlanGoodsInfoResultDto> goodsList = outplanGoodsMapper.outWhPlanGoodsInfoList(new Page<OutPlanGoodsInfoResultDto>(1,100),obj.getOutplanId()); //默认拉取对应100条
                obj.setGoodsList(goodsList);

//                //出库单
//                InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
//                params.setCompanyId(dto.getCompanyId());
//                params.setPlanId(obj.getPlanId());
//                params.setPageNo(1);
//                params.setPageSize(100);
//                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
//                if (inWarehouseOrderDtoList.getTotal()>0) {
//                    obj.setInWarehouseOrderDtoList(inWarehouseOrderDtoList.getRecords());
//                }
            }
        }
        return page.setRecords(list);
    }

    @Transactional(readOnly = true)
    @Override
    public OutWhPlanDto outWhPlanDetail(Long outPlanId, boolean flag, UserCompRel userCompRel) {

        OutWhPlanDto result = new OutWhPlanDto();

        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        obj.setOutplanId(outPlanId);
        OutWarehousePlan outWarehousePlan = this.selectOne(new EntityWrapper<OutWarehousePlan>(obj));
        if (null!=outWarehousePlan)  {
            BeanUtils.copyProperties(outWarehousePlan, result);
            result.setStoragePlanTime(DateUtility.date2String(outWarehousePlan.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        //详细信息
        OutplanGoods outplanGoods = new OutplanGoods();
        outplanGoods.setOutplanId(outPlanId);
        List<OutplanGoods> list = outplanGoodsService.selectList(new EntityWrapper<OutplanGoods>(outplanGoods));
        if (list != null) {
            if (flag) {//入库单
//                InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
//                params.setCompanyId(obj.getCompanyId());
//                params.setPlanId(obj.getPlanId());
//                params.setPageNo(1);
//                params.setPageSize(100);
//                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
//                if (inWarehouseOrderDtoList.getTotal()>0) {
//                    result.setInWarehouseOrderDtoList(inWarehouseOrderDtoList.getRecords());
//                }
            }
            List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = new ArrayList<OutWhPlanGoodsDto>();
            for (OutplanGoods obj1 :list) {
                OutWhPlanGoodsDto outWhPlanGoodsDto = new OutWhPlanGoodsDto();
                BeanUtils.copyProperties(obj1, outWhPlanGoodsDto);
                if (flag) { //统计该计划商品已配仓数量
//                    statDistributeNum(inWhPlanGoodsDto, result.getInWarehouseOrderDtoList());
//                    if (null!=inWhPlanGoodsDto.getRemainGoodsNum() && inWhPlanGoodsDto.getRemainGoodsNum()==0) {
//                        continue;//说明该商品已配完，继续下个
//                    }
                }
                outWhPlanGoodsDtoList.add(outWhPlanGoodsDto);
            }

            result.setOutWhPlanGoodsDtoList(outWhPlanGoodsDtoList);
        }

        return result;
    }



    @Transactional
    @Override
    public boolean outWhPlanAdd(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel) {
        OutWarehousePlan outWarehousePlan = new OutWarehousePlan();

        BeanUtils.copyProperties(outWhPlanDto, outWarehousePlan);
        outWarehousePlan.setCompanyId(userCompRel.getCompId());
        outWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.watting.getValue());
        outWarehousePlan.setCreateTime(new Date());
        outWarehousePlan.setCreateUserId(userCompRel.getUser().getUserId());
        outWarehousePlan.setCreateUserName(userCompRel.getUser().getRealName());
        if (!StringUtils.isEmpty(outWarehousePlan.getStoragePlanTime())) { //竞价开始
            try {
                outWarehousePlan.setStoragePlanTime(DateUtility.string2Date(outWhPlanDto.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //----outWarehousePlan.setPlanNo(inWarehousePlanMapper.getPlanCode());

        if (this.insert(outWarehousePlan)) {
            List<OutWhPlanGoodsDto> outWhPlanGoodsDtos = outWhPlanDto.getOutWhPlanGoodsDtoList();
            if (null != outWhPlanGoodsDtos) {
                List<OutplanGoods> outplanGoods = new ArrayList<OutplanGoods>();
                for (OutWhPlanGoodsDto dto : outWhPlanGoodsDtos) {
                    OutplanGoods obj = new OutplanGoods();
                    BeanUtils.copyProperties(dto, obj);
                    obj.setOutplanId(outWarehousePlan.getOutplanId());
                    outplanGoods.add(obj);
                }
                return outplanGoodsService.insertBatch(outplanGoods);
            }
        }
        return false;
    }

}
