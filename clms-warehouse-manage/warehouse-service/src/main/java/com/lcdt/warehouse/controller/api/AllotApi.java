package com.lcdt.warehouse.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.User;
import com.lcdt.util.ClmsBeanUtil;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.service.AllotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/5/8.
 */
@RestController
@RequestMapping("/api/allot")
@Api(value = "调拨api", description = "调拨操作")
public class AllotApi {
    @Autowired
    private AllotService allotService;

    @ApiOperation(value = "调拨单列表", notes = "调拨单列表数据")
    @GetMapping("/allotDtoList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_list')")
    public PageBaseDto allotDtoList(@Validated AllotDto dto,
                                    @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                    @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        dto.setCompanyId(companyId);

        Map map= ClmsBeanUtil.beanToMap(dto);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);

        PageInfo<List<AllotDto>> listPageInfo = allotService.allotDtoList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("新增")
    @RequestMapping(value = "/addAllot", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('allot_add')")
    public JSONObject addAllot(@Validated @RequestBody AllotDto dto) {
        User user = SecurityInfoGetter.getUser();
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setAllotStatus((short)0);//在途
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateTime(new Date());
        dto.setCompanyId(companyId);
        dto.setIsDeleted((short)0);

        boolean result = allotService.addAllot(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

}
