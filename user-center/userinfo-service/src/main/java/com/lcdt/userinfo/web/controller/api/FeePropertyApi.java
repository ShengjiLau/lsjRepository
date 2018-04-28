package com.lcdt.userinfo.web.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.FeeFlow;
import com.lcdt.traffic.service.FeePropertyRpcService;
import com.lcdt.userinfo.model.FeeProperty;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.FeePropertyService;
import com.lcdt.userinfo.web.dto.PageBaseDto;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tl.commons.util.StringUtility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/3/29.
 */
@RestController
@RequestMapping("/api/feeProperty")
@Api(value = "费用类型api", description = "费用类型操作")
public class FeePropertyApi {

    @Autowired
    private FeePropertyService feePropertyService;

    @Reference
    FeePropertyRpcService feePropertyRpcService;

    @ApiOperation("费用类型——列表")
    @RequestMapping(value = "/feePropertyList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_property_list')")
    public PageBaseDto feePropertyList(@Validated FeeProperty dto) {
        feePropertyService.initFeeProperty(SecurityInfoGetter.getUser(),SecurityInfoGetter.getCompanyId());
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("isDeleted", (short)0);

        if (dto.getType()!=null) {
            map.put("type",dto.getType());
        }
        if(dto.getIsReceivable() != null){
            map.put("isReceivable",dto.getIsReceivable());
        }
        if(dto.getIsShow() != null){
            map.put("isShow",dto.getIsShow());
        }
        if(dto.getName() != null){
            map.put("name",dto.getName());
        }
        PageInfo pageInfo = feePropertyService.feePropertyList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }

    @ApiOperation("费用类型——新增")
    @RequestMapping(value = "/addFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_property_add')")
    public JSONObject addFeeProperty(@Validated FeeProperty dto) {
        if(StringUtility.isNotEmpty(dto.getName()) && !"运费".equals(dto.getName())) {
            Long companyId = SecurityInfoGetter.getCompanyId();

            Map map = new HashMap();
            map.put("companyId", companyId);
            map.put("isDeleted", (short) 0);

            if (dto.getType() != null) {
                map.put("type", dto.getType());
            }
            if (dto.getIsReceivable() != null) {
                map.put("isReceivable", dto.getIsReceivable());
            }
            if (dto.getName() != null) {
                map.put("name", dto.getName());
            }
            PageInfo pageInfo = feePropertyService.feePropertyList(map);

            if (pageInfo.getTotal() > 0) {
                throw new RuntimeException("该所属款项下的费用类型已存在");
            } else {
                User user = SecurityInfoGetter.getUser();
                dto.setCompanyId(companyId);
                dto.setOperatorId(user.getUserId());
                dto.setOperatorName(user.getRealName());
                dto.setCreateDate(new Date());
                dto.setIsDeleted((short) 0);

                int result = feePropertyService.addFeeProperty(dto);
                if (result > 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", 0);
                    jsonObject.put("message", "添加成功");
                    return jsonObject;
                } else {
                    throw new RuntimeException("添加失败");
                }
            }
        }else{
            throw new RuntimeException("费用类型不能为‘运费’");
        }
    }

    @ApiOperation("费用类型——修改")
    @RequestMapping(value = "/modifyFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_property_modify')")
    public JSONObject modifyFeeProperty(@Validated FeeProperty dto) {
        if(StringUtility.isNotEmpty(dto.getName()) && !"运费".equals(dto.getName())) {
            Long companyId = SecurityInfoGetter.getCompanyId();
            Map map = new HashMap();
            map.put("companyId", companyId);
            map.put("isDeleted", (short)0);

            if (dto.getType()!=null) {
                map.put("type",dto.getType());
            }
            if (dto.getIsReceivable()!=null) {
                map.put("isReceivable",dto.getIsReceivable());
            }
            if (dto.getName()!=null) {
                map.put("name",dto.getName());
            }
            if(dto.getProId()!=null){
                map.put("proId",dto.getProId());
            }
            PageInfo pageInfo = feePropertyService.feePropertyList(map);

            if(pageInfo.getTotal() > 0){
                throw new RuntimeException("该所属款项下的费用类型已存在");
            }else {
                int result = feePropertyService.modifyFeeProperty(dto);
                if (result > 0) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", 0);
                    jsonObject.put("message", "修改成功");
                    return jsonObject;
                } else {
                    throw new RuntimeException("修改失败");
                }
            }
        }else{
            throw new RuntimeException("费用类型不能为‘运费’");
        }
    }

    @ApiOperation("费用类型——删除")
    @RequestMapping(value = "/deleteFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('fee_property_delete')")
    public JSONObject deleteFeeProperty(@ApiParam(value = "费用类型ID",required = true) @RequestParam Long proId) {
        List<FeeFlow> feeFlowList =  feePropertyRpcService.selectFlowsByProId(proId);
        if(feeFlowList!=null&&feeFlowList.size()>0)
        {
            throw new RuntimeException("此费用类型已经产生流水，不可删除");
        }
        else
        {
            //标记删除
            int result = feePropertyService.modifyFeePropertyIsDelete(proId);
            if(result==1)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 0);
                jsonObject.put("message", "删除成功");
                return jsonObject;
            }
            else {
                throw new RuntimeException("删除失败");
            }
        }
    }
}
