package com.lcdt.userinfo.registernotice;

import com.lcdt.userinfo.event.CreateCompanyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class CreateCompanyListener implements ApplicationListener<CreateCompanyEvent> {
    @Override
    public void onApplicationEvent(CreateCompanyEvent event) {

        Object source = event.getSource();
        if (source != null && source instanceof CreateCompanyEvent) {

        }


    }
}
