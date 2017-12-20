package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan publishWayBillPlan(WaybillParamsDto dto) throws WaybillPlanException { //只有是暂存(待发布状态的，才能点发布)
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
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
                                // planDetailMapper.updateByPrimaryKey(planDetail);
                            }
                            planDetailMapper.batchUpdatePlanDetail(planDetailList);

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

    @Override
    public WaybillPlan wayBillPlanCheckPass(WaybillParamsDto dto) throws WaybillPlanException {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        if (waybillPlan.getIsApproval()==0) throw new WaybillPlanException("计划不需要审批！");
        WaybillPlan updateObj = new WaybillPlan();
        updateObj.setWaybillPlanId(waybillPlan.getWaybillPlanId());
        updateObj.setCompanyId(waybillPlan.getCompanyId());
        Date opDate = new Date();
        if (!StringUtils.isEmpty(waybillPlan.getCarrierCollectionIds())) { //指定承运商
            short carrierType = waybillPlan.getCarrierType();
            if (carrierType == 1) { //承运商
                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//计划状态(派车中)
            } else { //司机
                updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED); //计划状态(已派完)
                updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//计划状态(已派完)
            }
            List<PlanDetail> planDetailList = waybillPlan.getPlanDetailList();
            for (PlanDetail obj : planDetailList) {
                obj.setRemainderAmount((float)0); //全部派完--剩余为0
                obj.setUpdateId(dto.getUpdateId());
                obj.setUpdateName(dto.getUpdateName());
                obj.setUpdateTime(opDate);
            }
            planDetailMapper.batchUpdatePlanDetail(planDetailList);
            SplitGoods splitGoods = new SplitGoods(); //派单
            splitGoods.setWaybillPlanId(waybillPlan.getWaybillPlanId());
            splitGoods.setCarrierType(waybillPlan.getCarrierType());
            splitGoods.setCarrierCollectionIds(waybillPlan.getCarrierCollectionIds());//承运商ID
            splitGoods.setCarrierCollectionNames(waybillPlan.getCarrierCollectionNames());//承运商
            splitGoods.setCarrierPhone(waybillPlan.getCarrierPhone());//司机电话
            splitGoods.setCarrierVehicle(waybillPlan.getCarrierVehicle());//车号
            splitGoods.setTransportWay(waybillPlan.getTransportWay());//承运方式
            splitGoods.setSplitRemark("审批通过后生成....");
            splitGoods.setCreateId(dto.getUpdateId());
            splitGoods.setCreateName(dto.getUpdateName());
            splitGoods.setCreateDate(opDate);
            splitGoods.setUpdateId(dto.getUpdateId());
            splitGoods.setUpdateName(dto.getCreateName());
            splitGoods.setUpdateTime(opDate);
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
                tObj.setDetailRemark("审批通过后生成...");
                tObj.setCreateId(dto.getUpdateId());
                tObj.setCreateName(dto.getUpdateName());
                tObj.setCreateDate(opDate);
                tObj.setUpdateId(dto.getUpdateId());
                tObj.setUpdateName(dto.getUpdateName());
                tObj.setUpdateTime(opDate);
                tObj.setCompanyId(dto.getCompanyId());
                tObj.setIsDeleted((short)0);
                splitGoodsDetailList.add(tObj);
            }
            splitGoodsDetailMapper.batchAddSplitGoodsDetail(splitGoodsDetailList);
            /***司机生成运单***/
        } else { //未指定承运商
            updateObj.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
            updateObj.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
       }
        //更新人信息
        updateObj.setUpdateId(dto.getUpdateId());
        updateObj.setUpdateName(dto.getUpdateName());
        updateObj.setUpdateTime(opDate);
        waybillPlanMapper.updateByPrimaryKey(updateObj);
        return waybillPlan;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    @Override
    public WaybillPlan loadWaybillPlan(WaybillParamsDto dto) throws WaybillPlanException  {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
        if (waybillPlan == null) {
            throw new WaybillPlanException("计划不存在！");
        }
        return waybillPlan;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public PlanLeaveMsg planLeaveMsgAdd(PlanLeaveMsgParamsDto dto) throws WaybillPlanException {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(),dto.getCreateCompanyId(),(short)0);
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
                //承运人也得保存
                planLeaveMsg.setCompanyId(waybillPlan.getCompanyId());
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


    @Transactional(readOnly = true)
    @Override
    public PageInfo planLeaveMsgList(Map map) {
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
        //先获取计划，根据计划判断是货主还是承运人
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(Long.valueOf(map.get("waybillPlanId").toString()),Long.valueOf(map.get("createCompanyId").toString()), (short)0);
        long companyId = Long.valueOf(map.get("companyId").toString());//登录人企业ID
        PageInfo pageInfo = null;
        if (waybillPlan != null) { //计划存在
            PlanLeaveMsg planLeaveMsg = new PlanLeaveMsg();
            if (waybillPlan.getCompanyId() == companyId) { //说明是货主
                map.put("companyId",companyId);
            } else {
                map.put("carrierCompanyId",companyId);
            }
            PageHelper.startPage(pageNo, pageSize);
            List<PlanLeaveMsg> list = planLeaveMsgMapper.selectByCondition(map);
            pageInfo = new PageInfo(list);
        }
        return pageInfo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer planDetailDelete(Long planDetailId, Long companyId) {
        return planDetailMapper.deleteByPrimaryKey(planDetailId,companyId);
    }

}
