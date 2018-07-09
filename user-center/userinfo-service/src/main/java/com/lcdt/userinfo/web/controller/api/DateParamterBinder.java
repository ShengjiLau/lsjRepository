package com.lcdt.userinfo.web.controller.api;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

@ControllerAdvice
public class DateParamterBinder {

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isEmpty(text)) {
                    setValue(null);
                    return;
                }
                setValue(new Date(Long.valueOf(text)));
            }
        });
    }

}
