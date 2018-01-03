package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.CustomerPlanDto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SplitVehicleDto;
import com.lcdt.traffic.exception.WaybillPlanException;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.CustomerPlanService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillItemsDto;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2017/12/27.
 */
@Service
public class CustomerPlanServiceImpl implements CustomerPlanService {

    @com.alibaba.dubbo.config.annotation.Reference
    private CustomerRpcService customerRpcService;  //客户信息

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper; //抢单主

    @Autowired
    private SnatchGoodsDetailMapper snatchGoodsDetailMapper; //抢单

    @Autowired
    private SplitGoodsMapper splitGoodsMapper; //派单主

    @Autowired
    private SplitGoodsDetailMapper splitGoodsDetailMapper; //派单子

    @Autowired
    private WaybillService waybillService; //运单


    /****
     * 1、登录人-指承运人，承运人登录企业ID及登录人所有的组权限；
     * 2、根据上述条件获取登录人对应的客户表中的绑定客户列表；
     * 3、绑定客户条件：绑定ID不为空，客户列表中的企业ID==登录人企业ID
     */
    private List<Customer> bindCustomerList(Map map) {
        map.put("bindCpid","111");//标识绑定企业ID不为空的企业
        List<Customer> customerList = customerRpcService.findBindCompanyIds(map);
        return  customerList;
    }

    /****
     * 客户计划查询公共条件（创建企业ID条件及竞价组条件）
     *
     * Map(bindCompId--客户绑定企业ID,companyId--客户创建企业ID,groupIds--客户组IDs)
     *
     *
     * @return
     */
    private Map  customerPlanSearch4CmpIdGroup(Map map, List<Customer> customerList) {
        Map resultMap = new HashMap();
        if (customerList!=null && customerList.size()>0) { //承运人ID
            /****
             * 1、登录人对应客户列表信息（承运人对应的货主列表信息）；
             * 2、遍历该列表信息，根据客户中绑定企业ID（这里指货主）及创建客户的企业ID（客户本身）;
             * 3、再次遍历客户列表找出客户所对应的竞价组信息
             *
             */
            StringBuffer sb = new StringBuffer();
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            String collectionIds = null;
            sb.append("(");
            for (int i=0;i<customerList.size();i++) {
                Customer tempObj = customerList.get(i);
                Long ownCompanyId = tempObj.getBindCpid(); //承运商绑定客户企业ID
                Long carrierCompanyId = tempObj.getCompanyId(); //当前登录人企业ID
                sb.append(" find_in_set('"+ownCompanyId+"',company_id)"); //创建计划企业ID
                if(i!=customerList.size()-1){
                    sb.append(" or ");
                }

                //查询当前人所在的承运商组
                map.put("companyId",ownCompanyId);
                map.put("bindCompId",carrierCompanyId);//标识绑定企业ID不为空的企业
                List<Customer> customer4GroupList = customerRpcService.findBindCompanyIds(map);
                for (Customer obj1: customer4GroupList) {
                    if (!StringUtils.isEmpty(obj1.getCollectionIds())) {
                        sb1.append(obj1.getCollectionIds()+",");
                    }
                }
            }
            sb.append(")");

            resultMap.put("companyIds",sb.toString()); //计划创建企业

            //竞价组
            collectionIds = sb1.toString().substring(0,sb1.toString().length()-1);
            if (!StringUtils.isEmpty(collectionIds)) {
                sb2.append("(");
                String[] strArrary = collectionIds.split(",");
                for (int i=0; i<strArrary.length; i++) {
                    sb.append(" find_in_set('"+strArrary[i]+"',carrier_collection_ids)"); //竞价组
                    if(i!=customerList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb2.append(")");
                resultMap.put("carrierCollectionIds",sb2.toString()); //竞价组
            }

        }
        return resultMap;
    }


    /***
     * 计划来源
     * @param compId
     * @param customerList
     * @return
     */
    private String planSource(Long compId, List<Customer> customerList) {
        for (Customer customer: customerList) {
            if(customer.getBindCpid()==compId) {
                return customer.getCustomerName();
            }
        }
        return null;
    }



    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Bidding(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlan4Bidding(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
       }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4Offer(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4Offer(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4Pass(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4Offer(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4VehicleDoing(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4VehicleDoing(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4VehicleHave(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4VehicleHave(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4Completed(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4Completed(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo customerPlanList4Cancel(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = customerPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",map.get("companyIds"));
        map.put("carrierCollectionIds",map.get("carrierCollectionIds"));
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
        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4Cancel(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int customerPlanOfferOwn(SnatchOfferDto dto, SnatchGoods snatchGoods) {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
        if (null == waybillPlan) {
            throw new WaybillPlanException("计划不存在！");
        }
        Date dt = new Date();
        snatchGoods.setWaybillPlanId(dto.getWaybillPlanId());
        snatchGoods.setCreateDate(dt);
        snatchGoods.setUpdateTime(dt);
        snatchGoods.setOfferDate(dt);//报价时间
        snatchGoods.setCarrierCollectionIds(waybillPlan.getCarrierCollectionIds()); //承运商组
        snatchGoods.setCarrierCollectionNames(waybillPlan.getCarrierCollectionNames());
        snatchGoods.setGroupId(waybillPlan.getGroupId()); //所属项目组
        snatchGoods.setIsDeleted((short)0);
        snatchGoods.setIsUsing(ConstantVO.SNATCH_GOODS_USING_DOING);
        int flag1 = 1,flag2 =1 ;
        flag1 = snatchGoodsMapper.insert(snatchGoods);

        List<PlanDetail> list = dto.getPlanDetailList();
        if (null != list  && list.size()>0) {
            List<SnatchGoodsDetail> snatchList = new ArrayList<SnatchGoodsDetail>();
            for (PlanDetail obj :list) {
                SnatchGoodsDetail tempObj = new SnatchGoodsDetail();
                tempObj.setSnatchGoodsId(snatchGoods.getSnatchGoodsId());
                tempObj.setPlanDetailId(obj.getPlanDetailId());
                tempObj.setCreateDate(dt);
                tempObj.setOfferPrice(obj.getOfferPrice());
                tempObj.setOfferTotal(obj.getOfferTotal());
                tempObj.setOfferRemark(obj.getOfferRemark());
                tempObj.setCreateId(snatchGoods.getCreateId());
                tempObj.setCreateName(snatchGoods.getCreateName());
                tempObj.setCreateDate(dt);
                tempObj.setUpdateId(snatchGoods.getCreateId());
                tempObj.setUpdateName(snatchGoods.getCreateName());
                tempObj.setUpdateTime(dt);
                tempObj.setIsDeleted((short)0);
                snatchList.add(tempObj);
            }
            flag2 = snatchGoodsDetailMapper.batchAddSnatchGoodsDetail(snatchList);
        }

        return flag1+flag2>1?1:0;

    }

    @Override
    public int customerPlanSplitVehicle(SplitVehicleDto dto, WaybillDto waybillDto) {
        WaybillPlan waybillPlan = waybillPlanMapper.selectByPrimaryKey(dto.getWaybillPlanId(), dto.getCompanyId(), (short)0);
        if (null == waybillPlan) {
            throw new WaybillPlanException("计划不存在！");
        }
        waybillDto.setWaybillPlanId(waybillPlan.getWaybillPlanId());//计划ID
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

        /***
         *
           0-上门装货
           1-送货到站
           3-送货上门
           4-到站自体
           5-加急
         */
        waybillDto.setDistributionWay(waybillPlan.getDistributionWay());
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

        List<PlanDetail> list = dto.getPlanDetailList();
        float sumAmount = 0; //统计本次派车重量
        List<WaybillItemsDto> waybillItemsDtos = new ArrayList<WaybillItemsDto>();
        if (null != list  && list.size()>0) {
            Date dt = new Date();

            for (PlanDetail obj :list) {
                WaybillItemsDto tempDto = new WaybillItemsDto();
                tempDto.setCompanyId(waybillDto.getCompanyId());
                tempDto.setCreateDate(dt);
                tempDto.setCreateId(waybillDto.getCreateId());
                tempDto.setCreateName(waybillDto.getCreateName());
                tempDto.setGoodsId(obj.getGoodsId());
                tempDto.setGoodsName(obj.getGoodsName());
                tempDto.setGoodsSpec(obj.getGoodsSepc());
                tempDto.setSubGoodsId(obj.getSubGoodsId());
                tempDto.setUnit(obj.getUnit());
                sumAmount = sumAmount + obj.getAllotAmount();
                tempDto.setAmount(obj.getAllotAmount()); //派单数量
                tempDto.setFreightPrice(obj.getFreightPrice());
                tempDto.setFreightTotal(obj.getFreightTotal());
                tempDto.setRemark(obj.getDetailRemark());//备注
                waybillItemsDtos.add(tempDto);
            }
        }

        //获取派单表中待派数量(因为竞价派单是一次生派单，但可以多次派车)
        SplitGoods splitGoods = splitGoodsMapper.selectByPrimaryKey(dto.getSplitGoodsId(),dto.getCompanyId());
        if (null==splitGoods) {
            throw new RuntimeException("没有派单记录，不能派车！");
        }
        float splitRemainAmount = 0 ;
        List<SplitGoodsDetail> splitGoodsDetails = splitGoods.getSplitGoodsDetailList();
        if (null!=splitGoodsDetails && splitGoodsDetails.size()>0) {
            for(SplitGoodsDetail obj : splitGoodsDetails) {
                splitRemainAmount = splitRemainAmount + obj.getRemainAmount();//统计派单剩余数量（国为竞价是一次派单所以这块可以循环统计，否则要取最大ID的剩余数）
            }
        }
        if (sumAmount>splitRemainAmount) {
            throw new RuntimeException("本次派车总数量："+sumAmount+"，剩余待派数量："+splitRemainAmount+"，派车失败！");
        }


        //整合运单主子关系
        waybillDto.setWaybillItemsDtoList(waybillItemsDtos);
        int flag = waybillService.addWaybill(waybillDto);
        if (flag>0) { //更新派单记录剩余派单数
            for(SplitGoodsDetail obj : splitGoodsDetails) {
                obj.setRemainAmount(obj.getRemainAmount() - sumAmount); //剩余=原剩余-本次派车数
                splitGoodsDetailMapper.updateByPrimaryKey(obj);
            }
        }
        return flag;
    }


}
