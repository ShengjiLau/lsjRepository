package com.lcdt.userinfo.web.dto;

import com.lcdt.userinfo.model.Department;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by yangbinq on 2017/11/9.
 */
public class DepartmentDto {
    @ApiModelProperty(value = "部门名称",required = true)
    private String deptName;
    @ApiModelProperty(value = "部门父ID，父一级为0",required = true)
    private Long deptPid;



    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getDeptPid() {
        return deptPid;
    }

    public void setDeptPid(Long deptPid) {
        this.deptPid = deptPid;
    }

}
