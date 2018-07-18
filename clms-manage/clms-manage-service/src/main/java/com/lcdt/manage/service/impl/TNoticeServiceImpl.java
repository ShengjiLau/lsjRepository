package com.lcdt.manage.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.manage.dto.NoticeListDto;
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
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
@Service
public class TNoticeServiceImpl extends ServiceImpl<TNoticeMapper , TNotice> implements TNoticeService {
    @Autowired
    private TNoticeMapper noticeMapper;

    @Override
    public List<NoticeListDto> findAllNoticesByCateId(Long categoryId) {

        List<NoticeListDto>  list = noticeMapper.findAllNoticesByCateId(categoryId);

        return list;
    }

    @Override
    public List<NoticeListDto> findNoticeAndNextById(TNotice currentNotice) {
        List<NoticeListDto> list = new ArrayList<>();
            TNotice p = new TNotice();
            PageHelper.startPage(1,1);
            list = noticeMapper.findTopNoticesByPage(currentNotice);
            return  list;
//        }
    }

    @Override
    public PageInfo<NoticeListDto> findTopNoticesByPage(PageInfo<NoticeListDto> page, TNotice p) {
        PageHelper.startPage(1,5);
        List<NoticeListDto> list = noticeMapper.findTopNoticesByPage(p);
        System.out.println("list.size="+list.size());
        PageInfo  page2 = new PageInfo(list);
        System.out.println("page.getList.size="+page2.getList().size());
        return page2;
    }
}
