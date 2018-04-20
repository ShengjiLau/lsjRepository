package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "业务项目组", description = "业务项目组仓库关联api")
@RestController
@RequestMapping("/api/groupwarehouse")
public class GroupWareHouseApi {

    @Autowired
    TUserGroupWarehouseRelationMapper dao;

    @Autowired
    GroupWareHouseService service;

    /**
     * 已添加的仓库列表
     */
    @PostMapping("/addedwarehouse")
    public PageResultDto AddedWareHouse(Long groupId, Integer pageSize, Integer pageNo){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<TUserGroupWarehouseRelation> list = service.addedUserGroupWareHouse(companyId, groupId);
        return new PageResultDto(list);
    }

    @PostMapping("/notaddwarehouse")
    public PageResultDto<TUserGroupWarehouseRelation> notAddWareHouse(Long groupId, Integer pageSize, Integer pageNo) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<TUserGroupWarehouseRelation> list = service.notAddUserGroupWareHouse(companyId, groupId);
        return new PageResultDto(list);
    }

    @PostMapping("/addwareHouse")
    public TUserGroupWarehouseRelation addWareHouse(Long wareHouseId, Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        TUserGroupWarehouseRelation relation = service.addWareHouseRelation(groupId, companyId, wareHouseId);
        return relation;
    }

    @PostMapping("/removeWareHouseRelation")
    public String removeWareHouse(Long wareHouseId, Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "删除成功");
        jsonObject.put("code", 0);
        return jsonObject.toString();
    }

}
