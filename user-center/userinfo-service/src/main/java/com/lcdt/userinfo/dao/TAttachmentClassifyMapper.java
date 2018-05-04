package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.TAttachmentClassify;

import java.util.List;
import java.util.Map;

public interface TAttachmentClassifyMapper {
    int deleteByPrimaryKey(Integer tAttachmentClassifyId);

    int insert(TAttachmentClassify record);

    TAttachmentClassify selectByPrimaryKey(Integer tAttachmentClassifyId);

    List<TAttachmentClassify> selectAll(Map<String, Object> map);

    int updateByPrimaryKey(TAttachmentClassify record);
}