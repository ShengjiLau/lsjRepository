package com.lcdt.traffic.web.dto;

/**
 * Created by yangbinq on 2017/12/16.
 */
public class PlanLeaveMsgParamsDto {

    private Long waybillPlanId; //计划ID

    private String leaveMsg; //留言内容

    private Long companyId; //登录企业ID

    private String companyName; //登录企业名称

    private Long userId; //登录用户ID

    private String realName; //登录用户名


    private Long createCompanyId; //创建企业ID


    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    public String getLeaveMsg() {
        return leaveMsg;
    }

    public void setLeaveMsg(String leaveMsg) {
        this.leaveMsg = leaveMsg;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(Long createCompanyId) {
        this.createCompanyId = createCompanyId;
    }
}

