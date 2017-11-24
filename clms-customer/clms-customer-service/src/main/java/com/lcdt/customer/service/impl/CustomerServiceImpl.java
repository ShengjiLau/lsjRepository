package com.lcdt.customer.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.dao.CustomerContactMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.dao.CustomerTypeRelationMapper;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.CustomerContact;
import com.lcdt.customer.model.CustomerTypeRelation;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerContactMapper customerContactMapper;

    @Autowired
    private CustomerTypeRelationMapper contomerTypeRelation;


    @Transactional(readOnly = true)
    @Override
    public PageInfo getCustomerList(Map m) {
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

    @Override
    public Customer getCustomerDetail(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }


    @Transient
    @Override
    public int addCustomer(Customer customer) {
        Map map = new HashMap();
        map.put("companyId", customer.getCompanyId());
        map.put("clientName", customer.getCustomerName());
        List<Customer> list = customerMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("客户名称已存在");
        }
        int flag = customerMapper.insert(customer);
        if (flag>0) {
            //默认联系人
            CustomerContact customerContact = new CustomerContact();
            customerContact.setName(customer.getLinkMan());
            customerContact.setDuty(customer.getLinkDuty());
            customerContact.setMobile(customer.getLinkTel());
            customerContact.setMail(customer.getLinkEmail());
            customerContact.setIsDefault((short) 1); //默认联系人
            customerContact.setCompanyId(customer.getCompanyId());
            customerContact.setProvince(customer.getProvince());
            customerContact.setCity(customer.getCity());
            customerContact.setCounty(customer.getCounty());
            customerContact.setDetailAddress(customer.getDetailAddress());
            customerContact.setCustomerId(customer.getCustomerId());
            customerContactMapper.insert(customerContact);

            //组关系表
            if (!StringUtils.isEmpty(customer.getClientTypes())) {
                String[] typeArrays = customer.getClientTypes().split(",");  //传过来的值用逗号隔开
                for (int i=0; i<typeArrays.length; i++) {

                    CustomerTypeRelation relationObj = new CustomerTypeRelation();
                    relationObj.setCustomerId(customer.getCustomerId());
                    relationObj.setCustomerName(customer.getCustomerName());
                    relationObj.setCustomerType(Short.valueOf(typeArrays[i]));
                    relationObj.setCompanyId(customer.getCompanyId());
                    contomerTypeRelation.insert(relationObj);
                }
            }
        }
        return flag;
    }





/*    @Override
    public List<MyClientLinkman> getMyClientLinkman(Long myClientId) {
        return myClientLinkmanMapper.selectByMyClientId(myClientId);
    }

    @Override
    public int updateMyClient(MyClient myClient) {
        return myClientMapper.updateByPrimaryKey(myClient);
    }


    @Override
    public int delMyClient(Long myClientId) {
        return myClientMapper.deleteByPrimaryKey(myClientId);
    }

    @Override
    public int toggleMyClientStatus(MyClient myClient) {
        return myClientMapper.updateStatus(myClient);
    }

    @Override
    public int updateMyClientLinkman(MyClientLinkman myClientLinkman) {
        return myClientLinkmanMapper.updateByPrimaryKey(myClientLinkman);
    }

    @Override
    public int addMyClientLinkman(MyClientLinkman myClientLinkman) {
        return myClientLinkmanMapper.insert(myClientLinkman);
    }

    @Override
    public int de·                                                                                                          lMyClientLinkman(Long myClientLinkmanId) {
        return myClientLinkmanMapper.deleteByPrimaryKey(myClientLinkmanId);
    }

    @Override
    public int setDefaultLinkman(Long myClientLinkmanId) {
        MyClientLinkman myClientLinkman = new MyClientLinkman();
        myClientLinkman.setIsDefault(new Short("1"));
        myClientLinkman.setMyClientId(myClientLinkmanId);
        return myClientLinkmanMapper.updateIsDefault(myClientLinkman);
    }*/
}
