package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import com.lcdt.userinfo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2017/12/13.
 */
@Service
public class PlanServiceImpl implements PlanService {

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

    @Autowired
    private WaybillService waybillService;


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
                                tObj.setAllotAmount(obj.getPlanAmount()); //派单数量
                                tObj.setRemainAmount((float) 0); //本次剩余
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
    public WaybillPlan wayBillPlanCheckPass(WaybillParamsDto dto) {
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
                tObj.setAllotAmount(obj.getPlanAmount()); //派单数量
                tObj.setRemainAmount((float) 0); //本次剩余
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
            /***
             *如果是司机需要生成运单
             */
            if (carrierType == 2) {
                WaybillDto waybillDto = PlanBO.getInstance().toWaybillItemsDto(waybillPlan, planDetailList, splitGoodsDetailList);
                if (null!=waybillDto) {
                    waybillService.addWaybill(waybillDto);
                }
            }



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
    public WaybillPlan loadWaybillPlan(WaybillParamsDto dto) {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
        if (waybillPlan == null) {
            throw new WaybillPlanException("计划不存在！");
        }
        return waybillPlan;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public PlanLeaveMsg planLeaveMsgAdd(PlanLeaveMsgParamsDto dto) {
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
    public Integer  planDetailDelete(Long planDetailId, Long companyId) {
        return planDetailMapper.deleteByPrimaryKey(planDetailId,companyId);
    }

    @Override
    public Integer ownPlanCancel(Long waybillPlanId, Long companyId, User user) {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(waybillPlanId, companyId, (short)0);
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        if (waybillPlan.getPlanStatus()==ConstantVO.PLAN_STATUS_COMPLETED) {
            throw new WaybillPlanException("计划已完成，不能取消！");
        }
        Date dt = new Date();
        //验证是否已经存在已卸货或者已完成的运单，如果存在则提示：该计划存在已经卸货或者完成的运单，不可取消！
        Map map = new HashMap<String,String>();
        map.put("companyId", companyId);
        map.put("waybillPlanId",waybillPlanId);
        map.put("isDeleted",0);
        PageInfo pg = waybillService.queryPlannedWaybillList(map);
        if (pg.getTotal()>0) {
            throw new WaybillPlanException("该计划存在已经卸货或者完成的运单，不可取消！");
        } else {
            map.put("waybillStatus",ConstantVO.WAYBILL_STATUS_HAVE_CANCEL);
            map.put("updateId",user.getUserId());
            map.put("updateName",user.getRealName());
            waybillService.modifyOwnWaybillStatusByWaybillPlanId(map);
        }
        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_CANCEL);
        waybillPlan.setUpdateTime(dt);
        waybillPlan.setUpdateId(user.getUserId());
        waybillPlan.setUpdateName(user.getRealName());
        waybillPlanMapper.updateByPrimaryKey(waybillPlan);
        return 1;
    }


}

