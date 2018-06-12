package com.lcdt.userinfo.web.controller.api.admin;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice(basePackages = {"com.lcdt.userinfo.web.controller.api.admin"})
public class InitBinderControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
//                super.setAsText(text);
                setValue(new Date(Long.valueOf(text)));
            }
        });
    }

}
