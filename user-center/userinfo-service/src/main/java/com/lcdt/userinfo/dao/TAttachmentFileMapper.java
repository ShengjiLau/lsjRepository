package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.TAttachmentFile;

import java.util.List;

public interface TAttachmentFileMapper {
    int deleteByPrimaryKey(Integer tAttachmentFileId);

    int insert(TAttachmentFile record);

    TAttachmentFile selectByPrimaryKey(Integer tAttachmentFileId);

    List<TAttachmentFile> selectAll();

    int updateByPrimaryKey(TAttachmentFile record);
}