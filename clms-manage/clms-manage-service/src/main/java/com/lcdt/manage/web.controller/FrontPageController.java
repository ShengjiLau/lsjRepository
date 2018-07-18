package com.lcdt.manage.web.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.lcdt.manage.dto.NoticeListDto;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by xrr on 2018/7/11.
 */
@Controller
public class FrontPageController {
    private static String LOGIN_PAGE = "/index";
    private static String NEWS_LIST_PAGE = "/news-list";
    private static String NEWS_PAGE = "/news";
    private static final String NewsCategory = "新闻资讯";
    @Autowired
    private TNoticeService noticeService;
    @Autowired
    private TNoticeCategoryService noticeCategoryService;
    private TNotice notice;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(){
        System.out.println("--------------index------------------");
        ModelAndView view = new ModelAndView(LOGIN_PAGE);
        Logger logger = LoggerFactory.getLogger(FrontPageController.class);
        PageInfo<NoticeListDto> noticesPage = new PageInfo<NoticeListDto>();
        noticesPage.setPageSize(5);
        noticesPage.setPageNum(1);
        TNoticeCategory cate = new TNoticeCategory();
        cate = noticeCategoryService.findByName(NewsCategory);
        if(cate!=null) {
            TNotice notice = new TNotice();
            notice.setCategoryId(cate.getCategoryId());
            noticesPage = noticeService.findTopNoticesByPage(noticesPage,notice);
            System.out.println("noticesPage.getList:"+noticesPage.getList());
            TNotice topOne = noticesPage.getList().remove(0);
            List<NoticeListDto> notices = noticesPage.getList();
            view.addObject("notices",notices);
            view.addObject("topOne",topOne);
        }
        return view;
    }


    /**
     * 列表页
     *
     * @return
     */
    @RequestMapping(value = "/news-list",method = RequestMethod.GET)
    public ModelAndView newsList(Integer start){
        ModelAndView view = new ModelAndView(NEWS_LIST_PAGE);
        //view.addObject("info","这是咨询" );
        PageInfo<NoticeListDto> noticesPage = new PageInfo<NoticeListDto>();
        noticesPage.setPageSize(10);
        if(start!=null&&start>0) {
            noticesPage.setPageNum(start);
        }
        else{
            noticesPage.setPageNum(1);
        }
        TNoticeCategory cate = new TNoticeCategory();
        cate = noticeCategoryService.findByName(NewsCategory);
        if(cate!=null) {
            TNotice notice = new TNotice();
            notice.setCategoryId(cate.getCategoryId());
            noticesPage = noticeService.findTopNoticesByPage(noticesPage,notice);
            List<NoticeListDto> notices = noticesPage.getList();
            view.addObject("notices",notices);
        }
        return view;
    }
    /**
     * 新闻详情页
     *
     * @return
     */
    @RequestMapping(value = "/news",method = RequestMethod.GET)
    public ModelAndView news(Long id){
        ModelAndView view = new ModelAndView(NEWS_PAGE);
        TNotice notice = noticeService.selectById(id);
        System.out.println(notice.getNoticeTitle());

        view.addObject("notice",notice);
        if(notice!=null) {
            List<NoticeListDto> nextNotice = noticeService.findNoticeAndNextById(notice);
            if (nextNotice!=null&&nextNotice.size()>0) {
                System.out.println("nextNotice.get(0).getNoticeTitle==="+nextNotice.get(0).getNoticeTitle());
                view.addObject("nextNotice", nextNotice.get(0));
            }
        }
        return view;
    }


}
