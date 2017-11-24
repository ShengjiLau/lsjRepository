package com.lcdt.customer.web.dto;

import com.lcdt.converter.ResponseData;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class ClientListDto implements ResponseData {

    private List<Customer> list;

    private long total;

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
}
