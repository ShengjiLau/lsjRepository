package com.lcdt.userinfo.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.service.WarehouseService;
import com.lcdt.userinfo.web.dto.PageBaseDto;
import com.lcdt.userinfo.web.dto.WarehouseDto;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/10.
 */
@RestController
@RequestMapping("/api/warehouse")
@Api(value = "仓库api", description = "仓库操作")
public class WarehouseApi {

    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation("仓库列表")
    @RequestMapping(value = "/customerList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('customer_list')")
    public PageBaseDto customerList(@Validated WarehouseDto dto,
                                    @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                    @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);

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
