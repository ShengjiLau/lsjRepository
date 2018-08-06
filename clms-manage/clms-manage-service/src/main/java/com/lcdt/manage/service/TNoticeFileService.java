package com.lcdt.manage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.FileParamsDto;
import com.lcdt.manage.dto.FileListDto;
import com.lcdt.manage.entity.TNoticeFile;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-08-01
 */
public interface TNoticeFileService extends IService<TNoticeFile> {

    Page<FileListDto> findListByPage(FileParamsDto paramDto);
}
