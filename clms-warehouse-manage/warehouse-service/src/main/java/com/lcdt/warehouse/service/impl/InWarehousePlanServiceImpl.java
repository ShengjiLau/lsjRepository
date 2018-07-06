package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
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
        if (!StringUtils.isEmpty(dto.getCreateBeginStr())) {
           dto.setCreateBeginStr(DateUtility.date2String(DateUtility.string2Date_safe(dto.getCreateBeginStr(),null))+" 00:00:00");
         }
        if (!StringUtils.isEmpty(dto.getCreateEndStr())) {
            dto.setCreateEndStr(DateUtility.date2String(DateUtility.string2Date_safe(dto.getCreateEndStr(),null))+" 23:59:59");
        }
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
                String[] pArray = {ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND+"",ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND+""};
                params.setInOrderStatus(pArray);
                params.setPageNo(1);
                params.setPageSize(100);
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderListOfPlan(params);
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
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderListOfPlan(params);
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
        Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderListOfPlan(params);
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
    public InWarehousePlan inWhPlanAdd(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel) {
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
                inplanGoodsInfoService.insertBatch(inplanGoodsInfos);
            }
        }
        return inWarehousePlan;
    }



    @Transactional(readOnly = true)
    @Override
    public InWhPlanDto inWhPlanDetail(Long planId,boolean flag, UserCompRel userCompRel,boolean bFlag,boolean bFlag1) {
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
                if(bFlag) {
                    String[] pArray = {ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE+"",ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE+""};
                    params.setInOrderStatus(pArray);
                }
                Page<InWarehouseOrderDto> inWarehouseOrderDtoList = inWarehouseOrderService.queryInWarehouseOrderListOfPlan(params);
                if (inWarehouseOrderDtoList.getTotal()>0) {
                    result.setInWarehouseOrderDtoList(inWarehouseOrderDtoList.getRecords());
                }
            }

            //详细与与库的单结合
            List<InWhPlanGoodsDto> inWhPlanGoodsDtoList = new ArrayList<InWhPlanGoodsDto>();
            for (InplanGoodsInfo obj1 :list) {
                InWhPlanGoodsDto inWhPlanGoodsDto = new InWhPlanGoodsDto();
                BeanUtils.copyProperties(obj1, inWhPlanGoodsDto);
                if (flag) { //统计该计划商品已配仓数量
                    statDistributeNum(inWhPlanGoodsDto, result.getInWarehouseOrderDtoList(),bFlag);
                    if (null!=inWhPlanGoodsDto.getRemainGoodsNum() && inWhPlanGoodsDto.getRemainGoodsNum()==0 && bFlag1) {
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
    private void statDistributeNum(InWhPlanGoodsDto inWhPlanGoodsDto, List<InWarehouseOrderDto> inWarehouseOrderDtoList,boolean bFlag) {
        if (null!=inWarehouseOrderDtoList && inWarehouseOrderDtoList.size()>0) {

            Float receivalbeAmount = 0f, inHouseAmount=0f;

            for (InWarehouseOrderDto obj : inWarehouseOrderDtoList) {


                List<InorderGoodsInfoDto> list = obj.getGoodsInfoDtoList();
                if (null!=list && list.size()>0) {
                    for (InorderGoodsInfoDto obj1 :list) { //配仓数量=对应入库单的应收数量
                         if (obj1.getInplanGoodsId().equals(inWhPlanGoodsDto.getRelationId())) {
                             inHouseAmount += obj1.getInHouseAmount()==null?0:obj1.getInHouseAmount(); //入库数量
                             receivalbeAmount += obj1.getReceivalbeAmount()==null?0:obj1.getReceivalbeAmount();//已配
                         }
                    }
                }
                if (!bFlag && obj.getInOrderStatus().equals(ConstantVO.IN_ORDER_STATUS_HAVE_CANCEL)) { //已配数为负数
                    receivalbeAmount = 0 - receivalbeAmount;
                }
            }
            inWhPlanGoodsDto.setInHouseAmount(inHouseAmount);//入库数
            inWhPlanGoodsDto.setRemainGoodsNum(inWhPlanGoodsDto.getPlanGoodsNum() - receivalbeAmount);//计划-已配=待配
            inWhPlanGoodsDto.setDisCompleteAmount(receivalbeAmount); //已配置

        } else {
            inWhPlanGoodsDto.setInHouseAmount(0f);//入库数
            inWhPlanGoodsDto.setRemainGoodsNum(inWhPlanGoodsDto.getPlanGoodsNum()-0);//计划-已配=待配
            inWhPlanGoodsDto.setDisCompleteAmount(0f); //已配置
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
        wrapperObj.setPlanId(inWhPlanAddParamsDto.getPlanId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
        if (this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(wrapperObj))) {
            //先删除原来所有记录
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
    public boolean distributeWh(InWhPlanDto inWhPlanAddParamsDto, UserCompRel userCompRel) {
        inWhPlanAddParamsDto.setCompanyId(userCompRel.getCompany().getCompId());
        InWhPlanDto _inWhPlanDto = inWhPlanDetail(inWhPlanAddParamsDto.getPlanId(),true,userCompRel,true,false);
        if (null == _inWhPlanDto) {
            throw new RuntimeException("计划不存在！");
        }
        /***
         * 再次检查数据库中的计划数量是否满足配仓数量
         */
        List<InWhPlanGoodsDto> _inWhPlanGoodsDtoList1 = inWhPlanAddParamsDto.getInWhPlanGoodsDtoList(); //前端提交来的
        List<InWhPlanGoodsDto> _inWhPlanGoodsDtoList2 = _inWhPlanDto.getInWhPlanGoodsDtoList(); //后端数据库中最新的
        Integer num = 0;
        for (InWhPlanGoodsDto obj1: _inWhPlanGoodsDtoList1) {
           if (obj1.getDistGoodsNum() == null) {
               num++;
            }
         }
        if (_inWhPlanGoodsDtoList1.size()==num) {
              throw new RuntimeException("配仓数量不能为0！");
        }
        if (null == _inWhPlanGoodsDtoList1 || null == _inWhPlanGoodsDtoList2) {
            throw new RuntimeException("配仓计划货物不存在！");
        }
        boolean flag = true;
        StringBuffer sb = new StringBuffer();
        for (InWhPlanGoodsDto obj1: _inWhPlanGoodsDtoList1) {

              for (InWhPlanGoodsDto obj2: _inWhPlanGoodsDtoList2) {
                    if (obj1.getRelationId().equals(obj2.getRelationId())) { //同一种货物

                        if (obj1.getDistGoodsNum() == null) {
                            obj1.setDistGoodsNum(0f);
                        }
                        if (obj1.getDistGoodsNum()>obj2.getRemainGoodsNum()) { //如果前端提交过来的大于数据库中的剩余的
                          sb.append("货物："+ obj1.getGoodsName()+"，剩余数量："+obj2.getRemainGoodsNum()+",不满足当前配仓数量："+obj1.getDistGoodsNum());
                        } else {
                            //如果本次配仓量+已配仓量=计划的
                            if (obj1.getDistGoodsNum() + obj2.getDisCompleteAmount() != obj2.getPlanGoodsNum()) {
                                flag = false;
                            }
                        }


                    }
              }

         }
         if (!StringUtils.isEmpty(sb.toString())) {
             throw new RuntimeException(sb.toString());
         }

         /*************生成派单BEGIN**************/
        InWarehouseOrderDto params = new InWarehouseOrderDto();
        params.setPlanId(_inWhPlanDto.getPlanId());
        params.setInOrderCode(_inWhPlanDto.getPlanNo());
        params.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_WATIE_STORAGE); //待入库
        params.setGroupId(_inWhPlanDto.getGroupId());
        params.setGroupName(_inWhPlanDto.getGroupName());
        params.setCustomerName(_inWhPlanDto.getCustomerName());
        params.setCustomerId(_inWhPlanDto.getCustomerId());
        params.setCustomerContactName(_inWhPlanDto.getCustomerContactName());
        params.setCustomerContactPhone(_inWhPlanDto.getCustomerContactPhone());
        params.setWarehouseId(inWhPlanAddParamsDto.getWareHouseId());
        params.setWarehouseName(inWhPlanAddParamsDto.getWarehouseName());
        params.setStorageType(_inWhPlanDto.getStorageType());
        params.setStoragePlanTime(new Date());
        params.setStorageRemark(inWhPlanAddParamsDto.getStorageRemark());
        params.setDeliverymanCar(inWhPlanAddParamsDto.getDeliverymanCar());
        params.setDeliverymanLinkman(inWhPlanAddParamsDto.getDeliverymanLinkman());
        params.setDeliverymanName(inWhPlanAddParamsDto.getDeliverymanName());
        params.setDeliverymanPhone(inWhPlanAddParamsDto.getDeliverymanPhone());
        params.setAttachments(_inWhPlanDto.getAttachment());
        params.setCompanyId(_inWhPlanDto.getCompanyId());
        params.setPurchaseCode(_inWhPlanDto.getCustomerPurchaseNo());//采购单号
        params.setContractCode(_inWhPlanDto.getContractNo());//合同编号
        params.setCreateDate(new Date());
        params.setCreateId(userCompRel.getUser().getUserId());
        params.setCreateName(userCompRel.getUser().getRealName());
        List<InorderGoodsInfoDto> inorderGoodsInfoList = new ArrayList<>();
        for (InWhPlanGoodsDto obj1: _inWhPlanGoodsDtoList1) {
           if(obj1.getDistGoodsNum()==null || obj1.getDistGoodsNum()<=0) continue;//如果配仓数为0，跳过
            InorderGoodsInfoDto tObj = new InorderGoodsInfoDto();
            tObj.setInplanGoodsId(obj1.getRelationId());
            tObj.setGoodsId(obj1.getGoodsId());
            tObj.setGoodsName(obj1.getGoodsName());
            tObj.setGoodsClassify(obj1.getGoodsClassify());
            tObj.setGoodsSpec(obj1.getGoodsSpec());
            tObj.setGoodsCode(obj1.getGoodsCode());
            tObj.setGoodsBarcode(obj1.getGoodsBarcode());
            tObj.setMinUnit(obj1.getMinUnit());
            tObj.setUnit(obj1.getUnit());
            tObj.setUnitData(obj1.getUnitData());
            if (StringUtils.isEmpty(obj1.getDisRemark())) {
               tObj.setRemark(obj1.getRemark());
            } else {
                tObj.setRemark(obj1.getDisRemark());
            }
            tObj.setReceivalbeAmount(obj1.getDistGoodsNum()); //配仓数量
            tObj.setInHousePrice(obj1.getInHousePrice());

            inorderGoodsInfoList.add(tObj);
        }
        params.setGoodsInfoDtoList(inorderGoodsInfoList);
        inWarehouseOrderService.addInWarehouseOrder(params);
        /*************生成派单END**************/

        //更新计划主表
        InWarehousePlan _inWarehousePlan = new InWarehousePlan();
        _inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
        _inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
        _inWarehousePlan.setUpdateDate(new Date());
        _inWarehousePlan.setDeliverymanName(inWhPlanAddParamsDto.getDeliverymanName());
        _inWarehousePlan.setDeliverymanLinkman(inWhPlanAddParamsDto.getDeliverymanLinkman());
        _inWarehousePlan.setDeliverymanPhone(inWhPlanAddParamsDto.getDeliverymanPhone());
        _inWarehousePlan.setDeliverymanCar(inWhPlanAddParamsDto.getDeliverymanCar());

        if (flag) { //如果全部配完，更改计划状态-已配仓
            _inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.isWarehouse.getValue());
        }
        InWarehousePlan wrapperObj = new InWarehousePlan();
        wrapperObj.setPlanId(inWhPlanAddParamsDto.getPlanId());
        wrapperObj.setCompanyId(userCompRel.getCompId());
        this.update(_inWarehousePlan,new EntityWrapper<InWarehousePlan>(wrapperObj));
        return true;
    }




    @Transactional
    @Override
    public boolean changeInWarehousePlanStatus(InWhPlanDto inWhPlanDto, UserCompRel userCompRel) {
        InWarehousePlan obj = new InWarehousePlan();
        obj.setCompanyId(userCompRel.getCompany().getCompId());
        obj.setPlanId(inWhPlanDto.getPlanId());

        InWarehousePlan inWarehousePlan = this.selectOne(new EntityWrapper<InWarehousePlan>(obj));
        if (inWarehousePlan == null) {
            return false;
        }
        if (inWarehousePlan.getPlanStatus().equals((Integer) InWhPlanStatusEnum.isWarehouse.getValue())) {
            inWarehousePlan.setPlanStatus((Integer) InWhPlanStatusEnum.publish.getValue());
            inWarehousePlan.setUpdateId(userCompRel.getUser().getUserId());
            inWarehousePlan.setUpdateName(userCompRel.getUser().getRealName());
            inWarehousePlan.setUpdateDate(new Date());
            return this.update(inWarehousePlan,new EntityWrapper<InWarehousePlan>(obj));
        }
        return false;
    }


}
