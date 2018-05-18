package com.lcdt.warehouse.service;

import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.entity.Check;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-16
 */
public interface CheckService extends IService<Check> {

    List<CheckListDto> selectList(CheckParamDto paramDto);
}
