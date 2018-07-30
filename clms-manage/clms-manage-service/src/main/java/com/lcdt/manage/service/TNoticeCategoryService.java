package com.lcdt.manage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.CategoryParamDto;
import com.lcdt.manage.entity.TNoticeCategory;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
public interface TNoticeCategoryService extends  IService<TNoticeCategory>{

    TNoticeCategory findByName(String name);

    boolean findExistTNoticeCategory(TNoticeCategory category);

    Page<TNoticeCategory> findCategoryPage(CategoryParamDto paramDto);

    List<TNoticeCategory> findCategoryAll();
}
