package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/3/24
 */
@Service
public class CustomerCompanyIds {
    @Reference
    private CustomerRpcService customerRpcService;

    /****
     * 返回当前登录人企业绑定企业id
     * 1、登录人-指承运人，承运人登录企业ID及登录人所有的组权限；
     * 2、根据上述条件获取登录人对应的客户表中的绑定客户列表；
     * 3、绑定客户条件：绑定ID不为空，客户列表中的企业ID==登录人企业ID
     * 4、运单查询公共条件（创建企业ID条件）
     * 5、Map(companyId--客户创建企业ID)
     * @return
     */
    public Map getCustomerCompanyIds(Map map) {
        Map resultMap = new HashMap();
        map.put("bindCpid", "111");//标识绑定企业ID不为空的企业
        List<Customer> customerList = customerRpcService.findBindCompanyIds(map);
        if (customerList != null && customerList.size() > 0) { //承运人ID
            /****
             * 1、登录人对应客户列表信息（承运人对应的货主列表信息）；
             * 2、遍历该列表信息，根据客户中绑定企业ID（这里指货主）及创建客户的企业ID（客户本身）;
             * 3、再次遍历客户列表找出客户所对应的竞价组信息
             *
             */
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i = 0; i < customerList.size(); i++) {
                Customer tempObj = customerList.get(i);
                Long ownCompanyId = tempObj.getBindCpid(); //承运商绑定客户企业ID
                sb.append(" find_in_set('" + ownCompanyId + "',company_id)"); //创建计划企业ID
                if (i != customerList.size() - 1) {
                    sb.append(" or ");
                }
            }
            sb.append(")");

            resultMap.put("companyIds", sb.toString()); //计划创建企业
        }
        return resultMap;
    }
}