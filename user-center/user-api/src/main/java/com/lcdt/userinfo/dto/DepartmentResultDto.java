package com.lcdt.userinfo.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;

/**
 * Created by yangbinq on 2018/3/23.
 */
public class DepartmentResultDto implements ResponseData {

    private Long deptId; //部门ID
    private String deptName; //部门名称
    private String childIds; //子部门ID串
    private int childCount; //子部门数
    private int empCount;//企业员工数

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getChildIds() {
        return childIds;
    }

    public void setChildIds(String childIds) {
        this.childIds = childIds;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getEmpCount() {
        return empCount;
    }

    public void setEmpCount(int empCount) {
        this.empCount = empCount;
    }


}
