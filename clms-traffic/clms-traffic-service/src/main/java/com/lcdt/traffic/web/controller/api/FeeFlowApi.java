package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.service.IFeeFlowService;
import com.lcdt.traffic.util.GroupIdsUtil;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.Group;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/4/12.
 */
@RestController
@RequestMapping("/fee/flow")
@Api(value = "记账流水",description = "记账流水")
public class FeeFlowApi {

    @Autowired
    private IFeeFlowService iFeeFlowService;

    @ApiOperation("应收/应付列表")
    @RequestMapping(value = "/search/list",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
    public PageBaseDto searchList(FeeFlow4SearchParamsDto dto) {
         StringBuffer sb = new StringBuffer();
         if (dto.getGroupId()>0) {//业务组
            dto.setGroupIds(dto.getGroupIds());
        } else {
            List<Group> groupList = SecurityInfoGetter.groups();
             for(int i=0;i<groupList.size();i++) {
                 Group group = groupList.get(i);
                 sb.append(group.getGroupId());
                 if(i!=groupList.size()-1){
                    sb.append(",");
                 }
            }
           dto.setGroupIds(sb.toString());
        }
        if (!StringUtils.isEmpty(dto.getCreateBegin())) {
             dto.setCreateBegin(dto.getCreateBegin()+" 00:00:00");
        }
        if (!StringUtils.isEmpty(dto.getCreateEnd())) {
            dto.setCreateEnd(dto.getCreateEnd()+" 23:59:59");
        }
        dto.setCompanyId(SecurityInfoGetter.getCompanyId());
        dto.setIsDeleted((short)0);
        PageInfo pg = iFeeFlowService.feeFlowList(dto);
        PageBaseDto pg_result = new PageBaseDto(pg.getList(), pg.getTotal());
        return pg_result;
    }








}
