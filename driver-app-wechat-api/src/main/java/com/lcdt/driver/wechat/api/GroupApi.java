package com.lcdt.driver.wechat.api;

/**
 * Created by yangbinq on 2017/11/13.
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.GroupManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/9.
 */
@Api(value = "业务项目组", description = "业务项目组api")
@RestController
@RequestMapping("/api/group")
public class GroupApi {

    @Reference
    private GroupManageService groupManageService;
    /**
     * 项目组列表
     *
     * @return
     */
    @ApiOperation("项目组列表")
    @RequestMapping(value = "/userGroupList", method = RequestMethod.GET)
    public PageBaseDto<List<Group>> groupList() {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        List<Group> list= userCompRel.getGroups();
        PageBaseDto pageBaseDto = new PageBaseDto(list, list.size());
        return pageBaseDto;
    }
}
