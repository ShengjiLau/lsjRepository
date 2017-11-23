package com.lcdt.items.dto;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */

public class ItemSpecKeyDto {
    private Long spkId;
    private String spName;
    private Long companyId;
    List<ItemSpecValueDto> itemSpecValueDtoList;

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

    public List<ItemSpecValueDto> getItemSpecValueDtoList() {
        return itemSpecValueDtoList;
    }

    public void setItemSpecValueDtoList(List<ItemSpecValueDto> itemSpecValueDtoList) {
        this.itemSpecValueDtoList = itemSpecValueDtoList;
    }
}
