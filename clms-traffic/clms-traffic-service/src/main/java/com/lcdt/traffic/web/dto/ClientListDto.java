package com.lcdt.traffic.web.dto;

import com.lcdt.client.model.MyClient;
import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.Department;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/21.
 */
public class ClientListDto implements ResponseData {

    private List<MyClient> list;

    private long total;

    public List<MyClient> getList() {
        return list;
    }

    public void setList(List<MyClient> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
