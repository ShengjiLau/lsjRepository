package com.lcdt.traffic.web.controller.api;

/**
 * Created by zhao on 2018/8/9.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.model.TrafficLine;
import com.lcdt.traffic.service.TrafficLineService;
import com.lcdt.traffic.web.dto.PageBaseDto;
import com.lcdt.traffic.web.dto.TrafficLineDto;
import com.lcdt.traffic.web.dto.TrafficLineParamDto;
import com.lcdt.userinfo.model.UserCompRel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(description = "首页统计api")
@RestController
@RequestMapping("/setting/line")
public class TrafficLineApi {
    //Logger logger = LoggerFactory.getLogger(LineApi.class);
    @Autowired
    TrafficLineService lineService;

    @ApiOperation("线路列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('line_query')")
    public PageBaseDto lineList(@Validated TrafficLineParamDto dto) {
        PageInfo pageInfo = lineService.selectByPage(dto);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }

    @ApiOperation("计价规则-线路选择接口")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageBaseDto lineQuery(@Validated TrafficLineParamDto dto) {
        dto.setLineStatus(0);
        PageInfo pageInfo = lineService.selectByPage(dto);
        PageBaseDto dto1 = new PageBaseDto(pageInfo.getList(), pageInfo.getTotal());
        return dto1;
    }

    @ApiOperation("添加线路")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('line_add')")
    public JSONObject lineInsert(@Validated TrafficLineDto dto) {
        TrafficLine line = new TrafficLine();
        BeanUtils.copyProperties(dto, line);
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        line.setCreateName(userCompRel.getName());
        line.setCreateId(userCompRel.getUserId());
        line.setCompanyId(userCompRel.getCompId());
        line.setCreateDate(new Date());
        int result = lineService.insert(line);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "添加失败");
        }
        return jsonObject;
    }

    @ApiOperation("更新线路")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('line_modify')")
    public JSONObject lineUpdate(@Validated TrafficLineDto dto) {
        int result = 0;
        JSONObject jsonObject = new JSONObject();
        TrafficLine old = lineService.selectById(dto.getLineId());
        if (old == null) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "记录不存在");
            return jsonObject;
        }
        BeanUtils.copyProperties(dto, old);
        System.out.println("更新线路：" + old);
        result = lineService.update(old);
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "更新成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "更新失败");
        }
        return jsonObject;
    }

    @ApiOperation("删除线路")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('line_delete')")
    public JSONObject lineDelete(@Validated TrafficLineDto dto) {
        UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
        int result = 0;
        JSONObject jsonObject = new JSONObject();
        TrafficLine line = lineService.selectById(dto.getLineId());

        if (line == null || !userCompRel.getCompId().equals(line.getCompanyId())) {
            jsonObject.put("code", -1);
            jsonObject.put("message", "线路不存在");
            return jsonObject;
        }

        //TODO:校验是否有相关的计价规则


        //删除
        result = lineService.delete(dto.getLineId());

        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "删除失败");
        }
        return jsonObject;
    }

}
