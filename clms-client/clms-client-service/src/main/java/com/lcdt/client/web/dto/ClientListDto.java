package com.lcdt.client.web.dto;

import com.lcdt.client.model.Client;
import com.lcdt.converter.ResponseData;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class ClientListDto implements ResponseData {

    private List<Client> list;

    private long total;

    public List<Client> getList() {
        return list;
    }

    public void setList(List<Client> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
