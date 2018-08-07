package com.lcdt.driver.wechat.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.TokenSecurityInfoGetter;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.dao.WTIndexMenuSettingDao;
import com.lcdt.userinfo.dto.WTIndexMenuSettingListParams;
import com.lcdt.userinfo.dto.WTIndexMenuSettingModifyParams;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.WTIndexMenuSettingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lyqishan on 2018/8/6
 */
@RestController
@RequestMapping("/api/menu/")
public class WTIndexMenuSettingApi {

    @Reference
    private WTIndexMenuSettingService wtIndexMenuSettingService;

    @ApiOperation("模块设置-菜单是否显示")
    @PutMapping(value = "/v1/setting")
    public JSONObject modifyMenuSetting(WTIndexMenuSettingModifyParams params){
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        params.setCompanyId(userCompRel.getCompany().getCompId())
                .setUserId(userCompRel.getUser().getUserId());
        JSONObject jsonObject=new JSONObject();
        if(wtIndexMenuSettingService.modifyWTIndexMenuSetting(params)>0){
            jsonObject.put("code",0);
            jsonObject.put("message","修改成功");
        }else{
            jsonObject.put("code",-1);
            jsonObject.put("message","修改失败");
        }
        return jsonObject;
    }

    @ApiOperation("首页菜单--列表")
    @GetMapping(value = "/v1/setting")
    public PageBaseDto<WTIndexMenuSettingDao> menuSettingList(WTIndexMenuSettingListParams params) {
        UserCompRel userCompRel = TokenSecurityInfoGetter.getUserCompRel();
        params.setCompanyId(userCompRel.getCompany().getCompId())
                .setUserId(userCompRel.getUser().getUserId());
        return new PageBaseDto(wtIndexMenuSettingService.queryWTIndexMenuSetting(params).getList());
    }
}
