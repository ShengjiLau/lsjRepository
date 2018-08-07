package com.lcdt.manage.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.CategoryParamDto;
import com.lcdt.manage.dto.FileParamsDto;
import com.lcdt.manage.entity.TNoticeFile;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-08-01
 */
public interface TNoticeFileMapper extends BaseMapper<TNoticeFile> {


    List findByPage(Page page, FileParamsDto params);
}
