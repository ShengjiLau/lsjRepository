package com.lcdt.items.dto;


import com.lcdt.items.model.ItemSpecValue;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecKeyDto {

    private Long spkId;

    @NotEmpty(message = "规格名称spName不能为空")
    private String spName;

    private Long companyId;

    private List<ItemSpecValue> itemSpecValueList;

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

    public List<ItemSpecValue> getItemSpecValueList() {
        return itemSpecValueList;
    }

    public void setItemSpecValueList(List<ItemSpecValue> itemSpecValueList) {
        this.itemSpecValueList = itemSpecValueList;
    }
}
