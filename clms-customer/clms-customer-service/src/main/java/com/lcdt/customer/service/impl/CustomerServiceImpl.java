package com.lcdt.customer.service.impl;


import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.dao.CustomerCollectionMapper;
import com.lcdt.customer.dao.CustomerContactMapper;
import com.lcdt.customer.dao.CustomerMapper;
import com.lcdt.customer.dao.CustomerTypeRelationMapper;
import com.lcdt.customer.exception.CustomerException;
import com.lcdt.customer.model.CustomerCollection;
import com.lcdt.customer.model.CustomerContact;
import com.lcdt.customer.model.CustomerTypeRelation;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.util.*;

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


    @Autowired
    private CustomerCollectionMapper customerCollectionMapper;


    @Override
    public Customer selectByCustomerId(Long customerId, Long companyId) {
        Customer customer = customerMapper.selectByPrimaryKey(customerId, companyId);
        return customer;
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

    @Transactional(readOnly = true)
    @Override
    public Customer getCustomerDetail(Long customerId, Long companyId) {
        return customerMapper.selectByPrimaryKey(customerId, companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerContactAdd(CustomerContact customerContact) {
        return customerContactMapper.insert(customerContact);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerAdd(Customer customer) throws CustomerException {
        Map map = new HashMap();
        map.put("companyId", customer.getCompanyId());
        map.put("customerName", customer.getCustomerName());
        List<Customer> list = customerMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("客户已存在，请联系管理员分配！");
        }
        customer.setStatus((short)1);
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
            customerContact.setCreateId(customer.getCreateId());
            customerContact.setCreateName(customer.getCreateName());
            customerContact.setCreateDate(new Date());
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

                    relationObj.setCreateId(customer.getCreateId());
                    relationObj.setCreateName(customer.getCreateName());
                    relationObj.setCreateDate(new Date());

                    contomerTypeRelation.insert(relationObj);
                }
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerUpdate(Customer customer) throws CustomerException  {
        Map map = new HashMap();
        map.put("companyId", customer.getCompanyId());
        map.put("customerId", customer.getCustomerId());
        map.put("customerName", customer.getCustomerName());
        List<Customer> list = customerMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("客户已存在，请联系管理员分配！");
        }
        int flag = customerMapper.updateByPrimaryKeySelective(customer);

        if (flag>0) {
             //组关系表
            if (!StringUtils.isEmpty(customer.getClientTypes())) {

                String[] typeArrays = customer.getClientTypes().split(",");  //传过来的值用逗号隔开
                if (typeArrays==null || typeArrays.length==0) { //如果不选，清楚原来的数据
                    //先清楚原来的组关系，再更新
                    CustomerTypeRelation tempObj = new CustomerTypeRelation();
                    tempObj.setCustomerId(customer.getCustomerId());
                    tempObj.setCompanyId(customer.getCompanyId());
                    contomerTypeRelation.deleteCustomerType(tempObj);
                } else {

                    Map map1 = new HashMap();
                    map1.put("companyId", customer.getCompanyId());
                    map1.put("customerId", customer.getCustomerId());
                    List<CustomerTypeRelation> typeList = contomerTypeRelation.selectByCondition(map1);
                    if (typeList!=null && typeList.size()>0) {
                        String[] typeArrays1 = new String[typeList.size()];
                        for(int i=0;i<typeList.size();i++) {
                            typeArrays1[i] = typeList.get(i).getCustomerTypeId().toString();
                        }


                        ArrayList<String> delList = new ArrayList<>();
                        ArrayList<String> addList = new ArrayList<>();
                        //算出新增的
                        for(int i=0;i<typeArrays.length;i++) {
                            String t1 = typeArrays[i];
                            boolean tflag = false;
                            for(int j=0;j<typeArrays1.length;j++) {
                                String t2 = typeArrays1[i];
                                if(t1==t2) {
                                    tflag = true;
                                    break;
                                }
                            }
                            if (!tflag) { //如果不存在，说明新增
                               addList.add(t1);
                            }
                        }


                        //算出取消的的
                        for(int i=0;i<typeArrays1.length;i++) {
                            String t1 = typeArrays1[i];
                            boolean tflag = false;
                            for(int j=0;j<typeArrays.length;j++) {
                                String t2 = typeArrays[i];
                                if(t1==t2) {
                                    tflag = true;
                                    break;
                                }
                            }
                            if (!tflag) { //如果不存在，说明删除的
                                delList.add(t1);
                            }
                        }

                        if (delList!=null && delList.size()>0) {
                            for (int i=0;i<delList.size();i++) {
                                 contomerTypeRelation.deleteByPrimaryKey(Long.valueOf(delList.get(i)),customer.getCompanyId());
                            }
                        }
                        if (addList!=null && addList.size()>0) {
                            for(int i=0;i<addList.size();i++) {
                                CustomerTypeRelation relationObj = new CustomerTypeRelation();
                                relationObj.setCustomerId(customer.getCustomerId());
                                relationObj.setCustomerName(customer.getCustomerName());
                                relationObj.setCustomerType(Short.valueOf(addList.get(i)));
                                relationObj.setCompanyId(customer.getCompanyId());
                                relationObj.setCreateId(customer.getCreateId());
                                relationObj.setCreateName(customer.getCreateName());
                                relationObj.setCreateDate(new Date());
                                contomerTypeRelation.insert(relationObj);

                            }
                        }

                    } else { //全部新增
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
            }
        }
        return flag;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerModify(Customer customer) {
        int flag = customerMapper.updateByPrimaryKey(customer);
          return flag;
    }


    @Transactional
    @Override
    public PageInfo customerContactList(Map m) {
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
        List<CustomerContact> list = customerContactMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return  pageInfo;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerCollectionBind(Customer customer) {
        int flag = customerMapper.updateByPrimaryKeySelective(customer);
        return flag;
    }





    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerContactUpdate(CustomerContact CustomerContact) {
        return customerContactMapper.updateByPrimaryKeySelective(CustomerContact);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerContact customerContactDetail(Long contactId, Long companyId) {
        Map map = new HashMap<String,Long>();
        map.put("contactId",contactId);
        map.put("companyId",companyId);
        return customerContactMapper.selectByPrimaryKey(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int oldCustomerContactIsNull(CustomerContact customerContact) {
        return customerContactMapper.oldCustomerContactIsNull(customerContact);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int customerContactRemove(Long contactId, Long companyId) {
        Map map = new HashMap<String,Long>();
        map.put("contactId",contactId);
        map.put("companyId",companyId);
        return customerContactMapper.deleteByPrimaryKey(map);
    }



    @Transactional
    @Override
    public PageInfo customerCollectionList(Map m) {
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
        List<CustomerCollection> list = customerCollectionMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return  pageInfo;
    }

    @Override
    public int customerCollectionAdd(CustomerCollection customerCollection) {
        Map map = new HashMap();
        map.put("companyId", customerCollection.getCompanyId());
        map.put("collectionName", customerCollection.getCollectionName());
        List<CustomerCollection> list = customerCollectionMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("组名已存在！");
        }
        int flag = customerCollectionMapper.insert(customerCollection);
        return flag;
    }

    @Override
    public int customerCollectionUpdate(CustomerCollection customerCollection) {
        Map map = new HashMap();
        map.put("companyId", customerCollection.getCompanyId());
        map.put("collectionName", customerCollection.getCollectionName());
        map.put("collectionId", customerCollection.getCollectionId());
        List<CustomerCollection> list = customerCollectionMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new CustomerException("组名已存在！");
        }
        int flag = customerCollectionMapper.updateByPrimaryKey(customerCollection);
        return flag;
    }

    @Override
    public int customerCollectionRemove(Map map) {
        return customerCollectionMapper.deleteByPrimaryKey(map);
    }

}
