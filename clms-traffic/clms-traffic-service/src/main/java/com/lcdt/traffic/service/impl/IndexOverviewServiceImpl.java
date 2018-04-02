package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.IndexOverviewMapper;
import com.lcdt.traffic.service.IndexOverviewService;
import com.lcdt.traffic.util.GroupIdsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        map=limitParams(map);
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
        HashMap cMap = new HashMap();
        cMap.put("companyId",map.get("companyId")); //企业ID
        cMap.put("groupIds",map.get("groupIds")); //客户组
        cMap.put("bindCpid","111");//标识绑定企业ID不为空的企业（承运商对应的所有绑定企业）
        List<Customer> customerList = customerRpcService.findBindCompanyIds(cMap);
        return  customerList;
    }


    /***
     * 查询相关权限
     * @param customerList
     * @return
     */
    private Map  customerPlanByCarrier4CmpIdGroup(List<Customer> customerList) {
        Map resultMap = new HashMap();
        if (customerList!=null && customerList.size()>0) { //承运人ID
            StringBuffer sb = new StringBuffer(); //创建计划企业ID
            StringBuilder sb_carrier_ids = new StringBuilder(); //承运商业竞价组集合
            StringBuilder sb_customerIDS = new StringBuilder(); //客户ID
            sb.append("(");
            for (int i=0;i<customerList.size();i++) {
                Customer tempObj = customerList.get(i);
                Long ownCompanyId = tempObj.getBindCpid(); //货主对应企业ID
                Long carrierCompanyId = tempObj.getCompanyId(); //当前登录人企业ID
                sb.append(" find_in_set('"+ownCompanyId+"',wp.company_id)"); //创建计划企业ID
                if(i!=customerList.size()-1){
                    sb.append(" or ");
                }
                //查询承运人在货主方建立客户关系中，所加入的承运竞价组集合
                Map map = new HashMap<String,String>();
                map.put("companyId",ownCompanyId);
                map.put("bindCompId",carrierCompanyId);
                List<Customer> customer4GroupList = customerRpcService.findBindCompanyIds(map); //承运商竞价组
                for (Customer obj1: customer4GroupList) {
                    if (!StringUtils.isEmpty(obj1.getCollectionIds())) {
                        sb_carrier_ids.append(obj1.getCollectionIds()+",");
                    }
                        sb_customerIDS.append(obj1.getCustomerId()+",");

                }
            }
            sb.append(")");

            /******返回结果集---分配计划的企业(企业创建者)******************/
            resultMap.put("companyIds",sb.toString());

            StringBuilder sb_10 = new StringBuilder();
            if(!StringUtils.isEmpty(sb_carrier_ids.toString())) { //承运商组
                String carrierIds = sb_carrier_ids.toString().substring(0,sb_carrier_ids.toString().length()-1);
                if (!StringUtils.isEmpty(carrierIds)) {
                    String[] strArrary = carrierIds.split(",");
                    for (int i=0; i<strArrary.length; i++) {
                        sb_10.append(" find_in_set('"+strArrary[i]+"',wp.carrier_collection_ids)");
                        if(i!=strArrary.length-1){
                            sb_10.append(" or ");
                        }
                    }
                }
            } else {
                sb_10.append(" find_in_set('000',wp.carrier_collection_ids)"); //没有承运条件的看不到
            }

            resultMap.put("carrierCollectionIds1","("+sb_10.toString()+")");

            StringBuilder sb_20 = new StringBuilder();
            StringBuilder sb_21 = new StringBuilder();
            if(!StringUtils.isEmpty(sb_customerIDS.toString())) { //直派承运商
                String customerIDS = sb_customerIDS.toString().substring(0,sb_customerIDS.toString().length()-1);
                if (!StringUtils.isEmpty(customerIDS)) {
                    String[] strArrary = customerIDS.split(",");
                    for (int i=0; i<strArrary.length; i++) {
                        sb_20.append(" find_in_set('"+strArrary[i]+"',wp.carrier_ids) or find_in_set('"+strArrary[i]+"',sp.carrier_collection_ids)"); //承运人(直接指定，录入完派单)
                        if(i!=strArrary.length-1){
                            sb_20.append(" or ");
                        }
                    }
                }
            }
            if(!StringUtils.isEmpty(sb_carrier_ids.toString())) { //竞价承运商
                String collectionIds = sb_carrier_ids.toString().substring(0,sb_carrier_ids.toString().length()-1);
                if (!StringUtils.isEmpty(collectionIds)) {
                    String[] strArrary = collectionIds.split(",");
                    for (int i=0; i<strArrary.length; i++) {
                        sb_21.append(" find_in_set('"+strArrary[i]+"',wp.carrier_collection_ids)"); //竞价组 or find_in_set('"+strArrary[i]+"',sp.carrier_collection_ids)
                        if(i!=strArrary.length-1){
                            sb_21.append(" or ");
                        }
                    }
                }
            }

            String rString = "";
            if(!sb_20.toString().isEmpty()) {
                rString = "(" +sb_20.toString();
                if(!sb_21.toString().isEmpty()) {
                    rString += " or "+sb_21.toString()+" )";
                } else {
                    rString += " )";
                }
            } else {
                if(!sb_21.toString().isEmpty()) {
                    rString = " ( "+sb_21.toString()+" )";
                } else {
                    rString = "";
                }
            }
            if(StringUtils.isEmpty(rString)) {
                rString = " find_in_set('000',wp.carrier_collection_ids)";
            }
            resultMap.put("carrierCollectionIds2",rString); //竞价组
        }
        return resultMap;
    }

    @Override
    public Map customerPlanStatistics(Map map) {
        String dengluCompanyId =   map.get("companyId").toString();
        map=limitParams(map);
        List<Customer> customerList = bindCustomerList(map);    //根据登录人（权限组），获取对应客户列表中绑定的客户企业（货主）
        if(customerList==null || customerList.size()==0) return null;
        Map cMap = customerPlanByCarrier4CmpIdGroup(customerList); //查询对应在的企业组、竞价组条件
        map.put("companyIds",cMap.get("companyIds"));
        map.put("carrierCollectionIds1",cMap.get("carrierCollectionIds1"));
        map.put("carrierCollectionIds2",cMap.get("carrierCollectionIds2"));
        if(!StringUtils.isEmpty(dengluCompanyId)) {

            map.put("snatchCompanyId"," and sn.company_id = "+dengluCompanyId);
        }



        Map<String,Object> result_map = new HashMap<>();
        List<Map<String,Object>> mapList = indexOverviewMapper.selectCustomerPlanData(map);     //统计详情查询用来做折线的数据
        result_map.put("detail",mapList);
        return result_map;
    }

    @Override
    public Map queryOwnWaybillStatistics(Map map) {
        map=limitParams(map);
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
        map=limitParams(map);
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
    public Map transportOverview(Map parameter){
        Long companyId=(Long)parameter.get("companyId");
        String ownGroupIds=parameter.get("ownGroupIds").toString(); //获取默认当前所有分组
        String customerGroupIds=parameter.get("customerGroupIds").toString();//获取默认当前所有分组

        Map ownMap=new HashMap();
        ownMap.put("companyId",companyId);
        ownMap.put("groupIds",ownGroupIds);

        Map cusMap=new HashMap();
        cusMap.put("companyId",companyId);
        cusMap.put("group_ids",customerGroupIds);



        Map<String,Object> resultMap = new HashMap<>();
        /**获取运输承运商和运输客户统计*/
        Map customerMap = customerRpcService.selectCarrierAndCustomer(companyId, customerGroupIds);

        if(null!=customerMap){
            resultMap.put("carrierNum",new Integer(customerMap.get("carrier_num")+""));     //设置运输承运商数量
            resultMap.put("carrierCustomNum",new Integer(customerMap.get("carrier_custom_num")+""));   //设置运输客户数量
        }else{
            resultMap.put("carrierNum",0);     //设置运输承运商数量为0
            resultMap.put("carrierCustomNum",0);   //设置运输客户数量为0
        }

        /**获取我的车辆和司机统计*/
        List<Map<String,Object>>  mapList1 = indexOverviewMapper.selectVehicleAndDriver(companyId);
        if(null!=mapList1 && mapList1.size()>0){
            Map driverMap = (Map)mapList1.get(0);
            Map map1 = (Map)mapList1.get(1);
            resultMap.put("vehicleNum",new Integer(driverMap.get("vehicle_driver")+""));     //设置我的车辆统计数量
            resultMap.put("driverNum",new Integer(map1.get("vehicle_driver")+""));   //设置我的司机统计数量
        }else{
            resultMap.put("vehicleNum",0);      //设置我的车辆统计数量为0
            resultMap.put("driverNum",0);   //设置我的司机统计数量为0
        }

        /**获取执行中的计划*/
        Long ownPlanNum = indexOverviewMapper.selectOwnPlan4Doing(companyId, ownGroupIds); //我创建的

        //我承运的
        Map map = new HashMap<String,String>();
        map.put("companyId",companyId);
        map.put("groupIds",customerGroupIds);
         List<Customer> customerList = bindCustomerList(map);    //根据登录人（权限组），获取对应客户列表中绑定的客户企业（货主）

        Long customerPlanNum = 0l;
        if(customerList==null || customerList.size()==0) {
            customerPlanNum = 0l;
        } else {
            Map cMap = customerPlanByCarrier4CmpIdGroup(customerList); //查询对应在的企业组、竞价组条件
            map.put("carrierCollectionIds1",cMap.get("carrierCollectionIds1"));
            map.put("companyIds",cMap.get("companyIds"));
            customerPlanNum = indexOverviewMapper.selectCustomerPlan4Doing(map); //指给我的

        }
        resultMap.put("planNum",(ownPlanNum+customerPlanNum));      //设置执行中的计划统计数量为0



        /**在途运单统计*/
        Map cMapIds = customerCompanyIds.getCustomerCompanyIds(cusMap);
        Map waybillCusMap=new HashMap();
        waybillCusMap.put("companyIds",cMapIds.get("companyIds"));
        waybillCusMap.put("carrierCompanyId",cusMap.get("companyId"));
        Integer ownWaybillNum = indexOverviewMapper.selectInTransitOwnWaybill(ownMap);
        Integer cusWaybillNum = indexOverviewMapper.selectInTransitCustomerWaybill(waybillCusMap);
        resultMap.put("waybillNum",(ownWaybillNum!=null?ownWaybillNum:0)+(cusWaybillNum!=null?cusWaybillNum:0));   //设置在途运单统计数量
        return resultMap;
    }


    private Map limitParams(Map map){
        if(map.containsKey("date_interval") ||( map.containsKey("pubdate_end") && map.containsKey("pubdate_start"))){
            if((map.get("date_interval")!=null&&!map.get("date_interval").toString().equals(""))||
                    ((map.get("pubdate_start")!=null&&!map.get("pubdate_start").toString().equals(""))&&(map.get("pubdate_end")!=null&&!map.get("pubdate_end").toString().equals("")))){
            }else {
                 throw new RuntimeException("参数错误");
            }

        }else{
            throw new RuntimeException("参数错误");
        }
        return map;
    }
}
