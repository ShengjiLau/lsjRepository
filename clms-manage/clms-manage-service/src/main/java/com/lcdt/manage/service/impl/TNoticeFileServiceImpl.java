package com.lcdt.manage.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.FileParamsDto;
import com.lcdt.manage.dto.FileListDto;
import com.lcdt.manage.entity.TNoticeFile;
import com.lcdt.manage.mapper.TNoticeFileMapper;
import com.lcdt.manage.service.TNoticeFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-08-01
 */
@Service
public class TNoticeFileServiceImpl extends ServiceImpl<TNoticeFileMapper, TNoticeFile> implements TNoticeFileService {
    @Override
    public Page<FileListDto> findListByPage(FileParamsDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        return page.setRecords(baseMapper.findByPage(page, params));
    }
}
