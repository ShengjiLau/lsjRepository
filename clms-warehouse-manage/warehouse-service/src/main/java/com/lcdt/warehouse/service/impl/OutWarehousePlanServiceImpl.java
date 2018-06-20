package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.entity.OutplanGoods;
import com.lcdt.warehouse.mapper.OutWarehousePlanMapper;
import com.lcdt.warehouse.mapper.OutplanGoodsMapper;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.lcdt.warehouse.service.OutWarehousePlanService;
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

    @Autowired
    private OutWarehouseOrderService outWarehouseOrderService;



    @Transactional(readOnly = true)
    @Override
    public Page<OutWarehousePlan> outWarehousePlanList(OutWhPlanSearchParamsDto dto, Page<OutWarehousePlan> page) {
        List<OutWarehousePlan> list = outWarehousePlanMapper.outWarehousePlanList(page, dto);
        if (null != list && list.size()>0) {
            for (OutWarehousePlan obj : list) {
                //计划划货物详细信息
                List<OutplanGoods> goodsList = outplanGoodsMapper.outWhPlanGoodsInfoList(new Page<OutplanGoods>(1,100),obj.getOutplanId()); //默认拉取对应100条
                obj.setGoodsList(goodsList);

                //出库单
                OutWhOrderSearchDto params = new OutWhOrderSearchDto();
                params.setCompanyId(dto.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<OutWhOrderDto> outWhOrderDtoList = outWarehouseOrderService.queryOutWarehouseOrderList(params);
                if (outWhOrderDtoList.getTotal()>0) {
                    obj.setOutWhOrderDtoList(outWhOrderDtoList.getRecords());
                } else {
                    obj.setOutWhOrderDtoList(new ArrayList<OutWhOrderDto>());
                }
            }
        }
        return page.setRecords(list);
    }



    @Transactional(readOnly = true)
    @Override
    public OutWhPlanDto outWhPlanDetail(Long outPlanId, boolean flag, UserCompRel userCompRel) { //注:flag-true为配仓用
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
            if (flag) {//出库单
                OutWhOrderSearchDto params = new OutWhOrderSearchDto();
                params.setCompanyId(obj.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<OutWhOrderDto> outWhOrderDtoList = outWarehouseOrderService.queryOutWarehouseOrderList(params);
                if (outWhOrderDtoList.getTotal()>0) {
                    result.setOutWhOrderDtoList(outWhOrderDtoList.getRecords());
                }else {
                    obj.setOutWhOrderDtoList(new ArrayList<OutWhOrderDto>());
                }
            }

            if(result.getOutWhOrderDtoList()!=null && result.getOutWhOrderDtoList().size()>0) {
                List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = new ArrayList<OutWhPlanGoodsDto>();
                for (OutplanGoods obj1 :list) {
                    OutWhPlanGoodsDto outWhPlanGoodsDto = new OutWhPlanGoodsDto();
                    BeanUtils.copyProperties(obj1, outWhPlanGoodsDto);
                    if (flag) { //统计该计划商品已配仓数量
                        statDistributeNum(outWhPlanGoodsDto, result.getOutWhOrderDtoList());
                        if (null!=outWhPlanGoodsDto.getRemainGoodsNum() && outWhPlanGoodsDto.getRemainGoodsNum()==0) {
                            continue;//说明该商品已配完，继续下个
                        }
                    }
                    outWhPlanGoodsDtoList.add(outWhPlanGoodsDto);
                }
                result.setOutWhPlanGoodsDtoList(outWhPlanGoodsDtoList);
            } else {
                List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = new ArrayList<OutWhPlanGoodsDto>();
                for (OutplanGoods obj1 :list) {
                    OutWhPlanGoodsDto outWhPlanGoodsDto = new OutWhPlanGoodsDto();
                    BeanUtils.copyProperties(obj1, outWhPlanGoodsDto);
                    outWhPlanGoodsDto.setRemainGoodsNum(obj1.getPlanGoodsNum());
                    outWhPlanGoodsDto.setOutOderGoodsNum(0f);
                    outWhPlanGoodsDtoList.add(outWhPlanGoodsDto);
                }
                result.setOutWhPlanGoodsDtoList(outWhPlanGoodsDtoList);
             }
        }
        return result;
    }


    /***
     * 统计配仓数量
     * @param outWhPlanGoodsDto
     * @param outWhOrderDtoList
     */
    private void statDistributeNum(OutWhPlanGoodsDto outWhPlanGoodsDto, List<OutWhOrderDto> outWhOrderDtoList) {
        if (null!=outWhOrderDtoList && outWhOrderDtoList.size()>0) {
            Float outGoodsNumber = 0f;
            for (OutWhOrderDto obj : outWhOrderDtoList) {
                List<OutOrderGoodsInfoDto> list = obj.getOutOrderGoodsInfoList();
                if (null!=list && list.size()>0) {
                    for (OutOrderGoodsInfoDto obj1 :list) { //配仓数量=对应入库单的应收数量
                        if (obj1.getOutplanRelationId().equals(outWhPlanGoodsDto.getRelationId())) {
                            outGoodsNumber += obj1.getGoodsNum(); //出库详细表中的出库数量就是配仓数量
                        }
                    }
                }
            }
            outWhPlanGoodsDto.setOutOderGoodsNum(outGoodsNumber);//已配仓数
            outWhPlanGoodsDto.setRemainGoodsNum(outWhPlanGoodsDto.getPlanGoodsNum() - outGoodsNumber);//计划-已配=剩余(代配)
        } else {
            outWhPlanGoodsDto.setOutOderGoodsNum(0f);//已配仓数
            outWhPlanGoodsDto.setRemainGoodsNum(outWhPlanGoodsDto.getPlanGoodsNum()==null?0:outWhPlanGoodsDto.getPlanGoodsNum() - 0);
        }
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
        if (!StringUtils.isEmpty(outWhPlanDto.getStoragePlanTime())) { //竞价开始
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
         *  逻辑：该计划是否存在对应的出库单，如果不存在，则直接可以取消；
         *  如果存在，需判断是否存在“待出库”“已完成”状态的出库单，如果存在，则不允许取消；
         */
        if (!outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.publish.getValue())
                && !outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.watting.getValue()) ) {
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.publish.getValue())) {
                OutWhOrderSearchDto params = new OutWhOrderSearchDto();
                params.setCompanyId(obj.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<OutWhOrderDto> outWhOrderDtoList = outWarehouseOrderService.queryOutWarehouseOrderList(params);
                if (outWhOrderDtoList.getTotal()>0) {
                    boolean flag = false;
                    for (OutWhOrderDto obj1 : outWhOrderDtoList.getRecords()) {
                        if (obj1.getOrderStatus()== ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND || obj1.getOrderStatus()== ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        throw new RuntimeException("该计划对应出库单存在“待出库“,“已出库“状态记录！");
                    }
                }
            }
        }
        outWarehousePlan.setPlanStatus((Integer) OutWhPlanStatusEnum.cancel.getValue());
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
        if (!outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.publish.getValue())
                && !outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.watting.getValue()) ) {
            throw new RuntimeException("该计划不允许取消（非待配状态或存在配仓记录）！");
        } else {
            if (outWarehousePlan.getPlanStatus().equals(OutWhPlanStatusEnum.publish.getValue())) {
                OutWhOrderSearchDto params = new OutWhOrderSearchDto();
                params.setCompanyId(obj.getCompanyId());
                params.setOutPlanId(obj.getOutplanId());
                params.setPageNo(1);
                params.setPageSize(100);
                Page<OutWhOrderDto> outWhOrderDtoList = outWarehouseOrderService.queryOutWarehouseOrderList(params);
                if (outWhOrderDtoList.getTotal()>0) {
                    boolean flag = false;
                    for (OutWhOrderDto obj1 : outWhOrderDtoList.getRecords()) {
                        if (obj1.getOrderStatus()== ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        throw new RuntimeException("该计划对应出库单存在“待出库”状态记录！");
                    }
                }
            }
        }
        outWarehousePlan.setPlanStatus((Integer) OutWhPlanStatusEnum.completed.getValue());
        return chagenOutPlanStatus(obj, outWarehousePlan,userCompRel);
    }




    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean distributeWh(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel) {
        outWhPlanDto.setCompanyId(userCompRel.getCompany().getCompId());
        OutWhPlanDto _outWhPlanDto = outWhPlanDetail(outWhPlanDto.getOutplanId(), true, userCompRel);
        if (_outWhPlanDto == null) {
            throw new RuntimeException("计划不存在！");
        }
        /***
         * 再次检查数据库中的计划数量是否满足配仓数量
         */
        List<OutWhPlanGoodsDto> _outWhPlanGoodsDtoList1 = outWhPlanDto.getOutWhPlanGoodsDtoList(); //前端提交来的
        List<OutWhPlanGoodsDto> _outWhPlanGoodsDtoList2 = _outWhPlanDto.getOutWhPlanGoodsDtoList(); //后端数据库中最新的
        if (null == _outWhPlanGoodsDtoList1 || null == _outWhPlanGoodsDtoList2) {
            throw new RuntimeException("配仓计划货物不存在！");
        }
        boolean flag = true;
        StringBuffer sb = new StringBuffer();
        for (OutWhPlanGoodsDto obj1: _outWhPlanGoodsDtoList1) {
            for (OutWhPlanGoodsDto obj2: _outWhPlanGoodsDtoList2) {
                if (obj1.getRelationId().equals(obj2.getRelationId())) { //同一种货物
                    if (obj1.getDistGoodsNum() == null) {
                        obj1.setDistGoodsNum(0f);
                    }
                    if (obj1.getDistGoodsNum()>obj2.getRemainGoodsNum()) { //如果前端提交过来的大于数据库中的剩余的
                        sb.append("货物："+ obj1.getGoodsName()+"，剩余数量："+obj2.getRemainGoodsNum()+",不满足当前配仓数量："+obj1.getDistGoodsNum());
                    } else {
                        //如果本次配仓量+已配仓量=计划的
                        if (obj1.getDistGoodsNum() + obj2.getOutOderGoodsNum()!=obj2.getPlanGoodsNum()) {
                            flag = false;
                        }
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(sb.toString())) {
            throw new RuntimeException(sb.toString());
        }
        /*************出库计划配仓BEGIN**************/
        OutWhOrderDto _tempObj = new OutWhOrderDto();
        _tempObj.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND);
        _tempObj.setOutPlanId(_outWhPlanDto.getOutplanId());
        _tempObj.setOutorderNo(_outWhPlanDto.getPlanNo());
        _tempObj.setGroupId(_outWhPlanDto.getGroupId());
        _tempObj.setGroupName(_outWhPlanDto.getGroupName());
        _tempObj.setContractNo(_outWhPlanDto.getContractNo());
        _tempObj.setPurchaseNo(_outWhPlanDto.getCustomerPurchaseNo());
        _tempObj.setCustomerId(_outWhPlanDto.getCustomerId());
        _tempObj.setCustomerName(_outWhPlanDto.getCustomerName());
        _tempObj.setCustomerContactName(_outWhPlanDto.getCustomerContactName());
        _tempObj.setCustomerContactPhone(_outWhPlanDto.getCustomerContactPhone());
        _tempObj.setWarehouseId(outWhPlanDto.getWareHouseId());
        _tempObj.setWarehouseName(outWhPlanDto.getWarehouseName());
        _tempObj.setOutboundType(_outWhPlanDto.getStorageType());
        _tempObj.setOutboundPlanTime(new Date());
        _tempObj.setOutboundRemark(outWhPlanDto.getStorageRemark());
        _tempObj.setPickupUnit(outWhPlanDto.getPickupUnit());
        _tempObj.setPickupLinkman(outWhPlanDto.getPickupLinkman());
        _tempObj.setPickupIdentiycard(outWhPlanDto.getPickupIdentiycard());
        _tempObj.setPickupPhone(outWhPlanDto.getPickupPhone());
        _tempObj.setPickupVehicleNum(outWhPlanDto.getPickupCar());
        _tempObj.setAttachments(outWhPlanDto.getAttachment());
        _tempObj.setCompanyId(outWhPlanDto.getCompanyId());
        _tempObj.setCreateDate(new Date());
        _tempObj.setCreateId(userCompRel.getUser().getUserId());
        _tempObj.setCreateName(userCompRel.getUser().getRealName());
        _tempObj.setIsDeleted(0);

        List<OutOrderGoodsInfoDto> outOrderGoodsInfoList = new ArrayList<OutOrderGoodsInfoDto>();
        for (OutWhPlanGoodsDto obj1 : _outWhPlanGoodsDtoList1) {
            OutOrderGoodsInfoDto tObj = new OutOrderGoodsInfoDto();
            if (obj1.getDistGoodsNum()==null) continue; //如果本次配仓数为0，跳过
            tObj.setOutplanRelationId(obj1.getRelationId());
            tObj.setGoodsId(obj1.getGoodsId());
            tObj.setGoodsName(obj1.getGoodsName());
            tObj.setGoodsCode(obj1.getGoodsCode());
            tObj.setGoodsBarCode(obj1.getGoodsBarcode());
            tObj.setGoodsSpec(obj1.getGoodsSpec());
            tObj.setGoodsClassifyId(obj1.getGoodsClassifyId());
            tObj.setGoodsClassifyName(obj1.getGoodsClassify());

            tObj.setMinUnit(obj1.getMinUnit());
            tObj.setUnit(obj1.getUnit());
            tObj.setUnitData(obj1.getUnitData());

            tObj.setBatch(obj1.getBatch());
            tObj.setStorageLocationId(obj1.getStorageLocationId());
            tObj.setStorageLocationCode(obj1.getStorageLocationCode());
            tObj.setGoodsNum(obj1.getDistGoodsNum());//配仓数
            tObj.setOutboundQuantity(obj1.getDistGoodsNum());
            tObj.setInStock(obj1.getInStock());//可用库存
            tObj.setRemark(obj1.getDisRemark());
            tObj.setInvertoryId(obj1.getInvertoryId());
            outOrderGoodsInfoList.add(tObj);
        }
        _tempObj.setOutOrderGoodsInfoList(outOrderGoodsInfoList);
        outWarehouseOrderService.addOutWarehouseOrder(_tempObj);
        /*************出库计划配仓END**************/
        //更新计划主表
        OutWarehousePlan _outWarehousePlan = new OutWarehousePlan();
        _outWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        _outWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        _outWarehousePlan.setUpdateDate(new Date());
        _outWarehousePlan.setPickupUnit(outWhPlanDto.getPickupUnit());
        _outWarehousePlan.setPickupLinkman(outWhPlanDto.getPickupLinkman());
        _outWarehousePlan.setPickupIdentiycard(outWhPlanDto.getPickupIdentiycard());
        _outWarehousePlan.setPickupPhone(outWhPlanDto.getPickupPhone());
        _outWarehousePlan.setPickupCar(outWhPlanDto.getPickupCar());

        if (flag) { //如果全部配完，更改计划状态-已配仓
            _outWarehousePlan.setPlanStatus((Integer) OutWhPlanStatusEnum.isWarehouse.getValue());
        }
        OutWarehousePlan wrapperObj = new OutWarehousePlan();
        wrapperObj.setOutplanId(outWhPlanDto.getOutplanId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
        return this.update(_outWarehousePlan,new EntityWrapper<OutWarehousePlan>(wrapperObj));
    }



    @Override
    public boolean changeOutWarehousePlanStatus(OutWhPlanDto outWhPlanDto, UserCompRel userCompRel) {
        OutWarehousePlan obj = new OutWarehousePlan();
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        obj.setOutplanId(outWhPlanDto.getOutplanId());
        OutWarehousePlan outWarehousePlan = this.selectOne(new EntityWrapper<OutWarehousePlan>(obj));
        if (outWarehousePlan == null) {
            return false;
        }
        if(outWarehousePlan.getPlanStatus().equals((Integer) OutWhPlanStatusEnum.isWarehouse.getValue())) {
            outWarehousePlan.setPlanStatus((Integer) OutWhPlanStatusEnum.publish.getValue());
            outWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
            outWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
            outWarehousePlan.setUpdateDate(new Date());
            return this.update(outWarehousePlan,new EntityWrapper<OutWarehousePlan>(obj));
        }
        return false;
    }


}
