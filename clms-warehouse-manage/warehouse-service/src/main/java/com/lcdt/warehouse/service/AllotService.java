package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.plugins.Page;
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
    Page<AllotDto> allotDtoList(Map m);
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
     * 删除/取消仓库
     * @param allotId
     * @return
     */
    boolean modifyAllotIsDelete(Long allotId);
    /**
     * 获取调拨单详情
     * @param allotId
     * @return
     */
    AllotDto getAllotInfo(Long allotId);
    /**
     * 入库
     * @param dto
     * @return
     */
    boolean allotPutInStorage(AllotDto dto);
}
