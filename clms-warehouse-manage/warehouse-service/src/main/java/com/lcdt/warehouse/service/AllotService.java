package com.lcdt.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.dto.AllotDto;

import java.util.Map;

/**
 * Created by liz on 2018/5/8.
 */
public interface AllotService {
    /**
     * 调拨单列表查询
     * @param m
     * @return
     */
    PageInfo allotDtoList(Map m);
    /**
     * 新增调拨单
     * @param dto
     * @return
     */
    boolean addAllot(AllotDto dto);
    /**
     * 编辑调拨单
     * @param dto
     * @return
     */
    boolean modifyAllot(AllotDto dto);
    /**
     * 删除仓库
     * @param allotId
     * @return
     */
    int modifyAllotIsDelete(Long allotId);
    /**
     * 启用/禁用仓库
     * @param whId
     * @return
     */
    int modifyWarehouseWhStatus(Long whId);
}
