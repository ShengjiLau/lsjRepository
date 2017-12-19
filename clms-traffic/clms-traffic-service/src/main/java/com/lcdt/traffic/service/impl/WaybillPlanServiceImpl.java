package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
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
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/13.
 */
@Service
public class WaybillPlanServiceImpl implements WaybillPlanService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单详细

    @Autowired
    private PlanLeaveMsgMapper planLeaveMsgMapper; //计划留言



    @com.alibaba.dubbo.config.annotation.Reference
    public CustomerRpcService customerRpcService;  //客户信息


    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan createWaybillPlan(WaybillParamsDto dto, short flag) {
        WaybillPlan vo = new WaybillPlan(); //复制传来对象值
        BeanUtils.copyProperties(dto, vo);
        vo.setCreateDate(new Date()); //创建时间
        vo.setUpdateId(dto.getCreateId()); //默认为创建人
        vo.setUpdateName(dto.getCreateName());
        vo.setUpdateTime(vo.getCreateDate());
        vo.setIsDeleted((short) 0); //未删除
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
        return vo;
    }



    /***
     * 直派
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
                    waybillPlanMapper.insert(vo);
                    List<PlanDetail> planDetailList = dto.getPlanDetailList();
                    for (PlanDetail obj : planDetailList) {
                        obj.setWaybillPlanId(vo.getWaybillPlanId());
                        obj.setRemainderAmount((float)0); //全部派完--剩余为0
                        obj.setCreateName(vo.getCreateName());
                        obj.setCreateId(vo.getCreateId());
                        obj.setCreateDate(new Date());
                        obj.setUpdateId(vo.getUpdateId());
                        obj.setUpdateName(obj.getUpdateName());
                        obj.setUpdateTime(obj.getCreateDate());
                        obj.setCompanyId(vo.getCompanyId());

                        obj.setIsDeleted((short)0);
                    }
                    planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细

                    SplitGoods splitGoods = new SplitGoods(); //派单
                    splitGoods.setWaybillPlanId(vo.getWaybillPlanId());
                    splitGoods.setCarrierType(vo.getCarrierType());
                    splitGoods.setCarrierCollectionIds(dto.getCarrierCollectionIds());//承运商ID
                    splitGoods.setCarrierCollectionNames(dto.getCarrierCollectionNames());//承运商
                    splitGoods.setCarrierPhone(dto.getCarrierPhone());//司机电话
                    splitGoods.setCarrierVehicle(dto.getCarrierVehicle());//车号
                    splitGoods.setTransportWay(dto.getTransportWay());//承运方式
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
                        tObj.setAllotAmount((float) 0); //待派数量
                        tObj.setFactAllotAmount(obj.getPlanAmount()); //实际派单数
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


                } else { //暂存 -- 操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                    vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                    waybillPlanMapper.insert(vo); //生成计划
                    List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();
                    for (PlanDetail obj : planDetailList) {
                        obj.setRemainderAmount(obj.getPlanAmount());//计划==剩余
                        obj.setWaybillPlanId(vo.getWaybillPlanId());
                        obj.setCreateId(vo.getCreateId());
                        obj.setCreateName(vo.getCreateName());
                        obj.setCreateDate(new Date());
                        obj.setUpdateId(vo.getUpdateId());
                        obj.setUpdateName(vo.getUpdateName());
                        obj.setUpdateTime(obj.getCreateDate());
                        obj.setCompanyId(vo.getCompanyId());
                        obj.setIsDeleted((short)0);
                        planDetailList.add(obj);
                    }
                    planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
                }

            } else { //需要审批 //生成计划单 （审批通过后，生成派单）
                if (flag==1) { //发布--操作
                     vo.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //审批中
                } else {
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                }
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                waybillPlanMapper.insert(vo); //生成计划
                List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();
                for (PlanDetail obj : planDetailList) {
                    obj.setWaybillPlanId(vo.getWaybillPlanId());
                    obj.setRemainderAmount(obj.getPlanAmount());
                    obj.setCreateId(vo.getCreateId());
                    obj.setCreateName(vo.getCreateName());
                    obj.setCreateDate(new Date());
                    obj.setUpdateId(vo.getUpdateId());
                    obj.setUpdateName(vo.getUpdateName());
                    obj.setUpdateTime(obj.getCreateDate());
                    obj.setCompanyId(vo.getCompanyId());
                    obj.setIsDeleted((short)0);
                    planDetailList.add(obj);
                }
                planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
            }
        } else { //未指定承运商(只生成计划)
            noCarrierProcedure(vo,dto,flag);
        }
    }


    /***
     * 未指定承运商
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
        waybillPlanMapper.insert(vo); //生成计划
        List<PlanDetail> planDetailList = dto.getPlanDetailList();
        for (PlanDetail obj : planDetailList) {
            obj.setWaybillPlanId(vo.getWaybillPlanId());
            obj.setRemainderAmount(obj.getPlanAmount());//初期【计划=剩余】
            obj.setCreateId(vo.getCreateId());
            obj.setCreateName(vo.getCreateName());
            obj.setCreateDate(new Date());
            obj.setUpdateId(vo.getUpdateId());
            obj.setUpdateName(vo.getUpdateName());
            obj.setUpdateTime(obj.getCreateDate());
            obj.setCompanyId(vo.getCompanyId());
            obj.setIsDeleted((short)0);
            planDetailList.add(obj);
        }
        planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
    }




    /***
     * 竞价
     * @param vo -- 需要保存的计划
     * @param dto -- 前端传来的计划参数
     * @param flag -- 操作动作(1-发布，2-暂存)
     *
     */
    private void planBiddingProcedure(WaybillPlan vo, WaybillParamsDto dto, short flag) { {
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
        waybillPlanMapper.insert(vo);
        List<PlanDetail> planDetailList = dto.getPlanDetailList();
        for (PlanDetail obj : planDetailList) {
            obj.setWaybillPlanId(vo.getWaybillPlanId());
            obj.setCreateId(vo.getCreateId());
            obj.setRemainderAmount(obj.getPlanAmount());
            obj.setCreateName(vo.getCreateName());
            obj.setCreateDate(new Date());
            obj.setUpdateId(vo.getUpdateId());
            obj.setUpdateName(vo.getUpdateName());
            obj.setUpdateTime(obj.getCreateDate());
            obj.setCompanyId(vo.getCompanyId());
            obj.setIsDeleted((short)0);
        }

    }

}

    @Override
    public PageInfo wayBillPlanList(Map map) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (map.containsKey("page_no")) {
            if (map.get("page_no") != null) {
                pageNo = (Integer) map.get("page_no");
            }
        }
        if (map.containsKey("page_size")) {
            if (map.get("page_size") != null) {
                pageSize = (Integer) map.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<WaybillPlan> list = waybillPlanMapper.selectByCondition(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan publishWayBillPlan(WaybillParamsDto dto) throws WaybillPlanException { //只有是暂存(待发布状态的，才能点发布)
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId());
        WaybillPlan updateObj = new WaybillPlan();
        updateObj.setWaybillPlanId(waybillPlan.getWaybillPlanId());
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        if (waybillPlan.getPlanStatus().equals(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH)) {
            if (!StringUtils.isEmpty(waybillPlan.getCarrierCollectionIds())) { //指定承运商
                if (waybillPlan.getIsApproval()==0 ) { //不需要审批
                    if (waybillPlan.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI)) { //直派
                            if (waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//计划状态(派车中)
                            } else if (waybillPlan.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机
                                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED); //计划状态(已派完)
                                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(已派完)
                            }

                            List<PlanDetail> planDetailList = waybillPlan.getPlanDetailList();
                            if (planDetailList!=null && planDetailList.size()>0) { //更新计划详细
                                for (PlanDetail planDetail : planDetailList) {
                                    planDetail.setRemainderAmount((float)0); //全部派完--剩余为0
                                    planDetail.setUpdateId(dto.getUpdateId());
                                    planDetail.setUpdateName(dto.getUpdateName());
                                    planDetail.setUpdateTime(new Date());
                                    planDetailMapper.updateByPrimaryKey(planDetail);
                                }

                                SplitGoods splitGoods = new SplitGoods(); //派单
                                splitGoods.setWaybillPlanId(waybillPlan.getWaybillPlanId());
                                splitGoods.setCarrierType(waybillPlan.getCarrierType());
                                splitGoods.setCarrierCollectionIds(waybillPlan.getCarrierCollectionIds());//承运商ID
                                splitGoods.setCarrierCollectionNames(waybillPlan.getCarrierCollectionNames());//承运商
                                splitGoods.setCarrierPhone(waybillPlan.getCarrierPhone());//司机电话
                                splitGoods.setCarrierVehicle(waybillPlan.getCarrierVehicle());//车号
                                splitGoods.setTransportWay(waybillPlan.getTransportWay());//承运方式
                                splitGoods.setSplitRemark("发布生成...");
                                splitGoods.setCreateId(dto.getCreateId());
                                splitGoods.setCreateName(dto.getCreateName());
                                splitGoods.setCreateDate(new Date());
                                splitGoods.setUpdateId(dto.getUpdateId());
                                splitGoods.setUpdateName(dto.getUpdateName());
                                splitGoods.setUpdateTime(splitGoods.getCreateDate());
                                splitGoods.setCompanyId(dto.getCompanyId());
                                splitGoods.setCarrierCompanyId(waybillPlan.getCarrierCompanyId());
                                splitGoods.setIsDeleted((short)0);
                                splitGoodsMapper.insert(splitGoods);

                                List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
                                for (PlanDetail obj : planDetailList) {
                                    SplitGoodsDetail tObj = new SplitGoodsDetail();
                                    tObj.setPlanDetailId(obj.getPlanDetailId());
                                    tObj.setAllotAmount((float) 0); //待派数量
                                    tObj.setFactAllotAmount(obj.getPlanAmount()); //实际派单数
                                    tObj.setFreightPrice(obj.getFreightPrice());
                                    tObj.setFreightTotal(obj.getFreightTotal());
                                    tObj.setDetailRemark("发布生在....");
                                    tObj.setCreateId(obj.getCreateId());
                                    tObj.setCreateName(obj.getCreateName());
                                    tObj.setCreateDate(new Date());
                                    tObj.setUpdateId(obj.getUpdateId());
                                    tObj.setUpdateName(obj.getUpdateName());
                                    tObj.setUpdateTime(tObj.getCreateDate());
                                    tObj.setCompanyId(obj.getCompanyId());
                                    tObj.setIsDeleted((short)0);
                                    splitGoodsDetailList.add(tObj);
                                }
                                splitGoodsDetailMapper.batchAddSplitGoodsDetail(splitGoodsDetailList);
                                /****************司机生成运单********************/
                            }

                    } else  if (waybillPlan.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_JINGJIA)) { //竞价
                            if (waybillPlan.getIsApproval()==0) { //不需要审批
                                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //计划状态(竞价中)
                                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(派车中)
                            } else { //需要审核
                                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //计划状态(竞价中)
                                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//计划状态(派车中)
                            }
                    }

                } else {// 需要审批
                    updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //审批中
                    updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
               }

            } else { //未指派承运人
                if(waybillPlan.getIsApproval()==0) { //不需要审批
                    updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                    updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
                } else { // 需要审批
                    updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //计划状态(审批)
                    updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
                }
             }
            //更新人信息
            updateObj.setUpdateId(dto.getUpdateId());
            updateObj.setUpdateName(dto.getUpdateName());
            updateObj.setUpdateTime(new Date());
            waybillPlanMapper.updateByPrimaryKey(updateObj);

        } else {
            throw new WaybillPlanException("计划处于'未发布'状态不能发布！");
        }
        return waybillPlan;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan modifyWayBillPlan(WaybillParamsDto dto) {
        return null;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public PlanLeaveMsg planLeaveMsgAdd(PlanLeaveMsgParamsDto dto) throws WaybillPlanException {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId());
        if (waybillPlan != null) { //计划存在
            PlanLeaveMsg planLeaveMsg = new PlanLeaveMsg();
            if (waybillPlan.getCompanyId()==dto.getCompanyId()) { //说明是货主
                planLeaveMsg.setLeaveType(ConstantVO.PLAN_LEAVE_MSG_TYPE_SHIPPER);
                planLeaveMsg.setCompanyName(dto.getCompanyName());
                planLeaveMsg.setCompanyId(dto.getCompanyId());
                planLeaveMsg.setShipperName(dto.getRealName());
            } else if(waybillPlan.getCarrierCompanyId() == dto.getCompanyId()) { //说明是承运人
                planLeaveMsg.setLeaveType(ConstantVO.PLAN_LEAVE_MSG_TYPE_CARRIER);
                planLeaveMsg.setCarrierCompanyName(dto.getCompanyName());
                planLeaveMsg.setCarrierCompanyId(dto.getCompanyId());
                planLeaveMsg.setCarrierName(dto.getRealName());
            }
            planLeaveMsg.setCreateId(dto.getUserId());
            planLeaveMsg.setCreateName(dto.getRealName());
            planLeaveMsg.setCreateDate(planLeaveMsg.getLeaveDate());
            planLeaveMsg.setUpdateId(dto.getUserId());
            planLeaveMsg.setUpdateName(dto.getRealName());
            planLeaveMsg.setUpdateTime(planLeaveMsg.getLeaveDate());
            planLeaveMsg.setLeaveMsg(dto.getLeaveMsg());
            planLeaveMsg.setLeaveDate(new Date());
            planLeaveMsg.setIsDeleted((short)0);
            planLeaveMsgMapper.insert(planLeaveMsg);
        } else {
            throw new WaybillPlanException("计划不存在！");
        }
        return null;
    }



}
