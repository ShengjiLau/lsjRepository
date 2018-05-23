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
import com.lcdt.warehouse.vo.ConstantVO;
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
                List<OutplanGoods> goodsList = outplanGoodsMapper.outWhPlanGoodsInfoList(new Page<OutplanGoods>(1,100),obj.getOutplanId()); //默认拉取对应100条
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
        outWarehousePlan.setPlanNo(outWarehousePlanMapper.getPlanCode());
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


    @Transactional
    @Override
    public boolean outWhPlanEdit(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel) {
        OutWarehousePlan outWarehousePlan = new OutWarehousePlan();
        BeanUtils.copyProperties(outWhPlanDto, outWarehousePlan);
        outWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.watting.getValue());
        outWarehousePlan.setUpdateDate(new Date());
        outWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        outWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        if (!StringUtils.isEmpty(outWhPlanDto.getStoragePlanTime())) { //竞价开始
            try {
                outWarehousePlan.setStoragePlanTime(DateUtility.string2Date(outWhPlanDto.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //详细信息
        OutWarehousePlan wrapperObj = new OutWarehousePlan();
        wrapperObj.setOutplanId(outWhPlanDto.getOutplanId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
        if (this.update(outWarehousePlan,new EntityWrapper<OutWarehousePlan>(wrapperObj))) {
            //先删除原来所有记录
            OutplanGoods outplanGoods = new OutplanGoods();
            outplanGoods.setOutplanId(outWarehousePlan.getOutplanId());
            outplanGoodsService.delete(new EntityWrapper<OutplanGoods>(outplanGoods));
            List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = outWhPlanDto.getOutWhPlanGoodsDtoList();
            if (null != outWhPlanGoodsDtoList) {
                List<OutplanGoods> outplanGoodsList = new ArrayList<OutplanGoods>();
                for (OutWhPlanGoodsDto dto : outWhPlanGoodsDtoList) {
                    OutplanGoods obj = new OutplanGoods();
                    BeanUtils.copyProperties(dto, obj);
                    obj.setOutplanId(outWarehousePlan.getOutplanId());
                    outplanGoodsList.add(obj);
                }
                return outplanGoodsService.insertOrUpdateBatch(outplanGoodsList);
            }
        }
        return false;
    }


    /***
     * 出库计划改变
     *
     * @param argObj --条件对象
     * @param outWarehousePlan --入库计划
     * @param userCompRel --登录用户参数
     * @return
     */
    private boolean chagenOutPlanStatus(OutWarehousePlan argObj, OutWarehousePlan outWarehousePlan, UserCompRel userCompRel) {
        outWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        outWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        outWarehousePlan.setUpdateDate(new Date());
        return this.update(outWarehousePlan,new EntityWrapper<OutWarehousePlan>(argObj));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean outWhPlanPublish(OutWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        OutWarehousePlan outWarehousePlan = this.selectOne(new EntityWrapper<OutWarehousePlan>(obj));
        if (outWarehousePlan == null) {
            throw new RuntimeException("发布计划不存在！");
        }
        outWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.publish.getValue());
        return chagenOutPlanStatus(obj, outWarehousePlan,userCompRel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean outWhPlanCancel(OutWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        OutWarehousePlan outWarehousePlan = this.selectOne(new EntityWrapper<OutWarehousePlan>(obj));
        if (outWarehousePlan == null) {
            throw new RuntimeException("计划不存在！");
        }
        /***
         *  逻辑：该计划是否存在对应的入库单，如果不存在，则直接可以取消；
         * 如果存在，需判断是否存在“待入库”“已完成”状态的入库单，如果存在，则不允许取消；
         */
        if (!outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())
                && !outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.watting.getValue()) ) {
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())) {
                OutWhPlanSearchParamsDto params = new OutWhPlanSearchParamsDto();
                params.setCompanyId(obj.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);

//              Page<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
//
//                if (inWarehouseOrderDtoList.getTotal()>0) {
//                    boolean flag = false;
//                    for (InWarehouseOrderDto obj1 : inWarehouseOrderDtoList.getRecords()) {
//                        if (obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE || obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE) {
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (flag) {
//                        throw new RuntimeException("该计划对应入库单存在“待入库”“已完成”状态记录！");
//                    }
              //  }
            }
        }
        outWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.cancel.getValue());
        return chagenOutPlanStatus(obj, outWarehousePlan,userCompRel);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean outWhPlanComplete(OutWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        OutWarehousePlan outWarehousePlan = this.selectOne(new EntityWrapper<OutWarehousePlan>(obj));
        if (outWarehousePlan == null) {
            throw new RuntimeException("计划不存在！");
        }
        /***
         *  逻辑：该计划是否存在对应的入库单，如果不存在，则直接可以取消；
         * 如果存在，需判断是否存在“待入库”“已完成”状态的入库单，如果存在，则不允许取消；
         */
        if (!outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())
                && !outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.watting.getValue()) ) {
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (outWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())) {
                OutWhPlanSearchParamsDto params = new OutWhPlanSearchParamsDto();
                params.setCompanyId(obj.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);

//                Page<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
//
//                if (inWarehouseOrderDtoList.getTotal()>0) {
//                    boolean flag = false;
//                    for (InWarehouseOrderDto obj1 : inWarehouseOrderDtoList.getRecords()) {
//                        if (obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE || obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE) {
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (flag) {
//                        throw new RuntimeException("该计划对应入库单存在“待入库”“已完成”状态记录！");
//                    }
                //  }
            }
        }
        outWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.completed.getValue());
        return chagenOutPlanStatus(obj, outWarehousePlan,userCompRel);
    }


}
