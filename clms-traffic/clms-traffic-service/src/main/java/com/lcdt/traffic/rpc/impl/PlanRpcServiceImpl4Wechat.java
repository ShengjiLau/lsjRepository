package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnatchOfferDto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by yangbinq on 2018/3/19.
 */
@Service
public class PlanRpcServiceImpl4Wechat implements IPlanRpcService4Wechat {

    @Autowired
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper; //我的司机组

    @Autowired
    private OwnDriverMapper ownDriverMapper; //我的司机

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息

    @Autowired
    private SplitGoodsMapper splitGoodsMapper;


    @Autowired
    private WaybillMapper waybillMapper;

    @Autowired
    private SnatchGoodsMapper snatchGoodsMapper;
    @Autowired
    private SnatchGoodsDetailMapper snatchGoodsDetailMapper; //抢单

    /***
     * 根据司机ID获取用户对应的竞价组
     * @param driverId
     * @return
     */
    private String biddingGroupByDriverId(Long driverId) {
        List<DriverGroupRelationship> driverGroupRelationshipList = driverGroupRelationshipMapper.selectByDriverId(driverId);
        StringBuffer sb_20 = new StringBuffer();
        if (driverGroupRelationshipList!=null && driverGroupRelationshipList.size()>0) {
            sb_20.append("(");
            for(int i=0;i<driverGroupRelationshipList.size();i++) {
                DriverGroupRelationship obj = driverGroupRelationshipList.get(i);
                sb_20.append(" find_in_set('"+obj.getDriverGroupId()+"',carrier_driver_ids) ");
                if(i!=driverGroupRelationshipList.size()-1){
                    sb_20.append(" or ");
                }
            }
            sb_20.append(")");
          }else {
            sb_20.append(" find_in_set('000',carrier_driver_ids) ");
        }
        return sb_20.toString();

      }


    /***
     * 货主企业组
     * @return
     */
    private String ownCompanyIdsByDriverId(String companyIds) {
        String s[] = companyIds.split(",");
        StringBuffer sb_20 = new StringBuffer();
        if (s!=null && s.length>0) {
            sb_20.append("(");
            for(int i=0;i<s.length;i++) {
                sb_20.append(" find_in_set('"+s[i]+"',company_id) ");
                if(i!=s.length-1){
                    sb_20.append(" or ");
                }
            }
            sb_20.append(")");
        } else {
            sb_20.append(" find_in_set('000',company_id) ");
        }
        return sb_20.toString();

      }

    /***
     * 根据司机ID获取司机对应的企业信息
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<OwnCompany4SnatchRdto> ownCompanyList(SnathBill4WaittingPdto dto) {
        return ownDriverMapper.selectCompanyByDriverId(dto.getDriverId());
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo snatchBill4WaittingList(SnathBill4WaittingPdto dto) {
        PageInfo pageInfo = null;
        String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
        String ownCompanyIds = ownCompanyIdsByDriverId(dto.getCompyIds()); //发布计划企业ID组
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        String orderField = "waybill_plan_id"; //默认排序
        String orderDesc = "desc";
        if(!StringUtils.isEmpty(dto.getOrderDesc())) {
            orderDesc = dto.getOrderDesc();
        }
        if(!StringUtils.isEmpty(dto.getOrderFiled())) {
            orderField = dto.getOrderFiled();
        }
        Map map = new HashMap<String,String>();
        map.put("orderDesc",orderDesc);
        map.put("orderFiled",orderField);
        map.put("carrierDriverGroupIds",driverGroupIds);
        map.put("ownCompanyIds",ownCompanyIds);
        map.put("offerId",dto.getDriverId());
        PageHelper.startPage(pageNo, pageSize);
        List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.wattingSnatch4Driver(map);
        if (snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
            for (SnatchBill4WaittingRdto obj1: snatchBill4WaittingRdtos) {
                Long companyId = obj1.getCompanyId();
                Company company =  companyRpcService.findCompanyByCid(companyId);
                if(company!=null) obj1.setCompanyName(company.getFullName());
                User user =  companyRpcService.selectByPrimaryKey(obj1.getCreateId()); //查询创建用户
                if(user!=null) obj1.setOwnPhone(user.getPhone());
                obj1.setStatus("待抢单");
            }
            pageInfo = new PageInfo(snatchBill4WaittingRdtos);

        }else {
            pageInfo = new PageInfo();
            pageInfo.setTotal(0l);
            pageInfo.setList(snatchBill4WaittingRdtos);
        }
        return pageInfo;

    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo snatchBill4CompleteList(SnathBill4WaittingPdto dto) {
        PageInfo pageInfo = null;
        //String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
        String ownCompanyIds = ownCompanyIdsByDriverId(dto.getCompyIds()); //发布计划企业ID组
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo()>0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize()>0) {
            pageSize = dto.getPageSize();
        }
        String orderField = "waybill_plan_id"; //默认排序
        String orderDesc = "desc";
        if(!StringUtils.isEmpty(dto.getOrderDesc())) {
            orderDesc = dto.getOrderDesc();
        }
        if(!StringUtils.isEmpty(dto.getOrderFiled())) {
            orderField = dto.getOrderFiled();
        }
        Map map = new HashMap<String,String>();
        map.put("orderDesc",orderDesc);
        map.put("orderFiled",orderField);
        map.put("carrierDriverGroupIds",""); //driverGroupIds
        map.put("ownCompanyIds",ownCompanyIds);
        map.put("offerId",dto.getDriverId());
        PageHelper.startPage(pageNo, pageSize);
        List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.completeSnatch4Driver(map);
        if (snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
            for (SnatchBill4WaittingRdto obj: snatchBill4WaittingRdtos) {
                Long companyId = obj.getCompanyId();
                Company company =  companyRpcService.findCompanyByCid(companyId);
                if(company!=null) obj.setCompanyName(company.getFullName());
                User user =  companyRpcService.selectByPrimaryKey(obj.getCreateId()); //查询创建用户
                if(user!=null) obj.setOwnPhone(user.getPhone());
                if(obj.getPlanStatus().equals("60"))  {
                    obj.setStatus("计划取消");
                } else {
                  //检查就改口派车
                    List<SplitGoods> splitGoodsList = splitGoodsMapper.statCount4DriverSnatch(obj.getWaybillPlanId(),obj.getCompanyId());
                    if (splitGoodsList.size()>0){ //已派车
                        //抢单成功：该已抢计划已经派车给我
                        boolean flag = false;
                        for (SplitGoods splitGoods : splitGoodsList) {
                             Long splitGoodsId = splitGoods.getSplitGoodsId();
                             Map map1 = new HashMap();
                             map1.put("companyId",obj.getCompanyId());
                             map1.put("waybillPlanId",obj.getWaybillPlanId());
                             map1.put("splitGoodsId",splitGoodsId);
                             List<Waybill> waybillList = waybillMapper.selectWaybillByPlanIdAndSplitGoodsId(map1);
                             boolean flag1 = false;
                             if(waybillList!=null && waybillList.size()>0) {
                                 for(Waybill waybill: waybillList) {
                                     if(waybill.getDriverId()!=null&&waybill.getDriverId().equals(dto.getDriverId())) {
                                         flag1 = true;
                                         break;
                                     }
                                 }
                             }
                             if(flag1) {
                                 flag = true;
                                 break;
                             }
                        }
                        if(flag) {
                            obj.setStatus("抢单成功");
                        } else {
                            obj.setStatus("抢单失败");
                        }
                    } else {
                        obj.setStatus("竞价中"); //该已抢计划还未派车
                    }
                }

                //统计总报价
                float offerPrice = snatchGoodsDetailMapper.statSnatchTotalPrice4Driver(obj.getWaybillPlanId(),dto.getDriverId());
                obj.setSnatchTotalPrice(offerPrice);
            }
            pageInfo = new PageInfo(snatchBill4WaittingRdtos);

            } else {
                pageInfo = new PageInfo();
                pageInfo.setTotal(0l);
                pageInfo.setList(snatchBill4WaittingRdtos);
            }
         return pageInfo;

    }

    @Transactional
    @Override
    public int driverOffer(SnatchOfferDto dto, SnatchGoods snatchGoods) {
        Date dt = new Date();
        snatchGoods.setWaybillPlanId(dto.getWaybillPlanId());
        snatchGoods.setCreateDate(dt);
        snatchGoods.setUpdateTime(dt);
        snatchGoods.setOfferDate(dt);//报价时间
        snatchGoods.setIsDeleted((short)0);
        snatchGoods.setOfferRemark(dto.getOfferRemark());
        snatchGoods.setIsUsing(ConstantVO.SNATCH_GOODS_USING_DOING);
        int flag1 = 1,flag2 =1 ;
        flag1 = snatchGoodsMapper.insert(snatchGoods);
        List<PlanDetail> list = JSONArray.parseArray(dto.getPlanDetailStr(), PlanDetail.class);
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

        //默认排序
        String orderField = "waybill_plan_id";
        String orderDesc = "desc";
        if (map.get("orderDesc")==null) {
            map.put("orderDesc", "orderDesc");
        }
        if (map.get("orderFiled")=="") {
            map.put("orderFiled", "orderField");
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




}
