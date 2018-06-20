package com.lcdt.traffic.util;

import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.dto.WaybillItemsDto;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.SplitGoods;
import com.lcdt.traffic.model.SplitGoodsDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.vo.ConstantVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangbinq on 2018/1/4.
 */
public class PlanBO {

    private static PlanBO instance;

    private PlanBO() {
    }

    public synchronized static PlanBO getInstance() {
        if (instance == null) {
            instance = new PlanBO();
        }
        return instance;
    }

    public void converPlan2Waybill(WaybillPlan waybillPlan, SplitGoods splitGoods,WaybillDto waybillDto) {
        waybillDto.setWaybillPlanId(waybillPlan.getWaybillPlanId());//计划ID
        if(splitGoods!=null) waybillDto.setSplitGoodsId(splitGoods.getSplitGoodsId());
        waybillDto.setWaybillStatus(ConstantVO.WAYBILL_STATUS_WATIE_SEND);
        waybillDto.setGroupId(waybillPlan.getGroupId());
        waybillDto.setGroupName(waybillPlan.getGroupName());
        waybillDto.setCustomerName(waybillPlan.getCustomerName());
        waybillDto.setCustomerPhone(waybillPlan.getCustomerPhone());
        waybillDto.setCustContactMan(waybillPlan.getCustContactMan());
        waybillDto.setSendMan(waybillPlan.getSendMan());
        waybillDto.setSendPhone(waybillPlan.getSendPhone());
        waybillDto.setSendProvince(waybillPlan.getSendProvince());
        waybillDto.setSendCity(waybillPlan.getSendCity());
        waybillDto.setSendCounty(waybillPlan.getSendCounty());
        waybillDto.setSendAddress(waybillPlan.getSendAddress());
        waybillDto.setReceiveMan(waybillPlan.getReceiveMan());
        waybillDto.setReceiveProvince(waybillPlan.getReceiveProvince());
        waybillDto.setReceiveCity(waybillPlan.getReceiveCity());
        waybillDto.setReceiveCounty(waybillPlan.getReceiveCounty());
        waybillDto.setReceivePhone(waybillPlan.getReceivePhone());
        waybillDto.setReceiveAddress(waybillPlan.getReceiveAddress());
        waybillDto.setTransportWay(waybillPlan.getTransportWay());//运输方式
        waybillDto.setGoodsType(waybillPlan.getGoodsType());
        waybillDto.setStartDate(waybillPlan.getStartDate());
        waybillDto.setArriveDate(waybillPlan.getArriveDate());
        waybillDto.setIsReceipt(waybillPlan.getIsReceipt());
        waybillDto.setIsUrgent(waybillPlan.getIsUrgent());
        waybillDto.setDistributionWay(waybillPlan.getDistributionWay());//     0-上门装货1-送货到站3-送货上门4-到站自体5-加急
        waybillDto.setAttachment1(waybillPlan.getAttachment1());
        waybillDto.setAttachment2(waybillPlan.getAttachment2());
        waybillDto.setAttachment3(waybillPlan.getAttachment3());
        waybillDto.setAttachment4(waybillPlan.getAttachment4());
        waybillDto.setAttachment5(waybillPlan.getAttachment5());
        waybillDto.setAttachment1Name(waybillPlan.getAttachment1Name());
        waybillDto.setAttachment2Name(waybillPlan.getAttachment2Name());
        waybillDto.setAttachment3Name(waybillPlan.getAttachment3Name());
        waybillDto.setAttachment4Name(waybillPlan.getAttachment4Name());
        waybillDto.setAttachment5Name(waybillPlan.getAttachment5Name());
        waybillDto.setCompanyId(waybillPlan.getCompanyId());

    }


    /**
     * 计划至运单转化(全部派完的)
     *
     * @param waybillPlan --计划
     * @param waybillPlan --派单
     * @param planDetailList --计划详细
     * @param splitGoodsDetailList -- 派单详细
     * @return
     */
    public WaybillDto toWaybillItemsDto(WaybillPlan waybillPlan, SplitGoods splitGoods,WaybillDto waybillDto , List<PlanDetail> planDetailList, List<SplitGoodsDetail> splitGoodsDetailList) {
        converPlan2Waybill(waybillPlan, splitGoods, waybillDto);
        List<WaybillItemsDto> waybillItemsDtos = new ArrayList<WaybillItemsDto>();
        Date dt = new Date();
        for (PlanDetail obj :planDetailList) {
            WaybillItemsDto tempDto = new WaybillItemsDto();
            tempDto.setCompanyId(waybillDto.getCompanyId());
            tempDto.setCreateDate(dt);
            tempDto.setCreateId(waybillDto.getCreateId());
            tempDto.setCreateName(waybillDto.getCreateName());
            tempDto.setGoodsId(obj.getGoodsId());
            tempDto.setGoodsName(obj.getGoodsName());
            tempDto.setGoodsSpec(obj.getGoodsSpec());
            tempDto.setSubGoodsId(obj.getSubGoodsId());
            tempDto.setUnit(obj.getUnit());
           // tempDto.setAmount(obj.getPlanAmount()); //派单数量
            tempDto.setFreightPrice(obj.getFreightPrice());
            tempDto.setFreightTotal(obj.getFreightTotal()); //应收
            tempDto.setPayPrice(obj.getPayPrice());
            tempDto.setPayTotal(obj.getPayTotal()); //应付

            tempDto.setRemark(obj.getDetailRemark());//备注
            tempDto.setPlanDetailId(obj.getPlanDetailId());//计划主ID
            waybillItemsDtos.add(tempDto);
        }
        for (SplitGoodsDetail obj : splitGoodsDetailList) {
            for (WaybillItemsDto waybillItemsDto : waybillItemsDtos) {
                if(obj.getPlanDetailId().equals(waybillItemsDto.getPlanDetailId())) {
                    waybillItemsDto.setSplitGoodsDetailId(obj.getSplitGoodsDetailId());
                    waybillItemsDto.setAmount(obj.getAllotAmount());//司机的话全部派完
                }
            }
        }
        waybillDto.setWaybillItemsDtoList(waybillItemsDtos);
        return waybillDto;
    }


    public  String toString(String arg) {

        if(arg==null || arg.equals("null"))
            return "";
        else
            return "";
    }



}
