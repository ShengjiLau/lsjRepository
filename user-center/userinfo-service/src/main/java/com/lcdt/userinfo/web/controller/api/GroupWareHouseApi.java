package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import com.lcdt.userinfo.web.dto.PageResultDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('group_warehouse') or hasRole('ROLE_SYS_ADMIN')")
    public PageResultDto AddedWareHouse(Long groupId, Integer pageSize, Integer pageNo){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<TUserGroupWarehouseRelation> list = service.addedUserGroupWareHouse(companyId, groupId);
        return new PageResultDto(list);
    }

    /**
     * 未添加的仓库列表
     */
    @PostMapping("/notaddwarehouse")
    @PreAuthorize("hasAnyAuthority('group_warehouse') or hasRole('ROLE_SYS_ADMIN')")
    public PageResultDto<TUserGroupWarehouseRelation> notAddWareHouse(Long groupId, Integer pageSize, Integer pageNo) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<TUserGroupWarehouseRelation> list = service.notAddUserGroupWareHouse(companyId, groupId);
        return new PageResultDto(list);
    }

    /**
     * 添加
     */
    @PostMapping("/addwareHouse")
    @PreAuthorize("hasAnyAuthority('group_warehouse') or hasRole('ROLE_SYS_ADMIN')")
    public TUserGroupWarehouseRelation addWareHouse(Long wareHouseId, Long groupId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        TUserGroupWarehouseRelation relation = service.addWareHouseRelation(groupId, userId, companyId, wareHouseId);
        return relation;
    }

    /**
     * 移除
     */
    @PostMapping("/removeWareHouseRelation")
    public String removeWareHouse(Long relationId) {
        service.removeWareHouseGroupRelation(relationId);
//        Long companyId = SecurityInfoGetter.getCompanyId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "删除成功");
        jsonObject.put("code", 0);
        return jsonObject.toString();
    }

}
