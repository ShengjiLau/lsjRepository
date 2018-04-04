package com.lcdt.contract.web.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-13
 */
public class ContractApprovalListDto implements Serializable{

    //合同主键
    private Long contractId;

    //审批人主键
    private Long caId;

    /**
     * 合同类型
     * 0 - 采购合同
     * 1 - 销售合同
     */
    private Short type;

    /**
     * 审批状态
     * 1 - 审批中      ==>    撤销
     * 2 - 审批完成    ==>    抄送
     * 3 - 已撤销      ==>    再次申请
     * 4 - 已驳回      ==>    再次申请
     */
    private Short approvalStatus;

    //审批状态内容
    private Long approvalStatusContent;

    //合同流水号
    private String contractCode;

    //合同标题
    private String title;

    //发起人姓名
    private String createUserName;

    private String currentUserName;

    //审批发起时间
    private Date approvalStartDate;

    //审批完成时间
    private Date approvalEndDate;

    /**
     * 用户id 当前用户id
     */
    private Long userId;

    //所属项目组
    private String groupId;

    //企业id
    private Long companyId;

    /**
     * tab筛选
     * 0 - 我的申请
     * 1 - 待我审批
     * 2 - 我已审批
     * 3 - 抄送
     *
     */

    private Short tempStatus;

    private int pageNum;

    private int pageSize;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getCaId() {
        return caId;
    }

    public void setCaId(Long caId) {
        this.caId = caId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getApprovalStatusContent() {
        return approvalStatusContent;
    }

    public void setApprovalStatusContent(Long approvalStatusContent) {
        this.approvalStatusContent = approvalStatusContent;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Date getApprovalStartDate() {
        return approvalStartDate;
    }

    public void setApprovalStartDate(Date approvalStartDate) {
        this.approvalStartDate = approvalStartDate;
    }

    public Date getApprovalEndDate() {
        return approvalEndDate;
    }

    public void setApprovalEndDate(Date approvalEndDate) {
        this.approvalEndDate = approvalEndDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
