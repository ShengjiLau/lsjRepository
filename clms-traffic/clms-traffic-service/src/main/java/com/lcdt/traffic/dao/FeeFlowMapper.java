package com.lcdt.traffic.dao;

import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.dto.FeeFlow4SearchResultDto;
import com.lcdt.traffic.model.FeeFlow;

import java.util.List;

public interface FeeFlowMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(FeeFlow record);

    FeeFlow selectByPrimaryKey(Long flowId);

    List<FeeFlow> selectAll();

    int updateByPrimaryKey(FeeFlow record);

    List<FeeFlow> selectByAccountId(Long accountId);

    List<FeeFlow> selectByProId(Long proId);


    /***
     * 查询列表
     * @param dto
     * @return
     */
    List<FeeFlow4SearchResultDto> searchCondition(FeeFlow4SearchParamsDto dto);
}