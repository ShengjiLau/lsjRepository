package com.lcdt.traffic.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.model.TrafficLine;
import com.lcdt.traffic.web.dto.TrafficLineParamDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code generate
 * @since 2018-08-09
 */
public interface TrafficLineService {

    PageInfo selectByPage(TrafficLineParamDto dto);

    int insert(TrafficLine line);

    int update(TrafficLine line);

    int delete(Long lineId);

    TrafficLine selectById(Long lineId);
}
