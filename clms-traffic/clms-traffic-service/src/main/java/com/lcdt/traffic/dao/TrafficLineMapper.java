package com.lcdt.traffic.dao;


import com.lcdt.traffic.model.TrafficLine;
import com.lcdt.traffic.web.dto.TrafficLineParamDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-08-09
 */
public interface TrafficLineMapper {
    /**
     * 列表查询
     * @param dto
     * @return
     */
    List<TrafficLine> selectByPage(TrafficLineParamDto dto) ;

    /**
     * 新增
     * @param line
     * @return
     */
    int insertLine(TrafficLine line);
    /**
     * 更新
     * @param line
     * @return
     */
    int updateAllById(TrafficLine line);

    /**
     *删除
     * @param lineId
     * @return
     */
    int deleteById(Long lineId);

    TrafficLine selectById(Long lineId);
}
