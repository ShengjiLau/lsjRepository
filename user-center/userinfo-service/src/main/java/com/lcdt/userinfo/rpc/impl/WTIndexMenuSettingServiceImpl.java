package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.WTIndexMenuSettingDao;
import com.lcdt.userinfo.dao.WTIndexMenuSettingMapper;
import com.lcdt.userinfo.dto.WTIndexMenuSettingListParams;
import com.lcdt.userinfo.dto.WTIndexMenuSettingModifyParams;
import com.lcdt.userinfo.model.WTIndexMenuSetting;
import com.lcdt.userinfo.rpc.WTIndexMenuSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 2018/8/6
 */
@Transactional
@Service
public class WTIndexMenuSettingServiceImpl implements WTIndexMenuSettingService {

    @Autowired
    private WTIndexMenuSettingMapper wtIndexMenuSettingMapper;


    @Override
    public int modifyWTIndexMenuSetting(WTIndexMenuSettingModifyParams params) {
        int result=0;
        WTIndexMenuSetting wtIndexMenuSetting=wtIndexMenuSettingMapper.selectMenuSettingById(params.getCompanyId(),params.getUserId(),params.getMenuId());
        if(wtIndexMenuSetting!=null){
            wtIndexMenuSetting.setMenuEnable(params.getMenuEnable());
            result+=wtIndexMenuSettingMapper.updateByPrimaryKey(wtIndexMenuSetting);
        }else{
            wtIndexMenuSetting=new WTIndexMenuSetting();
            wtIndexMenuSetting.setMenuEnable(params.getMenuEnable())
                    .setCompanyId(params.getCompanyId())
                    .setMenuId(params.getMenuId())
                    .setUserId(params.getUserId());
            result+=wtIndexMenuSettingMapper.insert(wtIndexMenuSetting);
        }
        return result;
    }

    @Override
    public PageInfo<WTIndexMenuSettingDao> queryWTIndexMenuSetting(WTIndexMenuSettingListParams params) {
        PageHelper.startPage(params.getPageNo(),params.getPageSize());
        return new PageInfo<>(wtIndexMenuSettingMapper.selectWTIndexMenuSetting(params));
    }
}
