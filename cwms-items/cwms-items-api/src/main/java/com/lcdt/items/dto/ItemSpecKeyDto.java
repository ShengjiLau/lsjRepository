package com.lcdt.items.dto;


import com.lcdt.items.model.ItemSpecValue;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecKeyDto implements Serializable{

    private Long spkId;

    @NotEmpty(message = "规格名称spName不能为空")
    private String spName;

    private Long companyId;

    private Long createId;

    private String createName;

    private String itemSpecValueList;

    public Long getSpkId() {
        return spkId;
    }

    public void setSpkId(Long spkId) {
        this.spkId = spkId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getItemSpecValueList() {
        return itemSpecValueList;
    }

    public void setItemSpecValueList(String itemSpecValueList) {
        this.itemSpecValueList = itemSpecValueList;
    }
}
