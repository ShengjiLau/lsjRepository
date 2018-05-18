package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.InplanGoodsInfo;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import com.lcdt.warehouse.mapper.InplanGoodsInfoMapper;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InplanGoodsInfoService;
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
public class InWarehousePlanServiceImpl extends ServiceImpl<InWarehousePlanMapper, InWarehousePlan> implements InWarehousePlanService {

    @Autowired
    private InWarehousePlanMapper inWarehousePlanMapper;

    @Autowired
    private InplanGoodsInfoMapper inplanGoodsInfoMapper;

    @Autowired
    InWarehouseOrderService inWarehouseOrderService;

    @Autowired
    InplanGoodsInfoService inplanGoodsInfoService;

    @Transactional(readOnly = true)
    @Override
    public Page<InWarehousePlan> inWarehousePlanList(InWhPlanSearchParamsDto dto, Page<InWarehousePlan> page) {
        List<InWarehousePlan> list = inWarehousePlanMapper.inWarehousePlanList(page, dto);
        if (null != list && list.size()>0) {
            for (InWarehousePlan obj : list) {
                //计划划货物详细信息
                List<InPlanGoodsInfoResultDto> goodsList = inplanGoodsInfoMapper.inWhPlanGoodsInfoList(new Page<InPlanGoodsInfoResultDto>(1,100),obj.getPlanId()); //默认拉取对应100条
                obj.setGoodsList(goodsList);

                //入库单
                InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
                params.setCompanyId(dto.getCompanyId());
                params.setPlanId(obj.getPlanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
                if (inWarehouseOrderDtoList.getTotal()>0) {
                    obj.setInWarehouseOrderDtoList(inWarehouseOrderDtoList.getRecords());
                }
            }
        }
        return page.setRecords(list);
    }


    /***
     * 入库计划改变
     *
     * @param argObj --条件对象
     * @param inWarehousePlan --入库计划
     * @param userCompRel --登录用户参数
     * @return
     */
    private boolean chagenInPlanStatus(InWarehousePlan argObj, InWarehousePlan inWarehousePlan, UserCompRel userCompRel) {
        inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        inWarehousePlan.setUpdateDate(new Date());
        return this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(argObj));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean inWhPlanPublish(InWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        if (inWarehousePlan == null) {
            throw new RuntimeException("发布计划不存在！");
        }
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.publish.getValue());
        return chagenInPlanStatus(obj, inWarehousePlan,userCompRel);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean inWhPlanCancel(InWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        if (inWarehousePlan == null) {
            throw new RuntimeException("计划不存在！");
        }
        /***
         *  逻辑：该计划是否存在对应的入库单，如果不存在，则直接可以取消；
         * 如果存在，需判断是否存在“待入库”“已完成”状态的入库单，如果存在，则不允许取消；
         */
        if (!inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())
             && !inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.watting.getValue()) ) {
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (inWarehousePlan.getPlanStatus().equals(InWhPlanStatusEnum.publish.getValue())) {
                InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
                params.setCompanyId(obj.getCompanyId());
                params.setPlanId(obj.getPlanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
                if (inWarehouseOrderDtoList.getTotal()>0) {
                    boolean flag = false;
                    for (InWarehouseOrderDto obj1 : inWarehouseOrderDtoList.getRecords()) {
                        if (obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE || obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        throw new RuntimeException("该计划对应入库单存在“待入库”“已完成”状态记录！");
                    }
                }
            }
        }
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.cancel.getValue());
        return chagenInPlanStatus(obj, inWarehousePlan,userCompRel);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean inWhPlanComplete(InWarehousePlan obj, UserCompRel userCompRel) {
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        if (inWarehousePlan == null) {
            throw new RuntimeException("计划不存在！");
        }
        /**
         * 逻辑：操作“完成”时需要验证：该计划是否存在对应的入库单，如果不存在，则直接可以完成；
            如果存在，“待入库”状态下不允许取消，“已入库”、“已取消”状态下可以操作完成，也就是说对应的入库单如果有一条是“待入库”的状态，那么这条计划就不允许取消。
         */
        InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
        params.setCompanyId(obj.getCompanyId());
        params.setPlanId(obj.getPlanId());
        params.setPageNo(1);
        params.setPageSize(100);
        Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
        if (inWarehouseOrderDtoList.getTotal() > 0) {
            boolean flag = false;
            for (InWarehouseOrderDto obj1 : inWarehouseOrderDtoList.getRecords()) {
                if (obj1.getInOrderStatus()== ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                throw new RuntimeException("该计划对应入库单存在“待入库”状态记录！");
            }

        }
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.completed.getValue());
         return chagenInPlanStatus(obj, inWarehousePlan,userCompRel);
    }

    @Transactional
    @Override
    public boolean inWhPlanAdd(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel) {
        InWarehousePlan inWarehousePlan = new InWarehousePlan();
        BeanUtils.copyProperties(inWhPlanAddParamsDto, inWarehousePlan);
        inWarehousePlan.setCompanyId(userCompRel.getCompId());
        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.watting.getValue());
        inWarehousePlan.setCreateTime(new Date());
        inWarehousePlan.setCreateUserId(userCompRel.getUser().getUserId());
        inWarehousePlan.setCreateUserName(userCompRel.getUser().getRealName());
        if (!StringUtils.isEmpty(inWhPlanAddParamsDto.getStoragePlanTime())) { //竞价开始
            try {
                inWarehousePlan.setStoragePlanTime(DateUtility.string2Date(inWhPlanAddParamsDto.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        inWarehousePlan.setPlanNo(inWarehousePlanMapper.getPlanCode());
        if (this.insert(inWarehousePlan)) {
            List<InWhPlanGoodsDto> inWhPlanAddGoodsParamsDtoList = inWhPlanAddParamsDto.getInWhPlanGoodsDtoList();
            if (null != inWhPlanAddGoodsParamsDtoList) {
                List<InplanGoodsInfo> inplanGoodsInfos = new ArrayList<InplanGoodsInfo>();
                for (InWhPlanGoodsDto dto : inWhPlanAddGoodsParamsDtoList) {
                    InplanGoodsInfo obj = new InplanGoodsInfo();
                    BeanUtils.copyProperties(dto, obj);
                    obj.setPlanId(inWarehousePlan.getPlanId());
                    inplanGoodsInfos.add(obj);
                }
                return inplanGoodsInfoService.insertBatch(inplanGoodsInfos);
            }
        }
        return false;
    }



    @Transactional(readOnly = true)
    @Override
    public InWhPlanDto inWhPlanDetail(Long planId,boolean flag, UserCompRel userCompRel) {
        InWhPlanDto result = new InWhPlanDto();

        InWarehousePlan obj = new InWarehousePlan();
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        obj.setPlanId(planId);
        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        if (null!=inWarehousePlan)  {
            BeanUtils.copyProperties(inWarehousePlan, result);
            result.setStoragePlanTime(DateUtility.date2String(inWarehousePlan.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
        }

        //详细信息
        InplanGoodsInfo inplanGoodsInfo = new InplanGoodsInfo();
        inplanGoodsInfo.setPlanId(planId);
        List<InplanGoodsInfo> list = inplanGoodsInfoService.selectList(new EntityWrapper<InplanGoodsInfo>(inplanGoodsInfo));

        if (list != null) {
            if (flag) {//入库单
                InWarehouseOrderSearchParamsDto params = new InWarehouseOrderSearchParamsDto();
                params.setCompanyId(obj.getCompanyId());
                params.setPlanId(obj.getPlanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderList(params);
                if (inWarehouseOrderDtoList.getTotal()>0) {
                    result.setInWarehouseOrderDtoList(inWarehouseOrderDtoList.getRecords());
                }
            }
            List<InWhPlanGoodsDto> inWhPlanGoodsDtoList = new ArrayList<InWhPlanGoodsDto>();
            for (InplanGoodsInfo obj1 :list) {
                InWhPlanGoodsDto inWhPlanGoodsDto = new InWhPlanGoodsDto();
                BeanUtils.copyProperties(obj1, inWhPlanGoodsDto);
                if (flag) { //统计该计划商品已配仓数量
                    statDistributeNum(inWhPlanGoodsDto, result.getInWarehouseOrderDtoList());
                    if (null!=inWhPlanGoodsDto.getRemainGoodsNum() && inWhPlanGoodsDto.getRemainGoodsNum()==0) {
                        continue;//说明该商品已配完，继续下个
                    }
                }

                inWhPlanGoodsDtoList.add(inWhPlanGoodsDto);
            }
            result.setInWhPlanGoodsDtoList(inWhPlanGoodsDtoList);
        }

        return result;
    }


    /***
     * 统计配仓数量
     * @param inWhPlanGoodsDto
     * @param inWarehouseOrderDtoList
     */
    private void statDistributeNum(InWhPlanGoodsDto inWhPlanGoodsDto, List<InWarehouseOrderDto> inWarehouseOrderDtoList) {
        if (null!=inWarehouseOrderDtoList && inWarehouseOrderDtoList.size()>0) {
            Float receivalbeAmount = 0f;
            for (InWarehouseOrderDto obj : inWarehouseOrderDtoList) {
                List<InorderGoodsInfoDto> list = obj.getGoodsInfoDtoList();
                if (null!=list && list.size()>0) {
                    for (InorderGoodsInfoDto obj1 :list) { //配仓数量=对应入库单的应收数量
                         if (obj1.getInplanGoodsId().equals(inWhPlanGoodsDto.getRelationId())) {
                             receivalbeAmount += obj1.getReceivalbeAmount();
                         }
                    }
                }
            }
            inWhPlanGoodsDto.setInOderGoodsNum(receivalbeAmount);//已配仓数
            inWhPlanGoodsDto.setRemainGoodsNum(inWhPlanGoodsDto.getPlanGoodsNum()-receivalbeAmount);//计划-已配=剩余
        } else {

            inWhPlanGoodsDto.setInOderGoodsNum(0f);//已配仓数
            inWhPlanGoodsDto.setRemainGoodsNum(inWhPlanGoodsDto.getPlanGoodsNum()-0);//计划-已配=剩余
        }
    }

    @Transactional
    @Override
    public boolean inWhPlanEdit(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel) {
        InWarehousePlan inWarehousePlan = new InWarehousePlan();
        BeanUtils.copyProperties(inWhPlanAddParamsDto, inWarehousePlan);

        inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.watting.getValue());
        inWarehousePlan.setUpdateDate(new Date());
        inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        if (!StringUtils.isEmpty(inWhPlanAddParamsDto.getStoragePlanTime())) { //竞价开始
            try {
                inWarehousePlan.setStoragePlanTime(DateUtility.string2Date(inWhPlanAddParamsDto.getStoragePlanTime(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //详细信息
        InWarehousePlan wrapperObj = new InWarehousePlan();
        wrapperObj.setPlanId(inWhPlanAddParamsDto.getCompanyId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
        if (this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(wrapperObj))) {

//            //先删除原来所有记录
            InplanGoodsInfo inplanGoodsInfo = new InplanGoodsInfo();
            inplanGoodsInfo.setPlanId(inWarehousePlan.getPlanId());
            inplanGoodsInfoService.delete(new EntityWrapper<InplanGoodsInfo>(inplanGoodsInfo));

            List<InWhPlanGoodsDto> inWhPlanAddGoodsParamsDtoList = inWhPlanAddParamsDto.getInWhPlanGoodsDtoList();
            if (null != inWhPlanAddGoodsParamsDtoList) {
                List<InplanGoodsInfo> inplanGoodsInfos = new ArrayList<InplanGoodsInfo>();
                for (InWhPlanGoodsDto dto : inWhPlanAddGoodsParamsDtoList) {
                    InplanGoodsInfo obj = new InplanGoodsInfo();
                    BeanUtils.copyProperties(dto, obj);
                    obj.setPlanId(inWarehousePlan.getPlanId());
                    inplanGoodsInfos.add(obj);
                }
                return inplanGoodsInfoService.insertOrUpdateBatch(inplanGoodsInfos);
            }
        }
        return false;
    }


    @Transactional
    @Override
    public InWhPlanDto distributeWh(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel) {
        InWhPlanDto _inWhPlanDto = inWhPlanDetail(inWhPlanAddParamsDto.getPlanId(),true,userCompRel);
        if (null == _inWhPlanDto) {
            throw new RuntimeException("计划不存在！");
        }

        /***
         * 再次检查数据库中的计划数量是否满足配仓数量
         */
        List<InWhPlanGoodsDto> _inWhPlanGoodsDtoList1 = _inWhPlanDto.getInWhPlanGoodsDtoList();
        List<InWhPlanGoodsDto> _inWhPlanGoodsDtoList2 = _inWhPlanDto.getInWhPlanGoodsDtoList();

//        if (null == _inWhPlanGoodsDtoList　) {
//            throw new RuntimeException("配仓计划货物不存在！");
//         }
//         for(InWhPlanGoodsDto obj1：)
//




        //更新计划主表
        InWarehousePlan _inWarehousePlan = new InWarehousePlan();
        _inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        _inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        _inWarehousePlan.setUpdateDate(new Date());
        _inWarehousePlan.setDeliverymanName(inWhPlanAddParamsDto.getDeliverymanName());
        _inWarehousePlan.setDeliverymanLinkman(inWhPlanAddParamsDto.getDeliverymanLinkman());
        _inWarehousePlan.setDeliverymanPhone(inWhPlanAddParamsDto.getDeliverymanPhone());
        _inWarehousePlan.setDeliverymanCar(inWhPlanAddParamsDto.getDeliverymanCar());

        InWarehousePlan wrapperObj = new InWarehousePlan();
        wrapperObj.setPlanId(inWhPlanAddParamsDto.getCompanyId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
         this.update(_inWarehousePlan,new EntityWrapper<InWarehousePlan>(wrapperObj));


        return null;
    }



}
