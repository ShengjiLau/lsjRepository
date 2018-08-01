package com.lcdt.manage.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.CategoryParamDto;
import com.lcdt.manage.entity.TNoticeCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
public interface TNoticeCategoryMapper extends BaseMapper<TNoticeCategory>{

    List<TNoticeCategory> findListByParam(Page page, CategoryParamDto params);

    List<TNoticeCategory> findAllExceptDelete();
}
