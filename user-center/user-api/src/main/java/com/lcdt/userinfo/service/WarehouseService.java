package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by yangbinq on 2018/1/10.
 */
public interface WarehouseService {

    /**
     * 仓库列表查询
     * @param m
     * @return
     */
    PageInfo warehouseList(Map m);
}
