package com.lcdt.userinfo.web.controller.api;

import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "业务项目组", description = "业务项目组仓库关联api")
@RestController
@RequestMapping("/api/groupwarehouse")
public class GroupWareHouseApi {

    @Autowired
    TUserGroupWarehouseRelationMapper dao;

    /**
     * 已添加的仓库列表
     */
    @RequestMapping("/addedwarehouse")
    public void AddedWareHouse(){

    }


}
