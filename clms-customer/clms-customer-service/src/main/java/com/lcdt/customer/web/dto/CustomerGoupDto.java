package com.lcdt.customer.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.customer.model.Customer;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/8.
 */
public class CustomerGoupDto  implements ResponseData  {


    private String collectionName;

    private Long collectionId;

    private List<CustomerDto> customerDtoList;


    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<CustomerDto> getCustomerDtoList() {
        return customerDtoList;
    }

    public void setCustomerDtoList(List<CustomerDto> customerDtoList) {
        this.customerDtoList = customerDtoList;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
}
