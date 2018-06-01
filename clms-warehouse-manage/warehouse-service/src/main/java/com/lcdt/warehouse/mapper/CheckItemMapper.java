package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.entity.CheckItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-17
 */
public interface CheckItemMapper extends BaseMapper<CheckItem> {

    List<CheckItem> selectByCheckId(Long checkId);
}
