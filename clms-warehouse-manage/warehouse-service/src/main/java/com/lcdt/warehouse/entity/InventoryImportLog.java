package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

public class InventoryImportLog implements Serializable {

    @TableId(value = "log_id",type = IdType.AUTO)
    private Long logId;

    private Date logTime;

    private Long logCompanyId;

    private Long logUserId;

    private Long logInventoryId;

    @TableField()
    private String logSn;

    public String getLogSn() {
        return logSn;
    }

    public void setLogSn(String logSn) {
        this.logSn = logSn;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Long getLogCompanyId() {
        return logCompanyId;
    }

    public void setLogCompanyId(Long logCompanyId) {
        this.logCompanyId = logCompanyId;
    }

    public Long getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Long logUserId) {
        this.logUserId = logUserId;
    }

    public Long getLogInventoryId() {
        return logInventoryId;
    }

    public void setLogInventoryId(Long logInventoryId) {
        this.logInventoryId = logInventoryId;
    }
}
