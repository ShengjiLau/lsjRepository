package com.lcdt.userinfo.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.customer.model.Customer;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;

import java.util.List;

/**
 * Created by yangbinq on 2017/11/9.
 */
public class GroupResultDto implements ResponseData {

    private List<Group> list;
    private List<User>  userList;
    private List<Customer> customerList;

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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
