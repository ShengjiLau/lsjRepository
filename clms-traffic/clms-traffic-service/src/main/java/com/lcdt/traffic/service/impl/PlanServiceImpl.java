package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
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
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
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


    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息





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

        if(list!=null && list.size()>0) { //
            for(WaybillPlan obj :list) {
                List<SnatchGoods> snatchGoodsList = obj.getSnatchGoodsList();
                if (snatchGoodsList!=null && snatchGoodsList.size()>0) {
                    for(SnatchGoods snatchGoods :snatchGoodsList) {
                        Long jj_company_id = snatchGoods.getCompanyId(); //竞价企业ID
                        Company carrierCompany = companyRpcService.findCompanyByCid(jj_company_id);
                        if (carrierCompany != null) {
                            snatchGoods.setOfferName(carrierCompany.getFullName());
                        }

                    }
                }


            }


        }


        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Transactional(readOnly = true)
    @Override
    public WaybillPlan loadWaybillPlan(WaybillParamsDto dto) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        if(dto.getCompanyId()!=null) {
            tMap.put("companyId", dto.getCompanyId());
        }
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan == null) {
            throw new WaybillPlanException("计划不存在！");
        }
        return waybillPlan;
    }


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
    public Integer ownPlanCancel(Long waybillPlanId, Long companyId, User user) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",waybillPlanId);
        tMap.put("companyId",companyId);
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
        waybillPlan.setCancelDate(new Date());
        waybillPlan.setCancelMan(user.getRealName());
        waybillPlanMapper.updateByPrimaryKey(waybillPlan);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan biddingFinish(Long waybillPlanId, Long companyId, User user) {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",waybillPlanId);
        tMap.put("companyId",companyId);
        tMap.put("isDeleted","0");
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(tMap);
        if (waybillPlan==null) throw new WaybillPlanException("计划不存在！");
        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //竞价结束派单中
       // waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_CANCEL);
        waybillPlan.setUpdateTime(new Date());
        waybillPlan.setUpdateId(user.getUserId());
        waybillPlan.setUpdateName(user.getRealName());
        waybillPlanMapper.updateByPrimaryKey(waybillPlan);
        return waybillPlan;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePlanStatusByWaybill(WaybillPlan waybillPlan) {
       return waybillPlanMapper.updateWaybillPlan(waybillPlan);
    }


}

