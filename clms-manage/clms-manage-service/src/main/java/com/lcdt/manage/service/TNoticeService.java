package com.lcdt.manage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.lcdt.manage.dto.NoticeListDto;
import com.lcdt.manage.dto.NoticeListParamsDto;
import com.lcdt.manage.entity.TNotice;
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
public interface TNoticeService extends IService<TNotice>{

    List<NoticeListDto> findAllNoticesByCateId(Long categoryId);

    /**
     * 获取新闻详细信息
     * @param currentNotice
     * @return
     */
    NoticeListDto findNoticeAndNextById(TNotice currentNotice);

    /**
     * 分页查询新闻列表
     * @param params
     * @return
     */
    Page<NoticeListDto> findTopNoticesByPage(NoticeListParamsDto params);
}
