package com.lcdt.manage.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.manage.dto.NoticeListDto;
import com.lcdt.manage.dto.NoticeListParamsDto;
import com.lcdt.manage.entity.TNotice;
import com.lcdt.manage.mapper.TNoticeMapper;
import com.lcdt.manage.service.TNoticeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
@Service
public class TNoticeServiceImpl extends ServiceImpl<TNoticeMapper, TNotice> implements TNoticeService {

    @Override
    public List<NoticeListDto> findAllNoticesByCateId(Long categoryId) {
        return baseMapper.findAllNoticesByCateId(categoryId);
    }

    @Override
    public NoticeListDto findNoticeAndNextById(NoticeListParamsDto params) {
        Page<NoticeListDto> page = new Page(1, 1);
        page.setRecords(baseMapper.findTopNoticesByPage(page, params));
        if(page.getRecords()!=null&&page.getRecords().size()>0)
            return page.getRecords().get(0);
        else
            return new NoticeListDto();
    }

    @Override
    public Page<NoticeListDto> findTopNoticesByPage(NoticeListParamsDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        System.out.println("params.getPageSize()"+params.getPageSize());
        return page.setRecords(baseMapper.findTopNoticesByPage(page, params));
    }


}
