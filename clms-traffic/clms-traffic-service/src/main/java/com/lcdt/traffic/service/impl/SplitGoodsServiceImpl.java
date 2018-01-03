package com.lcdt.traffic.service.impl;

import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.PlanDetailMapper;
import com.lcdt.traffic.dao.SplitGoodsDetailMapper;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.exception.SplitGoodsException;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.model.SplitGoodsDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.SplitGoodsDetailParamsDto;
import com.lcdt.traffic.web.dto.SplitGoodsParamsDto;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/21.
 */
@Service
public class SplitGoodsServiceImpl implements SplitGoodsService {

    Logger logger = LoggerFactory.getLogger(SplitGoodsServiceImpl.class);

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


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer splitGoods4Direct(SplitGoodsParamsDto dto, User user, Long companyId) {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), companyId, (short)0); //查询对应的计划
        if (waybillPlan == null) throw new SplitGoodsException("计划异常为空！");
        List<PlanDetail> planDetailList =  waybillPlan.getPlanDetailList();
        if (planDetailList!=null && planDetailList.size()>0) {
            //检查是否允许派单
            statAllotAmount(planDetailList, dto);
            StringBuffer sb = new StringBuffer();
            for (PlanDetail planDetail : planDetailList) {
                 if (!planDetail.isAllot()) {
                    sb.append("商品："+planDetail.getGoodsName()+"，规格："+planDetail.getGoodsSepc()+
                              "，剩余数量："+planDetail.getRemainderAmount()+"，派单数量:"+planDetail.getAllotAmountTotal()+"，不能派！\\n");
                 }
            }
            if (!sb.toString().isEmpty()) { //派单未通过
                throw new SplitGoodsException(sb.toString());
            }

            Date opDate = new Date();
            SplitGoods splitGoods = new SplitGoods(); //派单主信息
            BeanUtils.copyProperties(dto, splitGoods);
            splitGoods.setCreateDate(opDate);
            splitGoods.setCreateId(user.getUserId());
            splitGoods.setCreateName(user.getRealName());
            splitGoods.setUpdateId(user.getUserId());
            splitGoods.setUpdateName(user.getRealName());
            splitGoods.setUpdateTime(opDate);
            splitGoods.setIsDeleted((short)0);
            splitGoods.setCompanyId(companyId);

            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)
                String carrierId = dto.getCarrierCollectionIds(); //承运商ID（如果是承运商只存在一个）
                Customer customer = customerRpcService.findCustomerById(Long.valueOf(carrierId));
                splitGoods.setCarrierCompanyId(customer.getCompanyId());
            }
            //承运商---司机
            splitGoodsMapper.insert(splitGoods);
            for (PlanDetail planDetail : planDetailList) {
                 if (planDetail.getSplitGoodsDetailObj()!=null) { //说明要分配
                     if (planDetail.isAllot()) { //允许分配
                         //更新计划数
                         planDetail.setRemainderAmount(planDetail.getRemainderAmount() - planDetail.getAllotAmountTotal());
                         planDetail.setUpdateId(user.getUserId());
                         planDetail.setUpdateTime(opDate);
                         planDetail.setUpdateName(user.getRealName());
                         planDetailMapper.updateByPrimaryKey(planDetail);

                         //插入分配明细
                         SplitGoodsDetail splitGoodsDetail = new SplitGoodsDetail();
                         SplitGoodsDetailParamsDto splitGoodsDetailParamsDto =  (SplitGoodsDetailParamsDto)planDetail.getSplitGoodsDetailObj();
                         BeanUtils.copyProperties(splitGoodsDetailParamsDto, splitGoodsDetail);
                         splitGoodsDetail.setCreateId(user.getUserId());
                         splitGoodsDetail.setCreateName(user.getRealName());
                         splitGoodsDetail.setCreateDate(opDate);
                         splitGoodsDetail.setUpdateId(user.getUserId());
                         splitGoodsDetail.setUpdateName(user.getRealName());
                         splitGoodsDetail.setUpdateTime(opDate);
                         splitGoodsDetail.setIsDeleted((short)0);
                         splitGoodsDetail.setCompanyId(companyId);
                         splitGoodsDetail.setSplitGoodsId(splitGoods.getSplitGoodsId());
                         splitGoodsDetailMapper.insert(splitGoodsDetail);
                     }
                 }

            }
            //更新计划状态
            //再次重新拉取判断是否存在剩余
            Map map = new HashMap<String,String>();
            map.put("isDeleted",0);
            map.put("companyId",companyId);
            map.put("waybillPlanId",waybillPlan.getWaybillPlanId());
            List<PlanDetail> list  = planDetailMapper.selectByCondition(map);
            if (list!=null && list.size()>0) {
                float remainAmount  = 0; //剩余
                for (PlanDetail tobj : list) {
                    remainAmount += tobj.getRemainderAmount(); //所有剩余合计
                }
                if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                    if (remainAmount>0) { //还未派完
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    } else {
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    }

                }
                if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机
                    if (remainAmount>0) { //还未派完
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    } else {
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED); //计划状态(已派完)
                        waybillPlan.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//派车状态(已派完)
                    }

                    /***********这块要生成运单**********/



                }

            }
            waybillPlan.setUpdateId(user.getUserId());
            waybillPlan.setUpdateName(user.getRealName());
            waybillPlan.setUpdateTime(new Date());
            waybillPlanMapper.updateByPrimaryKey(waybillPlan);
        } else {
            throw new SplitGoodsException("计划详细为空！");
        }
        return null;
    }


    /***
     * 统计待分配计划
     * @param planDetailList
     * @param dto
     */
    private void statAllotAmount(List<PlanDetail> planDetailList, SplitGoodsParamsDto dto) {
        for (PlanDetail planDetail : planDetailList) {
            float allotAmountTotal = 0; //分配总数量
            List<SplitGoodsDetailParamsDto> list = dto.getList();
            SplitGoodsDetailParamsDto  tempObj = null;
            for (SplitGoodsDetailParamsDto obj1 : list) {
                    if (planDetail.getPlanDetailId()==obj1.getPlanDetailId()) {
                        allotAmountTotal += obj1.getAllotAmount(); //统计分配数量
                        tempObj = obj1;
                        break; //因为分配只有一种
                    }
            }
            if (tempObj!=null) {
                planDetail.setSplitGoodsDetailObj(tempObj);
            }
            planDetail.setAllotAmountTotal(allotAmountTotal);
            if (planDetail.getRemainderAmount()>=allotAmountTotal) { //如果剩余数量>=派单数量
                planDetail.setAllot(true);
            } else {
                planDetail.setAllot(false);
            }
        }
    }












}

