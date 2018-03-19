package com.lcdt.driver.wechat.api;

import com.lcdt.userinfo.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangbinq on 2018/3/19.
 * Desciption: 运输入计划API
 */

@RestController
@RequestMapping("/plan")
public class PlanApi {

   private User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


}
