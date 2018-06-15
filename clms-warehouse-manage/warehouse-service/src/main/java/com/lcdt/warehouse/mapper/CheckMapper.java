package com.lcdt.warehouse.mapper;

import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.entity.TCheck;
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
public interface CheckMapper extends BaseMapper<TCheck> {

    List<CheckListDto> selectListByParams(CheckParamDto paramDto);


    int cancelCheck(CheckParamDto checkDto);


    boolean addCheck(TCheck check);

    int updateCheckBySql(TCheck check);
}
