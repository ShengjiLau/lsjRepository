package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

public class CostComputeSet implements Serializable{

    @TableId(value = "company_id",type = IdType.INPUT)
    private Long companyId;

    private String computeType;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getComputeType() {
        return computeType;
    }

    public void setComputeType(String computeType) {
        this.computeType = computeType;
    }

    @Override
    public String toString() {
        return "CostComputeSet{" +
                "companyId=" + companyId +
                ", computeType='" + computeType + '\'' +
                '}';
    }
}
