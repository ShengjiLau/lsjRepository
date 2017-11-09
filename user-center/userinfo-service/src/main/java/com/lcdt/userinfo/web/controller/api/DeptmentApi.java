package com.lcdt.userinfo.web.controller.api;

import com.lcdt.userinfo.service.DepartmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangbinq on 2017/11/9.
 */
@Api(value = "部门api",description = "部门相关api")
@RestController
@RequestMapping("/api/dept")
public class DeptmentApi {

    @Autowired
    private DepartmentService departmentService;

}
