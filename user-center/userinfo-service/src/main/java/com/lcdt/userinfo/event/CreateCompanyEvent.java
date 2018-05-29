package com.lcdt.userinfo.event;

import org.springframework.context.ApplicationEvent;

public class CreateCompanyEvent extends ApplicationEvent{
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CreateCompanyEvent(Object source) {
        super(source);
    }
}
