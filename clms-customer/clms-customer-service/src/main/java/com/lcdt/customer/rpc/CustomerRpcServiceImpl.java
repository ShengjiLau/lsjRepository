package com.lcdt.customer.rpc;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.dao.CustomerContactMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.dao.CustomerTypeRelationMapper;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerContact;
import com.lcdt.customer.model.CustomerTypeRelation;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.customer.service.CustomerService;
import com.lcdt.userinfo.model.UserCompRel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/11/24.
 */
@Service(timeout = 5000)
public class CustomerRpcServiceImpl implements CustomerRpcService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    CustomerService customerService;

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Autowired
    private CustomerTypeRelationMapper contomerTypeRelation;

    @Override
    public Customer findCustomerById(Long customerId) {
        Customer vo = customerMapper.selectInnerByPrimaryKey(customerId);
        return vo;
    }

    @Override
    public Customer findCustomerById(Long customerId, Long companyId) {
        return customerMapper.selectByPrimaryKey(customerId,companyId);
    }


    @Override
    public List<Customer> findBindCompanyIds(Map map) {
        return customerMapper.selectByCondition(map);
    }

    @Override
    public Map<String,Object> selectCarrierAndCustomer(Long companyId,String groupIds){
        return customerMapper.selectCarrierAndCustomer(companyId, groupIds);
    }

    @Override
    public Customer queryCustomer(Long companyId, Long bindCompanyId) {
        Customer customer = customerMapper.selectByCustomerBindCompanyId(companyId, bindCompanyId);
        return customer;
    }

    @Override
    public int customerAdd(Customer customer) {
        return  customerService.customerAdd(customer)==null? -1:0;
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo customerList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Customer> list = customerMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return  pageInfo;
    }

    @Transactional
    @Override
    public Customer createCustomer(Map map) {
        if (map.get("customerType")!=null && map.get("customerName")!=null) {
            String customerType = map.get("customerType").toString();
           map.put("customerType","find_in_set('"+map.get("customerType").toString()+"',client_types)");
           map.put("companyId",map.get("companyId").toString());
           List<Customer> list = customerMapper.selectByCondition(map);
           if (list!=null && list.size()>0) {
                return list.get(0);
           } else {
               Customer customer = new Customer();
               customer.setCompanyId(Long.valueOf(map.get("companyId").toString()));
               customer.setCustomerName(map.get("customerName").toString());
               customer.setClientTypes(map.get("customerType").toString());
               customer.setProvince(map.get("province").toString());
               customer.setCity(map.get("city").toString());
               customer.setCounty(map.get("county").toString());
               customer.setDetailAddress(map.get("details").toString());
               customer.setCreateDate(new Date());
               customer.setCreateId(Long.valueOf(map.get("userId").toString()));
               customer.setCreateName(map.get("userName").toString());
               customer.setStatus((short)1);
               int flag = customerMapper.insert(customer);
               if (flag>0) {
                   //默认联系人
                   CustomerContact customerContact = new CustomerContact();
                   customerContact.setName(map.get("linkMan").toString());
                   customerContact.setMobile(map.get("mobile").toString());
                   customerContact.setIsDefault((short) 1); //默认联系人
                   customerContact.setCompanyId(customer.getCompanyId());
                   customerContact.setProvince(customer.getProvince());
                   customerContact.setCity(customer.getCity());
                   customerContact.setCounty(customer.getCounty());
                   customerContact.setDetailAddress(customer.getDetailAddress());
                   customerContact.setCustomerId(customer.getCustomerId());
                   customerContact.setCreateId(customer.getCreateId());
                   customerContact.setCreateName(customer.getCreateName());
                   customerContact.setCreateDate(new Date());
                   customerContactMapper.insert(customerContact);

                   //组关系表
                   if (!StringUtils.isEmpty(customerType)) {
                       String[] typeArrays = customerType.split(",");  //传过来的值用逗号隔开
                       for (int i=0; i<typeArrays.length; i++) {
                           CustomerTypeRelation relationObj = new CustomerTypeRelation();
                           relationObj.setCustomerId(customer.getCustomerId());
                           relationObj.setCustomerName(customer.getCustomerName());
                           relationObj.setCustomerType(Short.valueOf(typeArrays[i]));
                           relationObj.setCompanyId(customer.getCompanyId());
                           relationObj.setCreateId(customer.getCreateId());
                           relationObj.setCreateName(customer.getCreateName());
                           relationObj.setCreateDate(new Date());
                           contomerTypeRelation.insert(relationObj);
                       }
                   }
               }
               return customer;
           }

        }

        return null;
    }


}
