package com.lcdt.manage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.lcdt.manage.dto.NoticeListDto;
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
    List<NoticeListDto> findNoticeAndNextById(TNotice currentNotice);
    PageInfo<NoticeListDto> findTopNoticesByPage(PageInfo<NoticeListDto> page, TNotice p);


}
