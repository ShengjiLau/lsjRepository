package com.lcdt.warehouse.service.impl;

import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.entity.Check;
import com.lcdt.warehouse.entity.CheckItem;
import com.lcdt.warehouse.mapper.CheckItemMapper;
import com.lcdt.warehouse.mapper.CheckMapper;
import com.lcdt.warehouse.service.CheckService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-16
 */
@Service
public class CheckServiceImpl extends ServiceImpl<CheckMapper, Check> implements CheckService {
    @Autowired
    private CheckMapper checkMapper;
    private CheckItemMapper checkItemMapper;
    @Override
    public List<CheckListDto> selectList(CheckParamDto paramDto) {
        //return checkMapper.selectList(paramDto);
        List<Check> checks = checkMapper.selectListByParams(paramDto);
        if (checks!=null&&checks.size()>0){
            for(Check ch:checks){
                List<CheckItem> items = checkItemMapper.selectByCheckId(ch.getCheckId());

            }
        }


        return null;
    }
}
