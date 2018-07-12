package com.lcdt.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xrr on 2018/7/11.
 */
@Controller
public class FrontPageController {
    private static String LOGIN_PAGE = "/index";
    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView view = new ModelAndView(LOGIN_PAGE);
        view.addObject("info","这是咨询" );
        return view;
    }
}
