package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.DriverGroupRelationshipMapper;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.dto.SnatchBill4WaittingRdto;
import com.lcdt.traffic.dto.SnathBill4WaittingPdto;
import com.lcdt.traffic.model.DriverGroupRelationship;
import com.lcdt.traffic.service.IPlanRpcService4Wechat;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/3/19.
 */
@Service
public class PlanRpcServiceImpl4Wechat implements IPlanRpcService4Wechat {

    @Autowired
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper;

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;


    @Reference
    private CompanyRpcService companyRpcService; //企业信息


    /***
     * 根据用户ID获取用户对应的竞价组
     * @param userId
     * @return
     */
    private String biddingGroupByDriverId(Long userId) {
        List<DriverGroupRelationship> driverGroupRelationshipList = driverGroupRelationshipMapper.selectByDriverId(userId);
        StringBuffer sb = new StringBuffer();
        if (driverGroupRelationshipList!=null && driverGroupRelationshipList.size()>0) {
            for(DriverGroupRelationship obj : driverGroupRelationshipList) {
                sb.append(obj.getDriverGroupId()+",");
            }
            String driverGroupIds = sb.toString().substring(0,sb.toString().length()-1);
            StringBuffer sb_20 = new StringBuffer();
            if (!StringUtils.isEmpty(driverGroupIds)) {
                String[] strArrary = driverGroupIds.split(",");
                for (int i=0; i<strArrary.length; i++) {
                    sb_20.append(" find_in_set('"+strArrary[i]+"',carrier_driver_ids) ");
                    if(i!=strArrary.length-1){
                        sb_20.append(" or ");
                    }
                }
            } else {
                sb_20.append(" find_in_set('000',carrier_driver_ids) ");
            }
            return sb_20.toString();

        }
        return null;
      }



    @Transactional(readOnly = true)
    @Override
    public PageInfo SnathBill4WaittingList(SnathBill4WaittingPdto dto) {
        if (dto.getDriverId()>0) {
            String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
            if (!StringUtils.isEmpty(driverGroupIds)) {

                PageInfo page = null;
                int pageNo = 1;
                int pageSize = 0; //0表示所有
                if (dto.getPageNo()>0) {
                    pageNo = dto.getPageNo();
                }
                if (dto.getPageSize()>0) {
                    pageSize = dto.getPageSize();
                }
                String orderField = "waybill_plan_id";
                String orderDesc = "desc";

                if(!StringUtils.isEmpty(dto.getOrderDesc())) {
                    orderDesc = dto.getOrderDesc();
                }
                if(StringUtils.isEmpty(dto.getOrderFiled())) {
                    orderField = dto.getOrderFiled();
                }
                Map map = new HashMap<String,String>();
                map.put("orderDesc",orderDesc);
                map.put("orderFiled",orderField);
                map.put("carrierCollectionIds",driverGroupIds);
                PageHelper.startPage(pageNo, pageSize);
                List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.wattingSnatch4Driver(map);
                if(snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
                    for (SnatchBill4WaittingRdto obj1: snatchBill4WaittingRdtos) {
                        Long companyId = obj1.getCompanyId();
                        Company company =  companyRpcService.findCompanyByCid(companyId);
                        if(company!=null) obj1.setCompanyName(company.getFullName());

                    }
                    PageInfo pageInfo = new PageInfo(snatchBill4WaittingRdtos);
                    return pageInfo;
                }
            }
        }
        return null;

    }



}
