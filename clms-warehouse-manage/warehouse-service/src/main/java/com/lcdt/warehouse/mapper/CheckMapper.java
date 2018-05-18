package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.entity.Check;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-16
 */
public interface CheckMapper extends BaseMapper<Check> {

    List<Check> selectListByParams(CheckParamDto paramDto);
}
