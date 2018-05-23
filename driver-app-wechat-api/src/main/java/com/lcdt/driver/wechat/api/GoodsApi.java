package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.util.ClmsBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2018/5/22
 */
@RestController
@RequestMapping("/goods")
@Api(value = "商品", description = "商品接口")
public class GoodsApi {

    @Reference
    private SubItemsInfoService subItemsInfoService;

    @ApiOperation("查询商品列表")
    @GetMapping("/v1/goodslist")
    public PageBaseDto<List<GoodsInfoDao>> queryGoodsList(GoodsListParamsDto params, @ApiParam(value = "页码", required = true) @RequestParam Integer pageNo,
                                                          @ApiParam(value = "每页显示条数", required = true) @RequestParam Integer pageSize){
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        Long companyId = userCompRel.getCompany().getCompId();
        Map map= ClmsBeanUtil.beanToMap(params);
        map.put("companyId",companyId);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        PageInfo<List<GoodsInfoDao>> listPageInfo=subItemsInfoService.queryByCondition(map);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }
}
