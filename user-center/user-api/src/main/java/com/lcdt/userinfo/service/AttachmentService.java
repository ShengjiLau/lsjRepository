package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.model.TAttachmentClassify;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/3.
 */
public interface AttachmentService {

    /**
     * 文件列表查询
     * @return
     */
    PageInfo attachFileList();

    /**
     * 附件分类列表查询
     * @return
     */
    PageInfo attachmentClassifyList(Map<String, Object> map);

    /**
     * 新增附件分类
     * @param  attachmentClassify
     * @return
     */
    int addTAttachmentClassify(TAttachmentClassify attachmentClassify);
    /**
     * 编辑附件分类
     * @param  attachmentClassify
     * @return
     */
    int modifyTAttachmentClassify(TAttachmentClassify attachmentClassify);
    /**
     * 删除附件分类
     * @param attachmentClassifyId
     * @return
     */
    int modifyTAttachmentClassifyDelete(Integer attachmentClassifyId);

    /**
     * 是否有重复名称的附件分类
     * @return
     */
    boolean isRepeat(TAttachmentClassify attachmentClassify);

    TAttachmentClassify queryTAttachmentClassify(Integer attachmentClassifyId);
}
