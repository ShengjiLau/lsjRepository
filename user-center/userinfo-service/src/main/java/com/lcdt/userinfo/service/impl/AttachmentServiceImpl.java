package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.TAttachmentClassifyMapper;
import com.lcdt.userinfo.dao.TAttachmentFileMapper;
import com.lcdt.userinfo.model.TAttachmentClassify;
import com.lcdt.userinfo.model.TAttachmentFile;
import com.lcdt.userinfo.service.AttachmentService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/3.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private TAttachmentClassifyMapper attachmentClassifyMapper;
    @Autowired
    private TAttachmentFileMapper attachmentFileMapper;

    @Override
    public PageInfo attachFileList() {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        PageHelper.startPage(pageNo, pageSize);
        List<TAttachmentFile> list = attachmentFileMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public boolean isRepeat(TAttachmentClassify attachmentClassify) {
        Map<String, Object> map = new HashedMap();

        map.put("tAttachmentClassifyName", attachmentClassify.gettAttachmentClassifyName());
        map.put("tAttachmentFileCode", attachmentClassify.gettAttachmentFileCode());
        map.put("companyId", attachmentClassify.getCompanyId());

        List list = attachmentClassifyMapper.selectAll(map);

        return list.size() > 0;
    }

    @Override
    public PageInfo attachmentClassifyList(Map<String, Object> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageNo").toString()), Integer.parseInt(map.get("pageSize").toString()));
        List<TAttachmentClassify> list = attachmentClassifyMapper.selectAll(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int addTAttachmentClassify(TAttachmentClassify attachmentClassify) {
        if(isRepeat(attachmentClassify)){
            return -1;
        }
        return attachmentClassifyMapper.insert(attachmentClassify);
    }

    @Override
    public int modifyTAttachmentClassify(TAttachmentClassify attachmentClassify) {
        if(isRepeat(attachmentClassify)){
            return -1;
        }
        return attachmentClassifyMapper.updateByPrimaryKey(attachmentClassify);
    }



    @Override
    public int modifyTAttachmentClassifyDelete(Integer attachmentClassifyId) {
        return attachmentClassifyMapper.deleteByPrimaryKey(attachmentClassifyId);
    }

    @Override
    public TAttachmentClassify queryTAttachmentClassify(Integer attachmentClassifyId) {
        return attachmentClassifyMapper.selectByPrimaryKey(attachmentClassifyId);
    }
}
