package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-13
 */
public class PaApprovalListDto implements Serializable {

    /**
     * 付款单创建时间开始
     */
    private String paymentTimeStart;

    /**
     * 付款单创建时间结束
     */
    private String paymentTimeEnd;

    /**
     * 采购单流水号
     */
    private String orderSerialNo;

    /**
     * 采购单号
     */
    private String orderNo;

    /**
     * 采购合同号
     */
    private String contractCode;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 审批状态
     * 1 - 审批中      ==>    撤销
     * 2 - 审批完成    ==>    抄送
     * 3 - 已撤销      ==>    再次申请
     * 4 - 已驳回      ==>    再次申请
     */
    private Short approvalStatus;

    /**
     * 审批人主键
     */
    private Long paaId;


    /**
     * 审批状态内容
     */
    private Long approvalStatusContent;


    /**
     * 企业id
     */
    private Long companyId;

    private Long userId;

    /**
     * tab筛选
     * 0 - 我的申请
     * 1 - 待我审批
     * 2 - 我已审批
     * 3 - 抄送
     */
    private Short tempStatus;

    private int pageNum;

    private int pageSize;

    public String getPaymentTimeStart() {
        return paymentTimeStart;
    }

    public void setPaymentTimeStart(String paymentTimeStart) {
        this.paymentTimeStart = paymentTimeStart;
    }

    public String getPaymentTimeEnd() {
        return paymentTimeEnd;
    }

    public void setPaymentTimeEnd(String paymentTimeEnd) {
        this.paymentTimeEnd = paymentTimeEnd;
    }

    public String getOrderSerialNo() {
        return orderSerialNo;
    }

    public void setOrderSerialNo(String orderSerialNo) {
        this.orderSerialNo = orderSerialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getPaaId() {
        return paaId;
    }

    public void setPaaId(Long paaId) {
        this.paaId = paaId;
    }

    public Long getApprovalStatusContent() {
        return approvalStatusContent;
    }

    public void setApprovalStatusContent(Long approvalStatusContent) {
        this.approvalStatusContent = approvalStatusContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Short getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(Short tempStatus) {
        this.tempStatus = tempStatus;
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
