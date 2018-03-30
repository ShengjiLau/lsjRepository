package com.lcdt.traffic.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.traffic.dto.WaybillOwnListParamsDto;
import com.lcdt.traffic.model.FeeAccount;
import com.lcdt.traffic.service.FeeAccountService;
import com.lcdt.traffic.web.dto.PageBaseDto;
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

/**
 * Created by liz on 2018/3/29.
 */
@RestController
@RequestMapping("/api/waybillFee")
@Api(value = "记账api", description = "记账操作")
public class FeeAccountApi {
    @Autowired
    private FeeAccountService feeAccountService;

    @ApiOperation("记账——列表")
    @RequestMapping(value = "/waybillFeeList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('waybill_fee_list')")
    public PageBaseDto feePropertyList(@Validated WaybillOwnListParamsDto dto) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        dto.setCompanyId(companyId);
//        dto.setIsDelete((short) 0);
//        dto.setGroupIds(GroupIdsUtil.getOwnGroupIds(dto.getGroupId()));
//        PageInfo<List<Waybill>> listPageInfo = waybillRpcService.queryOwnWaybillList(dto);
//        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
//        return pageBaseDto;
//
//
//
//
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        Map map = new HashMap();
//        map.put("companyId", companyId);
//        map.put("isDeleted", (short)0);
//
//        if (dto.getType()!=null) {
//            map.put("type",dto.getType());
//        }
//        PageInfo pageInfo = feePropertyService.feePropertyList(map);
//        PageBaseDto baseDto = new PageBaseDto();
//        baseDto.setList(pageInfo.getList());
//        baseDto.setTotal(pageInfo.getTotal());
//        return baseDto;
        return null;
    }

    @ApiOperation("记账——新增")
    @RequestMapping(value = "/addFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_fee_property')")
    public JSONObject addFeeProperty(@Validated FeeAccount dto) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//
//        Map map = new HashMap();
//        map.put("companyId", companyId);
//        map.put("isDeleted", (short)0);
//
//        if (dto.getType()!=null) {
//            map.put("type",dto.getType());
//        }
//        if (dto.getIsReceivable()!=null) {
//            map.put("isReceivable",dto.getIsReceivable());
//        }
//        if (dto.getName()!=null) {
//            map.put("name",dto.getName());
//        }
//        PageInfo pageInfo = feePropertyService.feePropertyList(map);
//
//        if(pageInfo.getTotal() > 0){
//            throw new RuntimeException("该所属款项下的费用类型已存在");
//        }else {
//            User user = SecurityInfoGetter.getUser();
//            dto.setCompanyId(companyId);
//            dto.setOperatorId(user.getUserId());
//            dto.setOperatorName(user.getRealName());
//            dto.setCreateDate(new Date());
//            dto.setIsDeleted((short) 0);
//
//            int result = feePropertyService.addFeeProperty(dto);
//            if (result > 0) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code", 0);
//                jsonObject.put("message", "添加成功");
//                return jsonObject;
//            } else {
//                throw new RuntimeException("添加失败");
//            }
//        }
                throw new RuntimeException("添加失败");
    }

    @ApiOperation("记账——修改")
    @RequestMapping(value = "/modifyFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_fee_property')")
    public JSONObject modifyFeeProperty(@Validated FeeAccount dto) {
//        Long companyId = SecurityInfoGetter.getCompanyId();
//        Map map = new HashMap();
//        map.put("companyId", companyId);
//        map.put("isDeleted", (short)0);
//
//        if (dto.getType()!=null) {
//            map.put("type",dto.getType());
//        }
//        if (dto.getIsReceivable()!=null) {
//            map.put("isReceivable",dto.getIsReceivable());
//        }
//        if (dto.getName()!=null) {
//            map.put("name",dto.getName());
//        }
//        if(dto.getProId()!=null){
//            map.put("proId",dto.getProId());
//        }
//        PageInfo pageInfo = feePropertyService.feePropertyList(map);
//
//        if(pageInfo.getTotal() > 0){
//            throw new RuntimeException("该所属款项下的费用类型已存在");
//        }else {
//            int result = feePropertyService.modifyFeeProperty(dto);
//            if (result > 0) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("code", 0);
//                jsonObject.put("message", "修改成功");
//                return jsonObject;
//            } else {
//                throw new RuntimeException("修改失败");
//            }
//        }
                throw new RuntimeException("添加失败");
    }

    @ApiOperation("记账——删除")
    @RequestMapping(value = "/deleteFeeProperty", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('delete_fee_property')")
    public JSONObject deleteFeeProperty(@ApiParam(value = "费用类型ID",required = true) @RequestParam Long proId) {
//        int result = feePropertyService.modifyFeePropertyIsDelete(proId);
//        if (result > 0) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", 0);
//            jsonObject.put("message", "删除成功");
//            return jsonObject;
//        } else {
//            throw new RuntimeException("删除失败");
//        }

                throw new RuntimeException("添加失败");
    }
}
