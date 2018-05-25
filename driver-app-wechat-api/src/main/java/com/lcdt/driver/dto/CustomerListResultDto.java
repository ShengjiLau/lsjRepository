package com.lcdt.driver.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.model.CustomerCollection;
import com.lcdt.customer.model.CustomerContact;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class CustomerListResultDto implements ResponseData {

    private List<Customer> list;

    private List<CustomerContact> customerContactList;

    private List<CustomerCollection> customerCollectionList;

    private long total;

    private String collectionIds;


    public List<Customer> getList() {
        return list;
    }

    public void setList(List<Customer> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<CustomerContact> getCustomerContactList() {
        return customerContactList;
    }

    public void setCustomerContactList(List<CustomerContact> customerContactList) {
        this.customerContactList = customerContactList;
    }

    public List<CustomerCollection> getCustomerCollectionList() {
        return customerCollectionList;
    }

    public void setCustomerCollectionList(List<CustomerCollection> customerCollectionList) {
        this.customerCollectionList = customerCollectionList;
    }

    public String getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(String collectionIds) {
        this.collectionIds = collectionIds;
    }
}


