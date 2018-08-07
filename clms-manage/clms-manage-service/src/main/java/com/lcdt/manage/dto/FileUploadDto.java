package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNoticeFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/3.
 */
public class FileUploadDto {
    private Long categoryId;
    private List<Map<String,Object>> files;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Map<String,Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String,Object>> files) {
        this.files = files;
    }


    @Override
    public String toString() {
        return "FileUploadDto{" +
                " categoryId=" + categoryId +
                ",files=" + files +
                "}";
    }
}
