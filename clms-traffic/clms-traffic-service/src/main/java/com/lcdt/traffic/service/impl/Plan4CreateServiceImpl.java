package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.Timeline;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.NotifyUtils;
import com.lcdt.traffic.service.*;
import com.lcdt.traffic.util.PlanBO;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import io.swagger.annotations.ApiModelProperty;
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
public class Plan4CreateServiceImpl implements Plan4CreateService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单详细

    @Autowired
    private TransportWayItemsMapper transportWayItemsMapper;

    @Reference
    private CustomerRpcService customerRpcService;  //客户信息

    @Reference
    private CompanyRpcService companyRpcService; //企业信息


    @Autowired
    private WaybillService waybillService; //运单

    @Autowired
    private ClmsNotifyProducer producer;

    @Autowired
    private OwnDriverService ownDriverService;

    @Autowired
    private TrafficRpc trafficRpc;

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
        vo.setPubdate(new Date());//发布时间
        try {
            if (!StringUtils.isEmpty(dto.getBidingStart())) { //竞价开始
                vo.setBidingStart(DateUtility.string2Date(dto.getBidingStart(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getBidingEnd())) { //结束
                vo.setBidingEnd(DateUtility.string2Date(dto.getBidingEnd(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getStartDate())) { //起运时间
                vo.setStartDate(DateUtility.string2Date(dto.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.isEmpty(dto.getArriveDate())) { //时间
                vo.setArriveDate(DateUtility.string2Date(dto.getArriveDate(),"yyyy-MM-dd HH:mm:ss"));
            }
        } catch (ParseException e) {
            throw new RuntimeException("竞价/起运时间、送达有误！");
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


        //客戶部分
        if (vo.getCustomerId() == null && !StringUtils.isEmpty(vo.getCustomerName())) {
            Map customerMap = new HashMap<String,String>();
            customerMap.put("customerType","3"); //运输客户
            customerMap.put("customerName",vo.getCustomerName()); //运输客户
            customerMap.put("companyId",dto.getCompanyId());
            customerMap.put("province",dto.getReceiveProvince());
            customerMap.put("city",dto.getReceiveCity());
            customerMap.put("county",dto.getReceiveCounty());
            customerMap.put("details",dto.getReceiveAddress());
            customerMap.put("linkMan",dto.getReceiveMan());
            customerMap.put("mobile",dto.getReceivePhone());
            customerMap.put("userId",dto.getCreateId());
            customerMap.put("userName",dto.getCreateName());
            Customer customer = customerRpcService.createCustomer(customerMap);
            if (customer!=null) {
                customer.setCustomerId(customer.getCustomerId());
            }
        }




        //具体业务处理
        if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI)) { //直派
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商
                if (!StringUtils.isEmpty(dto.getCarrierIds())) {
                    String carrierId = dto.getCarrierIds(); //承运商ID（如果是承运商只存在一个）
                    Customer customer = customerRpcService.findCustomerById(Long.valueOf(carrierId));
                    vo.setCarrierCompanyId(customer.getBindCpid());
                    vo.setCarrierCompanyName(customer.getBindCompany()); //绑定企业
                } else {
                    vo.setCarrierType(ConstantVO.PLAN_CARRIER_TYPE_ELSE); //没选承运人的情况下
                }
                planDirectProcedure(vo, dto,  flag, (short)1);
            } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机


                //司机、车辆
                if (StringUtils.isEmpty(dto.getCarrierIds())) { //如果没有选司机
                    OwnDriver ownDriver = new OwnDriver();
                    ownDriver.setCompanyId(dto.getCompanyId());
                    ownDriver.setDriverName(dto.getCarrierNames());
                    ownDriver.setDriverPhone(dto.getCarrierPhone());
                    ownDriver.setCreateId(dto.getCreateId());
                    ownDriver.setCreateName(dto.getCreateName());
                    OwnDriver ownDriver1 = trafficRpc.addDriver(ownDriver);
                    if(ownDriver1!=null) {
                        vo.setCarrierIds(ownDriver1.getOwnDriverId() + "");
                    }

                }

                OwnVehicle ownVehicle = new OwnVehicle();
                ownVehicle.setVehicleNum(dto.getCarrierVehicle());
                ownVehicle.setCompanyId(dto.getCompanyId());
                ownVehicle.setCreateId(dto.getCreateId());
                ownVehicle.setCreateName(dto.getCreateName());
                ownVehicle.setVehicleDriverPhone(dto.getCarrierPhone());
                trafficRpc.addVehicle(ownVehicle);

                if (!StringUtils.isEmpty(dto.getCarrierIds())) {
                    vo.setCarrierCompanyId(vo.getCompanyId());
                    vo.setCarrierCompanyName(dto.getCompanyName());
                } else {
                    vo.setCarrierType(ConstantVO.PLAN_CARRIER_TYPE_ELSE);//没选承运人的情况下
                }
                planDirectProcedure(vo, dto,  flag,(short)2);
            } else { //其它（发布后派单）
                onlyCreateWaybillPlan(vo,dto,flag);
            }

        } else  if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_JINGJIA)) { //竞价
                if (flag==1) { //发布--操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_BIDDING); //竞价中
                } else { //暂存--操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //计划状态(待发布)
                }
                 vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //车状态(其它)
                 waybillPlanMapper.insert(vo); //生成计划
                 createTransportWayItems(dto, vo);//批量创建栏目
                 StringBuffer sb_goods = new StringBuffer(); //货物发送明细
                 StringBuffer sb_goods1 = new StringBuffer();
                 List<PlanDetail> planDetailList = dto.getPlanDetailList();
                  if (null!=planDetailList && planDetailList.size()>0) {
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
                        sb_goods.append(obj.getGoodsName()+":"+obj.getPlanAmount()+";");
                        sb_goods1.append(obj.getGoodsName()+":"+obj.getPlanAmount()+" "+obj.getUnit()+";");
                    }
                    planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
                }



              if (flag==1) { //发布--操作
                /***
                 * 发送消息:
                 *   就是新建计划，选择竞价计划，点击发布
                 */
                if (!StringUtils.isEmpty(dto.getCarrierCollectionIds()) || !StringUtils.isEmpty(dto.getCarrierDriverIds())) {
                    Map serialCodeMap = new HashMap();
                    serialCodeMap.put("waybillPlanId",vo.getWaybillPlanId());
                    WaybillPlan tWaybillPlan = waybillPlanMapper.selectByPrimaryKey(serialCodeMap);
                    String companyName = dto.getCompanyName(); // 货主企业
                    String serialCode = tWaybillPlan.getSerialCode(); //流水号
                    String sendAddress = vo.getSendProvince()+" "+vo.getSendCity()+" "+vo.getSendCounty()+" "+vo.getSendAddress();
                    String receiveAddress = vo.getReceiveProvince()+" "+vo.getReceiveCity()+" "+vo.getReceiveCounty()+" "+vo.getReceiveAddress();
                    DefaultNotifySender defaultNotifySender = NotifyUtils.notifySender(dto.getCompanyId(), dto.getCreateId()); //发送

                    if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) { //承运商(获取承运商ID)
                        String[] ids = null;
                        StringBuffer sb = new StringBuffer();
                        if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CARRIER)) {
                            ids = dto.getCarrierCollectionIds().split(","); //竞价组ID;
                            //承运商(获取承运商ID)
                            //竞价组内的客户->客户绑定的企业->企业的创建者
                            if (ids!=null && ids.length>0) {
                                sb.append("(");
                                for(int i=0;i<ids.length;i++) {
                                    sb.append(" find_in_set('"+ids[i]+"',collection_ids)");
                                    if(i!=ids.length-1){
                                        sb.append(" or ");
                                    }
                                }
                                sb.append(")");
                            }

                        } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) {
                            //司机
                        }

                        Map map = new HashMap();
                        if (!sb.toString().isEmpty()) {
                            map.put("collectionIds", sb.toString());
                            map.put("companyId",dto.getCompanyId());
                            map.put("bindCpid","111");//标识绑定企业ID不为空的企业（承运商对应的所有绑定企业）
                            List<Customer> customerList = customerRpcService.findBindCompanyIds(map);
                            if (null!=customerList && customerList.size()>0) {
                                for (Customer customer: customerList) {  //遍历客户，查询对应企业，进行发送
                                    Long bindCompId = customer.getBindCpid(); //绑定企业ID;
                                    Company company = companyRpcService.findCompanyByCid(bindCompId);
                                    if (null != company) {
                                        User user = companyRpcService.findCompanyCreate(company.getCompId());
                                        if (user!=null) {
                                            DefaultNotifyReceiver defaultNotifyReceiver = NotifyUtils.notifyCarrierReceiver(company.getCompId(),user.getUserId(),user.getPhone()); //接收
                                            CommonAttachment attachment = new CommonAttachment();
                                            attachment.setOwnerCompany(companyName);
                                            attachment.setPlanSerialNum(serialCode);
                                            attachment.setOriginAddress(sendAddress);
                                            attachment.setDestinationAdress(receiveAddress);
                                            attachment.setGoodsDetail(sb_goods.toString());
                                            attachment.setCarrierWebNotifyUrl("");//客户计划列表，按流水号查询
                                            TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("plan_publish", attachment, defaultNotifyReceiver, defaultNotifySender);
                                            producer.sendNotifyEvent(plan_publish_event);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机else
                             List<OwnDriver> driverList = ownDriverService.driverListByGroupId(dto.getCompanyId(),dto.getCarrierDriverIds());
                            if (null!=driverList && driverList.size()>0) {
                                for (OwnDriver driver: driverList) {  //遍历客户，查询对应企业，进行发送
                                    DefaultNotifyReceiver defaultNotifyReceiver = NotifyUtils.notifyDriverReceiver(driver.getDriverPhone()); //接收
                                    CommonAttachment attachment = new CommonAttachment();
                                    attachment.setOwnerCompany(companyName);
                                    attachment.setPlanSerialNum(serialCode);
                                    attachment.setOriginAddress(sendAddress);
                                    attachment.setDestinationAdress(receiveAddress);
                                    attachment.setGoodsDetail(sb_goods.toString());
                                    TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("plan_publish", attachment, defaultNotifyReceiver, defaultNotifySender);
                                    producer.sendNotifyEvent(plan_publish_event);
                                }
                            }
                        }
                }

                //router:发布
                Timeline event = new Timeline();
                event.setActionTitle("【计划发布】（操作人："+dto.getCompanyName()==null?"":dto.getCompanyName()+" "+vo.getCreateName()==null?"":vo.getCreateName()+"）");
                event.setActionTime(new Date());
                event.setCompanyId(vo.getCompanyId());
                event.setSearchkey("R_PLAN");
                event.setDataid(vo.getWaybillPlanId());
                producer.noteRouter(event);
            }
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
        if (!StringUtils.isEmpty(dto.getCarrierIds())) { //指定承运商
            if (dto.getIsApproval()==0) { //不需要审批
                if (flag==1) { //发布--操作
                    if(carrierType==1) { //承运商
                        vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                        vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_DOING);//派车状态(派车中)
                    } else { //司机
                        vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_OFF); //计划状态(已派完)
                        vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_COMPLETED);//派车状态(已派车)
                    }
                    waybillPlanMapper.insert(vo);
                    Map map = new HashMap();
                    map.put("waybillPlanId",vo.getWaybillPlanId());
                    WaybillPlan tWaybillPlan = waybillPlanMapper.selectByPrimaryKey(map);
                    vo.setSerialCode(tWaybillPlan.getSerialCode());
                    createTransportWayItems(dto, vo);//批量创建栏目

                    List<PlanDetail> planDetailList = dto.getPlanDetailList();
                    StringBuffer sb_goods = new StringBuffer(); //货物发送明细
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
                        if (obj.getFreightPrice()!=null) { //运费总价 = 单价 * 数量
                            obj.setFreightTotal(obj.getFreightPrice()*obj.getPlanAmount());
                        }
                        sb_goods.append(obj.getGoodsName()+":"+obj.getAllotAmount()+";"); //发送消息

                    }
                    planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
                    SplitGoods splitGoods = new SplitGoods(); //派单
                    splitGoods.setWaybillPlanId(vo.getWaybillPlanId());
                    splitGoods.setSplitRemark("计划直接生成的...");
                    splitGoods.setCreateId(vo.getCreateId());
                    splitGoods.setCreateName(vo.getCreateName());
                    splitGoods.setCreateDate(new Date());
                    splitGoods.setUpdateId(vo.getUpdateId());
                    splitGoods.setUpdateName(vo.getCreateName());
                    splitGoods.setUpdateTime(vo.getCreateDate());
                    splitGoods.setCompanyId(vo.getCompanyId());
                    splitGoods.setCarrierCompanyName(vo.getCarrierCompanyName());
                    splitGoods.setCarrierCompanyId(vo.getCarrierCompanyId());
                    splitGoods.setIsDeleted((short)0);
                    splitGoods.setCarrierCollectionIds(vo.getCarrierIds());
                    splitGoods.setCarrierCollectionNames(vo.getCarrierNames());
                    splitGoods.setCarrierPhone(vo.getCarrierPhone());
                    splitGoods.setCarrierVehicle(vo.getCarrierVehicle());
                    splitGoodsMapper.insert(splitGoods);
                    List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
                    for (PlanDetail obj : planDetailList) {
                        SplitGoodsDetail tObj = new SplitGoodsDetail();
                        tObj.setSplitGoodsId(splitGoods.getSplitGoodsId());
                        tObj.setPlanDetailId(obj.getPlanDetailId());
                        tObj.setAllotAmount(obj.getPlanAmount()); //派单数量
                        if (carrierType == ConstantVO.PLAN_CARRIER_TYPE_DRIVER) { //如果司机的话为0
                            tObj.setRemainAmount((float)0); //本次剩余
                        } else {
                            tObj.setRemainAmount(obj.getPlanAmount()); //本次剩余
                        }
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
                        waybillDto.setWaybillCode(vo.getSerialCode()); //流水号
                        waybillDto.setCarrierCompanyId(vo.getCarrierCompanyId());
                        waybillDto.setCarrierCompanyName(vo.getCarrierCompanyName());


                        waybillDto.setCreateId(vo.getCreateId());
                        waybillDto.setCreateName(vo.getCreateName());
                        waybillDto.setDriverPhone(vo.getCarrierPhone());
                        waybillDto.setVechicleNum(vo.getCarrierVehicle());
                        waybillDto.setWaybillRemark(vo.getPlanRemark());

                        if(!StringUtils.isEmpty(vo.getCarrierIds())) {
                            waybillDto.setDriverName(vo.getCarrierNames());
                            waybillDto.setDriverId(Long.valueOf(vo.getCarrierIds()));
                            waybillDto.setDriverPhone(vo.getCarrierPhone());
                        }
                        waybillDto.setCarrierCompanyId(vo.getCarrierCompanyId());
                        PlanBO.getInstance().toWaybillItemsDto(vo,splitGoods,waybillDto,planDetailList,splitGoodsDetailList);
                        if (null!=waybillDto) {
                            waybillService.addWaybill(waybillDto);
                        }
                        //如果生成运单触发消息机制
                        String receiveAddress = vo.getReceiveProvince()+" "+vo.getReceiveCity()+" "+vo.getReceiveCounty()+" "+vo.getReceiveAddress();

                        if (!StringUtils.isEmpty(splitGoods.getCarrierPhone())) {
                            Company company = companyRpcService.findCompanyByCid(vo.getCompanyId()); //货主企业
                            DefaultNotifySender defaultNotifySender = NotifyUtils.notifySender(vo.getCompanyId(), vo.getCreateId()); //发送

                            //接收对象
                            DefaultNotifyReceiver defaultNotifyReceiver = new DefaultNotifyReceiver();
                            defaultNotifyReceiver.setCustomerPhoneNum(vo.getCustomerPhone());//合同客户电话
                            defaultNotifyReceiver.setReceivePhoneNum(vo.getReceivePhone());//司机

                            CommonAttachment attachment = new CommonAttachment();
                            attachment.setOwnerCompany(company.getFullName()); //货主公司
                            attachment.setDestinationAdress(receiveAddress);
                            attachment.setGoodsDetail(sb_goods.toString());
                            attachment.setVehicleNum(vo.getCarrierVehicle()); //车辆
                            attachment.setDriverName(vo.getCarrierCollectionNames());//司机名
                            attachment.setDriverPhone(vo.getCarrierPhone()); //司机手机

                            TrafficStatusChangeEvent plan_publish_event = new TrafficStatusChangeEvent("bill_to_driver", attachment, defaultNotifyReceiver, defaultNotifySender);
                            producer.sendNotifyEvent(plan_publish_event);
                        }
                    }

                } else { //暂存 -- 操作
                    vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //待发布
                    vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //其它
                    waybillPlanMapper.insert(vo); //生成计划
                    createTransportWayItems(dto, vo);//批量创建栏目

                    List<PlanDetail> planDetailList = dto.getPlanDetailList();
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
                createTransportWayItems(dto, vo);//批量创建栏目
                List<PlanDetail> planDetailList = dto.getPlanDetailList(); //new ArrayList<PlanDetail>();
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
                }
                if (planDetailList!=null && planDetailList.size()>0) {
                    planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
                }
            }
        } else { //未指定承运商(只生成计划)
            onlyCreateWaybillPlan(vo,dto,flag);
        }
    }


    /***
     * 未指定承运商/竞价
     *
     * @param vo -- 需要保存的计划
     * @param dto -- 前端传来的计划参数
     * @param flag -- 操作动作(1-发布，2-暂存)
     */
    private void onlyCreateWaybillPlan(WaybillPlan vo, WaybillParamsDto dto,short flag) {
       // if (dto.getIsApproval()==0) { //不需要审批
     //   if (flag==1) { //发布--操作
            vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //计划状态(派单中)
            vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
/*        } else { //暂存--操作
            vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //计划状态(待发布)
            vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //车状态(其它)
        }*/
/*        } else { // 需要审批
            if (flag==1) { //发布--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_APPROVAL); //计划状态(审批)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE);//车状态(其它)
            } else { //暂存--操作
                vo.setPlanStatus(ConstantVO.PLAN_STATUS_WAITE＿PUBLISH); //计划状态(待发布)
                vo.setSendCardStatus(ConstantVO.PLAN_SEND_CARD_STATUS_ELSE); //车状态(其它)
            }
        }*/
        waybillPlanMapper.insert(vo); //生成计划
        createTransportWayItems(dto, vo);//批量创建栏目
        List<PlanDetail> planDetailList = dto.getPlanDetailList();
        if (null!=planDetailList && planDetailList.size()>0) {
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
            }
            planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
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
