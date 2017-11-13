package com.lcdt.userinfo.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.Department;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/9.
 */
public class DeparmentResultDto implements ResponseData {

    private List<Department> list;

    private long total;

    public List<Department> getList() {
        return list;
    }

    public void setList(List<Department> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
