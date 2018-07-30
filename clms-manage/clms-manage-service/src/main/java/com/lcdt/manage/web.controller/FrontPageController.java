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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2018/7/11.
 */
@Controller
@RequestMapping(value = "/front")
public class FrontPageController {
    private final Logger logger = LoggerFactory.getLogger(FrontPageController.class);
    private static final String INDEX_PAGE = "/index";

    private static final String NEWS_LIST_PAGE = "/news-list";
    private static final String NEWS_PAGE = "/news";
    private static final String PRICE_PAGE = "/price";
    private static final String ABOUT_PAGE = "/about";
    private static final String CLMS_PAGE = "/product/clms";
    private static final String COMS_PAGE = "/product/coms";
    private static final String CTMS_PAGE = "/product/ctms";
    private static final String CWMS_PAGE = "/product/cwms";
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
    @RequestMapping(value = INDEX_PAGE, method = RequestMethod.GET)
    public ModelAndView index() {
        logger.info("--------------index------------------");
        ModelAndView view = new ModelAndView(INDEX_PAGE);
        TNoticeCategory cate = new TNoticeCategory();
        cate = noticeCategoryService.findByName(NewsCategory);
        if (cate != null) {
            NoticeListParamsDto params = new NoticeListParamsDto();
            params.setPageNo(1).setPageSize(5);
            params.setCategoryId(cate.getCategoryId());
            params.setNoticeStatus(1);
            Page<NoticeListDto> page = noticeService.findTopNoticesByPage(params);
            TNotice topOne = page.getRecords().remove(0);
            List<NoticeListDto> notices = page.getRecords();
            view.addObject("notices", notices);
            view.addObject("topOne", topOne);
            view.addObject("active",1);
        }
        return view;
    }


    /**
     * 列表页
     *
     * @return
     */
    @RequestMapping(value = NEWS_LIST_PAGE, method = RequestMethod.GET)
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

            List<Map> pageNums = new ArrayList<Map>();
            //分页栏的起止页码
            int minPage=1,maxPage=1;
            if(page.getCurrent()<=3){
                minPage=1;
                maxPage=Math.min(page.getPages(),5);
            }
            else if(page.getCurrent()>page.getPages()-2){
                minPage = Math.max(page.getPages()-4,1);
                maxPage = page.getPages();
            }
            else{
                minPage = page.getCurrent()-2;
                maxPage = page.getCurrent()+2;
            }
            //因页面的未知原因，数组倒着拍才能正确输出
            Map next = new HashMap<>();
            next.put("num",Math.min(page.getCurrent()+1,page.getPages()));
            next.put("isPrevious",false);
            next.put("isNext",true);
            next.put("isCurrent",false);
            pageNums.add(next);

            for(int i=maxPage;i>=minPage;i--){
                Map m = new HashMap<>();
                m.put("num",i);
                m.put("isCurrent",(page.getCurrent()==i));
                m.put("isPrevious",false);
                m.put("isNext",false);
                pageNums.add(m);
            }

            Map previous = new HashMap<>();
            previous.put("isPrevious",true);
            previous.put("num",Math.max(page.getCurrent()-1,1));
            previous.put("isNext",false);
            previous.put("isCurrent",false);
            pageNums.add(previous);


            view.addObject("pageNums",pageNums);

        }
        view.addObject("active",3);
        return view;
    }

    /**
     * 新闻详情页
     *
     * @return
     */
    @RequestMapping(value = NEWS_PAGE, method = RequestMethod.GET)
    public ModelAndView news(Long id) {
        ModelAndView view = new ModelAndView(NEWS_PAGE);
        TNotice notice = noticeService.selectById(id);


        view.addObject("notice", notice);
        if (notice != null) {
            NoticeListParamsDto paramsDto = new NoticeListParamsDto();
            //BeanUtils.copyProperties(paramsDto,notice);
            paramsDto.setCategoryId(notice.getCategoryId());
            paramsDto.setNoticeId(notice.getNoticeId());

            NoticeListDto nextNotice = noticeService.findNoticeAndNextById(paramsDto);
            if (nextNotice != null) {
                logger.info("nextNotice.get(0).getNoticeTitle===" + nextNotice.getNoticeTitle());
                view.addObject("nextNotice", nextNotice);
            }
        }
        //导航菜单：3-资讯
        view.addObject("active",3);
        return view;
    }

    @RequestMapping(value = PRICE_PAGE, method = RequestMethod.GET)
    public ModelAndView price() {
        ModelAndView view = new ModelAndView(PRICE_PAGE);
        view.addObject("active",4);
        return view;
    }

    @RequestMapping(value = CLMS_PAGE, method = RequestMethod.GET)
    public ModelAndView clms() {
        ModelAndView view = new ModelAndView(CLMS_PAGE);
        view.addObject("active",2);
        return view;
    }
    @RequestMapping(value = COMS_PAGE, method = RequestMethod.GET)
    public ModelAndView coms() {
        ModelAndView view = new ModelAndView(COMS_PAGE);
        view.addObject("active",2);
        return view;
    }
    @RequestMapping(value = CTMS_PAGE, method = RequestMethod.GET)
    public ModelAndView ctms() {
        ModelAndView view = new ModelAndView(CTMS_PAGE);
        view.addObject("active",2);
        return view;
    }
    @RequestMapping(value = CWMS_PAGE, method = RequestMethod.GET)
    public ModelAndView cwms() {
        ModelAndView view = new ModelAndView(CWMS_PAGE);
        view.addObject("active",2);
        return view;
    }

    @RequestMapping(value = ABOUT_PAGE, method = RequestMethod.GET)
    public ModelAndView about() {
        ModelAndView view = new ModelAndView(ABOUT_PAGE);
        view.addObject("active",5);
        return view;
    }
}
