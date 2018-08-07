package com.lcdt.userinfo.rpc;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.WTIndexMenuSettingDao;
import com.lcdt.userinfo.dto.WTIndexMenuSettingListParams;
import com.lcdt.userinfo.dto.WTIndexMenuSettingModifyParams;

/**
 * Created by lyqishan on 2018/8/6
 */

public interface WTIndexMenuSettingService {

    /**
     * 修改菜单设置
     * @param params
     * @return
     */
    int modifyWTIndexMenuSetting(WTIndexMenuSettingModifyParams params);

    /**
     * 分页查询菜单设置
     *
     * @param params
     * @return
     */
    PageInfo<WTIndexMenuSettingDao> queryWTIndexMenuSetting(WTIndexMenuSettingListParams params);
}
