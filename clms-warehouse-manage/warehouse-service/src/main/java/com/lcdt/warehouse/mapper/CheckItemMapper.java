package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lcdt.warehouse.entity.TCheckItem;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-17
 */
public interface CheckItemMapper extends BaseMapper<TCheckItem> {

    List<TCheckItem> selectByCheckId(Long checkId);
}
