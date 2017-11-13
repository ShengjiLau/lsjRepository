package com.lcdt.userinfo.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.Group;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/9.
 */
public class GroupResultDto implements ResponseData {

    private List<Group> list;

    private long total;

    public List<Group> getList() {
        return list;
    }

    public void setList(List<Group> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
