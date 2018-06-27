package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.notify.model.Timeline;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.LeaveMsgDto;
import com.lcdt.traffic.dto.LeaveMsgParamDto;
import com.lcdt.traffic.dto.PlanDetailParamsDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.pay.ReduceServiceCount;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.PlanLeaveMsgParamsDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息

    @Autowired
    private WaybillRpcService waybillRpcService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public PlanLeaveMsg planLeaveMsgAdd(PlanLeaveMsgParamsDto dto) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("companyId",dto.getCreateCompanyId());
        tMap.put("isDeleted","0");
        PlanLeaveMsg planLeaveMsg = null;
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan != null) { //计划存在
            planLeaveMsg = new PlanLeaveMsg();
            planLeaveMsg.setWaybillPlanId(waybillPlan.getWaybillPlanId());
            if (waybillPlan.getCompanyId()==dto.getCompanyId()) { //说明是货主
                planLeaveMsg.setLeaveType(ConstantVO.PLAN_LEAVE_MSG_TYPE_SHIPPER);
                planLeaveMsg.setCompanyName(dto.getCompanyName());
                planLeaveMsg.setCompanyId(dto.getCompanyId());
                planLeaveMsg.setShipperName(dto.getRealName());
            }else{ //说明是承运人 //(waybillPlan.getCarrierCompanyId() == dto.getCompanyId())
                planLeaveMsg.setLeaveType(ConstantVO.PLAN_LEAVE_MSG_TYPE_CARRIER);
                planLeaveMsg.setCarrierCompanyName(dto.getCompanyName());
                planLeaveMsg.setCarrierCompanyId(dto.getCompanyId());
                planLeaveMsg.setCarrierName(dto.getRealName());
                planLeaveMsg.setCompanyId(waybillPlan.getCompanyId());
            }
            planLeaveMsg.setCreateId(dto.getUserId());
            planLeaveMsg.setCreateName(dto.getRealName());
            planLeaveMsg.setCreateDate(new Date());
            planLeaveMsg.setUpdateId(dto.getUserId());
            planLeaveMsg.setUpdateName(dto.getRealName());
            planLeaveMsg.setUpdateTime(planLeaveMsg.getLeaveDate());
            planLeaveMsg.setLeaveMsg(dto.getLeaveMsg());
            planLeaveMsg.setLeaveDate(new Date());
            planLeaveMsg.setUpdateTime(planLeaveMsg.getCreateDate());
            planLeaveMsg.setIsDeleted((short)0);
            planLeaveMsgMapper.insert(planLeaveMsg);
        } else {
            throw new WaybillPlanException("计划不存在！");
        }
        return planLeaveMsg;
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
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",map.get("waybillPlanId"));
        tMap.put("companyId",map.get("createCompanyId"));
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        long companyId = Long.valueOf(map.get("companyId").toString());//登录人企业ID
        PageInfo pageInfo = null;
        if (waybillPlan != null) { //计划存在
         PlanLeaveMsg planLeaveMsg = new PlanLeaveMsg();
            map.remove("companyId");
            map.remove("createCompanyId");
            if (waybillPlan.getCompanyId() == companyId) { //说明是货主
                map.put("companyId",companyId);
            } else {
                map.put("carrierCompanyId4leaveMsg",companyId);
                map.put("companyId",waybillPlan.getCompanyId());
            }
            PageHelper.startPage(pageNo, pageSize);
            List<PlanLeaveMsg> list = planLeaveMsgMapper.selectByCondition(map);
            pageInfo = new PageInfo(list);
        }
        return pageInfo;
    }


    @Transactional(readOnly = true)
    @Override
    public List<LeaveMsgDto> planLeaveMsgList4Batch(LeaveMsgParamDto leaveMsgParamDto) {
        if(leaveMsgParamDto==null) return null;
        for(LeaveMsgDto dto : leaveMsgParamDto.getLeaveMsgDtoList()) {
            Map tMap = new HashMap<String,String>();
            tMap.put("waybillPlanId",dto.getWaybillPlanId());
            tMap.put("companyId",dto.getCreateCompanyId());
            tMap.put("isDeleted","0");
            WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
            long companyId = Long.valueOf(leaveMsgParamDto.getLoginCmpId());//登录人企业ID
            PageInfo pageInfo = null;
            if (waybillPlan != null) { //计划存在
                Map map = new HashMap<String,String>();
                 PlanLeaveMsg planLeaveMsg = new PlanLeaveMsg();
                if (waybillPlan.getCompanyId() == companyId) { //说明是货主
                    map.put("companyId",companyId);
                } else {
                    map.put("carrierCompanyId4leaveMsg",companyId);
                    map.put("companyId",waybillPlan.getCompanyId());
                }
                List<PlanLeaveMsg> list = planLeaveMsgMapper.selectByCondition(map);
                dto.setList(list);
                dto.setTotal(list==null?0:list.size());
            }
        }
        return leaveMsgParamDto.getLeaveMsgDtoList();

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer  planDetailDelete(Long planDetailId, Long companyId) {
        Map tMap = new HashMap<String,String>();
        tMap.put("planDetailId",planDetailId);
        tMap.put("companyId",companyId);
        return planDetailMapper.deleteByPrimaryKey(tMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer ownPlanCancel(Long waybillPlanId, UserCompRel userCompRel) {
        User user = userCompRel.getUser();
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",waybillPlanId);
        tMap.put("companyId",userCompRel.getCompany().getCompId());
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        if (waybillPlan.getPlanStatus().equals(ConstantVO.PLAN_STATUS_COMPLETED)) {
            throw new WaybillPlanException("计划已完成，不能取消！");
        }
        if (waybillPlan.getPlanStatus().equals(ConstantVO.PLAN_STATUS_CANCEL)) {
            throw new WaybillPlanException("计划已取消！");
        }
        Date dt = new Date();
        //验证是否已经存在已卸货或者已完成的运单，如果存在则提示：该计划存在已经卸货或者完成的运单，不可取消！
        Map map = new HashMap<String,String>();
        map.put("companyId", userCompRel.getCompany().getCompId());
        map.put("waybillPlanId",waybillPlanId);
        map.put("isDeleted",0);
        PageInfo pg = waybillService.queryPlannedWaybillList(map);
        if (pg.getTotal()>0) {
            throw new WaybillPlanException("该计划存在已经卸货或者完成的运单，不可取消！");
        } else {
            map.put("waybillStatus",ConstantVO.WAYBILL_STATUS_HAVE_CANCEL);
            map.put("updateId",user.getUserId());
            map.put("updateName",user.getRealName());
            waybillRpcService.modifyOwnWaybillStatusByWaybillPlanId(map);
        }
        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_CANCEL);
        waybillPlan.setUpdateTime(dt);
        waybillPlan.setUpdateId(user.getUserId());
        waybillPlan.setUpdateName(user.getRealName());
        waybillPlan.setCancelDate(new Date());
        waybillPlan.setCancelMan(user.getRealName());
        waybillPlanMapper.updateByPrimaryKey(waybillPlan);


        //router:计划取消
        Timeline event = new Timeline();
        String createName = user.getRealName()==null?"":user.getRealName();
        String company = userCompRel.getCompany().getFullName()==null?"":userCompRel.getCompany().getFullName();

        event.setActionTitle("【计划取消】 （操作人："+company+" "+createName+"）");
        event.setActionTime(new Date());
        event.setCompanyId(userCompRel.getCompany().getCompId());
        event.setSearchkey("R_PLAN");
        event.setDataid(waybillPlan.getWaybillPlanId());

        return 1;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePlanStatusByWaybill(WaybillPlan waybillPlan) {
        //这块需要检查一下计划下的所有派单是否已完成
        Map map = new HashMap<String,String>();
        map.put("waybillPlanId",waybillPlan.getWaybillPlanId());
        map.put("isDeleted",0);
        WaybillPlan waybillPlan1 = waybillPlanMapper.selectByPrimaryKey(map);
        double remainCount = 0;
        if(waybillPlan1!=null) { //如果计划下的所有派单剩余数量为0的话，说明完成
            //派单详细
            List<SplitGoods> splitGoodsList = waybillPlan1.getSplitGoodsList();
            if (splitGoodsList!=null && splitGoodsList.size()>0) {
                for (SplitGoods splitGoods: splitGoodsList) {
                    List<SplitGoodsDetail> splitGoodsDetailList = splitGoods.getSplitGoodsDetailList();
                    if (splitGoodsDetailList!=null && splitGoodsDetailList.size()>0) {
                        for(SplitGoodsDetail splitGoodsDetail : splitGoodsDetailList) {
                                remainCount += splitGoodsDetail.getRemainAmount();
                        }

                    }
                }
            } else {
                //不存在
            }
            //计划详细
            List<PlanDetail> planDetailList = waybillPlan1.getPlanDetailList();
            if (planDetailList!=null && planDetailList.size()>0) {
                for (PlanDetail splitGoods: planDetailList) {
                    remainCount+=splitGoods.getRemainderAmount();
                }

            }
            if(remainCount==0) {
                waybillPlanMapper.updateWaybillPlan(waybillPlan); // 如果所有拍单剩余数量为0的话
            }
        }

       return 0;
    }




    @Transactional(rollbackFor = Exception.class)
    @Override
    public int adjustPlanRemainAmount(List<PlanDetailParamsDto> dtoList, UserCompRel userCompRel) {
        if (dtoList!=null && dtoList.size()>0) {
            Company company = userCompRel.getCompany();
            User user = userCompRel.getUser();
            List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();
            Date date = new Date();
            boolean flag = false;

            long planId = 0;
            for (PlanDetailParamsDto dto : dtoList) {
                PlanDetail planDetail = planDetailMapper.selectByPrimaryKey1(dto.getPlanDetailId(),company.getCompId());
                if (planDetail!=null) {
                    planId = planDetail.getWaybillPlanId();
                    if(dto.getAdjustAmount()+planDetail.getRemainderAmount()<0) {
                        flag = true;
                        break;
                    }
                    planDetail.setUpdateName(user.getRealName());
                    planDetail.setUpdateId(user.getUserId());
                    planDetail.setUpdateTime(date);
                    Float amount = planDetail.getPlanAmount()+dto.getAdjustAmount(); //计划量+调整量
                    planDetail.setPlanAmount(amount);
                    planDetail.setRemainderAmount(planDetail.getRemainderAmount()+dto.getAdjustAmount()); //剩余数量+调整量
                    if(planDetail.getFreightPrice()!=null)
                    {
                        planDetail.setFreightTotal(amount*planDetail.getFreightPrice());
                    }
                    planDetailList.add(planDetail);
                }
            }

            if(flag){
                throw new WaybillPlanException("修改后计划量需大于已派单数量！");
            }

            if(planDetailList!=null && planDetailList.size()>0) {
                 planDetailMapper.batchUpdatePlanDetail(planDetailList);
            }
            //再次检查计划下的所有货物剩余数为0 的话，计划状态为已派单
            if (planId > 0) {
                Map tMap = new HashMap<String,String>();
                tMap.put("waybillPlanId",planId);
                tMap.put("companyId", company.getCompId());
                tMap.put("isDeleted","0");
                WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
                if (waybillPlan!=null) {
                    List<PlanDetail> planDetailList1 = waybillPlan.getPlanDetailList();
                    Float _amount = 0f;
                    for(PlanDetail obj : planDetailList1) {
                        _amount +=obj.getRemainderAmount();
                    }
                    if(_amount<=0) {
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF);
                        waybillPlan.setUpdateTime(new Date());
                        waybillPlan.setUpdateId(user.getUserId());
                        waybillPlan.setUpdateName(user.getRealName());
                        waybillPlanMapper.updateWaybillPlan(waybillPlan);
                    }

                }
            }

        }

        return 1;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adjustPlanAndSplitAmount(WaybillDao waybillDao) {
        //查询计划
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",waybillDao.getWaybillPlanId());
        tMap.put("companyId",waybillDao.getCompanyId());
        tMap.put("isDeleted","0");
        PlanLeaveMsg planLeaveMsg = null;
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan != null) {
           // List<PlanDetail> planDetailList = waybillPlan.getPlanDetailList();
            List<SplitGoods> splitGoodsList = waybillPlan.getSplitGoodsList();
            List<WaybillItems> waybillItemsList = waybillDao.getWaybillItemsList();
            List<SplitGoodsDetail> splitGoodsDetailList = null;

            Float _splitRemainderAmount = 0f;
//    //先处理计划
//            if (planDetailList!=null && waybillItemsList.size()>0 && waybillItemsList!=null && waybillItemsList.size()>0) {
//                for (WaybillItems waybillItems: waybillItemsList) {
//                     for (PlanDetail planDetails :planDetailList) {
//                            if (waybillItems.getPlanDetailId().equals(planDetails.getPlanDetailId())) {
//                                planDetails.setRemainderAmount(waybillItems.getAmount()+planDetails.getRemainderAmount());
//
//                                planDetails.setUpdateTime(new Date());
//                                planDetails.setUpdateId(waybillDao.getUpdateId());
//                                planDetails.setUpdateName(waybillDao.getUpdateName());
//                                if (planDetails.getRemainderAmount() <= 0) {
//                                    throw new WaybillPlanException("调整数量大于计划待派数量！");
//                                }
//                                if (planDetails.getRemainderAmount()>planDetails.getPlanAmount()) {
//                                    throw new WaybillPlanException("调整数量大于计划原始数量！");
//                                }
//
//                            }
//
//                    }
//                }
//
//           }

            //再处理派单
            if (splitGoodsList!=null && splitGoodsList.size()>0 && waybillItemsList!=null && waybillItemsList.size()>0) {
                for (WaybillItems waybillItems: waybillItemsList) {
                    for (SplitGoods splitGoods : splitGoodsList) {
                        splitGoodsDetailList = splitGoods.getSplitGoodsDetailList();
                        if (splitGoodsDetailList!=null && splitGoodsDetailList.size()>0) {
                            for (SplitGoodsDetail splitGoodsDetail :splitGoodsDetailList) {
                                if(waybillItems.getSplitGoodsDetailId().equals(splitGoodsDetail.getSplitGoodsDetailId())) {
                                    splitGoodsDetail.setRemainAmount(waybillItems.getAmount()+splitGoodsDetail.getRemainAmount());
                                    if (splitGoodsDetail.getRemainAmount()>splitGoodsDetail.getAllotAmount()) {
                                        throw new WaybillPlanException("调整数量大于派单原始数量！");
                                    }
                                    if (splitGoodsDetail.getRemainAmount() < 0) {
                                        throw new WaybillPlanException("派单调整数量大于计划待派数量！");
                                    }
                                    splitGoodsDetail.setUpdateTime(new Date());
                                    splitGoodsDetail.setUpdateId(waybillDao.getUpdateId());
                                    splitGoodsDetail.setUpdateName(waybillDao.getUpdateName());

                                }
                           }

                        }
                    }
                }
            }
            splitGoodsDetailMapper.batchUpdateSplitGoodsDetail(splitGoodsDetailList);

            //再次查询派单剩余量是否
            waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
            splitGoodsList = waybillPlan.getSplitGoodsList();
            if (splitGoodsList!=null && splitGoodsList.size()>0 ) {
                for (SplitGoods splitGoods : splitGoodsList) {
                    splitGoodsDetailList = splitGoods.getSplitGoodsDetailList();
                    if (splitGoodsDetailList!=null && splitGoodsDetailList.size()>0) {
                        for (SplitGoodsDetail splitGoodsDetail :splitGoodsDetailList) {
                            _splitRemainderAmount +=splitGoodsDetail.getRemainAmount();
                        }

                    }
                }
            }
            if (_splitRemainderAmount>0) { //派单完成的更新计划状态
                waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS);
                waybillPlan.setUpdateTime(new Date());
                waybillPlan.setUpdateId(waybillDao.getUpdateId());
                waybillPlan.setUpdateName(waybillDao.getUpdateName());
                waybillPlanMapper.updateWaybillPlan(waybillPlan);
            }
        }
    }


}

