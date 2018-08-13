package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.TrafficLineMapper;
import com.lcdt.traffic.model.TrafficLine;
import com.lcdt.traffic.service.TrafficLineService;
import com.lcdt.traffic.web.dto.TrafficLineParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-08-09
 */
@Service
public class TrafficLineServiceImpl implements TrafficLineService {
    @Autowired
    TrafficLineMapper lineMapper;
    @Override
    public PageInfo selectByPage(TrafficLineParamDto dto) {
        PageInfo pageInfo = null;
        PageHelper.startPage(dto.getPageNo(), dto.getPageSize());
        List<TrafficLine> list = lineMapper.selectByPage(dto);
        pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int insert(TrafficLine line) {
        return   lineMapper.insertLine(line);
    }

    @Override
    public int update(TrafficLine line) {
      return   lineMapper.updateAllById(line);
    }

    @Override
    public int delete(Long lineId) {
        return lineMapper.deleteById(lineId);
    }

    @Override
    public TrafficLine selectById(Long lineId) {
        return lineMapper.selectById(lineId);
    }
}
