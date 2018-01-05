package com.lcdt.traffic.service.impl;

import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.Plan4EditService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
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
 * Created by yangbinq on 2017/12/13.
 */
@Service
public class Plan4EditServiceImpl implements Plan4EditService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单详细


    @com.alibaba.dubbo.config.annotation.Reference
    public CustomerRpcService customerRpcService;  //客户信息

    @Autowired
    private WaybillService waybillService; //运单




    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan waybillPlanEdit(WaybillParamsDto dto, short flag) throws WaybillPlanException {
        WaybillPlan vo = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(),dto.getCompanyId(),(short)0);
        if (vo==null) {
            throw new WaybillPlanException("计划不存在！");
        }
        BeanUtils.copyProperties(dto, vo);
        try {
            if (!StringUtils.isEmpty(dto.getBidingStart())) { //竞价开始
                vo.setBidingStart(DateUtility.string2Date(dto.getBidingStart(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getBidingEnd())) { //竞价结束
                vo.setBidingEnd(DateUtility.string2Date(dto.getBidingEnd(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getStartDate())) { //起运时间
                vo.setStartDate(DateUtility.string2Date(dto.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getArriveDate())) { //送达时间
                vo.setArriveDate(DateUtility.string2Date(dto.getArriveDate(),"yyyy-MM-dd HH:mm:ss"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)
            String carrierId = dto.getCarrierCollectionIds(); //承运商ID（如果是承运商只存在一个）
            Customer customer = customerRpcService.findCustomerById(Long.valueOf(carrierId));
            vo.setCarrierCompanyId(customer.getCompanyId());
        }

        //具体业务处理
        if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI)) { //直派
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                planDirectProcedure(vo, dto,  flag, (short)1);
            } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机
                planDirectProcedure(vo, dto,  flag,(short)2);
            } else { //其它（发布后派单）
                noCarrierProcedure(vo,dto,flag);
            }
        } else  if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_JINGJIA)) { //竞价
            planBiddingProcedure(vo, dto, flag);
        }
        return  vo;
    }


    /***
     * 直派(编辑)
     * @param vo -- 需要保存的计划
     * @param dto -- 前端传来的计划参数
     * @param flag -- 操作动作(1-发布，2-暂存)
     * @param carrierType -- 承运人类型(1-承运商，2-司机)
     *
     */
    private void planDirectProcedure(WaybillPlan vo, WaybillParamsDto dto, short flag, short carrierType) {
        if (!StringUtils.isEmpty(dto.getCarrierCollectionIds())) { //指定承运商
            if (dto.getIsApproval()==0) { //不需要审批
                if (flag==1) { //发布--操作
                    if(carrierType==1) { //承运商
                        vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                        vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//计划状态(派车中)
                    } else { //司机
                        vo.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED); //计划状态(已派完)
                        vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(已派完)
                    }
                    waybillPlanMapper.updateWaybillPlan(vo);
                    List<PlanDetail> planDetailList = dto.getPlanDetailList();
                    for (PlanDetail obj : planDetailList) {
                        obj.setWaybillPlanId(vo.getWaybillPlanId());
                        obj.setRemainderAmount((float)0); //全部派完--剩余为0
                        obj.setIsDeleted((short)0);
                        obj.setUpdateId(vo.getUpdateId());
                        obj.setUpdateName(obj.getUpdateName());
                        obj.setUpdateTime(new Date());
                        obj.setCompanyId(vo.getCompanyId());
                        if (obj.getPlanDetailId()>0) { //判断是否新增，删除
                            planDetailMapper.updateByPrimaryKey(obj);
                        } else {
                            obj.setCreateName(vo.getCreateName());
                            obj.setCreateId(vo.getCreateId());
                            obj.setCreateDate(new Date());
                            obj.setUpdateTime(obj.getCreateDate());
                            planDetailMapper.insert(obj);

                        }
                    }

                    SplitGoods splitGoods = new SplitGoods(); //派单
                    splitGoods.setWaybillPlanId(vo.getWaybillPlanId());
                    splitGoods.setSplitRemark("计划直接生成的...");
                    splitGoods.setCreateId(vo.getCreateId());
                    splitGoods.setCreateName(vo.getCreateName());
                    splitGoods.setCreateDate(new Date());
                    splitGoods.setUpdateId(splitGoods.getUpdateId());
                    splitGoods.setUpdateName(splitGoods.getCreateName());
                    splitGoods.setUpdateTime(splitGoods.getCreateDate());
                    splitGoods.setCompanyId(splitGoods.getCompanyId());
                    splitGoods.setCarrierCompanyId(vo.getCarrierCompanyId());
                    splitGoods.setIsDeleted((short)0);
                    splitGoodsMapper.insert(splitGoods);

                    List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
                    for (PlanDetail obj : planDetailList) {
                        SplitGoodsDetail tObj = new SplitGoodsDetail();
                        tObj.setPlanDetailId(obj.getPlanDetailId());
                        tObj.setAllotAmount(obj.getPlanAmount()); //派单数量
                        tObj.setRemainAmount((float) 0); //本次剩余
                        tObj.setFreightPrice(obj.getFreightPrice());
                        tObj.setFreightTotal(obj.getFreightTotal());
                        tObj.setDetailRemark("计划直接生成...");
                        tObj.setCreateId(vo.getCreateId());
                        tObj.setCreateName(vo.getCreateName());
                        tObj.setCreateDate(new Date());
                        tObj.setUpdateId(dto.getUpdateId());
                        tObj.setUpdateName(vo.getUpdateName());
                        tObj.setUpdateTime(tObj.getCreateDate());
                        tObj.setCompanyId(vo.getCompanyId());
                        tObj.setIsDeleted((short)0);
                        splitGoodsDetailList.add(tObj);
                    }
                    splitGoodsDetailMapper.batchAddSplitGoodsDetail(splitGoodsDetailList);

                    /***
                     *如果是司机需要生成运单
                     */
                    if (carrierType == 2) {
                        WaybillDto waybillDto = new WaybillDto();
                        PlanBO.getInstance().toWaybillItemsDto(vo,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
                        if (null!=waybillDto) {
                            waybillService.addWaybill(waybillDto);
                        }
                    }

                } else { //暂存 -- 操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                    vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                    waybillPlanMapper.updateWaybillPlan(vo); //生成计划

                    List<PlanDetail> planDetailList = dto.getPlanDetailList();
                    for (PlanDetail obj : planDetailList) {
                        obj.setWaybillPlanId(vo.getWaybillPlanId());
                        obj.setRemainderAmount(obj.getPlanAmount()); //全部派完--剩余为0
                        obj.setIsDeleted((short)0);
                        obj.setUpdateId(vo.getUpdateId());
                        obj.setUpdateName(obj.getUpdateName());
                        obj.setUpdateTime(new Date());
                        obj.setCompanyId(vo.getCompanyId());
                        if (obj.getPlanDetailId()>0) { //判断是否新增，删除
                            planDetailMapper.updateByPrimaryKey(obj);
                        } else {
                            obj.setCreateName(vo.getCreateName());
                            obj.setCreateId(vo.getCreateId());
                            obj.setCreateDate(new Date());
                            obj.setUpdateTime(obj.getCreateDate());
                            planDetailMapper.insert(obj);

                        }
                    }
                }

            } else { //需要审批 //生成计划单 （审批通过后，生成派单）
                if (flag==1) { //发布--操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //审批中
                } else {
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                }
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                waybillPlanMapper.updateWaybillPlan(vo); //生成计划
                List<PlanDetail> planDetailList = dto.getPlanDetailList();
                for (PlanDetail obj : planDetailList) {
                    obj.setWaybillPlanId(vo.getWaybillPlanId());
                    obj.setRemainderAmount(obj.getPlanAmount()); //全部派完--剩余为0
                    obj.setIsDeleted((short)0);
                    obj.setUpdateId(vo.getUpdateId());
                    obj.setUpdateName(obj.getUpdateName());
                    obj.setUpdateTime(new Date());
                    obj.setCompanyId(vo.getCompanyId());
                    if (obj.getPlanDetailId()>0) { //判断是否新增，删除
                        planDetailMapper.updateByPrimaryKey(obj);
                    } else {
                        obj.setCreateName(vo.getCreateName());
                        obj.setCreateId(vo.getCreateId());
                        obj.setCreateDate(new Date());
                        obj.setUpdateTime(obj.getCreateDate());
                        planDetailMapper.insert(obj);

                    }
                }
            }
        } else { //未指定承运商(只生成计划)
            noCarrierProcedure(vo,dto,flag);
        }
    }



    /***
     * 未指定承运商(编辑)
     *
     * @param vo -- 需要保存的计划
     * @param dto -- 前端传来的计划参数
     * @param flag -- 操作动作(1-发布，2-暂存)
     */
    private void noCarrierProcedure(WaybillPlan vo, WaybillParamsDto dto,short flag) {
        if (dto.getIsApproval()==0) { //不需要审批
            if (flag==1) { //发布--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
            } else { //暂存--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //计划状态(待发布)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //车状态(其它)
            }
        } else { // 需要审批
            if (flag==1) { //发布--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //计划状态(审批)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
            } else { //暂存--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //计划状态(待发布)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //车状态(其它)
            }
        }
        waybillPlanMapper.updateWaybillPlan(vo); //生成计划
        List<PlanDetail> planDetailList = dto.getPlanDetailList();
        for (PlanDetail obj : planDetailList) {
            obj.setWaybillPlanId(vo.getWaybillPlanId());
            obj.setRemainderAmount(obj.getPlanAmount()); //全部派完--剩余为0
            obj.setIsDeleted((short)0);
            obj.setUpdateId(vo.getUpdateId());
            obj.setUpdateName(obj.getUpdateName());
            obj.setUpdateTime(new Date());
            obj.setCompanyId(vo.getCompanyId());
            if (obj.getPlanDetailId()>0) { //判断是否新增，删除
                planDetailMapper.updateByPrimaryKey(obj);
            } else {
                obj.setCreateName(vo.getCreateName());
                obj.setCreateId(vo.getCreateId());
                obj.setCreateDate(new Date());
                obj.setUpdateTime(obj.getCreateDate());
                planDetailMapper.insert(obj);
            }
        }
    }

    /***
     * 竞价（编辑）
     * @param vo -- 需要保存的计划
     * @param dto -- 前端传来的计划参数
     * @param flag -- 操作动作(1-发布，2-暂存)
     *
     */
    private void planBiddingProcedure(WaybillPlan vo, WaybillParamsDto dto, short flag) {
        if (dto.getIsApproval()==0) { //不需要审批
            if (flag==1) { //发布--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //计划状态(竞价中)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(派车中)
            } else { //暂存--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //计划状态(竞价中)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//计划状态(派车中)
            }

        } else { //需要审核
            if (flag==1) { //发布--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //计划状态(审批中)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(派车中)
            } else { //暂存--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //计划状态(竞价中)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//计划状态(派车中)
            }
        }
        waybillPlanMapper.updateWaybillPlan(vo);
        List<PlanDetail> planDetailList = dto.getPlanDetailList();
        for (PlanDetail obj : planDetailList) {
            obj.setWaybillPlanId(vo.getWaybillPlanId());
            obj.setRemainderAmount(obj.getPlanAmount()); //全部派完--剩余为0
            obj.setIsDeleted((short)0);
            obj.setUpdateId(vo.getUpdateId());
            obj.setUpdateName(obj.getUpdateName());
            obj.setUpdateTime(new Date());
            obj.setCompanyId(vo.getCompanyId());
            if (obj.getPlanDetailId()>0) { //判断是否新增，删除
                planDetailMapper.updateByPrimaryKey(obj);
            } else {
                obj.setCreateName(vo.getCreateName());
                obj.setCreateId(vo.getCreateId());
                obj.setCreateDate(new Date());
                obj.setUpdateTime(obj.getCreateDate());
                planDetailMapper.insert(obj);
            }
        }

    }








}
