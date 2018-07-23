package com.lcdt.manage.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.manage.dto.NoticeListDto;
import com.lcdt.manage.entity.TNotice;
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
public interface TNoticeMapper extends BaseMapper<TNotice>{

    List<NoticeListDto> findAllNoticesByCateId(Long categoryId);


    List<NoticeListDto> findTopNoticesByPage(Pagination page, TNotice p);
}
