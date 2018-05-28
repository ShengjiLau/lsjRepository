package com.lcdt.warehouse.service.impl;

import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.entity.Check;
import com.lcdt.warehouse.entity.CheckItem;
import com.lcdt.warehouse.mapper.CheckItemMapper;
import com.lcdt.warehouse.mapper.CheckMapper;
import com.lcdt.warehouse.service.CheckService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private CheckItemMapper checkItemMapper;
    @Override
    public List<CheckListDto> selectList(CheckParamDto paramDto) {
        //return checkMapper.selectList(paramDto);
        List<Check> checks = checkMapper.selectListByParams(paramDto);
        List<CheckListDto> resultList = new ArrayList<CheckListDto>();
        if (checks!=null&&checks.size()>0){
            for(Check ch:checks){
                CheckListDto checkRow = new CheckListDto();
                BeanUtils.copyProperties(ch,checkRow);
                List<CheckItem> items = checkItemMapper.selectByCheckId(ch.getCheckId());
                if(items!=null&&items.size()>0){
                    StringBuffer goodsInfos = new StringBuffer();
                    StringBuffer locations = new StringBuffer();
                    //与李明确认过：只显示3条，多余的用省略号；品名、数量、单位
                    for(int i=0;i<items.size()&&i<4;i++){
                        CheckItem item = items.get(i);
                        if(goodsInfos.length()>0){
                            goodsInfos.append("， ");
                        }
                        if(locations.length()>0){
                            locations.append("， ");
                        }
                        if (i<3) {
                            goodsInfos.append(item.getGoodsName()).append(" "+item.getInvertoryAmount()).append(item.getGoodsUnit());
                            locations.append(item.getStorageLocationCode());
                        }
                        else{
                            goodsInfos.append("...");
                            locations.append("...");
                        }
                    }
                    checkRow.setGoodsInfos(goodsInfos.toString());
                    checkRow.setLocations(locations.toString());
                }

                resultList.add(checkRow);
            }
        }


        return resultList;
    }

    public int cancelCheck(CheckParamDto checkDto){
        return checkMapper.cancelCheck(checkDto);

    }
}
