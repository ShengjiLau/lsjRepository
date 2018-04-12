package com.lcdt.traffic.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.Plan4EditService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tl.commons.util.DateUtility;

import java.text.ParseException;
import java.util.*;

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


    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;





    @Transactional(rollbackFor = Exception.class)
    @Override
    public WaybillPlan waybillPlanEdit(WaybillParamsDto dto, short flag) throws WaybillPlanException {
        Map tMap = new HashMap<String,String>();
        tMap.put("waybillPlanId",dto.getWaybillPlanId());
        tMap.put("companyId",dto.getCompanyId());
        tMap.put("isDeleted","0");
        WaybillPlan vo = waybillPlanMapper.selectByPrimaryKey(tMap);
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
            throw new RuntimeException("竞价/起运时间、送达有误！");
        }

        if (!StringUtils.isEmpty(dto.getCarrierCollectionIds())) {
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)
                String carrierId = dto.getCarrierCollectionIds(); //承运商ID（如果是承运商只存在一个）
                Customer customer = customerRpcService.findCustomerById(Long.valueOf(carrierId));
                vo.setCarrierCompanyId(customer.getBindCpid());
            } else { // 司机
                vo.setCarrierCompanyId(vo.getCompanyId()); //获取下的本企业司机
            }
        }

        if (!StringUtils.isEmpty(dto.getAttachement())) { //附件信息处理
            JSONArray jsonArray = JSONArray.parseArray(dto.getAttachement());
            List<WaybillParamsDto> waybillParamsDtoList = jsonArray.toJavaList(WaybillParamsDto.class);
            for (WaybillParamsDto dto1 : waybillParamsDtoList) {
                vo.setAttachment1(dto1.getAttachment1());
                vo.setAttachment1Name(dto1.getAttachment1Name());
                vo.setAttachment2(dto1.getAttachment2());
                vo.setAttachment2Name(dto1.getAttachment2Name());
                vo.setAttachment3(dto1.getAttachment3());
                vo.setAttachment3Name(dto1.getAttachment3Name());
                vo.setAttachment4(dto1.getAttachment4());
                vo.setAttachment4Name(dto1.getAttachment4Name());
                vo.setAttachment5(dto1.getAttachment5());
                vo.setAttachment5Name(dto1.getAttachment5Name());
            }
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
                    createTransportWayItems(dto, vo);//批量创建栏目

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
                    splitGoods.setUpdateId(vo.getUpdateId());
                    splitGoods.setUpdateName(vo.getCreateName());
                    splitGoods.setUpdateTime(new Date());
                    splitGoods.setCompanyId(vo.getCompanyId());
                    splitGoods.setCarrierCompanyId(vo.getCarrierCompanyId());
                    splitGoods.setCarrierCollectionIds(vo.getCarrierCollectionIds());
                    splitGoods.setCarrierCollectionNames(vo.getCarrierCollectionNames());
                    splitGoods.setCarrierPhone(vo.getCarrierPhone());
                    splitGoods.setCarrierVehicle(vo.getCarrierVehicle());
                    splitGoods.setIsDeleted((short)0);
                    splitGoodsMapper.insert(splitGoods);

                    List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
                    StringBuffer msgSb = new StringBuffer(); //货物发送明细
                    for (PlanDetail obj : planDetailList) {
                        SplitGoodsDetail tObj = new SplitGoodsDetail();
                        tObj.setPlanDetailId(obj.getPlanDetailId());
                        tObj.setAllotAmount(obj.getPlanAmount()); //派单数量
                        if (carrierType == ConstantVO.PLAN_CARRIER_TYPE_DRIVER) { //如果司机的话为0
                            tObj.setRemainAmount((float)0); //本次剩余
                        } else {
                            tObj.setRemainAmount(obj.getPlanAmount()); //本次剩余
                        }
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
                        msgSb.append(obj.getGoodsName()+":"+obj.getPlanAmount()+";");
                    }
                    splitGoodsDetailMapper.batchAddSplitGoodsDetail(splitGoodsDetailList);

                    /***
                     *如果是司机需要生成运单
                     */
                    if (carrierType == ConstantVO.PLAN_CARRIER_TYPE_DRIVER) {
                        WaybillDto waybillDto = new WaybillDto();
                        waybillDto.setWaybillCode(vo.getSerialCode()); //流水号
                        PlanBO.getInstance().toWaybillItemsDto(vo,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
                        if (null!=waybillDto) {
                            waybillService.addWaybill(waybillDto);
                        }
                    }


                    /***
                     * 发送消息:
                     *   就是新建计划，选择竞价计划，点击发布
                     */
                    if (!StringUtils.isEmpty(dto.getCarrierCollectionIds())) {
                        String companyName = dto.getCompanyName(); // 货主企业
                        String serialCode = vo.getSerialCode(); //流水号
                        String sendAddress = vo.getSendProvince()+" "+vo.getSendCity()+" "+vo.getSendCounty();
                        String receiveAddress = vo.getReceiveProvince()+" "+vo.getReceiveCity()+" "+vo.getReceiveCounty();
                        if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)

                        } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机else

                        }
                    }

                } else { //暂存 -- 操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                    vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                    waybillPlanMapper.updateWaybillPlan(vo); //生成计划
                    createTransportWayItems(dto, vo);//批量创建栏目

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
                createTransportWayItems(dto, vo);//批量创建栏目

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
        createTransportWayItems(dto, vo);//批量创建栏目

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
        createTransportWayItems(dto, vo);//批量创建栏目

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
     * 创建运输入方式
     */
    private void createTransportWayItems(WaybillParamsDto dto,WaybillPlan vo) {
        if (dto.getTransportWayItemsList()!=null && dto.getTransportWayItemsList().size()>0) {
            transportWayItemsMapper.deleteByWaybillPlanId(vo.getWaybillPlanId());//删除原有的运输入方式
            for (TransportWayItems item : dto.getTransportWayItemsList()) {
                item.setWaybillPlanId(vo.getWaybillPlanId());
            }
            transportWayItemsMapper.batchAddTransportWayItems(dto.getTransportWayItemsList());
        }
    }





}
