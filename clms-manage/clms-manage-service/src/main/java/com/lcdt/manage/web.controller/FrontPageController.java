package com.lcdt.manage.web.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.manage.dto.NoticeListDto;
import com.lcdt.manage.dto.NoticeListParamsDto;
import com.lcdt.manage.entity.TNotice;
import com.lcdt.manage.entity.TNoticeCategory;
import com.lcdt.manage.service.TNoticeCategoryService;
import com.lcdt.manage.service.TNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by xrr on 2018/7/11.
 */
@Controller
public class FrontPageController {
    private final Logger logger = LoggerFactory.getLogger(FrontPageController.class);

    private static String LOGIN_PAGE = "/index";
    private static String NEWS_LIST_PAGE = "/news-list";
    private static String NEWS_PAGE = "/news";
    private static final String NewsCategory = "新闻资讯";
    @Autowired
    private TNoticeService noticeService;
    @Autowired
    private TNoticeCategoryService noticeCategoryService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        logger.info("--------------index------------------");
        ModelAndView view = new ModelAndView(LOGIN_PAGE);
        TNoticeCategory cate = new TNoticeCategory();
        cate = noticeCategoryService.findByName(NewsCategory);
        if (cate != null) {
            NoticeListParamsDto params = new NoticeListParamsDto();
            params.setPageNo(1).setPageSize(5);
            params.setCategoryId(cate.getCategoryId());
            Page<NoticeListDto> page = noticeService.findTopNoticesByPage(params);
            TNotice topOne = page.getRecords().remove(0);
            List<NoticeListDto> notices = page.getRecords();
            view.addObject("notices", notices);
            view.addObject("topOne", topOne);
        }
        return view;
    }


    /**
     * 列表页
     *
     * @return
     */
    @RequestMapping(value = "/news-list", method = RequestMethod.GET)
    public ModelAndView newsList(Integer start) {
        ModelAndView view = new ModelAndView(NEWS_LIST_PAGE);
        //view.addObject("info","这是咨询" );
        NoticeListParamsDto params = new NoticeListParamsDto();
        params.setPageSize(10);

        if (start != null && start > 0) {
            params.setPageNo(start);
        } else {
            params.setPageNo(1);
        }
        TNoticeCategory cate = new TNoticeCategory();
        cate = noticeCategoryService.findByName(NewsCategory);
        if (cate != null) {
            params.setCategoryId(cate.getCategoryId());
            Page<NoticeListDto> page = noticeService.findTopNoticesByPage(params);
            List<NoticeListDto> notices = page.getRecords();
            view.addObject("notices", notices);
        }
        return view;
    }

    /**
     * 新闻详情页
     *
     * @return
     */
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView news(Long id) {
        ModelAndView view = new ModelAndView(NEWS_PAGE);
        TNotice notice = noticeService.selectById(id);
        logger.info(notice.getNoticeTitle());

        view.addObject("notice", notice);
        if (notice != null) {
            NoticeListDto nextNotice = noticeService.findNoticeAndNextById(notice);
            if (nextNotice != null) {
                logger.info("nextNotice.get(0).getNoticeTitle===" + nextNotice.getNoticeTitle());
                view.addObject("nextNotice", nextNotice);
            }
        }
        return view;
    }


}
