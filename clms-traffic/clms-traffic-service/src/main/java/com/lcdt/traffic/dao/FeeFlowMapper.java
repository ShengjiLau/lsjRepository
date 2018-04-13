package com.lcdt.traffic.dao;

import com.lcdt.traffic.dto.FeeFlow4SearchParamsDto;
import com.lcdt.traffic.dto.FeeFlow4SearchResultDto;
import com.lcdt.traffic.dto.ReceivePayParamsDto;
import com.lcdt.traffic.model.FeeFlow;

import java.util.List;
import java.util.Map;

public interface FeeFlowMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(FeeFlow record);

    FeeFlow selectByPrimaryKey(Long flowId);

    List<FeeFlow> selectAll();

    int updateByPrimaryKey(FeeFlow record);

    List<FeeFlow> selectByAccountId(Long accountId);

    List<FeeFlow> selectByProId(Long proId);

    int insertBatch(List<FeeFlow> list);

    int updateBatch(List<FeeFlow> list);


    /***
     * 查询列表
     * @param dto
     * @return
     */
    List<FeeFlow4SearchResultDto> searchCondition(FeeFlow4SearchParamsDto dto);


    /***
     * 应收应付统计
     * @param dto
     */
    List<Map<String,Object>> receivePayStat(ReceivePayParamsDto dto);
}