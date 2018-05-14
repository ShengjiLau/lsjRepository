package com.lcdt.contract.web.dto;

import com.lcdt.contract.model.PaApproval;
import com.lcdt.contract.model.PaymentApplication;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-05-10
 */
public class PaApprovalDto extends PaymentApplication {

    /**
     * 审批人列表
     */
    private List<PaApproval> paApprovalList;

    /**
     * 审批创建人
     */
    private String approvalCreateName;

    public List<PaApproval> getPaApprovalList() {
        return paApprovalList;
    }

    public void setPaApprovalList(List<PaApproval> paApprovalList) {
        this.paApprovalList = paApprovalList;
    }

    public String getApprovalCreateName() {
        return approvalCreateName;
    }

    public void setApprovalCreateName(String approvalCreateName) {
        this.approvalCreateName = approvalCreateName;
    }
}
