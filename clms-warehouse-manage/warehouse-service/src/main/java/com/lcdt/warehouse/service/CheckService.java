package com.lcdt.warehouse.service;

import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.entity.TCheck;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-16
 */
public interface CheckService extends IService<TCheck> {

    List<CheckListDto> selectList(CheckParamDto paramDto);

    int cancelCheck(CheckParamDto checkDto);

    boolean insertCheckAndItems(TCheck check, List<Map<String, Object>> items);

    boolean completeCheckAndItems(TCheck check, List<Map<String, Object>> itemList);
}
