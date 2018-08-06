package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNoticeFile;

/**
 * Created by Administrator on 2018/8/1.
 */
public class FileListDto extends TNoticeFile {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
