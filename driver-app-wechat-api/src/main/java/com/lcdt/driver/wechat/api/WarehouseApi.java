package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.driver.dto.WarehouseDto;
import com.lcdt.util.WebProduces;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tl.commons.util.StringUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyqishan on 2018/5/23
 */
@RestController
@RequestMapping("/api/warehouse")
@Api(value = "仓库api", description = "仓库操作")
public class WarehouseApi {
    @Reference
    private WarehouseRpcService warehouseService;

    @ApiOperation("仓库管理——列表")
    @RequestMapping(value = "/warehouseList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    public PageBaseDto warehouseList(@Validated WarehouseDto dto,
                                     @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                     @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

        if(StringUtility.isNotEmpty(dto.getWhName())){
            map.put("whName", dto.getWhName());
        }
        if (dto.getWhType()!=null) {
            map.put("whType",dto.getWhType());
        }
        if (dto.getWhStatus()!=null) {
            map.put("whStatus",dto.getWhStatus());
        }
        if (dto.getIsDeleted()!=null) {
            map.put("isDeleted",dto.getIsDeleted());
        }
        PageInfo pageInfo = warehouseService.warehouseList(map);
        PageBaseDto baseDto = new PageBaseDto();
        baseDto.setList(pageInfo.getList());
        baseDto.setTotal(pageInfo.getTotal());
        return baseDto;
    }
}
