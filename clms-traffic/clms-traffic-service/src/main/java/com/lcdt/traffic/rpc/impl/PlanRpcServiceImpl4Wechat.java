package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.DriverGroupRelationshipMapper;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.dto.OwnCompany4SnatchRdto;
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
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper; //我的司机组

    @Autowired
    private OwnDriverMapper ownDriverMapper; //我的司机

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Reference
    private CompanyRpcService companyRpcService; //企业信息


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
     * @param driverId
     * @return
     */
    private String ownCompanyIdsByDriverId(Long driverId) {
        List<OwnCompany4SnatchRdto> ownCompany4SnatchRdtoList = ownDriverMapper.selectCompanyByDriverId(driverId);
        StringBuffer sb_20 = new StringBuffer();
        if (ownCompany4SnatchRdtoList!=null && ownCompany4SnatchRdtoList.size()>0) {
            sb_20.append("(");
            for(int i=0;i<ownCompany4SnatchRdtoList.size();i++) {
                OwnCompany4SnatchRdto obj = ownCompany4SnatchRdtoList.get(i);
                sb_20.append(" find_in_set('"+obj.getCompanyId()+"',company_id) ");
                if(i!=ownCompany4SnatchRdtoList.size()-1){
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
     * @param driverId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<OwnCompany4SnatchRdto> ownCompanyList(Long driverId) {
        return ownDriverMapper.selectCompanyByDriverId(driverId);
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo SnathBill4WaittingList(SnathBill4WaittingPdto dto) {
        PageInfo pageInfo = null;
        String driverGroupIds = biddingGroupByDriverId(dto.getDriverId()); //获取竞价组ID集合
        String ownCompanyIds = ownCompanyIdsByDriverId(dto.getDriverId()); //发布计划企业ID组
        if (!StringUtils.isEmpty(driverGroupIds) && !StringUtils.isEmpty(ownCompanyIds)) {
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
            if(StringUtils.isEmpty(dto.getOrderFiled())) {
                orderField = dto.getOrderFiled();
            }
            Map map = new HashMap<String,String>();
            map.put("orderDesc",orderDesc);
            map.put("orderFiled",orderField);
            map.put("carrierDriverGroupIds",driverGroupIds);
            map.put("ownCompanyIds",ownCompanyIds);

            PageHelper.startPage(pageNo, pageSize);
            List<SnatchBill4WaittingRdto> snatchBill4WaittingRdtos = waybillPlanMapper.wattingSnatch4Driver(map);
            if (snatchBill4WaittingRdtos!=null && snatchBill4WaittingRdtos.size()>0) {
                for (SnatchBill4WaittingRdto obj1: snatchBill4WaittingRdtos) {
                    Long companyId = obj1.getCompanyId();
                    Company company =  companyRpcService.findCompanyByCid(companyId);
                    if(company!=null) obj1.setCompanyName(company.getFullName());
                }
                pageInfo = new PageInfo(snatchBill4WaittingRdtos);

            }
        } else {
            pageInfo = new PageInfo();
            pageInfo.setTotal(0l);
            pageInfo.setList(null);
        }
        return pageInfo;

    }



}
