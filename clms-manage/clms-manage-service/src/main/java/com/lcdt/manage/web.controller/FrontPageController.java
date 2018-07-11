package com.lcdt.manage.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xrr on 2018/7/11.
 */
@Controller
public class FrontPageController {
    private static String LOGIN_PAGE = "/index";
    /**
     * 首页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/",""})
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView(LOGIN_PAGE);
        return view;
    }
}
