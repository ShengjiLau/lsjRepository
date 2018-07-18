package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNotice;

/**
 * Created by Administrator on 2018/7/12.
 */

public class NoticeListDto extends TNotice {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
