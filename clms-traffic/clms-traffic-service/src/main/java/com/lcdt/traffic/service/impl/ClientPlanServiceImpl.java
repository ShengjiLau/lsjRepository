package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.dto.ClientPlanDto;
import com.lcdt.traffic.service.ClientPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/12/27.
 */
@Service
public class ClientPlanServiceImpl implements ClientPlanService {


    @com.alibaba.dubbo.config.annotation.Reference
    private CustomerRpcService customerRpcService;  //客户信息


    @Autowired
    private WaybillPlanMapper waybillPlanMapper;


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
    private Map  clientPlanSearch4CmpIdGroup(Map map, List<Customer> customerList) {
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
    public PageInfo clientPlanList4Bidding(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlan4Bidding(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
       }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo clientPlanList4Offer(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlanList4Offer(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo clientPlanList4Pass(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlanList4Offer(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo clientPlanList4VehicleDoing(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlanList4VehicleDoing(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo clientPlanList4VehicleHave(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlanList4VehicleHave(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo clientPlanList4Completed(Map map) {
        return null;
    }

    @Override
    public PageInfo clientPlanList4Cancel(Map map) {
        List<Customer> customerList = bindCustomerList(map);
        Map cMap = clientPlanSearch4CmpIdGroup(map, customerList); //查询对应在的企业组、竞价组条件
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
        List<ClientPlanDto> list = waybillPlanMapper.clientPlanList4Cancel(map);
        if (list!=null && list.size()>0) {
            for(ClientPlanDto dto :list){
                dto.setPlanSource(planSource(dto.getCompanyId(),customerList));
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


}
