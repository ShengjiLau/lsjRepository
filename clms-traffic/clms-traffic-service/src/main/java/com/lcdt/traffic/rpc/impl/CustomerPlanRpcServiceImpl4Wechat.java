package com.lcdt.traffic.rpc.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.CustomerPlanDto;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.service.DriverGroupService;
import com.lcdt.traffic.service.ICustomerPlanRpcService4Wechat;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/3.
 */
@Service
public class CustomerPlanRpcServiceImpl4Wechat implements ICustomerPlanRpcService4Wechat {


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

    @Reference
    private CompanyRpcService companyRpcService; //企业信息

    @Autowired
    private ClmsNotifyProducer producer;

    @Autowired
    private DriverGroupService driverGroupService;



    /****
     * 1、登录人-指承运人，承运人登录企业ID及登录人所有的组权限；
     * 2、根据上述条件获取登录人对应的客户表中的绑定客户列表；
     * 3、绑定客户条件：绑定ID不为空，客户列表中的企业ID==登录人企业ID
     */
    private List<Customer> bindCustomerList(Map map) {
        HashMap map1 = new HashMap();
        map1.put("companyId",map.get("companyId")); //企业ID
        map1.put("groupIds",map.get("groupIds")); //客户组
        map1.put("bindCpid","111");//标识绑定企业ID不为空的企业（承运商对应的所有绑定企业）
        List<Customer> customerList = customerRpcService.findBindCompanyIds(map1);
        return  customerList;
    }


    /***
     * 查询相关权限
     * @param map
     * @param customerList
     * @param flag
     * @return
     */
    private Map  customerPlanByCarrier4CmpIdGroup(Map map, List<Customer> customerList, int flag) {
        Map resultMap = new HashMap();
        if (customerList!=null && customerList.size()>0) { //承运人ID

            StringBuffer sb = new StringBuffer(); //保存创建计划企业ID

            StringBuilder sb_carrier_ids = new StringBuilder(); //承运商业竞价组集合
            //StringBuilder sb_driver_ids = new StringBuilder(); //司机竞价组集合

            StringBuilder sb_customerIDS = new StringBuilder(); //客户ID
            sb.append("(");
            for (int i=0;i<customerList.size();i++) {
                Customer tempObj = customerList.get(i);
                Long ownCompanyId = tempObj.getBindCpid(); //货主对应企业ID
                Long carrierCompanyId = tempObj.getCompanyId(); //当前登录人企业ID
                sb.append(" find_in_set('"+ownCompanyId+"',company_id)"); //创建计划企业ID
                if(i!=customerList.size()-1){
                    sb.append(" or ");
                }

                //查询承运人在货主方建立客户关系中，所加入的承运竞价组集合
                map.put("companyId",ownCompanyId);
                map.put("bindCompId",carrierCompanyId);
                List<Customer> customer4GroupList = customerRpcService.findBindCompanyIds(map); //承运商竞价组
                for (Customer obj1: customer4GroupList) {
                    if (!StringUtils.isEmpty(obj1.getCollectionIds())) {
                        sb_carrier_ids.append(obj1.getCollectionIds()+",");
                    }
                    sb_customerIDS.append(obj1.getCustomerId()+",");

                }
                //查询承运人在货主建立的司机组中的，所有竞价组
/*                List<DriverGroup> driverGroupList = driverGroupService.selectAll(ownCompanyId);
                if (driverGroupList!=null && driverGroupList.size()>0) {
                    for (DriverGroup obj1: driverGroupList) {
                        if (!StringUtils.isEmpty(obj1.getDriverGroupId())) {
                            sb_driver_ids.append(obj1.getDriverGroupId()+",");
                        }
                    }
                }*/
            }
            sb.append(")");

            /******返回结果集---分配计划的企业(企业创建者)******************/
            resultMap.put("companyIds",sb.toString());
            if (flag==0) {
                StringBuilder sb_10 = new StringBuilder();
                StringBuilder sb_11 = new StringBuilder();
                if(!StringUtils.isEmpty(sb_carrier_ids.toString())) { //承运商组
                    String carrierIds = sb_carrier_ids.toString().substring(0,sb_carrier_ids.toString().length()-1);
                    if (!StringUtils.isEmpty(carrierIds)) {
                        String[] strArrary = carrierIds.split(",");
                        for (int i=0; i<strArrary.length; i++) {
                            sb_10.append(" find_in_set('"+strArrary[i]+"',carrier_collection_ids)");
                            if(i!=strArrary.length-1){
                                sb_10.append(" or ");
                            }
                        }
                    }
                } else {
                    sb_10.append(" find_in_set('000',carrier_collection_ids)"); //没有承运条件的看不到
                }
                /*   if(!StringUtils.isEmpty(sb_driver_ids.toString())) { //司机组
                    String driverIds = sb_driver_ids.toString().substring(0,sb_driver_ids.toString().length()-1);
                    if (!StringUtils.isEmpty(driverIds)) {
                        String[] strArrary = driverIds.split(",");
                        for (int i=0; i<strArrary.length; i++) {
                            sb_11.append(" find_in_set('"+strArrary[i]+"',carrier_driver_ids)");
                            if(i!=strArrary.length-1){
                                sb_11.append(" or ");
                            }
                        }

                    }
                }*/

                String rString = "";
                if(!sb_10.toString().isEmpty()) {
                    rString = "(" +sb_10.toString();
                    if(!sb_11.toString().isEmpty()) {
                        rString += " or "+sb_11.toString()+" )";
                    } else {
                        rString += " )";
                    }
                } else {
                    if(!sb_11.toString().isEmpty()) {
                        rString = " ( "+sb_11.toString()+" )";
                    } else {
                        rString = "";
                    }
                }
                resultMap.put("carrierCollectionIds",rString);

            } else if (flag==1)  { //承运组(客户ID)
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
                resultMap.put("carrierCollectionIds",rString); //竞价组
            }else if (flag==2)  { //已派车
                StringBuilder sb_20 = new StringBuilder();
                if(!StringUtils.isEmpty(sb_customerIDS.toString())) { //指派类型的
                    String customerIDS = sb_customerIDS.toString().substring(0,sb_customerIDS.toString().length()-1);
                    if (!StringUtils.isEmpty(customerIDS)) {
                        String[] strArrary = customerIDS.split(",");
                        for (int i=0; i<strArrary.length; i++) {
                            sb_20.append(" find_in_set('"+strArrary[i]+"',wp.carrier_ids) or find_in_set('"+strArrary[i]+"',sp.carrier_collection_ids)"); //承运人
                            if(i!=strArrary.length-1){
                                sb_20.append(" or ");
                            }
                        }
                    }
                }
                String rString = "";
                if(!sb_20.toString().isEmpty()) {
                    rString = "(" +sb_20.toString()+" )";

                }
                if(StringUtils.isEmpty(rString)) {
                    rString = " find_in_set('000',wp.carrier_collection_ids)";
                }
                resultMap.put("carrierCollectionIds",rString);
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
            if(customer.getBindCpid().equals(compId)) {
                return customer.getCustomerName();
            }
        }
        return null;
    }



    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Bidding(Map map) {

        String company_denglu = map.get("companyId").toString();
        //根据登录人（权限组），获取对应客户列表中绑定的客户企业（货主）
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");//移除

        //查询对应在的企业组、竞价组条件
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,0);
        map.put("companyIds",cMap.get("companyIds")); //分配计划的企业(企业创建者)
        map.put("carrierCollectionIds",cMap.get("carrierCollectionIds"));
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

        //抢单企业
        if (!StringUtils.isEmpty(company_denglu)) {
            map.put("snatchCompanyId"," and company_id="+company_denglu);
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


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Offer(Map map) {
        String company_denglu = map.get("companyId").toString();
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();

        map.remove("groupIds");//移除
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,0); //查询对应在的企业组、竞价组条件

        map.put("companyIds",cMap.get("companyIds"));
        map.put("carrierCollectionIds",cMap.get("carrierCollectionIds"));
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

        //抢单企业
        if (!StringUtils.isEmpty(company_denglu)) {
            map.put("snatchCompanyId"," and company_id="+company_denglu);
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


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Pass(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,0); //查询对应在的企业组、竞价组条件

        map.put("companyIds",cMap.get("companyIds"));
        map.put("carrierCollectionIds",cMap.get("carrierCollectionIds"));

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

        List<CustomerPlanDto> list = waybillPlanMapper.customerPlanList4Pass(map);
        if (list!=null && list.size()>0) {
            for(CustomerPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4VehicleDoing(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,1); //查询对应在的企业组、竞价组条件
        if(!StringUtils.isEmpty(cMap.get("companyIds"))) {
            map.put("companyIds", cMap.get("companyIds").toString().replace("company_id", "wp.company_id"));
        }
        if (!StringUtils.isEmpty(cMap.get("carrierCollectionIds"))) {
            map.put("carrierCollectionIds", cMap.get("carrierCollectionIds").toString());
        }
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


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4VehicleHave(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,1); //查询对应在的企业组、竞价组条件
        if(!StringUtils.isEmpty(cMap.get("companyIds"))) {
            map.put("companyIds", cMap.get("companyIds").toString().replace("company_id", "wp.company_id"));
        }
        if (!StringUtils.isEmpty(cMap.get("carrierCollectionIds"))) {
            map.put("carrierCollectionIds", cMap.get("carrierCollectionIds").toString());
        }
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
            System.out.println(list.size());
            for(CustomerPlanDto dto :list){
                if(dto==null) continue;
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Completed(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,1); //查询对应在的企业组、竞价组条件
        if(!StringUtils.isEmpty(cMap.get("companyIds"))) {
            map.put("companyIds", cMap.get("companyIds").toString().replace("company_id", "wp.company_id"));
        }
        if (!StringUtils.isEmpty(cMap.get("carrierCollectionIds"))) {
            map.put("carrierCollectionIds", cMap.get("carrierCollectionIds").toString());
        }
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


    @Transactional(readOnly = true)
    @Override
    public PageInfo customerPlanList4Cancel(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        if(customerList==null || customerList.size()==0) return new PageInfo();
        map.remove("groupIds");
        Map cMap = customerPlanByCarrier4CmpIdGroup(map, customerList,1); //查询对应在的企业组、竞价组条件
        if(!StringUtils.isEmpty(cMap.get("companyIds"))) {
            map.put("companyIds", cMap.get("companyIds").toString().replace("company_id", "wp.company_id"));
        }
        if (!StringUtils.isEmpty(cMap.get("carrierCollectionIds"))) {
            map.put("carrierCollectionIds", cMap.get("carrierCollectionIds").toString());
        }
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
}
