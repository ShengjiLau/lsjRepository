package com.lcdt.manage.dto;

import com.lcdt.manage.entity.TNoticeFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/3.
 */
public class FileUploadDto {
    private List<TNoticeFile> files;

    public List<TNoticeFile> getFiles() {
        return files;
    }

    public void setFiles(List<TNoticeFile> files) {
        this.files = files;
    }
}
