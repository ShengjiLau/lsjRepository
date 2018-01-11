package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.IndexOverviewMapper;
import com.lcdt.traffic.service.IndexOverviewService;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2018-01-11
 */
@Service
public class IndexOverviewServiceImpl implements IndexOverviewService {

    @Autowired
    private IndexOverviewMapper indexOverviewMapper;

    @Reference
    private CustomerRpcService customerRpcService;

    @Override
    public Map planStatistics(Map map) {
        return indexOverviewMapper.selectPlan(map);
    }

    @Override
    public Map waybillStatistics(Map map) {
        return indexOverviewMapper.selectWaybill(map);
    }

    @Override
    public Map transportOverview(Long companyId){
        Map<String,Object> resultMap = new HashMap<>();
        /**获取运输承运商和运输客户统计*/
        Map customerMap = customerRpcService.selectCarrierAndCustomer(companyId);
        if(null!=customerMap){
            resultMap.put("carrierNum",new Integer(customerMap.get("carrier_num")+""));     //设置运输承运商数量
            resultMap.put("carrierCustomNum",new Integer(customerMap.get("carrier_custom_num")+""));   //设置运输客户数量
        }else{
            resultMap.put("carrierNum",0);     //设置运输承运商数量为0
            resultMap.put("carrierCustomNum",0);   //设置运输客户数量为0
        }
        /**获取执行中的计划和在途运单统计*/
        List<Map<String,Object>>  mapList = indexOverviewMapper.selectPlanAndWaybill(companyId);
        if(null!=mapList && mapList.size()>0){
            Map map = (Map)mapList.get(0);
            Map map1 = (Map)mapList.get(0);
            resultMap.put("planNum",new Integer(map.get("plan_waybill")+""));     //设置执行中的计划统计数量
            resultMap.put("waybillNum",new Integer(map1.get("plan_waybill")+""));   //设置在途运单统计数量
        }else{
            resultMap.put("planNum",0);      //设置执行中的计划统计数量为0
            resultMap.put("waybillNum",0);   //设置在途运单统计数量为0
        }
        /**获取我的车辆和司机统计*/
        List<Map<String,Object>>  mapList1 = indexOverviewMapper.selectVehicleAndDriver(companyId);
        if(null!=mapList1 && mapList1.size()>0){
            Map map = (Map)mapList1.get(0);
            Map map1 = (Map)mapList1.get(1);
            resultMap.put("vehicleNum",new Integer(map.get("vehicle_driver")+""));     //设置我的车辆统计数量
            resultMap.put("driverNum",new Integer(map1.get("vehicle_driver")+""));   //设置我的司机统计数量
        }else{
            resultMap.put("vehicleNum",0);      //设置我的车辆统计数量为0
            resultMap.put("driverNum",0);   //设置我的司机统计数量为0
        }
        return resultMap;
    }
}
