package com.lcdt.items.service;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

/**
 * Created by lyqishan on 2018/2/6
 */

public interface ItemsInfoInitializationService {
    int itemInfoInitialization(Long companyId,String userName,Long userId);
}
