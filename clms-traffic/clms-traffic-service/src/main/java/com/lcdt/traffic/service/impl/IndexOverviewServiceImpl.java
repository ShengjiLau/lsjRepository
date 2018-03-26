package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.IndexOverviewMapper;
import com.lcdt.traffic.service.IndexOverviewService;
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

    @Autowired
    private CustomerCompanyIds customerCompanyIds;

    @Override
    public Map planStatistics(Map map) {
        Map<String,Object> map1 = indexOverviewMapper.selectPlan(map);  //总的统计
        if (null == map1) {
            map1 = new HashMap<>();
            map1.put("paidanzhong", 0);
            map1.put("jingjiazhong", 0);
            map1.put("yipaidan", 0);
            map1.put("yipaiche", 0);
            map1.put("yiwancheng", 0);
        }
        List<Map<String,Object>> mapList = indexOverviewMapper.selectPlanData(map);     //统计详情查询用来做折线的数据
        map1.put("detail",mapList);
        return map1;
    }


    /**
     * 根据用户ID及相关组，获取关联客户
     * @param map
     * @return
     */
    private List<Customer> bindCustomerList(Map map) {
        HashMap map1 = new HashMap();
        map1.put("companyId",map.get("companyId")); //企业ID
        map1.put("groupIds",map.get("groupIds")); //客户组
        map1.put("bindCpid","111");//标识绑定企业ID不为空的企业（承运商对应的所有绑定企业）
        List<Customer> customerList = customerRpcService.findBindCompanyIds(map1);
        return  customerList;
    }



    @Override
    public Map customerPlanStatistics(Map map) {






        return null;



    }

    @Override
    public Map queryOwnWaybillStatistics(Map map) {
        Map<String,Object> map1 = indexOverviewMapper.selectOwnWaybill(map);
        if (null == map1) {
            map1 = new HashMap<>();
            map1.put("daifahuo", 0);
            map1.put("yiruchang", 0);
            map1.put("yichuku", 0);
            map1.put("yunshuzhong", 0);
            map1.put("yixiehuo", 0);
            map1.put("yiqianshou", 0);
            map1.put("yiwancheng", 0);
        }
        List<Map<String,Object>> mapList = indexOverviewMapper.selectOwnWaybillData(map);     //统计详情查询用来做折线的数据
        map1.put("detail",mapList);
        return map1;
    }

    @Override
    public Map queryCustomerWaybillStatistics(Map map) {
        Map cMapIds = customerCompanyIds.getCustomerCompanyIds(map);
        map.put("companyIds",cMapIds.get("companyIds"));
        map.put("carrierCompanyId",map.get("companyId"));
        map.remove("companyId");

        Map<String,Object> map1 = indexOverviewMapper.selectCustomerWaybill(map);
        if (null == map1) {
            map1 = new HashMap<>();
            map1.put("daifahuo", 0);
            map1.put("yiruchang", 0);
            map1.put("yichuku", 0);
            map1.put("yunshuzhong", 0);
            map1.put("yixiehuo", 0);
            map1.put("yiqianshou", 0);
            map1.put("yiwancheng", 0);
        }
        List<Map<String,Object>> mapList = indexOverviewMapper.selectCustomerWaybillData(map);     //统计详情查询用来做折线的数据
        map1.put("detail",mapList);
        return map1;
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
            resultMap.put("planNum",new Integer(null==map?"0":map.get("plan_waybill")+""));     //设置执行中的计划统计数量
            resultMap.put("waybillNum",new Integer(null==map1?"0":map1.get("plan_waybill")+""));   //设置在途运单统计数量
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
