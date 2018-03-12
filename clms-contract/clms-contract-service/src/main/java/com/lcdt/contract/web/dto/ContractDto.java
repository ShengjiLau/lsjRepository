package com.lcdt.contract.web.dto;


import java.util.Date;

import com.lcdt.contract.model.Contract;

/**
 * @AUTHOR liuh
 * @DATE 2018-02-28
 */



import com.lcdt.contract.model.ContractApproval;
import com.lcdt.contract.model.ContractProduct;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liz on 2018/3/7.
 */
public class ContractDto extends Contract{

    @ApiModelProperty(value = "合同类型 0-采购 1-销售")
    private Short type;

    @ApiModelProperty(value = "合同状态 0-生效中 1-待生效 2-创建中 3-已失效")
    private Short contractStatus;

    private List<ContractApproval> contractApprovalList;

    private List<ContractProduct> contractProductList;

    private int tempStatus;

    private Long userId;

    private int pageNum;

    private int pageSize;

    @Override
    public Short getType() {
        return type;
    }

    @Override
    public void setType(Short type) {
        this.type = type;
    }

    @Override
    public Short getContractStatus() {
        return contractStatus;
    }

    @Override
    public void setContractStatus(Short contractStatus) {
        this.contractStatus = contractStatus;
    }

    public List<ContractApproval> getContractApprovalList() {
        return contractApprovalList;
    }

    public void setContractApprovalList(List<ContractApproval> contractApprovalList) {
        this.contractApprovalList = contractApprovalList;
    }

    public List<ContractProduct> getContractProductList() {
        return contractProductList;
    }

    public void setContractProductList(List<ContractProduct> contractProductList) {
        this.contractProductList = contractProductList;
    }

    public int getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(int tempStatus) {
        this.tempStatus = tempStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;

    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
