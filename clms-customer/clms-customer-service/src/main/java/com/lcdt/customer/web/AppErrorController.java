package com.lcdt.customer.web;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppErrorController implements ErrorController{

    private final static String ERROR_PATH = "/error";

    private Logger logger = LoggerFactory.getLogger(AppErrorController.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
        Map<String, Object> map = getAttributes(request, false);
        ModelAndView mav = null;

        Integer status = (Integer) map.get("status");
        if (status == 404){
            mav = new ModelAndView("error", map);
        } else if (status == 403){
            mav = new ModelAndView("error/403", map);
        } else if (status == 500){
            mav = new ModelAndView("error", map);
        } else {
            mav = new ModelAndView("error", map);
        }

        User user = SecurityInfoGetter.getUser();
        if (user != null) {
            mav.addObject("username", user.getRealName());
            mav.addObject("headimg", user.getPictureUrl());
        }
        return mav;
    }
    private Map<String, Object> getAttributes(HttpServletRequest request,
                                              boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes,includeStackTrace);
        String URL = request.getRequestURL().toString();
        map.put("URL", URL);
        logger.debug("AppErrorController.method [error info]: status-" + map.get("status") +", request url-" + URL);
        return map;
    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
